package com.sju.roomreservationbackend.domain.reservation.regular.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateRegularRevResDTO extends GeneralResDTO {
    private RegularRev regularRev;
}
