package com.sju.roomreservationbackend.domain.reservation.regular.entity;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.entity.UserProfile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class RegularRev {
    private Long id;
    private Room room;
    private UserProfile user;
    private LocalDate validThru;
    private Integer iteration;
    private RegularRevType type;
    private DayOfWeek dayOfWeek;
    private Integer weekNum;
    private Integer dayOfMonth;
    private LocalTime start;
    private LocalTime end;
    private Boolean confirmed;
}
