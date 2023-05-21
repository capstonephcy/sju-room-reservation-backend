package com.sju.roomreservationbackend.domain.metrics.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchRoomReservationStatsResDTO extends GeneralResDTO {
    private Integer revCnt;
    private Integer noShowCnt;
    private Double noShowRate;

    public void setRoomStat(RoomStat roomStat) {
        this.revCnt = roomStat.getRevCnt();
        this.noShowCnt = roomStat.getNoShowCnt();
        this.noShowRate = roomStat.getNoShowRate();
    }
}
