package com.sju.roomreservationbackend.domain.room.profile.services;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.repository.RoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomLogicServ {
    protected final RoomRepo roomRepo;
    protected final MessageSource msgSrc = MessageConfig.getRoomMsgSrc();

    protected void checkRoomIsValid(Room room) throws Exception {

    }
}
