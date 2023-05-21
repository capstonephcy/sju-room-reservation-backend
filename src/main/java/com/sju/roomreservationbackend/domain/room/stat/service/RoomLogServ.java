package com.sju.roomreservationbackend.domain.room.stat.service;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomAction;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomLog;
import com.sju.roomreservationbackend.domain.room.stat.repository.RoomLogRepo;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomLogServ {

    private final RoomLogRepo roomLogRepo;
    private final MessageSource msgSrc = MessageConfig.getRoomMsgSrc();


    public Page<RoomLog> fetchRoomReservationLogs(Long roomId, int pageIdx, int pageLimit) {
        return roomLogRepo.findAllByRoomId(roomId, PageRequest.of(pageIdx, pageLimit));
    }

    public Page<RoomLog> fetchRoomReservationLogsByRoomAndUser(Long roomId, Long userId, int pageIdx, int pageLimit) {
        return roomLogRepo.findAllByRoomIdAndUserId(roomId, userId, PageRequest.of(pageIdx, pageLimit));
    }

    public Page<RoomLog> fetchRoomReservationLogsByRoomAndTimeRange(Long roomId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int pageIdx, int pageLimit) {
        return roomLogRepo.findAllByRoomIdAndDateBetweenAndTimeBetween(roomId, startDate, endDate, startTime, endTime, PageRequest.of(pageIdx, pageLimit));
    }

    @Transactional
    public void createRoomLog(Room room, UserProfile revOwner, RoomAction reserve) {
        RoomLog roomLog = RoomLog.builder()
                .room(room)
                .user(revOwner)
                .action(reserve)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .build();

        roomLogRepo.save(roomLog);
    }

    public List<RoomLog> fetchRoomLogsByRoomAndDate(Room room, LocalDate date) {
        return roomLogRepo.findAllByRoomAndDate(room, date);
    }
}
