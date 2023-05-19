package com.sju.roomreservationbackend.domain.room.stat.entity;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_log_id")
    private Long id;

    @OneToOne(targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column
    private LocalDate date;
    private LocalTime time;

    @OneToOne(targetEntity = UserProfile.class, fetch = FetchType.EAGER)
    private UserProfile user;

    @Enumerated(EnumType.ORDINAL)
    private RoomAction action;
}
