package com.sju.roomreservationbackend.domain.user.profile.dto.request;

import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FetchUserProfileReqDTO {
    @Email(message = "valid.user.email.email")
    @Size(max = 50, message = "valid.user.email.size")
    private String email;
    @Size(min = 2, max = 20, message = "valid.user.username.size")
    private String username;
    @Size(max = 50, message = "valid.user.name.size")
    private String name;
    private Permission permission;
}
