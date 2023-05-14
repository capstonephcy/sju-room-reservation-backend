package com.sju.roomreservationbackend.domain.reservation.regular.entity;

public enum RegularRevType {
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY");

    private final String value;

    RegularRevType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
