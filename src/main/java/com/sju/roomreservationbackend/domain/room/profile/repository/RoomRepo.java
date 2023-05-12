package com.sju.roomreservationbackend.domain.room.profile.repository;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Long> {
    List<Room> findAllByNameLike(String name);
    Page<Room> findAllByBuilding(String building, Pageable pageable);
}
