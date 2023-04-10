package com.sju.roomreservationbackend.domain.reservation.profile;

import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.*;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.response.*;
import com.sju.roomreservationbackend.domain.reservation.profile.service.ReservationCrudServ;

public class ReservationAPI {
    private ReservationCrudServ reservationCrudServ;

    public CreateReserveResDTO createReservation(CreateReserveReqDTO reqDTO) {
        return new CreateReserveResDTO();
    }

    public FetchReserveResDTO fetchReservation(FetchReserveReqDTO reqDTO, FetchReserveReqOptionType optionType) {
        return new FetchReserveResDTO();
    }

    public UpdateReserveResDTO updateReservation(UpdateReserveReqDTO reqDTO) {
        return new UpdateReserveResDTO();
    }

    public DeleteReserveResDTO deleteReservation(DeleteReserveReqDTO reqDTO) {
        return new DeleteReserveResDTO();
    }

    public CheckInReserveResDTO checkInReservation(CheckInReserveReqDTO reqDTO) {
        return new CheckInReserveResDTO();
    }
}
