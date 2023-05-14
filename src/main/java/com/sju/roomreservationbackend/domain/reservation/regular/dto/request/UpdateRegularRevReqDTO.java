package com.sju.roomreservationbackend.domain.reservation.regular.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class UpdateRegularRevReqDTO {
    @NotNull(message = "valid.regularRev.id.null")
    @PositiveOrZero(message = "valid.regularRev.id.positive")
    private Long id;

    List<Long> attendants;

    private Boolean confirmed;
}
