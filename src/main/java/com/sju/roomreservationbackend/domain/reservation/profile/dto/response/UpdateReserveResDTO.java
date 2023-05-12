package com.sju.roomreservationbackend.domain.reservation.profile.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateReserveResDTO extends GeneralResDTO {
    private Reservation reservation;
}
