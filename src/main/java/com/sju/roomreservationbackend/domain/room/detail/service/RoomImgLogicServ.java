package com.sju.roomreservationbackend.domain.room.detail.service;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.common.storage.StorageService;
import com.sju.roomreservationbackend.domain.room.detail.repository.RoomImgRepo;
import com.sju.roomreservationbackend.domain.room.profile.repository.RoomRepo;
import com.sju.roomreservationbackend.domain.room.profile.services.RoomCrudServ;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class RoomImgLogicServ extends RoomCrudServ {
    protected RoomImgRepo roomImgRepo;
    protected static MessageSource msgSrc = MessageConfig.getRoomMsgSrc();

    public RoomImgLogicServ(RoomImgRepo roomImgRepo, RoomRepo roomRepo, StorageService storageServ) {
        super(roomRepo, storageServ);
        this.roomImgRepo = roomImgRepo;
    }
}
