package com.sju.roomreservationbackend.domain.user.auth.entity;

import com.sju.roomreservationbackend.common.security.JWTTokenProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {
    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "valid.user.refresh.token.blank")
    private String refreshToken;

    @Column
    @NotBlank(message = "valid.user.username.blank")
    private String username;

    @Column
    private String ipAddress;

    @Column
    private LocalDateTime issuedAt;

    @Column
    private LocalDateTime expiredAt;

    @Column
    private Boolean loggedOut;


    public RefreshToken(String token, String username, String ipAddress) {
        this.refreshToken = token;
        this.username = username;
        this.ipAddress = ipAddress;
        this.issuedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.expiredAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusSeconds(JWTTokenProvider.JWT_REFRESH_TOKEN_VALIDITY);
        this.loggedOut = false;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }

    public void blockTokenForLogout() {
        this.loggedOut = true;
    }
}
