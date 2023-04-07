package com.dmtlabs.aidocentserver.global.storage;

public enum FileType {
    MEDIA_AUDIO("MEDIA_AUDIO"),
    MEDIA_PHOTO("MEDIA_PHOTO"),
    MEDIA_VIDEO("MEDIA_VIDEO"),
    MEDIA_DOCUMENT("MEDIA_DOCUMENT"),
    GEO_JSON("GEO_JSON");

    private final String value;
    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
