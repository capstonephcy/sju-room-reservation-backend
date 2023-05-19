package com.sju.roomreservationbackend.domain.user.recovery;

import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.domain.user.recovery.dto.request.RecoverPasswordReqDTO;
import com.sju.roomreservationbackend.domain.user.recovery.dto.request.RecoverUsernameReqDTO;
import com.sju.roomreservationbackend.domain.user.recovery.dto.response.RecoverPasswordResDTO;
import com.sju.roomreservationbackend.domain.user.recovery.dto.response.RecoverUsernameResDTO;
import com.sju.roomreservationbackend.domain.user.recovery.services.UserRecoveryServ;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserRecoveryAPI {
    private final UserRecoveryServ userRecoveryServ;

    @PostMapping("/users/recovery/username")
    public ResponseEntity<?> recoverUsername(@RequestBody RecoverUsernameReqDTO reqDto) {
        // 이메일을 통해 계정 세부정보 조회 및 유저네임 반환
        RecoverUsernameResDTO resDTO = new RecoverUsernameResDTO();
        return new APIUtil<RecoverUsernameResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setUsername(userRecoveryServ.recoverUsernameByEmail(reqDto));
            }
        }.execute(resDTO, "res.recover.username.success");
    }

    @PostMapping("/users/recovery/password")
    public ResponseEntity<?> recoverPassword(@RequestBody RecoverPasswordReqDTO reqDto) {
        RecoverPasswordResDTO resDTO = new RecoverPasswordResDTO();
        return new APIUtil<RecoverPasswordResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                // 유저네임 및 이메일 인증여부 확인 후 비밀번호 초기화
                userRecoveryServ.recoverPasswordByVerifyEmail(reqDto);
            }
        }.execute(resDTO, "res.recover.password.success");
    }
}
