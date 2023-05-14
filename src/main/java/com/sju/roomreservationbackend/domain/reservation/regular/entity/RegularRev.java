package com.sju.roomreservationbackend.domain.reservation.regular.entity;

import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

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

    // TODO(krapie): Change FetchType to LAZY, and configure new DTOs for fetching
    @ManyToOne(targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    // TODO(krapie): Change FetchType to LAZY, and configure new DTOs for fetching
    @ManyToOne(targetEntity = UserProfile.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserProfile revOwner;

    @Column
    private Integer iteration;

    @Column
    @Enumerated(EnumType.STRING)
    private RegularRevType type;

    @Column
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Boolean confirmed;

    public RegularRevType getType() {
        return type;
    }
}
