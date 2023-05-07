package com.sju.roomreservationbackend.common.security;

import com.sju.roomreservationbackend.domain.user.auth.services.UserAuthServ;
import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
/* JWT 토큰 필터를 구현하는 클래스 */
public class JWTFilter extends OncePerRequestFilter {
    private final JWTTokenProvider jwtTokenProvider;
    private final UserAuthServ userAuthServ;


    // 의존성 주입용 생성자
    public JWTFilter(UserAuthServ userAuthServ, JWTTokenProvider jwtTokenProvider) {
        this.userAuthServ = userAuthServ;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // request 헤더에서 Authorization 필드를 검출
        final String requestAccessTokenHeader = request.getHeader("Authorization");
        final String requestRefreshTokenHeader = request.getHeader("Refresh");

        String username = null;
        String accessJwtToken = null;
        String refreshJwtToken = null;

        if (requestAccessTokenHeader != null) {
            // 헤더에서 억세스 토큰 검출
            accessJwtToken = requestAccessTokenHeader.substring(7);
            try {
                username = jwtTokenProvider.getUsernameFromToken(accessJwtToken);
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        } else {
            logger.warn("User requested Ajax but Authorization Header is null");
        }
        if (requestRefreshTokenHeader != null) {
            // 헤더에서 리프레시 토큰 검출
            refreshJwtToken = requestRefreshTokenHeader.substring(7);
            try {
                username = jwtTokenProvider.getUsernameFromToken(refreshJwtToken);
            } catch (Exception e) {
                userAuthServ.disposeExpiredRefreshToken(refreshJwtToken);
                logger.warn(e.getMessage());
            }
        }

        // 억세스 토큰이 유효하다면, 스프링시큐리티 컨텍스트에 인증 정보 로드
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userAuthServ.loadUserByUsername(username);
                // 미승인/정지 유저인지 확인
                if (!userDetails.isAccountNonLocked()) {
                    throw new LockedException("Account not verified");
                }
                try {
                    if (jwtTokenProvider.validateToken(accessJwtToken, userDetails)) {
                        setAuthentication(userDetails, request);
                    }
                } catch (Exception ignored) {
                    if (refreshJwtToken != null) {
                        // 억세스 토큰 유효성 검사 실패 시 리프레시 토큰 사용해서 억세스 토큰 재발급 시도
                        if (jwtTokenProvider.refreshTokenValidation(refreshJwtToken, userDetails)) {
                            // 리프레시 토큰으로 아이디 정보 가져오기
                            String loginId = jwtTokenProvider.getUsernameFromToken(refreshJwtToken);
                            UserProfile userProfile = (UserProfile) userDetails;

                            // 새로운 억세스 토큰 발급
                            String newAccessToken = jwtTokenProvider.createAccessToken(loginId, userProfile.getPermissions());
                            response.setHeader("New-Access-Token", newAccessToken);
                            // Security context에 인증 정보 넣기
                            setAuthentication(userDetails, request);
                        } else {
                            // 리프레시 토큰이 만료 || 리프레시 토큰이 DB와 비교했을때 똑같지 않다면
                            userAuthServ.disposeExpiredRefreshToken(refreshJwtToken);
                            throw new SecurityException("Login failed: All Tokens are not valid");
                        }
                    }
                }
            } catch (UsernameNotFoundException e) {
                logger.warn("Login failed: User Not Found");
            } catch (LockedException e) {
                logger.warn("Login failed: User Not Verified");
            } catch (SecurityException e) {
                logger.warn("Login failed: Both Token Damaged or Expired");
            } catch (Exception e) {
                logger.warn("Login failed: " + e.getLocalizedMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    // SecurityContext 에 Authentication 객체를 저장합니다.
    public void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    // 검증하지 않을 예외 조건
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return JWTFilterRules.getExcludesByRequest(request);
    }
}
