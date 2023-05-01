package com.sju.roomreservationbackend.domain.user.profile.dto.response;

import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateUserProfileResDTO extends GeneralResDTO {
    private UserProfile createdUserProfile;
}
