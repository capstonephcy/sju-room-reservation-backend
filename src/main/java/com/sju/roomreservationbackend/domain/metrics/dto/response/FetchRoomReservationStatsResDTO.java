package com.sju.roomreservationbackend.domain.metrics.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchRoomReservationStatsResDTO extends GeneralResDTO {
    private Integer revCnt;
    private Integer noShowCnt;
    private Double noShowRate;

    private Double loadRate;

    private List<RoomStat> roomStats;

    public void setRoomStat(RoomStat roomStat) {
        this.revCnt = roomStat.getRevCnt();
        this.noShowCnt = roomStat.getNoShowCnt();
        this.noShowRate = roomStat.getNoShowRate();
        this.loadRate = roomStat.getLoadRate();
    }

    public void setRoomStats(List<RoomStat> roomStats) {
        this.roomStats = roomStats;
    }
}
