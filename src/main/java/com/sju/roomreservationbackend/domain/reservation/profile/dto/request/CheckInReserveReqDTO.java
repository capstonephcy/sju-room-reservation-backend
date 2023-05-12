package com.sju.roomreservationbackend.domain.reservation.profile.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CheckInReserveReqDTO {
    @NotNull(message = "valid.reservation.id.null")
    @PositiveOrZero(message = "valid.reservation.id.positive")
    private Long id;

    private String checkInCode;
}
