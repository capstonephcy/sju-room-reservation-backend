package com.sju.roomreservationbackend.domain.room.profile.services;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.repository.RoomRepo;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class RoomLogicServ {
    protected RoomRepo roomRepo;
    protected MessageSource msgSrc = MessageConfig.getRoomMsgSrc();

    protected void checkRoomIsValid(Room room) throws Exception {

    }
}
