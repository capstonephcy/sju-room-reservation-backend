package com.sju.roomreservationbackend.common.base.media.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMultipleMediaResDTO<T> extends GeneralResDTO {
    protected List<T> fileInfos;
}
