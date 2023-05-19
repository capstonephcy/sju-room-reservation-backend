package com.sju.roomreservationbackend.domain.user.recovery.services;

import com.sju.roomreservationbackend.common.email.EmailService;
import com.sju.roomreservationbackend.common.email.verification.services.EmailVerifyServ;
import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.common.tempcode.TempCodeGenerator;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.domain.user.profile.repository.UserProfileRepo;
import com.sju.roomreservationbackend.domain.user.recovery.dto.request.RecoverPasswordReqDTO;
import com.sju.roomreservationbackend.domain.user.recovery.dto.request.RecoverUsernameReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserRecoveryServ {
    private final UserProfileRepo userProfileRepo;
    private final EmailService emailServ;
    private final EmailVerifyServ emailVerifyServ;
    private final PasswordEncoder pwEncoder;
    private final MessageSource msgSrc = MessageConfig.getUserMsgSrc();

    public String recoverUsernameByEmail(RecoverUsernameReqDTO reqDto) throws Exception {
        UserProfile foundProfile;
        Object[] args = {
                reqDto.getEmail()
        };
        foundProfile = userProfileRepo.findByEmail(reqDto.getEmail())
                .orElseThrow(() -> new Exception(
                        msgSrc.getMessage("error.recover.email.notExist", args, Locale.ENGLISH)
                ));
        return foundProfile.getUsername();
    }

    @Transactional
    public void recoverPasswordByVerifyEmail(RecoverPasswordReqDTO reqDto) throws Exception {
        UserProfile foundProfile = userProfileRepo.findByEmail(reqDto.getEmail())
                .orElseThrow(() -> new Exception(msgSrc.getMessage("error.user.notExist", null, Locale.ENGLISH)));
        // 이메일 인증 확인
        if (emailVerifyServ.checkAuthCode(foundProfile.getEmail(), reqDto.getCode())) {
            String tempPassword = this.createTempPassword();
            // 임시 비밀번호 통지
            emailServ.sendTempPasswordNotifyMessage(foundProfile.getEmail(),tempPassword);
            // 임시 비밀번호 적용
            foundProfile.setPassword(pwEncoder.encode(tempPassword));
            userProfileRepo.save(foundProfile);
        } else {
            Object[] args = { foundProfile.getEmail() };
            throw new Exception(msgSrc.getMessage("error.recover.email.denied", args, Locale.ENGLISH));
        }
    }

    private String createTempPassword() {
        char[] tempPasswordCharSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '!','@','#','$','%','^','&'
        };
        return TempCodeGenerator.generate(tempPasswordCharSet, 8);
    }
}
