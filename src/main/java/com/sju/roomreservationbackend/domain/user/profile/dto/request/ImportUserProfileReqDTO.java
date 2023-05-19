package com.sju.roomreservationbackend.domain.user.profile.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImportUserProfileReqDTO {
    MultipartFile csvFile;
}
