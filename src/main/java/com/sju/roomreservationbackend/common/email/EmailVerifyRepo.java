package com.sju.roomreservationbackend.common.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerifyRepo extends JpaRepository<EmailVerifyLog, Long>, EmailVerifyDslRepo {
    Optional<EmailVerifyLog> findByEmail(String email);

    Optional<EmailVerifyLog> findByEmailAndVerifiedIsFalse(String email);

    Optional<EmailVerifyLog> findByEmailAndVerifiedIsTrue(String email);
}
