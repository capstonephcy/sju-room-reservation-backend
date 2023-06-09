package com.sju.roomreservationbackend.domain.user.auth;

import com.sju.roomreservationbackend.common.email.EmailService;
import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.domain.user.auth.dto.request.LoginReqDTO;
import com.sju.roomreservationbackend.domain.user.auth.dto.request.SendVerificationCodeToEmailReqDTO;
import com.sju.roomreservationbackend.domain.user.auth.dto.response.LoginResDTO;
import com.sju.roomreservationbackend.domain.user.auth.dto.response.LogoutResDTO;
import com.sju.roomreservationbackend.domain.user.auth.dto.response.SendVerificationCodeToEmailResDTO;
import com.sju.roomreservationbackend.domain.user.auth.services.UserAuthServ;
import com.sju.roomreservationbackend.domain.user.auth.services.UserTokenServ;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserAuthAPI {
    private final UserAuthServ userAuthServ;
    private final UserTokenServ userTokenServ;
    private final EmailService emailServ;

    // 이메일로 로그인
    @PostMapping("/users/auths/login")
    public ResponseEntity<?> loginAccount(@RequestBody LoginReqDTO reqDto) {
        LoginResDTO resDTO = new LoginResDTO();
        String ipAddress = APIUtil.getClientIpAddressIfServletRequestExist();
        return new APIUtil<LoginResDTO>() {
            @Override
            protected void onSuccess() {
                UserProfile profile = userAuthServ.loginByCredential(reqDto.getUsername(), reqDto.getPassword());
                resDTO.setAccessToken(userTokenServ.issueAccessToken(profile));
                resDTO.setRefreshToken(userTokenServ.fetchRefreshToken(profile, ipAddress));
                resDTO.setProfile(profile);
            }
        }.execute(resDTO, "res.login.success");
    }

    @GetMapping("/users/auths/logout")
    public ResponseEntity<?> loginAccount(Authentication auth) {
        LogoutResDTO resDTO = new LogoutResDTO();
        return new APIUtil<LogoutResDTO>() {
            @Override
            protected void onSuccess() {
                resDTO.setProfile(userAuthServ.logout(auth));
            }
        }.execute(resDTO, "res.logout.success");
    }

    // 이메일 인증 메일 발송
    @PostMapping("/users/auths/email/send")
    public ResponseEntity<?> sendVerificationCodeToEmail(@RequestBody @Valid SendVerificationCodeToEmailReqDTO reqDto) {
        SendVerificationCodeToEmailResDTO resDTO = new SendVerificationCodeToEmailResDTO();
        return new APIUtil<SendVerificationCodeToEmailResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                emailServ.sendVerificationMessage(reqDto.getEmail());
            }
        }.execute(resDTO, "res.authcode.send.success");
    }
}
