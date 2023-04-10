package com.sju.roomreservationbackend.domain.reservation.profile.entity;

import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.entity.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private Room room;
    private UserProfile user;
    private RegularRev regularRev;
    private RevType revType;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Boolean noShow;
}
