package com.sju.roomreservationbackend.domain.room.stat.service;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomLog;
import com.sju.roomreservationbackend.domain.room.stat.repository.RoomLogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomLogServ {

    private final RoomLogRepo roomLogRepo;
    private final MessageSource msgSrc = MessageConfig.getRoomMsgSrc();

    public List<RoomLog> fetchRoomLogByRoomId(Long roomId) throws Exception {
        return roomLogRepo.findAllByRoomId(roomId);
    }

    public List<RoomLog> fetchRoomLogByRoomIdAndDate(Long roomId, LocalDate date) throws Exception {
        return roomLogRepo.findAllByRoomIdAndDate(roomId, date);
    }
}
