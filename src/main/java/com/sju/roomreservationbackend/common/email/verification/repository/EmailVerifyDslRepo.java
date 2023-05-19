package com.sju.roomreservationbackend.common.email.verification.repository;

public interface EmailVerifyDslRepo {
    void deleteExpiredLog();
}
