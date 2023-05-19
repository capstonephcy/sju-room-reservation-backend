package com.sju.roomreservationbackend.domain.room.stat.repository;

import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomStatRepo extends CrudRepository<RoomStat, Long> {
}
