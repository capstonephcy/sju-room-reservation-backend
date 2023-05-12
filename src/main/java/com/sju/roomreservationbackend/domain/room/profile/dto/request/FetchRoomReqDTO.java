package com.sju.roomreservationbackend.domain.room.profile.dto.request;

import com.sju.roomreservationbackend.common.http.GeneralPageableReqDTO;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchRoomReqDTO extends GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.room.id.positive")
    private Long id;
    @Size(max = 50, message = "valid.room.name.size")
    private String building;
    @Size(max = 50, message = "valid.room.name.size")
    private String name;
}
