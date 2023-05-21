package com.sju.roomreservationbackend.domain.room.stat.repository;

import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomStatRepo extends CrudRepository<RoomStat, Long> {
    RoomStat findByRoomIdAndDate(Long roomId, LocalDate date);

    List<RoomStat> findAllByRoomIdAndDateBetween(Long roomId, LocalDate startOfWeek, LocalDate endOfWeek);
}
