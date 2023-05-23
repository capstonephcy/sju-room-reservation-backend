package com.sju.roomreservationbackend.domain.notification.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotificationSendReqDTO {
    private List<Long> userIds;

    private String title;
    private String body;

    public NotificationSendReqDTO(List<Long> attendantIds, String title, String body) {
        this.userIds = attendantIds;
        this.title = title;
        this.body = body;
    }
}
