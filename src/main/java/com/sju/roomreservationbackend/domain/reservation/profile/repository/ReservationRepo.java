package com.sju.roomreservationbackend.domain.reservation.profile.repository;

import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface ReservationRepo extends CrudRepository<Reservation, Long>, ReservationDslRepo {
    Page<Reservation> findAllByRoomId(Long roomId, Pageable pageable);

    Page<Reservation> findAllByRevOwnerIdOrAttendantsId(Long userId, Long attendantId, Pageable pageable);

    Page<Reservation> findAllByRegularRevId(Long regularRevId, Pageable pageable);

    Page<Reservation> findAllByDateBetweenAndStartGreaterThanEqualAndEndLessThanEqualAndRevOwnerAndAttendantsContains(
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime,
            UserProfile owner,
            UserProfile attendant,
            Pageable pageable
    );

    // Find all matching reservations in a date range and time range
    Page<Reservation> findAllByDateBetweenAndStartGreaterThanEqualAndEndLessThanEqual(
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime,
            Pageable pageable
    );

    // Find all matching reservations in a time range
    boolean existsByDateAndStartGreaterThanEqualAndEndLessThanEqual(
            LocalDate date,
            LocalTime start,
            LocalTime end
    );

    List<Reservation> findAllByRoomAndDate(Room room, LocalDate date);
}
