package com.sju.roomreservationbackend.domain.user.auth.dto.response;

import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResDTO extends GeneralResDTO {
    private String token;
    private UserProfile profile;
}
