package com.dmtlabs.aidocentserver.global.base.media.dto.response;

import com.dmtlabs.aidocentserver.global.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMediaResDTO<T> extends GeneralResDTO {
    protected T fileInfo;
}
