package com.dmtlabs.aidocentserver.global.email;

public interface EmailVerifyServObservable {
    void checkEmailVerified(String email) throws Exception;
}
