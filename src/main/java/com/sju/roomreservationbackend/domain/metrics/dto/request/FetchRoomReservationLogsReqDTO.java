package com.sju.roomreservationbackend.domain.metrics.dto.request;

import com.sju.roomreservationbackend.common.http.GeneralPageableReqDTO;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FetchRoomReservationLogsReqDTO extends GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.reservation.id.positive")
    private Long roomId;

    @PositiveOrZero(message = "valid.user.id.positive")
    private Long userId;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
