package com.sju.roomreservationbackend.domain.user.profile.dto.request;

import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
public class UpdateUserPermissionReqDTO {
    @NotNull(message = "valid.user.id.null")
    @PositiveOrZero(message = "valid.user.id.positive")
    private Long id;
    @NotNull(message = "valid.user.permission.null")
    private Permission newPermission;
}
