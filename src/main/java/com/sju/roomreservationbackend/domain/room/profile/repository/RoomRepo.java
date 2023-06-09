package com.sju.roomreservationbackend.domain.room.profile.repository;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Long> {
    Boolean existsByName(String name);
    Boolean existsByBuildingAndNumber(String building, Integer number);
    List<Room> findAllByNameContainingIgnoreCase(String name);
    Page<Room> findAllByBuilding(String building, Pageable pageable);
}
