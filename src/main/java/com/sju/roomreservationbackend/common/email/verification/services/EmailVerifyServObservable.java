package com.sju.roomreservationbackend.common.email.verification.services;

public interface EmailVerifyServObservable {
    void checkEmailVerified(String email) throws Exception;
}
