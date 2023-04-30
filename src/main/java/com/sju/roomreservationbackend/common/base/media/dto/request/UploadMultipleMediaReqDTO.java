package com.sju.roomreservationbackend.common.base.media.dto.request;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMultipleMediaReqDTO extends GeneralResDTO {
    @NotEmpty(message = "valid.media.file.empty")
    protected List<MultipartFile> files;

    @NotBlank(message = "valid.media.author.blank")
    @Size(max = 50, message = "valid.media.author.size")
    protected String author;

    @NotBlank(message = "valid.media.translator.blank")
    @Size(max = 50, message = "valid.media.translator.size")
    protected String translator;
}
