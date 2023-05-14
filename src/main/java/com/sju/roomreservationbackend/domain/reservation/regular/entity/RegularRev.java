package com.sju.roomreservationbackend.domain.reservation.regular.entity;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegularRev {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="regular_rev_id")
    private Long id;

    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(targetEntity = UserProfile.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserProfile revOwner;

    @Column
    private LocalDate validThru;
    private Integer iteration;
    private RegularRevType type;
    private DayOfWeek dayOfWeek;
    private Integer weekNum;
    private Integer dayOfMonth;
    private LocalTime start;
    private LocalTime end;
    private Boolean confirmed;
}
