package com.sju.roomreservationbackend.domain.reservation.regular.service;

import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.reservation.profile.repository.ReservationRepo;
import com.sju.roomreservationbackend.domain.reservation.profile.service.ReservationCrudServ;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import com.sju.roomreservationbackend.domain.reservation.regular.repository.RegularRevRepo;
import com.sju.roomreservationbackend.domain.room.profile.services.RoomCrudServ;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegularRevLogicServ {
    protected RegularRevRepo regularRevRepo;

    protected void checkRegularRevIsValid(RegularRev regularRev) throws Exception {

    }

    protected void checkRegularRevIsDuplicated(RegularRev regularRev) throws Exception {

    }

    protected void evalRegularRevIsNeedToConfirm(RegularRev regularRev) {

    }

    protected List<Reservation> generateRevForRegularRev(RegularRev reservation) {
        return new ArrayList<>();
    }
}
