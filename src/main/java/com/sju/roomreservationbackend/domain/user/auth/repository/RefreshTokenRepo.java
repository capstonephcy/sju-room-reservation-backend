package com.sju.roomreservationbackend.domain.user.auth.repository;

import com.sju.roomreservationbackend.domain.user.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findByUsernameAndLoggedOutAndExpiredAtGreaterThan(String username, Boolean loggedOut, LocalDateTime now);
}
