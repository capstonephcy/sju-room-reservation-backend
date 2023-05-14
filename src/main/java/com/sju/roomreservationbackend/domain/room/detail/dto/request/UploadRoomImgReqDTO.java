package com.sju.roomreservationbackend.domain.room.detail.dto.request;

import com.sju.roomreservationbackend.common.base.media.dto.request.UploadMultipleMediaReqDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadRoomImgReqDTO extends UploadMultipleMediaReqDTO {
    @NotNull(message = "valid.room.id.null")
    @PositiveOrZero(message = "valid.room.id.positive")
    private Long roomId;
}
