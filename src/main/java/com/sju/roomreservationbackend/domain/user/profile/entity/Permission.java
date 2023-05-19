package com.sju.roomreservationbackend.domain.user.profile.entity;

public enum Permission {
    ROOT_ADMIN("ROOT_ADMIN"),
    ADMIN("ADMIN"),
    PROFESSOR("PROFESSOR"),
    GRADUATED("GRADUATED"),
    STUDENT("STUDENT");

    private final String value;
    Permission(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
