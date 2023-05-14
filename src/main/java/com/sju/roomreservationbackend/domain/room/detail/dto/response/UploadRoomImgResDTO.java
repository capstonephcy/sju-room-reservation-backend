package com.sju.roomreservationbackend.domain.room.detail.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.room.detail.entity.RoomImage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadRoomImgResDTO extends GeneralResDTO {
    private RoomImage uploadedRoomImage;
}
