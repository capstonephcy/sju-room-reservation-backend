package com.sju.roomreservationbackend.domain.user.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserProfileReqDTO {
    @NotBlank(message = "valid.user.username.blank")
    @Size(min = 2, max = 20, message = "valid.user.username.size")
    private String username;

    @NotBlank(message = "valid.user.phone.blank")
    @Size(min = 9, max = 13, message = "valid.user.phone.size")
    @Pattern(regexp = "(^02|^\\d{3})-(\\d{3}|\\d{4})-\\d{4}", message = "valid.user.phone.phone")
    private String phone;

    @NotBlank(message = "valid.user.name.blank")
    @Size(max = 50, message = "valid.user.name.size")
    private String name;
}
