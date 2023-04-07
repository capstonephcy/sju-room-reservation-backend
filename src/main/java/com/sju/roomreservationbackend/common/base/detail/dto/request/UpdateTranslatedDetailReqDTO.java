package com.dmtlabs.aidocentserver.global.base.detail.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
public class UpdateTranslatedDetailReqDTO {
    @NotNull(message="valid.detail.id.null")
    @PositiveOrZero(message = "valid.detail.id.positive")
    protected Long id;

    @NotBlank(message="valid.detail.name.blank")
    @Size(max=200, message="valid.detail.name.size")
    protected String name;

    @NotBlank(message = "valid.detail.author.blank")
    @Size(max = 50, message = "valid.detail.author.size")
    protected String author;

    @NotBlank(message = "valid.detail.translator.blank")
    @Size(max = 50, message = "valid.detail.translator.size")
    protected String translator;
}
