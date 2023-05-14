package com.sju.roomreservationbackend.domain.room.detail;

import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.domain.room.detail.dto.request.DeleteRoomImgReqDto;
import com.sju.roomreservationbackend.domain.room.detail.dto.request.UploadRoomImgReqDTO;
import com.sju.roomreservationbackend.domain.room.detail.dto.response.DeleteRoomImgResDto;
import com.sju.roomreservationbackend.domain.room.detail.dto.response.UploadRoomImgResDTO;
import com.sju.roomreservationbackend.domain.room.detail.service.RoomImgCrudServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RoomImgAPI {
    private final RoomImgCrudServ roomImgCrudServ;

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @PostMapping("/musics")
    ResponseEntity<?> uploadMusic(@Valid UploadRoomImgReqDTO reqDTO) {
        UploadRoomImgResDTO resDTO = new UploadRoomImgResDTO();

        return new APIUtil<UploadRoomImgResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setUploadedRoomImage(roomImgCrudServ.createRoomImg(reqDTO));
            }
        }.execute(resDTO, "res.room.image.upload.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @DeleteMapping("/musics")
    ResponseEntity<?> deleteMusic(@Valid DeleteRoomImgReqDto reqDTO) {
        DeleteRoomImgResDto resDTO = new DeleteRoomImgResDto();

        return new APIUtil<DeleteRoomImgResDto>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setDeleteRoomImgId(roomImgCrudServ.deleteRoomImg(reqDTO.getId()));
            }
        }.execute(resDTO, "res.room.image.delete.success");
    }
}
