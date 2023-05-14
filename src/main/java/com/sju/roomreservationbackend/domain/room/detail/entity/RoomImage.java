package com.sju.roomreservationbackend.domain.room.detail.entity;

import com.sju.roomreservationbackend.common.base.media.FileMetadata;
import com.sju.roomreservationbackend.domain.room.profile.entity.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RoomImage extends FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_img_id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = Room.class)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    private Room room;
}
