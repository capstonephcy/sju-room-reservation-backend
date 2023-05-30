package com.sju.roomreservationbackend.domain.reservation.profile.service;

import com.sju.roomreservationbackend.domain.notification.dto.NotificationSendReqDTO;
import com.sju.roomreservationbackend.domain.notification.service.NotificationServ;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.services.RoomCrudServ;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ReservationNotificationSendTask implements Tasklet {

    private final RoomCrudServ roomCrudServ;
    private final ReservationCrudServ reservationCrudServ;
    private final NotificationServ notificationServ;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("---Reservation Notification Send Start---");

        // get all rooms
        List<Room> rooms = roomCrudServ.fetchAllRoom();
        // for each room
        for (Room room : rooms) {
            // send notification before 10 min
            sendNotificationFor(room, 10);
            // send notification before 1 hour
            sendNotificationFor(room, 60);
        }
        System.out.println("------Reservation Notification Send End---");
        return RepeatStatus.FINISHED;
    }

    private void sendNotificationFor(Room room, int minutes) throws Exception {
        // set notification message that reservation is [n] minutes left
        String title = room.getName() + " 예약 알림";
        String body = "예약하신 ";
        switch (minutes) {
            case 10 -> body += room.getName() + " 예약이 10분 남았어요.";
            case 60 -> body += room.getName() + " 예약이 1시간 남았어요.";
        }

        // get reservation which date is today and reservation time range is [n] minutes later from now
        List<Reservation> reservations = reservationCrudServ.fetchReservationByRoomAndDateAndTimeLeft(room, LocalDate.now(ZoneId.of("Asia/Seoul")), LocalTime.now(ZoneId.of("Asia/Seoul")), LocalTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(minutes));

        for (Reservation reservation : reservations) {
            // for every attendant, get id
            List<Long> attendantIds = new ArrayList<>();
            for (UserProfile attendant : reservation.getAttendants()) {
                attendantIds.add(attendant.getId());
            }

            // send notification
            NotificationSendReqDTO notificationSendReqDTO = new NotificationSendReqDTO(attendantIds, title, body);
            notificationServ.sendNotification(notificationSendReqDTO);
        }
    }
}
