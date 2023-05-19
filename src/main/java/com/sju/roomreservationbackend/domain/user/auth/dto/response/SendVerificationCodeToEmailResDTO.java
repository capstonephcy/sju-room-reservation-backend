package com.sju.roomreservationbackend.domain.user.auth.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SendVerificationCodeToEmailResDTO extends GeneralResDTO {
}
