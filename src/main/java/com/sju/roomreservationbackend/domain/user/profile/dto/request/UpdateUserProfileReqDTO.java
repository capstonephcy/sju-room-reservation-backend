package com.sju.roomreservationbackend.domain.user.profile.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserProfileReqDTO {
    @Email(message = "valid.user.email.email")
    @Size(max=50, message = "valid.user.email.size")
    private String email;

    @Pattern(regexp = "(^02|^\\d{3})-(\\d{3}|\\d{4})-\\d{4}", message = "valid.user.phone.phone")
    @Size(min = 9, max = 13, message = "valid.user.phone.size")
    private String phone;

    @Size(max = 50, message = "valid.user.name.size")
    private String name;

    @Size(max=50, message = "valid.user.department.size")
    private String department;
}
