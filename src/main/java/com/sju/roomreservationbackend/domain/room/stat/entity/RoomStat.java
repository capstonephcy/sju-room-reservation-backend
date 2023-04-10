package com.sju.roomreservationbackend.domain.room.stat.entity;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;

import java.time.LocalDate;
import java.util.List;

public class RoomStat {
    private Long id;
    private Room room;
    private LocalDate date;
    private Integer revCnt;
    private Integer regularRevCnt;
    private Double noShowRate;
    private Double loadRate;
    private List<Integer> peakHours;
    private List<Integer> normalHours;
    private List<Integer> looseHours;
}
