package com.sju.roomreservationbackend.domain.user.profile.entity;

public enum Permission {
    ROOT_ADMIN("ROOT_ADMIN"),
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    GRADUATED("GRADUATED"),
    PROFESSOR("PROFESSOR");

    private final String value;
    Permission(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
