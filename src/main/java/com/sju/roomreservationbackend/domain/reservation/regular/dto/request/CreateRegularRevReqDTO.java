package com.sju.roomreservationbackend.domain.reservation.regular.dto.request;

import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRevType;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class CreateRegularRevReqDTO {
    @NotNull(message = "valid.room.id.null")
    @PositiveOrZero(message = "valid.room.id.positive")
    private Long roomId;
    private String type;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    private Integer iteration;

    List<Long> attendants;

    public RegularRev toEntity(Room room, UserProfile user) {
        return RegularRev.builder()
                .room(room)
                .revOwner(user)
                .type(RegularRevType.valueOf(type))
                .iteration(iteration)
                .date(date)
                .start(start)
                .end(end)
                .confirmed(false)
                .build();
    }
}
