package com.sju.roomreservationbackend.domain.notification.service;

import com.sju.roomreservationbackend.common.firebase.NotificationPushService;
import com.sju.roomreservationbackend.domain.notification.dto.NotificationSendReqDTO;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServ {

    private final NotificationPushService notificationPushService;
    private final UserProfileCrudServ userProfileCrudServ;

    public void sendNotification(NotificationSendReqDTO reqDTO) throws Exception {
        String title = reqDTO.getTitle();
        String body = reqDTO.getBody();

        if (reqDTO.getUserIds().size() == 1) {
            UserProfile sendSubjectUser = userProfileCrudServ.fetchUserProfileById(reqDTO.getUserIds().get(0));
            notificationPushService.sendToSingleClient(title, body, sendSubjectUser);
        } else {
            List<UserProfile> sendSubjectUsers = new ArrayList<>();
            for (Long userId : reqDTO.getUserIds()) {
                sendSubjectUsers.add(userProfileCrudServ.fetchUserProfileById(userId));
            }
            notificationPushService.sendToMultipleClient(title, body, sendSubjectUsers);
        }
    }
}
