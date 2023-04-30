package com.sju.roomreservationbackend.common.base.media;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class MediaMetadata extends FileMetadata {
    @Column(nullable = false)
    protected String author;

    @Column(nullable = false)
    protected String translator;
}
