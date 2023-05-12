package com.sju.roomreservationbackend.domain.room.profile.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateRoomResDTO extends GeneralResDTO {
    private Room updatedRoomProfile;
}
