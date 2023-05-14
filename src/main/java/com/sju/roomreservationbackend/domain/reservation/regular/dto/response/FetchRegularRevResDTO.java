package com.sju.roomreservationbackend.domain.reservation.regular.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralPageableResDTO;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchRegularRevResDTO extends GeneralPageableResDTO {
    List<RegularRev> regularRevs;
}
