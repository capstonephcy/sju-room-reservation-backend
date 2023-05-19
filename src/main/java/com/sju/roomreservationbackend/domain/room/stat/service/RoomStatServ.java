package com.sju.roomreservationbackend.domain.room.stat.service;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import com.sju.roomreservationbackend.domain.room.stat.repository.RoomStatRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class RoomStatServ {

    private final RoomStatRepo roomStatRepo;
    private final MessageSource msgSrc = MessageConfig.getRoomMsgSrc();

    public RoomStat fetchRoomStatByRoomId(Long roomId) throws Exception {
        return roomStatRepo.findById(roomId).orElseThrow(
                () -> new Exception(msgSrc.getMessage("error.room.notExist", null, Locale.ENGLISH))
        );
    }
}
