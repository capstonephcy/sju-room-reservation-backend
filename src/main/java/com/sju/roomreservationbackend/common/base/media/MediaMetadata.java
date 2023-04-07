package com.dmtlabs.aidocentserver.global.base.media;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

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
