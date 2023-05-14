package com.sju.roomreservationbackend.domain.room.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String building;

    @Column
    private Integer number;

    @Column
    private String description;

    @Column
    private Integer capacity;

    @Column
    private Boolean whiteboard;

    @Column
    private Boolean projector;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private RoomCongestion congestion;

    @Column
    private Integer maxPeakTimeForGrad;

    @Column
    private Integer maxPeakTimeForStud;

    @Column
    private Integer maxNormalTimeForGrad;

    @Column
    private Integer maxNormalTimeForStud;

    @Column
    private Integer maxLooseTimeForGrad;

    @Column
    private Integer maxLooseTimeForStud;
}
