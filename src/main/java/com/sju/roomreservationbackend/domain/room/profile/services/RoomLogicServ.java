package com.sju.roomreservationbackend.domain.room.profile.services;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.repository.RoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class RoomLogicServ {
    protected final RoomRepo roomRepo;
    protected final MessageSource msgSrc = MessageConfig.getRoomMsgSrc();

    protected void checkNameDuplication(String name) throws Exception {
        if (roomRepo.existsByName(name)) {
            throw new Exception(msgSrc.getMessage("error.room.name.dup", null, Locale.ENGLISH));
        }
    }
    protected void checkBuildingAndNumberDuplication(String building, Integer number) throws Exception {
        if (roomRepo.existsByBuildingAndNumber(building,number)) {
            throw new Exception(msgSrc.getMessage("error.room.building.dup", null, Locale.ENGLISH));
        }
    }
}
