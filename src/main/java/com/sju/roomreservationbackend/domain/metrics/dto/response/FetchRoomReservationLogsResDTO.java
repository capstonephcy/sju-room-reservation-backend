package com.sju.roomreservationbackend.domain.metrics.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralPageableResDTO;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchRoomReservationLogsResDTO extends GeneralPageableResDTO {
    List<RoomLog> roomLogs;
}
