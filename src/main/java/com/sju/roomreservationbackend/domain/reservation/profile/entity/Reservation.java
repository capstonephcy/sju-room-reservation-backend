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
    @Column(name="rev_id")
    private Long id;

    // TODO(krapie): Change FetchType to LAZY, and configure new DTOs for fetching
    @ManyToOne(targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    // TODO(krapie): Change FetchType to LAZY, and configure new DTOs for fetching
    @ManyToOne(targetEntity = UserProfile.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserProfile revOwner;

    // Attendant is a list of users who are attending the reservation including revOwner
    @Column
    // TODO(krapie): Change FetchType to LAZY, and configure new DTOs for fetching
    @ManyToMany(targetEntity = UserProfile.class, fetch = FetchType.EAGER)
    private List<UserProfile> attendants;

    // TODO(krapie): Change FetchType to LAZY, and configure new DTOs for fetching
    @ManyToOne(targetEntity = RegularRev.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "regular_rev_id")
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

    public boolean isCheckedIn() {
        return checkIn;
    }

    public boolean isNoShow() {
        return noShow;
    }
}
