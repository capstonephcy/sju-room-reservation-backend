package com.sju.roomreservationbackend.domain.user.profile.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class UpdateUserPasswordReqDTO {
    @NotBlank(message = "valid.user.password.blank")
    @Size(min=6, max=20, message = "valid.user.password.size")
    private String password;
    @NotBlank(message = "valid.user.password.blank")
    @Size(min=6, max=20, message = "valid.user.password.size")
    private String newPassword;
}
