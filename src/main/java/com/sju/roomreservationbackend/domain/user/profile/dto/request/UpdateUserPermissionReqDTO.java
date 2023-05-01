package com.sju.roomreservationbackend.domain.user.profile.dto.request;

import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
public class UpdateUserPermissionReqDTO {
    @NotBlank(message = "valid.user.id.blank")
    @PositiveOrZero(message = "valid.user.id.positive")
    private Long id;
    @NotNull(message = "valid.user.permission.null")
    private Permission newPermission;
}
