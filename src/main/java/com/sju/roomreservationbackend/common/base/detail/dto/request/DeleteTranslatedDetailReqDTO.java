package com.sju.roomreservationbackend.common.base.detail.dto.request;

import lombok.Data;

import jakarta.validation.constraints.PositiveOrZero;

@Data
public class DeleteTranslatedDetailReqDTO {
    @PositiveOrZero(message = "valid.detail.id.positive")
    protected Long id;
}
