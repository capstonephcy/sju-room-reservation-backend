package com.sju.roomreservationbackend.domain.room.stat.repository;

import com.sju.roomreservationbackend.domain.room.stat.entity.RoomLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomLogRepo extends CrudRepository<RoomLog, Long> {
    List<RoomLog> findAllByRoomId(Long roomId);

    List<RoomLog> findAllByRoomIdAndDate(Long roomId, LocalDate date);
}
