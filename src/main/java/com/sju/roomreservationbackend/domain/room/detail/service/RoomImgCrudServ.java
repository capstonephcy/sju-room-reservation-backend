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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class RoomImgCrudServ extends RoomImgLogicServ{

    public RoomImgCrudServ(RoomImgRepo roomImgRepo, RoomRepo roomRepo, StorageService storageServ) {
        super(roomImgRepo, roomRepo, storageServ);
    }

    @Transactional
    public List<RoomImage> createRoomImg(UploadRoomImgReqDTO reqDTO) throws Exception {
        List<RoomImage> images = new ArrayList<>();

        for (MultipartFile file : reqDTO.getFiles()) {
            images.add(this.createRoomImg(reqDTO.getRoomId(), file));
        }
        return images;
    }

    @Transactional
    public RoomImage createRoomImg(Long roomId, MultipartFile file) throws Exception {
        FileMetadata metadata = this.storageServ.saveFile(file, FileType.MEDIA_PHOTO, "room", roomId);

        RoomImage roomImg = RoomImage.builder()
                .room(this.fetchRoomById(roomId))
                .type(metadata.getType())
                .fileName(metadata.getFileName())
                .fileSize(metadata.getFileSize())
                .fileURL(metadata.getFileURL())
                .timestamp(LocalDateTime.now())
                .build();
        return roomImgRepo.save(roomImg);
    }

    public List<RoomImage> fetchRoomImgById(Long roomId) {
        return roomImgRepo.findAllByRoom_Id(roomId);
    }

    public List<List<RoomImage>> fetchRoomImgByIds(List<Long> roomIds) {
        return roomIds.stream().map(id -> roomImgRepo.findAllByRoom_Id(id)).collect(Collectors.toList());
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
