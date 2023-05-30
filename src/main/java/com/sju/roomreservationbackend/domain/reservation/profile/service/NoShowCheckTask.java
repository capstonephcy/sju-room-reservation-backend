package com.sju.roomreservationbackend.domain.reservation.profile.service;

import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.services.RoomCrudServ;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomAction;
import com.sju.roomreservationbackend.domain.room.stat.service.RoomLogServ;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
public class NoShowCheckTask implements Tasklet {

    private final UserProfileCrudServ userProfileCrudServ;
    private final RoomCrudServ roomCrudServ;
    private final ReservationCrudServ reservationCrudServ;
    private final RoomLogServ roomLogServ;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("---No Show Check Task Start---");

        // get all rooms
        List<Room> rooms = roomCrudServ.fetchAllRoom();
        // for each room
        for (Room room : rooms) {
            // get all reservations from previous day
            List<Reservation> reservations = reservationCrudServ.fetchReservationsByRoomAndDate(room, LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(1));

            // for each reservation
            for (Reservation reservation : reservations) {
                // if reservation is not checked in
                if (!reservation.isCheckedIn()) {
                    // update reservation status to no show
                    reservationCrudServ.updateReservationNoShow(reservation);
                    // create new room log for no show
                    roomLogServ.createRoomLog(room, reservation.getRevOwner(), RoomAction.NOSHOW);
                    // increase reservation attendees no show count
                    for (UserProfile attendee : reservation.getAttendants()) {
                        userProfileCrudServ.updateNoShowCnt(attendee);
                    }
                }
            }
        }

        System.out.println("------No Show Check Task End---");
        return RepeatStatus.FINISHED;
    }
}
