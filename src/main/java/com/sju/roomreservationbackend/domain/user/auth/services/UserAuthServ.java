package com.sju.roomreservationbackend.domain.user.auth.services;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.user.auth.entity.RefreshToken;
import com.sju.roomreservationbackend.domain.user.auth.repository.RefreshTokenRepo;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.domain.user.profile.repository.UserProfileRepo;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileServCommon;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

@Service
@AllArgsConstructor
public class UserAuthServ implements UserDetailsService, UserProfileServCommon {
    private final UserProfileRepo userProfileRepo;
    private final RefreshTokenRepo refreshTokenRepo;
    private final PasswordEncoder pwEncoder;
    private final MessageSource msgSrc = MessageConfig.getUserMsgSrc();

    @Override
    // 스프링 시큐리티 유저 권한 정보 로드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userProfileRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        msgSrc.getMessage("error.user.notExist", null, Locale.ENGLISH))
                );
    }

    // 유저네임과 비밀번호로 스프링 시큐리티 인증
    public UserProfile loginByCredential(String username, String password) {
        // 아이디 확인
        UserProfile profile = userProfileRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // 비밀번호 확인
        if(!pwEncoder.matches(password, profile.getPassword())) {
            throw new BadCredentialsException(msgSrc.getMessage("error.login.fail", null, Locale.ENGLISH));
        }

        // 마지막 로그인 일자 갱신
        profile.setLastLoginAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        return profile;
    }

    @Transactional
    public UserProfile logout(Authentication auth) {
        UserProfile profile = fetchCurrentUser(auth);

        // 기존에 유효한 리프레시 토큰이 있는지 확인
        // 유효함의 기준: username이 동일하고, 로그아웃되지 않았으며, 만료일이 미래인 토큰
        RefreshToken activeRefreshToken = refreshTokenRepo.findByUsernameAndLoggedOutAndExpiredAtGreaterThan(
                profile.getUsername(), false, LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        ).orElse(null);

        // 로그아웃 실시
        if (activeRefreshToken != null) {
            activeRefreshToken.blockTokenForLogout();
        }
        return profile;
    }

    @Transactional
    public void disposeExpiredRefreshToken(String tokenStr) {
        // 토큰 스트링으로 해당 토큰 찾기
        RefreshToken activeRefreshToken = refreshTokenRepo.findByRefreshToken(tokenStr).orElse(null);
        // 로그아웃 실시
        if (activeRefreshToken != null) {
            activeRefreshToken.blockTokenForLogout();
        }
    }
}
