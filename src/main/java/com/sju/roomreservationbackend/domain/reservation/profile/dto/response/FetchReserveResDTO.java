package com.sju.roomreservationbackend.domain.reservation.profile.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchReserveResDTO extends GeneralResDTO {
    List<Reservation> reservations;
}
