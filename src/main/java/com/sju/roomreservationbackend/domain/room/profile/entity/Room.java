package com.sju.roomreservationbackend.domain.room.profile.entity;

public class Room {
    private Long id;
    private String name;
    private String building;
    private String number;
    private String description;
    private Integer capacity;
    private Boolean whiteboard;
    private Boolean projecter;
    private Integer maxPeakTimeForGrad;
    private Integer maxPeakTimeForStud;
    private Integer maxNormalTimeForGrad;
    private Integer maxNormalTimeForStud;
    private Integer maxLooseTimeForGrad;
    private Integer maxLooseTimeForStud;
}
