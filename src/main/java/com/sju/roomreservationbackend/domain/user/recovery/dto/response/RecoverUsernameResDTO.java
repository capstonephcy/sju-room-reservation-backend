package com.sju.roomreservationbackend.domain.user.recovery.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecoverUsernameResDTO extends GeneralResDTO {
    private String username;
}
