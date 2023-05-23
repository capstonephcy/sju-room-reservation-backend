package com.sju.roomreservationbackend.domain.reservation.profile.service;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.common.tempcode.TempCodeGenerator;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.reservation.profile.repository.ReservationRepo;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class ReservationLogicServ {

    protected final ReservationRepo reservationRepo;
    protected final MessageSource reservationMsgSrc = MessageConfig.getReservationMsgSrc();

    protected boolean isReservationIsValid(Reservation reservation) {
        // check if time range is valid
        return !reservation.getStart().isAfter(reservation.getEnd());
    }

    // TODO(krapie): refactor this method into smaller methods
    protected boolean isResTimeIsAllowedForUser(UserProfile user, Reservation reservation) {
        // 1. if user is professor, there is no limit
        if(user.getPermissions().contains(Permission.PROFESSOR)) {
            return true;
        }

        // 2. check if user is allowed to reserve at this point of date
        if(user.getPermissions().contains(Permission.GRADUATED)) {
            // if user is graduated, user can reserve up to 1-week period
            return reservation.getDate().isBefore(LocalDate.now().plusDays(7));
        }
        if(user.getPermissions().contains(Permission.STUDENT)) {
            // if user is student, user can reserve before 2 days period
            return reservation.getDate().isBefore(LocalDate.now().plusDays(2));
        }

        // 3. check room's state (max/normal/loose), and check if user is allowed to reserve
        Room room = reservation.getRoom();
        switch(room.getCongestion()) {
            case HIGH:
                if (user.getPermissions().contains(Permission.GRADUATED)) {
                    // return false when range of start time and end time is greater than room's maxPeakTimeForGrad
                    return reservation.getStart().plusMinutes(room.getMaxPeakTimeForGrad()).isBefore(reservation.getEnd());
                }
                else { // user is student (Permission.STUDENT)
                    // return false when range of start time and end time is greater than room's maxPeakTimeForStud
                    return reservation.getStart().plusMinutes(room.getMaxPeakTimeForStud()).isBefore(reservation.getEnd());
                }
            case MEDIUM:
                if (user.getPermissions().contains(Permission.GRADUATED)) {
                    // return false when range of start time and end time is greater than room's normalPeakTimeForGrad
                    return reservation.getStart().plusMinutes(room.getMaxNormalTimeForGrad()).isBefore(reservation.getEnd());
                }
                else { // user is student (Permission.STUDENT)
                    // return false when range of start time and end time is greater than room's normalPeakTimeForStud
                    return reservation.getStart().plusMinutes(room.getMaxNormalTimeForStud()).isBefore(reservation.getEnd());
                }
            case LOW:
                if (user.getPermissions().contains(Permission.GRADUATED)) {
                    // return false when range of start time and end time is greater than room's loosePeakTimeForGrad
                    return reservation.getStart().plusMinutes(room.getMaxLooseTimeForGrad()).isBefore(reservation.getEnd());
                }
                else { // user is student (Permission.STUDENT)
                    // return false when range of start time and end time is greater than room's loosePeakTimeForStud
                    return reservation.getStart().plusMinutes(room.getMaxLooseTimeForStud()).isBefore(reservation.getEnd());
                }
        }

        return true;
    }

    protected boolean isReservationIsDuplicated(Reservation reservation) {
        // check if reservation's time range is overlaps existing reservations in db.
        return reservationRepo.existsByDateAndStartGreaterThanEqualAndEndLessThanEqual(
                reservation.getDate(),
                reservation.getStart(),
                reservation.getEnd()
        );
    }

    protected boolean userNotOwnsReservation(UserProfile user, Reservation reservation) {
        return !user.getId().equals(reservation.getRevOwner().getId());
    }

    protected void checkIn(Reservation reservation, String checkInCode) {
        // check if reservation's date is same as today,
        // and check current time is between reservation's start and end time
        if(!LocalDate.now().isEqual(reservation.getDate()) || !LocalTime.now().isAfter(reservation.getStart()) || !LocalTime.now().isBefore(reservation.getEnd())) {
            throw new IllegalArgumentException(reservationMsgSrc.getMessage("error.reservation.checkIn.expired", null, Locale.ENGLISH));
        }

        // if verifyCode is same as reservation's verifyCode, update checkIn to true
        if(!reservation.getCheckInCode().equals(checkInCode)) {
            throw new IllegalArgumentException(reservationMsgSrc.getMessage("error.reservation.verificationCode.notMatch", null, Locale.ENGLISH));
        }

        reservation.setCheckIn(true);
        reservationRepo.save(reservation);
    }

    protected String generateCheckInCode() {
        char[] authCodeCharSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        return TempCodeGenerator.generate(authCodeCharSet, 6);
    }
}
