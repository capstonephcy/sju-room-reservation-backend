package com.sju.roomreservationbackend.common.base.media;

import com.sju.roomreservationbackend.common.storage.FileType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class FileMetadata {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected FileType type;

    @Column(unique = true, nullable = false)
    protected String fileName;

    @Column(unique = true, nullable = false)
    protected String fileURL;

    @Column(nullable = false)
    protected Long fileSize;

    @Column(nullable = false)
    protected LocalDateTime timestamp;
}
