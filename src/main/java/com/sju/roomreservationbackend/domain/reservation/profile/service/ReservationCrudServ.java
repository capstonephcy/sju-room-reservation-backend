package com.sju.roomreservationbackend.domain.reservation.profile.service;

import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.CheckInReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.CreateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.UpdateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.reservation.profile.repository.ReservationRepo;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.service.RoomCrudServ;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ReservationCrudServ extends ReservationLogicServ {

    private final UserProfileCrudServ userProfileCrudServ;
    private final RoomCrudServ roomCrudServ;

    public ReservationCrudServ(
            ReservationRepo reservationRepo,
            UserProfileCrudServ userProfileCrudServ,
            RoomCrudServ roomCrudServ
    ) {
        super(reservationRepo);
        this.userProfileCrudServ = userProfileCrudServ;
        this.roomCrudServ = roomCrudServ;
    }

    @Transactional
    public Reservation createReservation(Authentication auth, CreateReserveReqDTO reqDTO) throws Exception {
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);
        Room room = roomCrudServ.fetchRoomById(auth, reqDTO.getRoomId());

        // create reservation entity
        Reservation reservation = reqDTO.toEntity(room);

        // validate reservation
        if(!isReservationIsValid(reservation)) {
            throw new Exception(
                    reservationMsgSrc.getMessage("valid.reservation.inValid", null, Locale.ENGLISH)
            );
        }

        // validate reservation time range is allowed for user
        if(!isResTimeIsAllowedForUser(user, reservation)) {
            throw new Exception(
                    reservationMsgSrc.getMessage("valid.reservation.timeRange.notAllowed", null, Locale.ENGLISH)
            );
        }

        // check reservation is available
        if(isReservationIsDuplicated(reservation)) {
            throw new Exception(
                    reservationMsgSrc.getMessage("error.reservation.alreadyReserved", null, Locale.ENGLISH)
            );
        }

        // create reservation
        return reservationRepo.save(reservation);
    }

    public Reservation fetchReservationById(Long id) throws Exception {
        return reservationRepo.findById(id)
                .orElseThrow(() -> new Exception(
                        reservationMsgSrc.getMessage("error.reservation.notExists", null, Locale.ENGLISH)
                ));
    }

    public List<Reservation> fetchReservationsByUser(Long userId) {
        return reservationRepo.findAllByUserId(userId);
    }

    public List<Reservation> fetchReservationsByRoom(Long roomId) {
        return reservationRepo.findAllByRoomId(roomId);
    }

    public List<Reservation> fetchReservationsByTimeRange(
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime
    ) {
        return reservationRepo.findAllByDateBetweenAndStartLessThanEqualAndEndGreaterThanEqual(startDate, endDate, startTime, endTime);
    }

    @Transactional
    public Reservation updateReservation(Authentication auth, UpdateReserveReqDTO reqDTO) throws Exception {
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);

        // get reservation from id
        Reservation reservation = this.fetchReservationById(reqDTO.getId());

        // check if user is owner of reservation
        if(isUserOwnsReservation(user, reservation)) {
            throw new IllegalArgumentException("error.reservation.notOwner");
        }

        // fetch attendants from user ids
        List<UserProfile> attendants = new ArrayList<>();
        for(Long userId : reqDTO.getAttendants()) {
            attendants.add(userProfileCrudServ.fetchUserProfileById(userId));
        }

        // update columns
        reservation.setAttendants(attendants);

        return reservationRepo.save(reservation);
    }

    @Transactional
    public void deleteReservation(Authentication auth, Long reservationId) throws Exception {
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);

        // get reservation from id
        Reservation reservation = this.fetchReservationById(reservationId);

        // check if user is owner of reservation
        if(isUserOwnsReservation(user, reservation)) {
            throw new IllegalArgumentException("error.reservation.notOwner");
        }

        // delete reservation
        reservationRepo.delete(reservation);
    }

    @Transactional
    public void checkInReservation(Authentication auth, CheckInReserveReqDTO reqDto) throws Exception {
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);

        // get reservation from id
        Reservation reservation = this.fetchReservationById(reqDto.getId());

        // check if user is owner of reservation
        if(isUserOwnsReservation(user, reservation)) {
            throw new IllegalArgumentException("error.reservation.notOwner");
        }

        // check in
        checkIn(reservation, reqDto.getCheckInCode());
    }
}
