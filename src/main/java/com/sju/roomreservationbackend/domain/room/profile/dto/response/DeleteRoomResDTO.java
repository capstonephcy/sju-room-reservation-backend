package com.sju.roomreservationbackend.domain.room.profile.dto.response;


import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteRoomResDTO extends GeneralResDTO {
    private Long deletedRoomId;
}
