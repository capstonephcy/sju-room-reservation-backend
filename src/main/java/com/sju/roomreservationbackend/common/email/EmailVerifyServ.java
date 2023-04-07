package com.dmtlabs.aidocentserver.global.email;

import com.dmtlabs.aidocentserver.global.tempcode.TempCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class EmailVerifyServ implements EmailVerifyServObservable {
    private final EmailVerifyRepo emailVerifyRepo;

    @Transactional
    public String createAuthCode(String email) {
        // 이메일 인증요청 리스트에 새로운 인증요청정보 추가
        char[] authCodeCharSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String authCode = TempCodeGenerator.generate(authCodeCharSet, 8);

        // 해당 이메일로 기존에 발송한 인증코드가 있을 경우 무효화
        emailVerifyRepo.findByEmail(email).ifPresent(this.emailVerifyRepo::delete);

        // 이메일 인증번호 만료시간 산출
        LocalDateTime expiredTime = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);

        // 이메일 인증요청 리스트에 추가
        EmailVerifyLog log = EmailVerifyLog.builder()
                .email(email)
                .code(authCode)
                .verified(false)
                .validUntil(expiredTime)
                .build();
        this.emailVerifyRepo.save(log);

        return authCode;
    }

    @Transactional
    public boolean checkAuthCode(String email, String authCode) {
        // 이메일 인증코드 검사
        EmailVerifyLog log = this.emailVerifyRepo.findByEmailAndVerifiedIsFalse(email).orElse(null);
        boolean checkResult = log != null && log.getCode().equals(authCode) && LocalDateTime.now().isBefore(log.getValidUntil());
        if (log != null) {
            log.setVerified(checkResult);
        }
        return checkResult;
    }

    @Transactional
    public void deleteExpiredAuthCode() {
        // 이메일 인증요청 리스트 정리 (현재 시간부로 만료시간이 된 데이터 삭제)
        this.emailVerifyRepo.deleteExpiredLog();
    }

    @Override
    public void checkEmailVerified(String email) throws Exception {
        this.emailVerifyRepo.findByEmailAndVerifiedIsTrue(email).orElseThrow(() -> new Exception(
                "Email not verified"
        ));
    }
}
