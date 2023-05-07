package com.sju.roomreservationbackend.domain.user.auth.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LogoutResDTO extends GeneralResDTO {
    private UserProfile profile;
}
