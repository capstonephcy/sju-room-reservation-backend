package com.dmtlabs.aidocentserver.global.http;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
public class GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.page.positive")
    private Integer pageIdx;

    @PositiveOrZero(message = "valid.page.positive")
    private Integer pageLimit;
}
