package com.sju.roomreservationbackend.domain.metrics.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchRoomReservationMetricsResDTO extends GeneralResDTO {

}
