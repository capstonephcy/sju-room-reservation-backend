package com.sju.roomreservationbackend.domain.room.profile.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateRoomReqDTO {
    @NotBlank(message = "valid.room.name.blank")
    @Size(max = 50, message = "valid.room.name.size")
    private String name;
    @NotBlank(message = "valid.room.building.blank")
    @Size(max = 50, message = "valid.room.building.size")
    private String building;
    @NotNull(message = "valid.room.number.null")
    private Integer number;
    @Size(max = 1000, message = "valid.room.description.size")
    private String description;
    @NotNull(message = "valid.room.capacity.null")
    private Integer capacity;
    @NotNull(message = "valid.room.whiteboard.null")
    private Boolean whiteboard;
    @NotNull(message = "valid.room.projector.null")
    private Boolean projector;
    @NotNull(message = "valid.room.time.null")
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxPeakTimeForGrad;
    @NotNull(message = "valid.room.time.null")
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxPeakTimeForStud;
    @NotNull(message = "valid.room.time.null")
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxNormalTimeForGrad;
    @NotNull(message = "valid.room.time.null")
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxNormalTimeForStud;
    @NotNull(message = "valid.room.time.null")
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxLooseTimeForGrad;
    @NotNull(message = "valid.room.time.null")
    @Max(value = 24, message = "valid.room.time.size")
    @Min(value = 1, message = "valid.room.time.size")
    private Integer maxLooseTimeForStud;
}
