package com.sju.roomreservationbackend.domain.room.profile.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralPageableResDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchRoomForDeviceResDTO extends GeneralPageableResDTO {
    private Room room;
    private Reservation currentRev;
}
