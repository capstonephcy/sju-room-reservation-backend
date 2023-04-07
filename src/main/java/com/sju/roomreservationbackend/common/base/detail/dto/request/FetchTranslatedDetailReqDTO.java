package com.dmtlabs.aidocentserver.global.base.detail.dto.request;

import com.dmtlabs.aidocentserver.global.http.GeneralPageableReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchTranslatedDetailReqDTO extends GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.detail.id.positive")
    protected Long id;

    @Size(max=20, message="valid.detail.language.size")
    protected String language;

    @Size(max=200, message="valid.detail.name.size")
    protected String name;
}
