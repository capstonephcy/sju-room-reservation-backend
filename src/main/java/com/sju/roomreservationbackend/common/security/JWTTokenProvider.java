package com.sju.roomreservationbackend.common.security;


import com.google.gson.Gson;
import com.sju.roomreservationbackend.domain.user.auth.entity.RefreshToken;
import com.sju.roomreservationbackend.domain.user.auth.repository.RefreshTokenRepo;
import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
/* JWT 토큰을 발행하고 관리하는 클래스 */
public class JWTTokenProvider {
    private final RefreshTokenRepo refreshTokenRepo;

    // 토큰 생성용 난수 시드값
    public final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // JSON String 변환기
    private final Gson gson = new Gson();


    // 리프레쉬 토큰 유효기간 (한 달)
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 30 * 24 * 60 * 60;

    // 인증 토큰 유효기간 (5분)
    public static final long JWT_ACCESS_TOKEN_VALIDITY = 5 * 60;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String createAccessToken(String id, List<Permission> roles) {
        // JWT payload 에 저장되는 정보단위, 보통 여기서 user 를 식별하는 값을 넣는다 (claim 의 주체)
        Claims claims = Jwts.claims().setSubject(id);
        // 권한 정보 삽입
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY * 1000))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String id, List<Permission> roles) {
        // JWT payload 에 저장되는 정보단위, 보통 여기서 user 를 식별하는 값을 넣는다 (claim 의 주체)
        Claims claims = Jwts.claims().setSubject(id);
        // 권한 정보 삽입
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        UserProfile profile = (UserProfile) userDetails;
        return (username.equals(profile.getUsername())) && !isTokenExpired(token);
    }

    // refreshToken 토큰 검증
    // db에 저장되어 있는 token과 비교
    public Boolean refreshTokenValidation(String refreshTokenStr, UserDetails userDetails) {
        UserProfile profile = (UserProfile) userDetails;

        // 1차 토큰 검증
        if(!validateToken(refreshTokenStr, profile)) return false;

        // DB에 저장한 토큰 비교
        RefreshToken refreshToken = refreshTokenRepo.findByUsernameAndLoggedOutAndExpiredAtGreaterThan(
                profile.getUsername(), false, LocalDateTime.now()
        ).orElse(null);

        // 리프레시 토큰 존재 여부 & 로그아웃 여부 & 토큰값 일치 검사
        return refreshToken != null && !refreshToken.getLoggedOut() && refreshToken.getRefreshToken().equals(refreshTokenStr);
    }
}
