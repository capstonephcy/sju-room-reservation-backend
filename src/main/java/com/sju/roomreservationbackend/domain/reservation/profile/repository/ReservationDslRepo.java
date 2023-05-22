package com.sju.roomreservationbackend.domain.reservation.profile.repository;

import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDslRepo {
    Reservation fetchCurrentReservationByRoomId(Long roomId);
}
