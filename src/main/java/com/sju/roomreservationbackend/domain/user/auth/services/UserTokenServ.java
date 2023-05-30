package com.sju.roomreservationbackend.domain.user.auth.services;

import com.sju.roomreservationbackend.common.security.JWTTokenProvider;
import com.sju.roomreservationbackend.domain.user.auth.entity.RefreshToken;
import com.sju.roomreservationbackend.domain.user.auth.repository.RefreshTokenRepo;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserTokenServ {
    private final JWTTokenProvider tokenProvider;
    private final RefreshTokenRepo refreshTokenRepo;

    public RefreshToken fetchRefreshToken(UserProfile profile, String ipAddress) {
        // 기존에 유효한 리프레시 토큰이 있는지 확인
        // 유효함의 기준: username이 동일하고, 로그아웃되지 않았으며, 만료일이 미래인 토큰
        RefreshToken activeRefreshToken = fetchActiveRefreshToken(profile);

        if (activeRefreshToken != null) {
            if (Objects.equals(activeRefreshToken.getIpAddress(), ipAddress)) {
                // 기존 리프레시 토큰이 있으며 같은 기기인 경우 같은 토큰 반환
                return activeRefreshToken;
            } else {
                // 기존 리프레시 토큰이 있으나 다른 기기에서 사용중인 경우 기존 토큰을 무효화 시킨 후 신규 발급하여 반환
                activeRefreshToken.blockTokenForLogout();
                return issueRefreshToken(profile, ipAddress);
            }
        } else {
            // 기존 리프레시 토큰이 없는 경우 신규 발급하여 반환
            return issueRefreshToken(profile, ipAddress);
        }
    }

    public RefreshToken fetchActiveRefreshToken(UserProfile profile) {
        return refreshTokenRepo.findByUsernameAndLoggedOutAndExpiredAtGreaterThan(
                profile.getUsername(), false, LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        ).orElse(null);
    }

    // 리프레시 토큰 발급
    private RefreshToken issueRefreshToken(UserProfile profile, String ipAddress) {
        RefreshToken refreshToken = new RefreshToken(
                tokenProvider.createRefreshToken(profile.getUsername(), profile.getPermissions()),
                profile.getUsername(),
                ipAddress
        );

        return refreshTokenRepo.save(refreshToken);
    }

    // 엑세스 토큰 발급
    public String issueAccessToken(UserProfile profile) {
        return tokenProvider.createAccessToken(profile.getUsername(), profile.getPermissions());
    }
}
