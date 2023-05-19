package com.sju.roomreservationbackend.domain.reservation.profile.repository;

import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepo extends CrudRepository<Reservation, Long> {
    Page<Reservation> findAllByRoomId(Long roomId, Pageable pageable);

    // Find all matching reservations in a date range and time range
    Page<Reservation> findAllByDateBetweenAndStartGreaterThanEqualAndEndLessThanEqual(
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime,
            Pageable pageable
    );

    // Find all matching reservations in a time range in a room
    boolean existsByRoomAndDateAndStartGreaterThanEqualAndEndLessThanEqual(
            Room room,
            LocalDate date,
            LocalTime start,
            LocalTime end
    );

    Page<Reservation> findAllByRevOwnerIdOrAttendantsId(Long userId, Long attendantId, Pageable pageable);

    Page<Reservation> findAllByRegularRevId(Long regularRevId, Pageable pageable);

    Page<Reservation> findAllByDateBetweenAndStartGreaterThanEqualAndEndLessThanEqualAndRevOwnerIdOrAttendantsId(
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime,
            Long revOwnerId,
            Long attendantId,
            Pageable pageable
    );
}
