package com.sju.roomreservationbackend.common.base.media.dto.request;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMultipleMediaReqDTO extends GeneralResDTO {
    @NotEmpty(message = "valid.media.file.empty")
    protected List<MultipartFile> files;
}
