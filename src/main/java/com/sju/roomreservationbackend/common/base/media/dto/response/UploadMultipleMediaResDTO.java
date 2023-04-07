package com.dmtlabs.aidocentserver.global.base.media.dto.response;

import com.dmtlabs.aidocentserver.global.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMultipleMediaResDTO<T> extends GeneralResDTO {
    protected List<T> fileInfos;
}
