package com.sju.roomreservationbackend.common.base.media.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class UploadMediaReqDTO {
    @NotNull(message = "valid.media.media.file.null")
    protected MultipartFile file;
}
