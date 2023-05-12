package com.sju.roomreservationbackend.domain.reservation.profile;

import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.*;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.response.*;
import com.sju.roomreservationbackend.domain.reservation.profile.service.ReservationCrudServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Locale;

@RestController
@AllArgsConstructor
public class ReservationAPI {
    private final ReservationCrudServ reservationCrudServ;
    private final MessageSource msgSrc = MessageConfig.getReservationMsgSrc();

    // 신규 단건 예약 생성 -> 회의실 예약
    @PostMapping("/reservations/profiles")
    public ResponseEntity<?> createReservation(Authentication auth, @Valid @RequestBody CreateReserveReqDTO reqDTO) {
        CreateReserveResDTO resDTO = new CreateReserveResDTO();

        return new APIUtil<CreateReserveResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setReservation(reservationCrudServ.createReservation(auth, reqDTO));
            }
        }.execute(resDTO, "res.reservation.create.success");
    }

    // 단건 예약 조회
    @GetMapping("/reservations/profiles")
    public ResponseEntity<?> fetchReservation(
            @Valid @RequestBody FetchReserveReqDTO reqDTO,
            @RequestHeader("Request-Type") FetchReserveReqOptionType optionType
    ) {
        FetchReserveResDTO resDTO = new FetchReserveResDTO();

        return new APIUtil<FetchReserveResDTO>() {
               @Override
                protected void onSuccess() throws Exception {
                    switch(optionType) {
                        case ID -> resDTO.setReservations(Collections.singletonList(reservationCrudServ.fetchReservationById(reqDTO.getId())));
                        case ROOM_ID -> resDTO.setReservations(reservationCrudServ.fetchReservationsByRoom(reqDTO.getRoomId()));
                        case USER_ID -> resDTO.setReservations(reservationCrudServ.fetchReservationsByUser(reqDTO.getUserId()));
                        case TIME_RANGE -> resDTO.setReservations(reservationCrudServ.fetchReservationsByTimeRange(reqDTO.getStartDate(), reqDTO.getEndDate(), reqDTO.getStartTime(), reqDTO.getEndTime()));
                        default -> throw new Exception(
                                msgSrc.getMessage("valid.binding", null, Locale.ENGLISH)
                        );
                    }
                }
        }.execute(resDTO, "res.reservation.fetch.success");
    }

    // 단건 예약 정보 업데이트
    @PutMapping("/reservations/profiles")
    public ResponseEntity<?> updateReservation(Authentication auth, @Valid @RequestBody UpdateReserveReqDTO reqDTO) {
        UpdateReserveResDTO resDTO = new UpdateReserveResDTO();

        return new APIUtil<UpdateReserveResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setReservation(reservationCrudServ.updateReservation(auth, reqDTO));
            }
        }.execute(resDTO, "res.reservation.update.success");
    }

    // 단건 예약 삭제
    @DeleteMapping("/reservations/profiles")
    public ResponseEntity<?> deleteReservation(Authentication auth, @Valid @RequestBody DeleteReserveReqDTO reqDTO) {
        DeleteReserveResDTO resDTO = new DeleteReserveResDTO();

        return new APIUtil<DeleteReserveResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                reservationCrudServ.deleteReservation(auth, reqDTO.getId());
            }
        }.execute(resDTO, "res.reservation.delete.success");
    }

    // 단건 예약 체크인
    @PutMapping("/reservations/profiles/checkin")
    public ResponseEntity<?> checkInReservation(Authentication auth, @Valid @RequestBody CheckInReserveReqDTO reqDTO) {
        CheckInReserveResDTO resDTO = new CheckInReserveResDTO();

        return new APIUtil<CheckInReserveResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                reservationCrudServ.checkInReservation(auth, reqDTO);
            }
        }.execute(resDTO, "res.reservation.checkin.success");
    }
}
