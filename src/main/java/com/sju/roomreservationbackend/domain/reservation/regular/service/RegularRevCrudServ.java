package com.sju.roomreservationbackend.domain.reservation.regular.service;

import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.CreateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.UpdateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.reservation.profile.service.ReservationCrudServ;
import com.sju.roomreservationbackend.domain.reservation.regular.dto.request.CreateRegularRevReqDTO;
import com.sju.roomreservationbackend.domain.reservation.regular.dto.request.UpdateRegularRevReqDTO;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import com.sju.roomreservationbackend.domain.reservation.regular.repository.RegularRevRepo;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.services.RoomCrudServ;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class RegularRevCrudServ extends RegularRevLogicServ {

    private final UserProfileCrudServ userProfileCrudServ;
    private final RoomCrudServ roomCrudServ;
    private final ReservationCrudServ reservationCrudServ;
    public RegularRevCrudServ(RegularRevRepo regularRevRepo, UserProfileCrudServ userProfileCrudServ, RoomCrudServ roomCrudServ, ReservationCrudServ reservationCrudServ) {
        super(regularRevRepo);
        this.userProfileCrudServ = userProfileCrudServ;
        this.roomCrudServ = roomCrudServ;
        this.reservationCrudServ = reservationCrudServ;
    }

    public RegularRev createRegularRev(Authentication auth, CreateRegularRevReqDTO reqDTO) throws Exception {
        // get current user and room
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);
        Room room = roomCrudServ.fetchRoomById(reqDTO.getRoomId());

        // create regularRev entity
        RegularRev regularRev = reqDTO.toEntity(room, user);

        // validate regularRev is allowed
        if(!isRegularRevIsAllowed(user, regularRev)) {
            throw new Exception(
                    reservationMsgSrc.getMessage("error.regularRev.notAllowed", null, Locale.ENGLISH)
            );
        }

        // save regularRev
        regularRev = regularRevRepo.save(regularRevRepo.save(regularRev));

        // create reservation create request dto
        List<CreateReserveReqDTO> reserveReqDTOs = generateRevReqDtoForRegularRev(regularRev, reqDTO.getAttendants());

        // save reservations
        // if fails, rollback and throw error
        // TODO(krapie): introduce transactional, batch insert
        try {
            for (CreateReserveReqDTO reserveReqDTO : reserveReqDTOs) {
                reservationCrudServ.createReservation(auth, reserveReqDTO);
            }
        } catch (Exception e) {
            // delete regularRev
            deleteRegularRev(auth, regularRev.getId());
            throw e;
        }

        // save regularRev
        return regularRev;
    }

    public RegularRev fetchRegularRevById(Long id) throws Exception {
        return regularRevRepo.findById(id)
                .orElseThrow(() -> new Exception(
                        reservationMsgSrc.getMessage("error.regularRev.notExists", null, Locale.ENGLISH)
                ));
    }

    public Page<RegularRev> fetchRegularRevsByUser(Long userId, int pageIdx, int pageLimit) {
        return regularRevRepo.findAllByRevOwnerId(userId, PageRequest.of(pageIdx, pageLimit));
    }

    public Page<RegularRev> fetchRegularRevsByRoom(Long roomId, int pageIdx, int pageLimit) {
        return regularRevRepo.findAllByRoomId(roomId, PageRequest.of(pageIdx, pageLimit));
    }

    public RegularRev updateRegularRev(Authentication auth, UpdateRegularRevReqDTO reqDTO) throws Exception {
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);

        // get regularRev from id
        RegularRev regularRev = this.fetchRegularRevById(reqDTO.getId());

        // check if user is not owner, nor admin for regularRev
        if(userNotOwnsRegularRev(user, regularRev) && !userProfileCrudServ.checkCurrentUserIsAdmin(auth)) {
            throw new Exception(
                    reservationMsgSrc.getMessage("error.regularRev.notAllowed", null, Locale.ENGLISH)
            );
        }

        // fetch reservations from regularRev
        List<Reservation> reservations = reservationCrudServ.fetchReservationsByRegularRevId(regularRev.getId(), 0, Integer.MAX_VALUE).getContent();

        // update reservations
        for(Reservation reservation : reservations) {
            reservationCrudServ.updateReservation(auth, new UpdateReserveReqDTO(reservation.getId(), reqDTO.getAttendants()));
        }

        // update regularRev columns
        regularRev.setConfirmed(reqDTO.getConfirmed());

        // update regularRev
        return regularRevRepo.save(regularRev);
    }

    public void deleteRegularRev(Authentication auth, Long regularRevId) throws Exception {
        UserProfile user = userProfileCrudServ.fetchCurrentUser(auth);

        // get regularRev from id
        RegularRev regularRev = this.fetchRegularRevById(regularRevId);

        // check if user is not owner, nor admin for regularRev
        if(userNotOwnsRegularRev(user, regularRev) && !userProfileCrudServ.checkCurrentUserIsAdmin(auth)) {
            throw new Exception(
                    reservationMsgSrc.getMessage("error.regularRev.notAllowed", null, Locale.ENGLISH)
            );
        }

        // fetch reservations from regularRev
        List<Reservation> reservations = reservationCrudServ.fetchReservationsByRegularRevId(regularRev.getId(), 0, Integer.MAX_VALUE).getContent();

        // delete all reservations
        for(Reservation reservation : reservations) {
            reservationCrudServ.deleteReservation(auth, reservation.getId());
        }

        // delete regularRev
        regularRevRepo.delete(regularRev);
    }
}
