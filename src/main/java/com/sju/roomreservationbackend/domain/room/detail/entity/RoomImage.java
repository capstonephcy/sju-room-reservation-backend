package com.sju.roomreservationbackend.domain.room.detail.entity;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;

import java.time.LocalDateTime;

public class RoomImage {
    private Long id;
    private Room room;
    private String fileName;
    private String fileSize;
    private String fileUrl;
    private LocalDateTime timestamp;
}
