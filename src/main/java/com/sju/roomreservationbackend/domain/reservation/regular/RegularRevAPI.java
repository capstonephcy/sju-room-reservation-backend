package com.sju.roomreservationbackend.domain.reservation.regular;

import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.reservation.regular.dto.request.*;
import com.sju.roomreservationbackend.domain.reservation.regular.dto.response.*;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import com.sju.roomreservationbackend.domain.reservation.regular.service.RegularRevCrudServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Locale;

@RestController
@AllArgsConstructor
public class RegularRevAPI {
    private final RegularRevCrudServ reservationCrudServ;
    private final MessageSource msgSrc = MessageConfig.getReservationMsgSrc();

    @PostMapping("/reservation/regulars")
    public ResponseEntity<?> createRegularRev(Authentication auth, @Valid @RequestBody CreateRegularRevReqDTO reqDTO) {
        CreateRegularRevResDTO resDTO = new CreateRegularRevResDTO();

        return new APIUtil<CreateRegularRevResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setRegularRev(reservationCrudServ.createRegularRev(auth, reqDTO));
            }
        }.execute(resDTO, "res.regularRev.create.success");
    }

    @GetMapping("/reservation/regulars")
    public ResponseEntity<?> fetchRegularRev(
            @Valid @RequestBody FetchRegularRevReqDTO reqDTO,
            @RequestHeader("Request-Type") FetchRegularRevReqOptionType optionType) {
        FetchRegularRevResDTO resDTO = new FetchRegularRevResDTO();

        return new APIUtil<FetchRegularRevResDTO>() {
            Page<RegularRev> resultPage;
            @Override
            protected void onSuccess() throws Exception {
                switch(optionType) {
                    case ID -> resDTO.setRegularRevs(Collections.singletonList(reservationCrudServ.fetchRegularRevById(reqDTO.getId())));
                    case ROOM_ID -> {
                        resultPage = reservationCrudServ.fetchRegularRevsByRoom(reqDTO.getRoomId(), reqDTO.getPageIdx(), reqDTO.getPageLimit());

                        resDTO.setRegularRevs(resultPage.getContent());
                        resDTO.setPageable(true);
                        resDTO.setPageIdx(resultPage.getNumber());
                        resDTO.setPageElementSize(resultPage.getTotalElements());
                        resDTO.setTotalPage(resultPage.getTotalPages());
                    }
                    case USER_ID -> {
                        resultPage = reservationCrudServ.fetchRegularRevsByUser(reqDTO.getUserId(), reqDTO.getPageIdx(), reqDTO.getPageLimit());

                        resDTO.setRegularRevs(resultPage.getContent());
                        resDTO.setPageable(true);
                        resDTO.setPageIdx(resultPage.getNumber());
                        resDTO.setPageElementSize(resultPage.getTotalElements());
                        resDTO.setTotalPage(resultPage.getTotalPages());
                    }
                    default -> throw new Exception(
                        msgSrc.getMessage("valid.binding", null, Locale.ENGLISH)
                    );
                }
            }
        }.execute(resDTO, "res.regularRev.fetch.success");
    }

    @PutMapping("/reservation/regulars")
    public ResponseEntity<?> updateRegularRev(Authentication auth, @Valid @RequestBody UpdateRegularRevReqDTO reqDTO) {
        UpdateRegularRevResDTO resDTO = new UpdateRegularRevResDTO();

        return new APIUtil<UpdateRegularRevResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setRegularRev(reservationCrudServ.updateRegularRev(auth, reqDTO));
            }
        }.execute(resDTO, "res.regularRev.update.success");
    }

    @DeleteMapping("/reservation/regulars")
    public ResponseEntity<?> deleteRegularRev(Authentication auth, @Valid @RequestBody DeleteRegularRevReqDTO reqDTO) {
        DeleteRegularRevResDTO resDTO = new DeleteRegularRevResDTO();

        return new APIUtil<DeleteRegularRevResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                reservationCrudServ.deleteRegularRev(auth, reqDTO.getId());
            }
        }.execute(resDTO, "res.regularRev.delete.success");
    }
}
