package com.sju.roomreservationbackend.domain.reservation.profile.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FetchReserveReqDTO {
    @PositiveOrZero(message = "valid.reservation.id.positive")
    private Long id;

    @NotNull(message = "valid.user.id.null")
    @PositiveOrZero(message = "valid.user.id.positive")
    private Long userId;

    @NotNull(message = "valid.reservation.id.null")
    @PositiveOrZero(message = "valid.reservation.id.positive")
    private Long roomId;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
