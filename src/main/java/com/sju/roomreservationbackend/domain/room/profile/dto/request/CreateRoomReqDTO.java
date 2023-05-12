package com.sju.roomreservationbackend.domain.room.profile.dto.request;

import lombok.Data;

@Data
public class CreateRoomReqDTO {
    private String name;
    private String building;
    private Integer number;
    private String description;
    private Integer capacity;
    private Boolean whiteboard;
    private Boolean projector;
    private Integer maxPeakTimeForGrad;
    private Integer maxPeakTimeForStud;
    private Integer maxNormalTimeForGrad;
    private Integer maxNormalTimeForStud;
    private Integer maxLooseTimeForGrad;
    private Integer maxLooseTimeForStud;
}
