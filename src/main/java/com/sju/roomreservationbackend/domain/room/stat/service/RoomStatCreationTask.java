package com.sju.roomreservationbackend.domain.room.stat.service;

import com.sju.roomreservationbackend.domain.reservation.profile.service.ReservationCrudServ;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.room.profile.services.RoomCrudServ;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomAction;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomLog;
import com.sju.roomreservationbackend.domain.room.stat.entity.RoomStat;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class RoomStatCreationTask implements Tasklet {

    private final RoomCrudServ roomCrudServ;
    private final RoomLogServ roomLogServ;
    private final RoomStatServ roomStatServ;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("---Room Stat Creation Task Start---");

        // get all rooms
        List<Room> rooms = roomCrudServ.fetchAllRoom();
        // for each room
        for (Room room : rooms) {
            LocalDate previousDate = LocalDate.now().minusDays(1);
            // create room stat
            RoomStat roomStat = RoomStat.builder()
                    .room(room)
                    .date(previousDate)
                    .revCnt(0)
                    .noShowCnt(0)
                    .build();

            // get all room logs
            List<RoomLog> roomLogs = roomLogServ.fetchRoomLogsByRoomAndDate(room, previousDate);
            // from room logs, calculate:
            // 1. room stat's revCnt = room logs  list size where action reserve
            for (RoomLog roomLog : roomLogs) {
                if (roomLog.getAction() == RoomAction.RESERVE) {
                    roomStat.setRevCnt(roomStat.getRevCnt() + 1);
                }
            }
            // 2. room stat's noShowCnt = room logs list size where action is no show
            for (RoomLog roomLog : roomLogs) {
                if (roomLog.getAction() == RoomAction.NOSHOW) {
                    roomStat.setNoShowCnt(roomStat.getNoShowCnt() + 1);
                }
            }
            // 3. room stat's noShowRate = noShowCnt / revCnt
            roomStat.setNoShowRate((double) roomStat.getNoShowCnt() / roomStat.getRevCnt());

            // save room stat
            roomStatServ.createRoomStat(roomStat);
        }

        System.out.println("---Room Stat Creation Task End---");
        return RepeatStatus.FINISHED;
    }
}
