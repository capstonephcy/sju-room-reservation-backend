package com.sju.roomreservationbackend.domain.room.detail.repository;

import com.sju.roomreservationbackend.domain.room.detail.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomImgRepo extends JpaRepository<RoomImage, Long> {
}
