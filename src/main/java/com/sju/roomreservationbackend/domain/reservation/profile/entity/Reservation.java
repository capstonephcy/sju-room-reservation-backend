package com.sju.roomreservationbackend.domain.reservation.profile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column
    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    private Room room;

    @Column
    @ManyToOne(targetEntity = UserProfile.class, fetch = FetchType.LAZY)
    private UserProfile reserver;

    @Column
    @ManyToMany(targetEntity = UserProfile.class, fetch = FetchType.LAZY)
    private List<UserProfile> attendants;

    @Column
    @ManyToOne(targetEntity = RegularRev.class, fetch = FetchType.LAZY)
    private RegularRev regularRev;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private RevType revType;

    @Column
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    @JsonIgnore
    @Column
    private String checkInCode;
    private Boolean checkIn;
    private Boolean noShow;

}
