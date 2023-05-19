package com.sju.roomreservationbackend.domain.user.profile.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sju.roomreservationbackend.common.exception.objects.DTOValidityException;
import com.sju.roomreservationbackend.domain.user.profile.dto.request.CreateUserProfileReqDTO;
import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import com.sju.roomreservationbackend.domain.user.profile.repository.UserProfileRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class UserProfileLogicServ implements UserProfileServCommon {
    protected final UserProfileRepo userProfileRepo;
    protected final PasswordEncoder pwEncoder;

    protected List<CreateUserProfileReqDTO> parsePlaceCSV(String filePath) throws Exception {
        List<CreateUserProfileReqDTO> profileCreateReqDTOs = new ArrayList<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(new FileInputStream(filePath)))
                .withSkipLines(2)
                .build()) {

            // Read Place Profile Data
            String[] rawUserProfile;
            do {
                rawUserProfile = csvReader.readNext();
                if (rawUserProfile != null) {
                    // Build Create DTO
                    CreateUserProfileReqDTO createDTO = new CreateUserProfileReqDTO();
                    profileCreateReqDTOs.add(createDTO.parseFromCSVRow(rawUserProfile, pwEncoder.encode(rawUserProfile[4])));
                }
            } while (rawUserProfile != null);
            return profileCreateReqDTOs;
        } catch (Exception e) {
            throw new Exception(
                    userMsgSrc.getMessage("error.user.csv.parse", null, Locale.ENGLISH)
            );
        }
    }

    protected void checkPermissionIsValid(Permission permission) throws DTOValidityException {
        if (permission == Permission.ROOT_ADMIN) {
            throw new DTOValidityException(
                    userMsgSrc.getMessage("error.user.unique.superuser", null, Locale.ENGLISH)
            );
        }
    }

    protected void checkUsernameDuplication(String username) throws DTOValidityException {
        // 사용자 로그인 유저네임 중복 확인
        if (username != null && userProfileRepo.existsByUsername(username)) {
            throw new DTOValidityException(
                    userMsgSrc.getMessage("error.user.dup.username",null, Locale.ENGLISH)
            );
        }
    }

    protected void checkEmailDuplication(String email) throws DTOValidityException {
        // 이메일 중복 확인
        if (email != null && userProfileRepo.existsByEmail(email)) {
            throw new DTOValidityException(
                    userMsgSrc.getMessage("error.user.dup.email",null, Locale.ENGLISH)
            );
        }
    }

    protected void checkPhoneDuplication(String phone) throws DTOValidityException {
        // 전화번호 중복 확인
        if (phone != null && userProfileRepo.existsByPhone(phone.replaceAll("-", ""))) {
            throw new DTOValidityException(
                    userMsgSrc.getMessage("error.user.dup.phone",null,Locale.ENGLISH)
            );
        }
    }
}
