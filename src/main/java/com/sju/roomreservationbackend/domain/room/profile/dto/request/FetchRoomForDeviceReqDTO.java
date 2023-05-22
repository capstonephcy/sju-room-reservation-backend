package com.sju.roomreservationbackend.domain.room.profile.dto.request;

import com.sju.roomreservationbackend.common.http.GeneralPageableReqDTO;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchRoomForDeviceReqDTO extends GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.room.id.positive")
    private Long id;
}
