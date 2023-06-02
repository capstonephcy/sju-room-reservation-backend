package com.sju.roomreservationbackend.domain.reservation.profile.service;

import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.CheckInReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.CreateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.UpdateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.reservation.profile.repository.ReservationRepo;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.services.RoomCrudServ;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomAction;
import com.sju.roomreservationbackend.domain.room.stat.service.RoomLogServ;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ReservationCrudServ extends ReservationLogicServ {

    private final UserProfileCrudServ userProfileCrudServ;
    private final RoomCrudServ roomCrudServ;
    private final RoomLogServ roomLogServ;

    public ReservationCrudServ(
            ReservationRepo reservationRepo,
            UserProfileCrudServ userProfileCrudServ,
            RoomCrudServ roomCrudServ,
            RoomLogServ roomLogServ
    ) {
        super(reservationRepo);
        this.userProfileCrudServ = userProfileCrudServ;
        this.roomCrudServ = roomCrudServ;
        this.roomLogServ = roomLogServ;
    }

    @Transactional
    public Reservation createReservation(Authentication auth, CreateReserveReqDTO reqDTO) throws Exception {
        // get current user and room
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);
        Room room = roomCrudServ.fetchRoomById(reqDTO.getRoomId());

        // get list of users by ids using lambda
        List<UserProfile> attendants = new ArrayList<>();
        for(Long id : reqDTO.getAttendants()) {
            attendants.add(userProfileCrudServ.fetchUserProfileById(id));
        }
        attendants.add(user);

        // create check in code
        String checkInCode = generateCheckInCode();

        // create reservation entity
        Reservation reservation = reqDTO.toEntity(user, room, attendants, checkInCode);

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
        reservation = reservationRepo.save(reservation);

        // create room log for reservation creation
        roomLogServ.createRoomLog(reservation.getRoom(), reservation.getRevOwner(), RoomAction.RESERVE);

        // update rev owner and attendants reserveCnt
        userProfileCrudServ.updateReserveCnt(reservation.getRevOwner());
        for(UserProfile attendant : reservation.getAttendants()) {
            userProfileCrudServ.updateReserveCnt(attendant);
        }

        return reservation;
    }

    public Reservation fetchReservationById(Long id) throws Exception {
        return reservationRepo.findById(id)
                .orElseThrow(() -> new Exception(
                        reservationMsgSrc.getMessage("error.reservation.notExists", null, Locale.ENGLISH)
                ));
    }

    public Page<Reservation> fetchReservationsByUser(Long userId, int pageIdx, int pageLimit) {
        return reservationRepo.findAllByRevOwnerIdOrAttendantsId(userId, userId, PageRequest.of(pageIdx, pageLimit));
    }

    public Page<Reservation> fetchReservationsByRoom(Long roomId, int pageIdx, int pageLimit) {
        return reservationRepo.findAllByRoomId(roomId, PageRequest.of(pageIdx, pageLimit));
    }

    public Reservation fetchCurrentReservationByRoom(Long roomId) {
        return reservationRepo.fetchCurrentReservationByRoomId(roomId);
    }

    public Page<Reservation> fetchReservationsByRegularRevId(Long regularRevId, int pageIdx, int pageLimit) {
        return reservationRepo.findAllByRegularRevId(regularRevId, PageRequest.of(pageIdx, pageLimit));
    }

    public Page<Reservation> fetchReservationsByTimeRange(
            Long userId,
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime,
            int pageIdx,
            int pageLimit
    ) throws Exception {
        // if user is empty, fetch all reservations
        if(userId == null) {
            return reservationRepo.findAllByDateBetweenAndStartGreaterThanEqualAndEndLessThanEqual(startDate, endDate, startTime, endTime, PageRequest.of(pageIdx, pageLimit));
        } else {
            UserProfile user = userProfileCrudServ.fetchUserProfileById(userId);
            return reservationRepo.findAllByDateBetweenAndStartGreaterThanEqualAndEndLessThanEqualAndRevOwnerAndAttendantsContains(startDate, endDate, startTime, endTime, user, user, PageRequest.of(pageIdx, pageLimit));
        }
    }

    public List<Reservation> fetchReservationsByRoomAndDate(Room room, LocalDate date) {
        return reservationRepo.findAllByRoomAndDate(room, date);
    }

    public List<Reservation> fetchReservationByRoomAndDateAndTimeLeft(Room room, LocalDate date, LocalTime time) {
        System.out.println("get reservations for notification: " + room.getId() + " " + date + " " + time);
        return reservationRepo.findByRoomAndDateAndStart(room, date, time.truncatedTo(ChronoUnit.MINUTES));
    }

    @Transactional
    public Reservation updateReservation(Authentication auth, UpdateReserveReqDTO reqDTO) throws Exception {
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);

        // get reservation from id
        Reservation reservation = this.fetchReservationById(reqDTO.getId());

        // check if user is owner of reservation
        if(userNotOwnsReservation(user, reservation)) {
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
        if(userNotOwnsReservation(user, reservation)) {
            throw new IllegalArgumentException("error.reservation.notOwner");
        }

        // check if current time is passed reservation's end time
        if(reservationTimePassed(reservation)) {
            throw new IllegalArgumentException("error.reservation.alreadyPassed");
        }

        // delete reservation
        reservationRepo.delete(reservation);

        // create room log for reservation deletion
        roomLogServ.createRoomLog(reservation.getRoom(), reservation.getRevOwner(), RoomAction.CANCEL);
    }

    private boolean reservationTimePassed(Reservation reservation) {
        return LocalDate.now(ZoneId.of("Asia/Seoul")).isAfter(reservation.getDate()) || LocalTime.now(ZoneId.of("Asia/Seoul")).isAfter(reservation.getEnd());
    }

    @Transactional
    public void checkInReservation(Authentication auth, CheckInReserveReqDTO reqDto) throws Exception {
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);

        // get reservation from id
        Reservation reservation = this.fetchReservationById(reqDto.getId());

        // check if user is owner of reservation
        if(userNotOwnsReservation(user, reservation)) {
            throw new IllegalArgumentException("error.reservation.notOwner");
        }

        // check in
        checkIn(reservation, reqDto.getCheckInCode());

        // create room log for reservation check in
        roomLogServ.createRoomLog(reservation.getRoom(), reservation.getRevOwner(), RoomAction.CHECKIN);
    }

    @Transactional
    public void updateReservationNoShow(Reservation reservation) {
        reservation.setNoShow(true);
        reservationRepo.save(reservation);
    }
}
