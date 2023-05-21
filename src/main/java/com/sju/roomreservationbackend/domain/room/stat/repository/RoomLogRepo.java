package com.sju.roomreservationbackend.domain.room.stat.repository;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RoomLogRepo extends CrudRepository<RoomLog, Long> {
    Page<RoomLog> findAllByRoomId(Long roomId, Pageable pageable);

    Page<RoomLog> findAllByRoomIdAndUserId(Long roomId, Long userId, Pageable pageable);

    Page<RoomLog> findAllByRoomIdAndDateBetweenAndTimeBetween(Long roomId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Pageable pageable);

    List<RoomLog> findAllByRoomAndDate(Room room, LocalDate date);
}
