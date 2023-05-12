package com.sju.roomreservationbackend.domain.reservation.profile.repository;

import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepo extends CrudRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(Long userId);

    List<Reservation> findAllByRoomId(Long roomId);

    // Find all matching reservations in a time range
    List<Reservation> findAllByDateBetweenAndStartLessThanEqualAndEndGreaterThanEqual(
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime
    );

    // Find all matching reservations in a time range in a room
    boolean existsByRoomAndDateAndEndGreaterThanEqualAndStartLessThanEqual(
            Room room,
            LocalDate date,
            LocalTime end,
            LocalTime start
    );
}
