package com.sju.roomreservationbackend.domain.reservation.profile.dto.request;

import com.sju.roomreservationbackend.common.http.GeneralPageableReqDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FetchReserveReqDTO extends GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.reservation.id.positive")
    private Long id;

    @PositiveOrZero(message = "valid.user.id.positive")
    private Long userId;

    @PositiveOrZero(message = "valid.reservation.id.positive")
    private Long roomId;

    @PositiveOrZero(message = "valid.reservation.regularRev.id.positive")
    private Long regularRevId;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
