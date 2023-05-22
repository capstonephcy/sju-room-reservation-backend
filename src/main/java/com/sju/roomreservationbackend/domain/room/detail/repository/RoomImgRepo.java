package com.sju.roomreservationbackend.domain.room.detail.repository;

import com.sju.roomreservationbackend.domain.room.detail.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImgRepo extends JpaRepository<RoomImage, Long> {
    List<RoomImage> findAllByRoom_Id(Long roomId);
}
