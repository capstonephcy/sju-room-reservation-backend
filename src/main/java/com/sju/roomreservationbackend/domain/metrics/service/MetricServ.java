package com.sju.roomreservationbackend.domain.metrics.service;

import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import com.sju.roomreservationbackend.domain.room.stat.service.RoomStatServ;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class MetricServ {

    private final UserProfileCrudServ userProfileCrudServ;
    private final RoomStatServ roomStatServ;

    public RoomStat fetchRoomReservationMetricsAll(Long roomId) throws Exception {
        return roomStatServ.fetchRoomStatByRoomId(roomId);
    }

    public RoomStat fetchRoomReservationMetricsDaily(Long roomId, LocalDate date) {
        // get room logs

        // get reserve, cancel, checkin, noshow count from room logs

        //
        return null;
    }

    public RoomStat fetchRoomReservationMetricsWeekly(Long roomId, LocalDate date) {
        return null;
    }

    public RoomStat fetchRoomReservationMetricsMonthly(Long roomId, LocalDate date) {
        return null;
    }

    public UserProfile fetchUserMetrics(Long userId) throws Exception {
        // fetch user
        return userProfileCrudServ.fetchUserProfileById(userId);
    }
}
