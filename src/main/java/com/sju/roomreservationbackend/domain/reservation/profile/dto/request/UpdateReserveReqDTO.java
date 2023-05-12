package com.sju.roomreservationbackend.domain.reservation.profile.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class UpdateReserveReqDTO {
    @NotNull(message = "valid.reservation.id.null")
    @PositiveOrZero(message = "valid.reservation.id.positive")
    private Long id;

    List<Long> attendants;
}
