package com.sju.roomreservationbackend.domain.room.profile.services;

import com.sju.roomreservationbackend.common.storage.StorageService;
import com.sju.roomreservationbackend.domain.room.profile.dto.request.CreateRoomReqDTO;
import com.sju.roomreservationbackend.domain.room.profile.dto.request.UpdateRoomReqDTO;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.repository.RoomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class RoomCrudServ extends RoomLogicServ {
    protected final StorageService storageServ;

    public RoomCrudServ(RoomRepo roomRepo, StorageService storageServ) {
        super(roomRepo);
        this.storageServ = storageServ;
    }

    @Transactional
    public Room createRoom(CreateRoomReqDTO reqDTO) throws Exception {
        
        // 명칭 중복 체크
        this.checkNameDuplication(reqDTO.getName());
        this.checkBuildingAndNumberDuplication(reqDTO.getBuilding(), reqDTO.getNumber());

        Room room = Room.builder()
                .name(reqDTO.getName())
                .building(reqDTO.getBuilding())
                .number(reqDTO.getNumber())
                .description(reqDTO.getDescription())
                .capacity(reqDTO.getCapacity())
                .whiteboard(reqDTO.getWhiteboard())
                .projector(reqDTO.getProjector())
                .maxPeakTimeForGrad(reqDTO.getMaxPeakTimeForGrad())
                .maxPeakTimeForStud(reqDTO.getMaxPeakTimeForStud())
                .maxNormalTimeForGrad(reqDTO.getMaxNormalTimeForGrad())
                .maxNormalTimeForStud(reqDTO.getMaxNormalTimeForStud())
                .maxLooseTimeForGrad(reqDTO.getMaxLooseTimeForGrad())
                .maxLooseTimeForStud(reqDTO.getMaxLooseTimeForStud())
                .description(reqDTO.getDescription())
                .build();

        room = roomRepo.save(room);
        storageServ.createEntityStorage("room", room.getId());

        return room;
    }

    public Room fetchRoomById(Long id) throws Exception {
        return roomRepo.findById(id).orElseThrow(
                () -> new Exception(msgSrc.getMessage("error.room.notExist", null, Locale.ENGLISH))
        );
    }

    public List<Room> fetchRoomsByName(String name) {
        return roomRepo.findAllByNameContainingIgnoreCase(name);
    }

    public Page<Room> fetchRoomsByBuilding(String building, int pageIdx, int pageLimit) {
        return roomRepo.findAllByBuilding(building, PageRequest.of(pageIdx, pageLimit));
    }

    @Transactional
    public Room updateRoom(UpdateRoomReqDTO reqDTO) throws Exception {


        Room room = this.fetchRoomById(reqDTO.getId());

        if (!room.getName().equals(reqDTO.getName())) {
            this.checkNameDuplication(reqDTO.getName());
            room.setName(reqDTO.getName() == null ? room.getName() : reqDTO.getName());
        }
        room.setDescription(reqDTO.getName() == null ? room.getName() : reqDTO.getName());
        room.setCapacity(reqDTO.getCapacity() == null ? room.getCapacity() : reqDTO.getCapacity());
        room.setWhiteboard(reqDTO.getWhiteboard() == null ? room.getWhiteboard() : reqDTO.getWhiteboard());
        room.setProjector(reqDTO.getProjector() == null ? room.getProjector() : reqDTO.getProjector());
        room.setMaxPeakTimeForGrad(reqDTO.getMaxPeakTimeForGrad() == null ? room.getMaxLooseTimeForGrad() : reqDTO.getMaxPeakTimeForGrad());
        room.setMaxPeakTimeForStud(reqDTO.getMaxPeakTimeForStud() == null ? room.getMaxLooseTimeForStud() : reqDTO.getMaxPeakTimeForStud());
        room.setMaxNormalTimeForGrad(reqDTO.getMaxNormalTimeForGrad() == null ? room.getMaxLooseTimeForGrad() : reqDTO.getMaxNormalTimeForGrad());
        room.setMaxNormalTimeForStud(reqDTO.getMaxNormalTimeForStud() == null ? room.getMaxLooseTimeForStud() : reqDTO.getMaxNormalTimeForStud());
        room.setMaxLooseTimeForGrad(reqDTO.getMaxLooseTimeForGrad() == null ? room.getMaxNormalTimeForGrad() : reqDTO.getMaxLooseTimeForGrad());
        room.setMaxLooseTimeForStud(reqDTO.getMaxLooseTimeForStud() == null ? room.getMaxNormalTimeForStud() : reqDTO.getMaxLooseTimeForStud());

        return room;
    }

    @Transactional
    public Long deleteRoom(Long reservationId) throws Exception {
        Room targetRoom = this.fetchRoomById(reservationId);

        storageServ.deleteEntityStorage("room", targetRoom.getId());

        roomRepo.delete(targetRoom);
        return targetRoom.getId();
    }
}
