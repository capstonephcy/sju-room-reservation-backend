package com.sju.roomreservationbackend.common.base.media.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMediaResDTO<T> extends GeneralResDTO {
    protected T fileInfo;
}
