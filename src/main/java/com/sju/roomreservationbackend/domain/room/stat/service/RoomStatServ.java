package com.sju.roomreservationbackend.domain.room.stat.service;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import com.sju.roomreservationbackend.domain.room.stat.repository.RoomStatRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomStatServ {

    private final RoomStatRepo roomStatRepo;
    private final MessageSource msgSrc = MessageConfig.getRoomMsgSrc();

    @Transactional
    public void createRoomStat(RoomStat roomStat) {
        roomStatRepo.save(roomStat);
    }

    public RoomStat fetchRoomReservationStatsByDay(Long roomId, LocalDate date) {
        return roomStatRepo.findByRoomIdAndDate(roomId, date);
    }

    public RoomStat fetchRoomReservationStats(Long roomId) {
        return roomStatRepo.findByRoomIdAndDate(roomId, null);
    }

    public List<RoomStat> fetchRoomReservationStatsByRoomAndTimeRange(Long roomId, LocalDate startOfWeek, LocalDate endOfWeek) {
        return roomStatRepo.findAllByRoomIdAndDateBetween(roomId, startOfWeek, endOfWeek);
    }
}
