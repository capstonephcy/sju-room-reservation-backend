package com.sju.roomreservationbackend.domain.user.entity;


public class UserProfile {
    private Long id;
    private String username;
    private String password;
    private Boolean active;
    private String email;
    private String phone;
    private String name;
    private String permission;
    private Double noShowRate;
    private Integer reserveCnt;
    private Integer noShowCnt;
}
