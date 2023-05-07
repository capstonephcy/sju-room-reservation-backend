package com.sju.roomreservationbackend.domain.user.profile.services;

import com.sju.roomreservationbackend.common.exception.objects.DTOValidityException;
import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import com.sju.roomreservationbackend.domain.user.profile.repository.UserProfileRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class UserProfileLogicServ implements UserProfileServCommon {
    protected final UserProfileRepo userProfileRepo;
    protected final PasswordEncoder pwEncoder;

    protected void checkPermissionIsValid(Permission permission) throws Exception {
        if (permission == Permission.ROOT_ADMIN) {
            throw new Exception(
                    userMsgSrc.getMessage("error.user.unique.superuser", null, Locale.ENGLISH)
            );
        }
    }

    protected void checkUsernameDuplication(String username) throws Exception {
        // 사용자 로그인 유저네임 중복 확인
        if (username != null && userProfileRepo.existsByUsername(username)) {
            throw new DTOValidityException(
                    userMsgSrc.getMessage("error.user.dup.username",null, Locale.ENGLISH)
            );
        }
    }

    protected void checkEmailDuplication(String email) throws Exception {
        // 이메일 중복 확인
        if (email != null && userProfileRepo.existsByEmail(email)) {
            throw new DTOValidityException(
                    userMsgSrc.getMessage("error.user.dup.email",null, Locale.ENGLISH)
            );
        }
    }

    protected void checkPhoneDuplication(String phone) throws Exception {
        // 전화번호 중복 확인
        if (phone != null && userProfileRepo.existsByPhone(phone.replaceAll("-", ""))) {
            throw new DTOValidityException(
                    userMsgSrc.getMessage("error.user.dup.phone",null,Locale.ENGLISH)
            );
        }
    }
}
