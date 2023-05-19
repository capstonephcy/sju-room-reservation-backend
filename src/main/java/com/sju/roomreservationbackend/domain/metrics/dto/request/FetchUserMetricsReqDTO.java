package com.sju.roomreservationbackend.domain.metrics.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class FetchUserMetricsReqDTO {
    @NotNull(message = "valid.user.id.null")
    @PositiveOrZero(message = "valid.user.id.positive")
    private Long userId;
}
