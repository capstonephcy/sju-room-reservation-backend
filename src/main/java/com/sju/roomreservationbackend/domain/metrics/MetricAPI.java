package com.sju.roomreservationbackend.domain.metrics;

import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.metrics.dto.request.FetchRoomReservationMetricsReqDTO;
import com.sju.roomreservationbackend.domain.metrics.dto.request.FetchRoomReservationMetricsReqOptionType;
import com.sju.roomreservationbackend.domain.metrics.dto.request.FetchUserMetricsReqDTO;
import com.sju.roomreservationbackend.domain.metrics.dto.response.FetchRoomReservationMetricsResDTO;
import com.sju.roomreservationbackend.domain.metrics.dto.response.FetchUserMetricsResDTO;
import com.sju.roomreservationbackend.domain.metrics.service.MetricServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@AllArgsConstructor
@RestController
public class MetricAPI {
    private final MetricServ metricServ;
    private final MessageSource msgSrc = MessageConfig.getMetricMsgSrc();

    // 회의실 단위 일별/주별/월별 예약현황 조회
    @GetMapping("/metrics/room/reservations")
    public ResponseEntity<?> fetchRoomReservationMetrics(
            @Valid FetchRoomReservationMetricsReqDTO reqDTO,
            @RequestHeader("Request-Type") FetchRoomReservationMetricsReqOptionType optionType
    ) {
        FetchRoomReservationMetricsResDTO resDTO = new FetchRoomReservationMetricsResDTO();

        return new APIUtil<FetchRoomReservationMetricsResDTO>() {
            @Override
            protected void onSuccess() throws Exception {

            }
        }.execute(resDTO, "res.metrics.room.reservations.fetch.success");
    }

    // 사용자별 예약 관련 통계량 조회
    @GetMapping("/metrics/user")
    public ResponseEntity<?> fetchUserMetrics(
            @Valid FetchUserMetricsReqDTO reqDTO
    ) {
        FetchUserMetricsResDTO resDTO = new FetchUserMetricsResDTO();

        return new APIUtil<FetchUserMetricsResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setUserMetrics(metricServ.fetchUserMetrics(reqDTO.getUserId()));
            }
        }.execute(resDTO, "res.metrics.user.fetch.success");
    }

}
