package com.sju.roomreservationbackend.domain.reservation.profile.service;

import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.reservation.profile.repository.ReservationRepo;

public class ReservationLogicServ {
    protected ReservationRepo reservationRepo;

    protected void checkReservationIsValid(Reservation reservation) throws Exception {

    }

    protected void checkReservationIsDuplicated(Reservation reservation) throws Exception {

    }

    protected void checkIn(Reservation reservation, String verifyCode) {

    }

    protected void sendNotification(Reservation reservation) {

    }
}
