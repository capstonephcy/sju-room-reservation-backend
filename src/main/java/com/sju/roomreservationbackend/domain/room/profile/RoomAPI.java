package com.sju.roomreservationbackend.domain.room.profile;

import com.sju.roomreservationbackend.domain.room.profile.dto.request.*;
import com.sju.roomreservationbackend.domain.room.profile.dto.response.CreateRoomResDTO;
import com.sju.roomreservationbackend.domain.room.profile.dto.response.DeleteRoomResDTO;
import com.sju.roomreservationbackend.domain.room.profile.dto.response.FetchRoomResDTO;
import com.sju.roomreservationbackend.domain.room.profile.dto.response.UpdateRoomResDTO;
import com.sju.roomreservationbackend.domain.room.profile.service.RoomCrudServ;

public class RoomAPI {
    private RoomCrudServ roomCrudServ;

    public CreateRoomResDTO createRoom(CreateRoomReqDTO reqDTO) {
        return new CreateRoomResDTO();
    }

    public FetchRoomResDTO fetchRoom(FetchRoomReqDTO reqDTO, FetchRoomReqOptionType optionType) {
        return new FetchRoomResDTO();
    }

    public UpdateRoomResDTO updateRoom(UpdateRoomReqDTO reqDTO) {
        return new UpdateRoomResDTO();
    }

    public DeleteRoomResDTO deleteRoom(DeleteRoomReqDTO reqDTO) {
        return new DeleteRoomResDTO();
    }
}
