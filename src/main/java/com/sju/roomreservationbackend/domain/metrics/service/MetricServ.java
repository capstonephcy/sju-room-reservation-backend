package com.sju.roomreservationbackend.domain.metrics.service;

import com.sju.roomreservationbackend.domain.room.stat.entity.RoomLog;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import com.sju.roomreservationbackend.domain.room.stat.service.RoomLogServ;
import com.sju.roomreservationbackend.domain.room.stat.service.RoomStatServ;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MetricServ {

    private final RoomStatServ roomStatServ;
    private final RoomLogServ roomLogServ;
    
    public RoomStat fetchRoomReservationStatsByDay(Long roomId, LocalDate date) {
        return roomStatServ.fetchRoomReservationStatsByDay(roomId, date);
    }

    public RoomStat fetchRoomReservationStatsByWeek(Long roomId, LocalDate date) {
        // calculate start and end date of the week from date
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // fetch room reservation stats by room and time range
        return calculateRoomStats(roomId, startOfWeek, endOfWeek);
    }

    public RoomStat fetchRoomReservationStatsByMonth(Long roomId, LocalDate date) {
        // calculate start and end date of the month from date
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        return calculateRoomStats(roomId, startOfMonth, endOfMonth);
    }

    private RoomStat calculateRoomStats(Long roomId, LocalDate startOfMonth, LocalDate endOfMonth) {
        // fetch room reservation stats by room and time range
        List<RoomStat> roomStats = roomStatServ.fetchRoomReservationStatsByRoomAndTimeRange(roomId, startOfMonth, endOfMonth);

        // create new single RoomStat for return
        RoomStat monthlyRoomStat = new RoomStat();

        // sum of all RoomStat's fields
        // 1. sum of revCnt
        monthlyRoomStat.setRevCnt(roomStats.stream().mapToInt(RoomStat::getRevCnt).sum());
        // 2. sum of regularRevCnt
        monthlyRoomStat.setNoShowCnt(roomStats.stream().mapToInt(RoomStat::getNoShowCnt).sum());
        // 3. recalculate noShowRate
        monthlyRoomStat.setNoShowRate(roomStats.stream().mapToDouble(RoomStat::getNoShowRate).average().orElse(0));

        return monthlyRoomStat;
    }

    public RoomStat fetchRoomReservationStats(Long roomId, LocalDate date) {
        // RoomStat with date = null is total room stats
        return roomStatServ.fetchRoomReservationStats(roomId);
    }

    public List<RoomStat> fetchRoomReservationStatsByDateRange(Long roomId, LocalDate startDate, LocalDate endDate) {
        return roomStatServ.fetchRoomReservationStatsByRoomAndDateRange(roomId, startDate, endDate);
    }

    public Page<RoomLog> fetchRoomReservationLogs(Long roomId, int pageIdx, int pageLimit) {
        return roomLogServ.fetchRoomReservationLogs(roomId, pageIdx, pageLimit);
    }

    public Page<RoomLog> fetchRoomReservationLogsByRoomAndUser(Long roomId, Long userId, int pageIdx, int pageLimit) {
        return roomLogServ.fetchRoomReservationLogsByRoomAndUser(roomId, userId, pageIdx, pageLimit);
    }

    public Page<RoomLog> fetchRoomReservationLogsByRoomAndTimeRange(Long roomId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int pageIdx, int pageLimit) {
        return roomLogServ.fetchRoomReservationLogsByRoomAndTimeRange(roomId, startDate, endDate, startTime, endTime, pageIdx, pageLimit);
    }
}
