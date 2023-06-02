package com.sju.roomreservationbackend.domain.room.profile.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateRoomReqDTO {
    @NotNull(message = "valid.room.id.null")
    @PositiveOrZero(message = "valid.room.id.positive")
    private Long id;

    @Size(max = 50, message = "valid.room.name.size")
    private String name;

    @Size(max = 1000, message = "valid.room.description.size")
    private String description;

    private Integer capacity;
    private Boolean whiteboard;
    private Boolean projector;

    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxPeakTimeForGrad;
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxPeakTimeForStud;
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxNormalTimeForGrad;
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxNormalTimeForStud;
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxLooseTimeForGrad;
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxLooseTimeForStud;

    private String congestion;
}
