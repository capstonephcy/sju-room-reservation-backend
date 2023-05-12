package com.sju.roomreservationbackend.domain.room.profile.dto.request;

import com.sju.roomreservationbackend.common.http.GeneralPageableReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchRoomReqDTO extends GeneralPageableReqDTO {
    private Long id;
    private String building;
    private String name;
}
