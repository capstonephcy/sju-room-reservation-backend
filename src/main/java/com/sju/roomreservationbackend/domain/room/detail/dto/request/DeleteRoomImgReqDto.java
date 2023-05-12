package com.sju.roomreservationbackend.domain.room.detail.dto.request;

import com.sju.roomreservationbackend.common.base.media.dto.request.DeleteMediaReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteRoomImgReqDto extends DeleteMediaReqDTO {
    private Long id;
}
