package com.sju.roomreservationbackend.domain.user.auth.dto.request;

import lombok.Data;

@Data
public class LoginReqDTO {
    private String email;
    private String password;
}
