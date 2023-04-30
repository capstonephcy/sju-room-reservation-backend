package com.sju.roomreservationbackend.common.email;

public interface EmailVerifyServObservable {
    void checkEmailVerified(String email) throws Exception;
}
