package com.sju.roomreservationbackend.domain.room.stat.entity;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;

import java.time.LocalDate;
import java.time.LocalTime;

public class RoomLog {
    private Long id;
    private Room room;
    private LocalDate date;
    private LocalTime time;
    private UserProfile user;
    private RoomAction action;
}
