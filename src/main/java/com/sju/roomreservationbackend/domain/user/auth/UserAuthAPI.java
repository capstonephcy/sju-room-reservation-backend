package com.sju.roomreservationbackend.domain.user.auth;

import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.common.security.JWTTokenProvider;
import com.sju.roomreservationbackend.domain.user.auth.dto.request.LoginReqDTO;
import com.sju.roomreservationbackend.domain.user.auth.dto.response.LoginResDTO;
import com.sju.roomreservationbackend.domain.user.auth.services.UserAuthServ;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthAPI {
    private final UserAuthServ userAuthServ;
    private final JWTTokenProvider tokenProvider;

    public UserAuthAPI(UserAuthServ userAuthServ, JWTTokenProvider tokenProvider) {
        this.userAuthServ = userAuthServ;
        this.tokenProvider = tokenProvider;
    }

    // 이메일로 로그인
    @PostMapping("/users/auths/login")
    public ResponseEntity<?> loginAccount(@RequestBody LoginReqDTO reqDto) {
        LoginResDTO resDTO = new LoginResDTO();
        return new APIUtil<LoginResDTO>() {
            @Override
            protected void onSuccess() {
                UserProfile profile = userAuthServ.loginByCredential(reqDto.getEmail(), reqDto.getPassword());
                resDTO.setProfile(profile);
                resDTO.setToken(tokenProvider.createToken(profile.getEmail(), profile.getPermissions()));
            }
        }.execute(resDTO, "res.login.success");
    }
}
