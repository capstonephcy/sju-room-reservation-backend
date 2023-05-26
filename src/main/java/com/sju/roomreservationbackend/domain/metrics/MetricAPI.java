package com.sju.roomreservationbackend.domain.metrics;

import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.metrics.dto.request.FetchRoomLogReqOptionType;
import com.sju.roomreservationbackend.domain.metrics.dto.request.FetchRoomReservationLogsReqDTO;
import com.sju.roomreservationbackend.domain.metrics.dto.request.FetchRoomReservationStatsReqDTO;
import com.sju.roomreservationbackend.domain.metrics.dto.request.FetchRoomReservationStatsReqOptionType;
import com.sju.roomreservationbackend.domain.metrics.dto.response.FetchRoomReservationLogsResDTO;
import com.sju.roomreservationbackend.domain.metrics.dto.response.FetchRoomReservationStatsResDTO;
import com.sju.roomreservationbackend.domain.metrics.service.MetricServ;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomLog;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
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

    // 회의실 일별/주별/월별 예약현황 조회
    // 회의실 단위로 보여줌
    @GetMapping("/metrics/rooms/reservations/stats")
    public ResponseEntity<?> fetchRoomReservationStats(
            @Valid FetchRoomReservationStatsReqDTO reqDTO,
            @RequestHeader("Request-Type") FetchRoomReservationStatsReqOptionType optionType
    ) {
        FetchRoomReservationStatsResDTO resDTO = new FetchRoomReservationStatsResDTO();

        return new APIUtil<FetchRoomReservationStatsResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                switch (optionType) {
                    case DAILY -> resDTO.setRoomStat(metricServ.fetchRoomReservationStatsByDay(reqDTO.getRoomId(), reqDTO.getDate()));
                    case WEEKLY -> resDTO.setRoomStat(metricServ.fetchRoomReservationStatsByWeek(reqDTO.getRoomId(), reqDTO.getDate()));
                    case MONTHLY -> resDTO.setRoomStat(metricServ.fetchRoomReservationStatsByMonth(reqDTO.getRoomId(), reqDTO.getDate()));
                    case ALL -> resDTO.setRoomStat(metricServ.fetchRoomReservationStats(reqDTO.getRoomId(), reqDTO.getDate()));
                    case TIME_RANGE -> resDTO.setRoomStats(metricServ.fetchRoomReservationStatsByDateRange(reqDTO.getRoomId(), reqDTO.getDate(), reqDTO.getEndDate()));
                    default -> throw new Exception(
                            msgSrc.getMessage("valid.binding", null, Locale.ENGLISH)
                    );
                }
            }
        }.execute(resDTO, "res.metrics.room.reservations.stats.fetch.success");
    }

    // 회의실 예약현황 로그 조회
    // 회의실 단위로 보여줌
    @GetMapping("/metrics/rooms/reservations/logs")
    public ResponseEntity<?> fetchRoomReservationLogs(
            @Valid FetchRoomReservationLogsReqDTO reqDTO,
            @RequestHeader("Request-Type") FetchRoomLogReqOptionType optionType
    ) {
        FetchRoomReservationLogsResDTO resDTO = new FetchRoomReservationLogsResDTO();

        return new APIUtil<FetchRoomReservationLogsResDTO>() {

            Page<RoomLog> resultPage;
            @Override
            protected void onSuccess() throws Exception {
                switch (optionType) {
                    case ALL -> {
                        resultPage = metricServ.fetchRoomReservationLogs(reqDTO.getRoomId(), reqDTO.getPageIdx(), reqDTO.getPageLimit());

                        resDTO.setRoomLogs(resultPage.getContent());
                        resDTO.setPageable(true);
                        resDTO.setPageIdx(resultPage.getNumber());
                        resDTO.setPageElementSize(resultPage.getTotalElements());
                        resDTO.setTotalPage(resultPage.getTotalPages());
                    }
                    case USER_ID -> {
                        resultPage = metricServ.fetchRoomReservationLogsByRoomAndUser(reqDTO.getRoomId(), reqDTO.getUserId(), reqDTO.getPageIdx(), reqDTO.getPageLimit());

                        resDTO.setRoomLogs(resultPage.getContent());
                        resDTO.setPageable(true);
                        resDTO.setPageIdx(resultPage.getNumber());
                        resDTO.setPageElementSize(resultPage.getTotalElements());
                        resDTO.setTotalPage(resultPage.getTotalPages());
                    }
                    case TIME_RANGE -> {
                        resultPage = metricServ.fetchRoomReservationLogsByRoomAndTimeRange(reqDTO.getRoomId(), reqDTO.getStartDate(), reqDTO.getEndDate(), reqDTO.getStartTime(), reqDTO.getEndTime(), reqDTO.getPageIdx(), reqDTO.getPageLimit());

                        resDTO.setRoomLogs(resultPage.getContent());
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
        }.execute(resDTO, "res.metrics.room.reservations.logs.fetch.success");
    }
}
