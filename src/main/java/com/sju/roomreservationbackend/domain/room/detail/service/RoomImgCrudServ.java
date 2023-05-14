package com.sju.roomreservationbackend.domain.room.detail.service;

import com.sju.roomreservationbackend.common.base.media.FileMetadata;
import com.sju.roomreservationbackend.common.storage.FileType;
import com.sju.roomreservationbackend.common.storage.StorageService;
import com.sju.roomreservationbackend.domain.room.detail.dto.request.UploadRoomImgReqDTO;
import com.sju.roomreservationbackend.domain.room.detail.entity.RoomImage;
import com.sju.roomreservationbackend.domain.room.detail.repository.RoomImgRepo;
import com.sju.roomreservationbackend.domain.room.profile.repository.RoomRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class RoomImgCrudServ extends RoomImgLogicServ{

    public RoomImgCrudServ(RoomImgRepo roomImgRepo, RoomRepo roomRepo, StorageService storageServ) {
        super(roomImgRepo, roomRepo, storageServ);
    }

    @Transactional
    public RoomImage createRoomImg(UploadRoomImgReqDTO reqDTO) throws Exception {
        MultipartFile file = reqDTO.getFile();
        FileMetadata metadata = this.storageServ.saveFile(file, FileType.MEDIA_PHOTO, "room", reqDTO.getRoomId());

        RoomImage roomImg = RoomImage.builder()
                .room(this.fetchRoomById(reqDTO.getRoomId()))
                .type(metadata.getType())
                .fileName(metadata.getFileName())
                .fileSize(metadata.getFileSize())
                .fileURL(metadata.getFileURL())
                .timestamp(LocalDateTime.now())
                .build();
        return roomImgRepo.save(roomImg);
    }

    @Transactional
    public Long deleteRoomImg(Long roomImgId) throws Exception {
        RoomImage target = roomImgRepo.findById(roomImgId).orElseThrow(
                () -> new Exception(msgSrc.getMessage("error.room.image.notExist", null, Locale.ENGLISH))
        );

        storageServ.deleteFile(target.getFileURL());
        roomImgRepo.delete(target);
        return target.getId();
    }
}
