package com.sju.roomreservationbackend.domain.user.profile.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImportUserProfileResDTO extends GeneralResDTO {
    private List<UserProfile> importedUserProfiles;
    private List<Integer> failedIndices;
    private List<String> failedErrorMsg;
}
