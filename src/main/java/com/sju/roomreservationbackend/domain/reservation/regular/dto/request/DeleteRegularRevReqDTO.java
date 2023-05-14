package com.sju.roomreservationbackend.domain.reservation.regular.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class DeleteRegularRevReqDTO {
    @NotNull(message = "valid.regularRev.id.null")
    @PositiveOrZero(message = "valid.regularRev.id.positive")
    private Long id;
}
