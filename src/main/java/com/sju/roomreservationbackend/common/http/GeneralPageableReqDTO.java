package com.sju.roomreservationbackend.common.http;

import lombok.Data;

import jakarta.validation.constraints.PositiveOrZero;

@Data
public class GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.page.positive")
    private Integer pageIdx;

    @PositiveOrZero(message = "valid.page.positive")
    private Integer pageLimit;
}
