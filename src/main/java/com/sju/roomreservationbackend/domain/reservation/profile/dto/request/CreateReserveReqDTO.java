package com.sju.roomreservationbackend.domain.reservation.profile.dto.request;

import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.RevType;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
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

    private List<Long> attendants;

    private RegularRev regularRev;

    public CreateReserveReqDTO(Long roomId, LocalDate date, LocalTime start, LocalTime end, List<Long> attendants, RegularRev regularRev) {
        this.roomId = roomId;
        this.date = date;
        this.start = start;
        this.end = end;
        this.attendants = attendants;
        this.regularRev = regularRev;
    }

    public Reservation toEntity(UserProfile user, Room room, List<UserProfile> attendants, String checkInCode) {
        return Reservation.builder()
                .room(room)
                .revOwner(user)
                .attendants(attendants)
                .regularRev(regularRev)
                .revType((regularRev == null) ? RevType.ONCE : RevType.REGULAR)
                .date(date)
                .start(start)
                .end(end)
                .checkIn(false)
                .checkInCode(checkInCode)
                .noShow(false)
                .build();
    }
}
