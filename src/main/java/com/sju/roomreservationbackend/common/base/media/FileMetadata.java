package com.dmtlabs.aidocentserver.global.base.media;

import com.dmtlabs.aidocentserver.global.storage.FileType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
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
