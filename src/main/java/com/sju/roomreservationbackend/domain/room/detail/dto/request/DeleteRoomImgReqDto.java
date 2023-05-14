package com.sju.roomreservationbackend.domain.room.detail.dto.request;

import com.sju.roomreservationbackend.common.base.media.dto.request.DeleteMediaReqDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteRoomImgReqDto extends DeleteMediaReqDTO {
    @NotNull(message = "valid.room.image.id.null")
    @PositiveOrZero(message = "valid.room.image.id.positive")
    private Long id;
}
