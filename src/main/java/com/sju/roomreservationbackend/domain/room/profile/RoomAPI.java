package com.sju.roomreservationbackend.domain.room.profile;

import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.domain.room.profile.dto.request.*;
import com.sju.roomreservationbackend.domain.room.profile.dto.response.CreateRoomResDTO;
import com.sju.roomreservationbackend.domain.room.profile.dto.response.DeleteRoomResDTO;
import com.sju.roomreservationbackend.domain.room.profile.dto.response.FetchRoomResDTO;
import com.sju.roomreservationbackend.domain.room.profile.dto.response.UpdateRoomResDTO;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.services.RoomCrudServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RoomAPI {
    private RoomCrudServ roomCrudServ;

    @PreAuthorize("hasAnyAuthority('ROOT_ADMIN', 'ADMIN')")
    @PostMapping("/rooms/profiles")
    public ResponseEntity<?> createRoom(@Valid @RequestBody CreateRoomReqDTO reqDTO) {
        CreateRoomResDTO resDTO = new CreateRoomResDTO();

        return new APIUtil<CreateRoomResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setCreatedRoomProfile(roomCrudServ.createRoom(reqDTO));
            }
        }.execute(resDTO, "res.room.create.success");
    }

    @GetMapping("/rooms/profiles")
    public ResponseEntity<?> fetchRoom(@RequestBody @Valid FetchRoomReqDTO reqDTO, @RequestHeader("Request-Type") String reqType) {
        FetchRoomReqOptionType reqOptionType = FetchRoomReqOptionType.valueOf(reqType);
        FetchRoomResDTO resDTO = new FetchRoomResDTO();

        return new APIUtil<FetchRoomResDTO>() {
            Page<Room> resultPage;

            @Override
            protected void onSuccess() throws Exception {
                switch (reqOptionType) {
                    case ID -> resDTO.setRoom(roomCrudServ.fetchRoomById(reqDTO.getId()));
                    case NAME -> resDTO.setRooms(roomCrudServ.fetchRoomsByName(reqDTO.getName()));
                    case BUILDING -> {
                        resultPage = roomCrudServ.fetchRoomsByBuilding(reqDTO.getBuilding(), reqDTO.getPageIdx(), reqDTO.getPageLimit());
                        resDTO.setRooms(resultPage.getContent());
                        resDTO.setPageable(true);
                        resDTO.setPageIdx(resultPage.getNumber());
                        resDTO.setPageElementSize(resultPage.getTotalElements());
                        resDTO.setTotalPage(resultPage.getTotalPages());
                    }
                }
            }
        }.execute(resDTO, "res.room.fetch.success");
    }

    @PreAuthorize("hasAnyAuthority('ROOT_ADMIN', 'ADMIN')")
    @PutMapping("/rooms/profiles")
    public ResponseEntity<?> updateRoom(@Valid @RequestBody UpdateRoomReqDTO reqDTO) {
        UpdateRoomResDTO resDTO = new UpdateRoomResDTO();

        return new APIUtil<UpdateRoomResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setUpdatedRoomProfile(roomCrudServ.updateRoom(reqDTO));
            }
        }.execute(resDTO, "res.room.update.success");
    }

    @PreAuthorize("hasAnyAuthority('ROOT_ADMIN', 'ADMIN')")
    @DeleteMapping("/rooms/profiles")
    public ResponseEntity<?> deleteRoom(DeleteRoomReqDTO reqDTO) {
        DeleteRoomResDTO resDTO = new DeleteRoomResDTO();

        return new APIUtil<DeleteRoomResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setDeletedRoomId(roomCrudServ.deleteRoom(reqDTO.getId()));
            }
        }.execute(resDTO, "res.room.delete.success");
    }
}
