package com.sju.roomreservationbackend.domain.notification;

import com.sju.roomreservationbackend.common.http.APIUtil;
import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.notification.dto.NotificationSendReqDTO;
import com.sju.roomreservationbackend.domain.notification.dto.NotificationSendResDTO;
import com.sju.roomreservationbackend.domain.notification.service.NotificationServ;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationAPI {

    private final NotificationServ notificationServ;
    private final MessageSource msgSrc = MessageConfig.getNotificationMsgSrc();

    // Admin send notification to end user
    @PreAuthorize("hasAnyAuthority('ROOT_ADMIN', 'ADMIN')")
    @PostMapping("/notification/send")
    public ResponseEntity<?> sendNotification(@Valid @RequestBody NotificationSendReqDTO reqDTO) {
        NotificationSendResDTO resDTO = new NotificationSendResDTO();

        return new APIUtil<NotificationSendResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                notificationServ.sendNotification(reqDTO);
            }
        }.execute(resDTO, "res.notification.send.success");
    }
}
