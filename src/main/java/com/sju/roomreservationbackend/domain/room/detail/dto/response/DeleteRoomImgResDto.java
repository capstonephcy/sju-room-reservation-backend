package com.sju.roomreservationbackend.domain.room.detail.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteRoomImgResDto extends GeneralResDTO {
    private Long deleteRoomImgId;
}
