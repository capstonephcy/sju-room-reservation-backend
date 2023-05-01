package com.sju.roomreservationbackend.domain.user.profile.dto.request;

import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Data
public class FetchUserProfileReqDTO {
    @Email(message = "valid.user.email.email")
    @Size(max = 50, message = "valid.user.email.size")
    private String email;
    @Size(min = 2, max = 20, message = "valid.user.username.size")
    private String username;
    private Permission permission;
}
