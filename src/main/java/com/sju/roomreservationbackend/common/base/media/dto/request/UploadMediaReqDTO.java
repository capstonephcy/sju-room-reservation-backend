package com.dmtlabs.aidocentserver.global.base.media.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UploadMediaReqDTO {
    @NotNull(message = "valid.media.media.file.null")
    protected MultipartFile file;

    @NotBlank(message = "valid.media.author.blank")
    @Size(max = 50, message = "valid.media.author.size")
    protected String author;

    @NotBlank(message = "valid.media.translator.blank")
    @Size(max = 50, message = "valid.media.translator.size")
    protected String translator;
}
