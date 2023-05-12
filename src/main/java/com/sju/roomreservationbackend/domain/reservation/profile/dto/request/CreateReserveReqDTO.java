package com.sju.roomreservationbackend.domain.reservation.profile.dto.request;

import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class CreateReserveReqDTO {
    @NotNull(message = "valid.room.id.null")
    @PositiveOrZero(message = "valid.room.id.positive")
    private Long roomId;

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    List<Long> attendants;

    public Reservation toEntity(Room room) {
        return Reservation.builder()
                .room(room)
                .date(date)
                .start(start)
                .end(end)
                .build();
    }
}
