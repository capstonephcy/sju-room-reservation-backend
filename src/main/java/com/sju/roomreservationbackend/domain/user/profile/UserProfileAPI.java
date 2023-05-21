package com.sju.roomreservationbackend.domain.user.profile;

import com.sju.roomreservationbackend.domain.user.profile.dto.request.*;
import com.sju.roomreservationbackend.domain.user.profile.dto.response.*;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.common.message.MessageConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Locale;

@RestController
@AllArgsConstructor
public class UserProfileAPI {
    private final UserProfileCrudServ userProfileCrudServ;
    private final MessageSource msgSrc = MessageConfig.getUserMsgSrc();

    // 신규 사용자 계정 생성
    @PostMapping("/users/profiles")
    public ResponseEntity<?> createUserProfile(@Valid @RequestBody CreateUserProfileReqDTO reqDTO) {
        CreateUserProfileResDTO resDTO = new CreateUserProfileResDTO();
        return new APIUtil<CreateUserProfileResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setCreatedUserProfile(userProfileCrudServ.createUserProfile(reqDTO));
            }
        }.execute(resDTO, "res.user.create.success");
    }

    // 신규 사용자 계정 생성 (파일 임포트)
    @PostMapping("/users/profiles/csv")
    public ResponseEntity<?> createUserProfile(@Valid ImportUserProfileReqDTO reqDTO) {
        ImportUserProfileResDTO resDTO = new ImportUserProfileResDTO();
        return new APIUtil<ImportUserProfileResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                ImportUserProfileResDTO result = userProfileCrudServ.importUserProfile(reqDTO);
                resDTO.setImportedUserProfiles(result.getImportedUserProfiles());
                result.setFailedIndices(result.getFailedIndices());
                result.setFailedErrorMsg(result.getFailedErrorMsg());
            }
        }.execute(resDTO, "res.user.import.success");
    }

    // 사용자 프로파일 조회
    @GetMapping("/users/profiles")
    public ResponseEntity<?> fetchUserProfile(Authentication auth, @Valid FetchUserProfileReqDTO reqDTO, @RequestHeader("Request-Type") String reqType) {
        FetchUserProfileResDTO resDTO = new FetchUserProfileResDTO();
        FetchUserProfileReqOptionType reqOptionType = FetchUserProfileReqOptionType.valueOf(reqType);
        return new APIUtil<FetchUserProfileResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                switch(reqOptionType) {
                    case CURRENT -> resDTO.setUserProfile(userProfileCrudServ.fetchCurrentUser(auth));
                    case USERNAME -> resDTO.setUserProfile(userProfileCrudServ.fetchUserProfileByUsername(reqDTO.getUsername()));
                    case EMAIL -> resDTO.setUserProfile(userProfileCrudServ.fetchUserProfileByEmail(reqDTO.getEmail()));
                    case NAME -> resDTO.setUserProfiles(userProfileCrudServ.fetchUserProfileByName(reqDTO.getName()));
                    case WAITING_PERMIT -> resDTO.setUserProfiles(userProfileCrudServ.fetchWaitingPermitProfile(reqDTO.getPermission()));
                    default -> throw new Exception(
                            msgSrc.getMessage("valid.binding", null, Locale.ENGLISH)
                    );
                }
            }
        }.execute(resDTO, "res.user.fetch.success");
    }

    // 사용자 프로파일 업데이트
    @PutMapping("/users/profiles")
    public ResponseEntity<?> updateUserProfile(Authentication auth, @Valid @RequestBody UpdateUserProfileReqDTO reqDTO) {
        UpdateUserProfileResDTO resDTO = new UpdateUserProfileResDTO();
        return new APIUtil<UpdateUserProfileResDTO>() {
            @Override
            protected void onSuccess() {
                resDTO.setUpdatedUserProfile(userProfileCrudServ.updateUserProfile(auth, reqDTO));
            }
        }.execute(resDTO, "res.user.update.success");
    }

    // 사용자 비밀번호 업데이트(변경)
    @PutMapping("/users/profiles/password")
    public ResponseEntity<?> updateUserPassword(Authentication auth, @Valid @RequestBody UpdateUserPasswordReqDTO reqDto) {
        UpdateUserPasswordResDTO resDTO = new UpdateUserPasswordResDTO();
        return new APIUtil<UpdateUserPasswordResDTO>() {
            @Override
            protected void onSuccess() {
                userProfileCrudServ.updateUserPassword(auth, reqDto);
            }
        }.execute(resDTO, "res.password.update.success");
    }

    // 사용자 권한 강제 변경 (관리자만 가능)
    @PreAuthorize("hasAnyAuthority('ROOT_ADMIN', 'ADMIN')")
    @PutMapping("/users/profiles/permission")
    public ResponseEntity<?> updateUserPermission(Authentication auth, @Valid @RequestBody UpdateUserPermissionReqDTO reqDto) {
        UpdateUserPermissionResDTO resDTO = new UpdateUserPermissionResDTO();
        return new APIUtil<UpdateUserPermissionResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                userProfileCrudServ.updateUserPermission(auth, reqDto);
            }
        }.execute(resDTO, "res.permission.update.success");
    }

    // 회원탈퇴
    @DeleteMapping("/users/profiles")
    public ResponseEntity<?> deleteUserProfile(Authentication auth) {
        DeleteUserProfileResDTO resDTO = new DeleteUserProfileResDTO();
        return new APIUtil<DeleteUserProfileResDTO>() {
            @Override
            protected void onSuccess() {
                userProfileCrudServ.deleteUserProfile(auth);
            }
        }.execute(resDTO, "res.user.delete.success");
    }
}
