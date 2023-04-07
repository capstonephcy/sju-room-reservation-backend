package com.dmtlabs.aidocentserver.global.base.detail.dto.request;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
public class DeleteTranslatedDetailReqDTO {
    @PositiveOrZero(message = "valid.detail.id.positive")
    protected Long id;
}
