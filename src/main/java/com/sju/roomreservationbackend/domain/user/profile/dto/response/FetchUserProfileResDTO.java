package com.sju.roomreservationbackend.domain.user.profile.dto.response;

import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchUserProfileResDTO extends GeneralResDTO {
    private UserProfile userProfile;
    private List<UserProfile> userProfiles;
}
