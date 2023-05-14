package com.sju.roomreservationbackend.domain.room.detail.dto.request;

import com.sju.roomreservationbackend.common.base.media.dto.request.UploadMediaReqDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadRoomImgReqDTO extends UploadMediaReqDTO {
    @NotNull(message = "valid.room.id.null")
    @PositiveOrZero(message = "valid.room.id.positive")
    private Long roomId;
}
