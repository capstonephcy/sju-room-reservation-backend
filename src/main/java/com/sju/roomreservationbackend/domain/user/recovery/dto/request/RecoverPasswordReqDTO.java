package com.sju.roomreservationbackend.domain.user.recovery.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecoverPasswordReqDTO {
    @Email(message = "valid.user.email.email")
    @NotBlank(message = "valid.user.email.blank")
    @Size(max=50, message = "valid.user.email.size")
    private String email;
    @NotBlank(message = "valid.user.code.blank")
    @Size(max=50, message = "valid.user.code.size")
    private String code;
}
