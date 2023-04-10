package com.sju.roomreservationbackend.domain.room.profile.service;

import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.CreateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.UpdateReserveReqDTO;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

public class RoomCrudServ extends RoomLogicServ {
    public Room createRoom(Authentication auth, CreateReserveReqDTO reqDTO) {
        return new Room();
    }

    public Room fetchRoomById(Authentication auth, Long id) {
        return new Room();
    }

    public Room fetchRoomByName(Authentication auth) {
        return new Room();
    }

    public List<Room> fetchRoomsByBuilding(Authentication auth, Long userId) {
        return new ArrayList<>();
    }

    public Room updateRoom(Authentication auth, UpdateReserveReqDTO reqDTO) {
        return new Room();
    }

    public Room deleteRoom(Authentication auth, Long reservationId) {
        return new Room();
    }
}
