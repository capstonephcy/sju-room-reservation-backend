package com.sju.roomreservationbackend.domain.reservation.profile.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sju.roomreservationbackend.domain.reservation.profile.entity.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.sju.roomreservationbackend.domain.reservation.profile.entity.QReservation.reservation;

@AllArgsConstructor
@Repository
public class ReservationRepoImpl implements ReservationDslRepo{
    private JPAQueryFactory queryFactory;

    @Override
    public Reservation fetchCurrentReservationByRoomId(Long roomId) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        return queryFactory.select(reservation).from(reservation)
                .where(reservation.room.id.eq(roomId)
                        .and(reservation.date.eq(currentDate))
                        .and(reservation.start.before(currentTime))
                        .and(reservation.end.after(currentTime))
                )
                .fetchFirst();
    }
}
