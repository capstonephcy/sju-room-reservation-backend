package com.sju.roomreservationbackend.domain.reservation.profile.service;

import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.CreateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.UpdateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

public class ReservationCrudServ extends ReservationLogicServ {
    public Reservation createReservation(Authentication auth, CreateReserveReqDTO reqDTO) {
        return new Reservation();
    }

    public Reservation fetchReservationById(Authentication auth, Long id) {
        return new Reservation();
    }

    public List<Reservation> fetchReservationsByUser(Authentication auth) {
        return new ArrayList<>();
    }

    public List<Reservation> fetchReservationsByUser(Authentication auth, Long userId) {
        return new ArrayList<>();
    }

    public List<Reservation> fetchReservationsByRoom(Authentication auth, Long roomId) {
        return new ArrayList<>();
    }

    public Reservation updateReservation(Authentication auth, UpdateReserveReqDTO reqDTO) {
        return new Reservation();
    }

    public Reservation deleteReservation(Authentication auth, Long reservationId) {
        return new Reservation();
    }
}
