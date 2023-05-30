package com.sju.roomreservationbackend.domain.user.profile.services;

import com.sju.roomreservationbackend.common.storage.StorageService;
import com.sju.roomreservationbackend.domain.user.profile.dto.request.*;
import com.sju.roomreservationbackend.domain.user.profile.dto.response.ImportUserProfileResDTO;
import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.domain.user.profile.repository.UserProfileRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class UserProfileCrudServ extends UserProfileLogicServ implements UserProfileServCommon, UserProfileObservable {
    private final StorageService storageServ;

    public UserProfileCrudServ(UserProfileRepo userProfileRepo, PasswordEncoder pwEncoder, StorageService storageServ) {
        super(userProfileRepo, pwEncoder);
        this.storageServ = storageServ;
    }

    @Transactional
    public UserProfile createUserProfile(CreateUserProfileReqDTO reqDTO) throws Exception {
        this.checkUsernameDuplication(reqDTO.getUsername());
        this.checkEmailDuplication(reqDTO.getEmail());
        this.checkPhoneDuplication(reqDTO.getPhone());

        List<Permission> perms = new ArrayList<>();
        perms.add(reqDTO.getPermission());
        this.checkPermissionIsValid(reqDTO.getPermission());

        UserProfile userProfile = UserProfile.builder()
                .username(reqDTO.getUsername())
                .password(pwEncoder.encode(reqDTO.getPassword()))
                .email(reqDTO.getEmail())
                .phone(reqDTO.getPhone())
                .name(reqDTO.getName())
                .department(reqDTO.getDepartment())
                .noShowRate(0.0d)
                .reserveCnt(0)
                .noShowCnt(0)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .permissions(perms)
                .active(reqDTO.getPermission() == Permission.STUDENT)
                .build();

        return userProfileRepo.save(userProfile);
    }

    @Transactional
    public ImportUserProfileResDTO importUserProfile(ImportUserProfileReqDTO reqDTO) throws Exception {
        String tempFilePath = storageServ.saveTempFile(reqDTO.getCsvFile());

        List<CreateUserProfileReqDTO> requests = this.parsePlaceCSV(tempFilePath);
        List<UserProfile> created = new ArrayList<>();
        List<Integer> failedIdx = new ArrayList<>();
        List<String> failedErrorMsg = new ArrayList<>();

        // Create User Entity
        for (int i = 0 ; i < requests.size(); i++) {
            try {
                created.add(this.createUserProfile(requests.get(i)));
            } catch (Exception e) {
                failedIdx.add(i);
                failedErrorMsg.add(e.getLocalizedMessage());
            }
        }

        // Pack to DTO
        ImportUserProfileResDTO resDTO = new ImportUserProfileResDTO();
        resDTO.setImportedUserProfiles(created);
        resDTO.setFailedIndices(failedIdx);
        resDTO.setFailedErrorMsg(failedErrorMsg);
        return resDTO;
    }

    @Override
    public UserProfile fetchUserProfileById(Long userId) throws Exception {
        return userProfileRepo.findById(userId)
                .orElseThrow(() -> new Exception(
                        userMsgSrc.getMessage("error.user.notExist", null, Locale.ENGLISH)
                ));
    }
    public UserProfile fetchUserProfileByUsername(String username) throws Exception {
        return userProfileRepo.findByUsername(username)
                .orElseThrow(() -> new Exception(
                        userMsgSrc.getMessage("error.user.notExist", null, Locale.ENGLISH)
                ));
    }
    public UserProfile fetchUserProfileByEmail(String email) throws Exception {
        return userProfileRepo.findByEmail(email)
                .orElseThrow(() -> new Exception(
                        userMsgSrc.getMessage("error.user.notExist", null, Locale.ENGLISH)
                ));
    }
    public List<UserProfile> fetchUserProfileByName(String name) {
        return userProfileRepo.findAllByName(name);
    }
    public List<UserProfile> fetchWaitingPermitProfile(Permission permission) {
        return userProfileRepo.findAllByPermissionsContainingAndActive(permission, false);
    }

    @Transactional
    public UserProfile updateUserProfile(Authentication auth, UpdateUserProfileReqDTO reqDTO) {
        UserProfile userProfile = this.fetchCurrentUser(auth);

        userProfile.setEmail(reqDTO.getEmail() == null ? userProfile.getEmail() : reqDTO.getEmail());
        userProfile.setPhone(reqDTO.getPhone() == null ? userProfile.getPhone() : reqDTO.getPhone());
        userProfile.setName(reqDTO.getName() == null ? userProfile.getName() : reqDTO.getName());
        userProfile.setDepartment(reqDTO.getDepartment() == null ? userProfile.getDepartment() : reqDTO.getDepartment());
        userProfile.setFcmRegistrationToken(reqDTO.getFcmRegistrationToken() == null ? userProfile.getFcmRegistrationToken() : reqDTO.getFcmRegistrationToken());

        return userProfileRepo.save(userProfile);
    }

    @Transactional
    public void updateUserPassword(Authentication auth, UpdateUserPasswordReqDTO reqDTO) {
        UserProfile userProfile = this.fetchCurrentUser(auth);

        // 기존 비밀번호 일치 확인
        if (!pwEncoder.matches(reqDTO.getPassword(), userProfile.getPassword())) {
            throw new BadCredentialsException(
                    userMsgSrc.getMessage("error.login.fail", null, Locale.ENGLISH)
            );
        }

        // 새로운 비밀번호가 기존 비밀번호와 다르면 업데이트
        if (!pwEncoder.encode(userProfile.getPassword()).equals(reqDTO.getNewPassword())) {
            userProfile.setPassword(pwEncoder.encode(reqDTO.getNewPassword()));
        }
        userProfileRepo.save(userProfile);
    }

    @Transactional
    public void updateUserPermission(Authentication auth, UpdateUserPermissionReqDTO reqDTO) throws Exception {
        // 권한을 변경하거나 박탈할 유저
        UserProfile userProfile = this.fetchUserProfileById(reqDTO.getId());
        // 권한을 제어하려는 유저 (슈퍼유저 - ROOT_ADMIN 과 관리자 - ADMIN 만 허용)
        UserProfile adminProfile = this.fetchCurrentUser(auth);
        if (!(adminProfile.getPermissions().contains(Permission.ROOT_ADMIN) ||
                adminProfile.getPermissions().contains(Permission.ADMIN))) {
            throw new Exception(
                    userMsgSrc.getMessage("error.notEnoughPermission", null, Locale.ENGLISH)
            );
        }

        // 슈퍼유저 - ROOT_ADMIN 은 권한을 빅틸하거나 변경할 수 없음 (루트 계정)
        if (userProfile.getPermissions().contains(Permission.ROOT_ADMIN)) {
            throw new Exception(
                    userMsgSrc.getMessage("error.rootAdmin", null, Locale.ENGLISH)
            );
        }

        List<Permission> perms = userProfile.getPermissions();
        perms.clear();

        perms.add(reqDTO.getNewPermission());

        userProfile.setPermissions(perms);
        userProfileRepo.save(userProfile);
    }

    @Transactional
    public void deleteUserProfile(Authentication auth) {
        UserProfile userProfile = this.fetchCurrentUser(auth);
        userProfileRepo.delete(userProfile);
    }

    @Transactional
    public void updateReserveCnt(UserProfile user) {
        user.setReserveCnt(user.getReserveCnt() + 1);
        userProfileRepo.save(user);
    }

    @Transactional
    public void updateNoShowCnt(UserProfile user) {
        user.setNoShowCnt(user.getNoShowCnt() + 1);
        userProfileRepo.save(user);
    }
}
