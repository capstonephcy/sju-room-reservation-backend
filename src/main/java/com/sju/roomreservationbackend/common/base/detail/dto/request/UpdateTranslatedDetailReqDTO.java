package com.sju.roomreservationbackend.common.base.detail.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Data
public class UpdateTranslatedDetailReqDTO {
    @NotNull(message = "valid.detail.id.null")
    @PositiveOrZero(message = "valid.detail.id.positive")
    protected Long id;

    @NotBlank(message = "valid.detail.name.blank")
    @Size(max = 200, message = "valid.detail.name.size")
    protected String name;

    @NotBlank(message = "valid.detail.author.blank")
    @Size(max = 50, message = "valid.detail.author.size")
    protected String author;

    @NotBlank(message = "valid.detail.translator.blank")
    @Size(max = 50, message = "valid.detail.translator.size")
    protected String translator;
}
