package com.sju.roomreservationbackend.domain.room.stat.entity;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// RoomStat stores stats of reservation of specific room in specific day
public class RoomStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_stat_id")
    private Long id;

    @OneToOne(targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column
    private LocalDate date;

    @Column
    private Integer revCnt;
    private Integer noShowCnt;
    private Double noShowRate;
    private Double loadRate;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> peakHours;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> normalHours;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> looseHours;
}
