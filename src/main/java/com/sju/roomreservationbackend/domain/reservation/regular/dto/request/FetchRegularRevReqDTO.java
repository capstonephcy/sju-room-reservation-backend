package com.sju.roomreservationbackend.domain.reservation.regular.dto.request;

import com.sju.roomreservationbackend.common.http.GeneralPageableReqDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class FetchRegularRevReqDTO extends GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.regularRev.id.positive")
    private Long id;

    @PositiveOrZero(message = "valid.user.id.positive")
    private Long userId;

    @PositiveOrZero(message = "valid.reservation.id.positive")
    private Long roomId;
}
