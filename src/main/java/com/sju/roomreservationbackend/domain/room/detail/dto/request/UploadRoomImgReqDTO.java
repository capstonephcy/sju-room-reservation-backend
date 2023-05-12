package com.sju.roomreservationbackend.domain.room.detail.dto.request;

import com.sju.roomreservationbackend.common.base.media.dto.request.UploadMediaReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadRoomImgReqDTO extends UploadMediaReqDTO {
    private Long roomId;
}
