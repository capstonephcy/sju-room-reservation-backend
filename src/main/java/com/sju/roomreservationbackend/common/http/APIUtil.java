package com.sju.roomreservationbackend.common.http;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.Objects;

public abstract class APIUtil<TResDTO extends GeneralResDTO> {
    private final MessageSource msgSrc = MessageConfig.getResponseMsgSrc();
    private static final Logger logger = LogManager.getLogger();

    // 클라이언트의 IP 주소가 담겨있을 수 있는 헤더
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    protected abstract void onSuccess() throws Exception;

    public ResponseEntity<?> execute(TResDTO resDTO, String successMsg) {
        try {
            logger.info("Requested API: " + resDTO.getClass().getName());
            onSuccess();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            logger.warn("------------------------------ Request Process Failed | StackTrace ------------------------------");
            logger.warn(e);
            logger.warn("-------------------------------------------------------------------------------------------------");
            resDTO.set_metadata(new DTOMetadata(e.getMessage(), e.getClass().getName()));
            return ResponseEntity.status(400).body(resDTO);
        }
        resDTO.set_metadata(new DTOMetadata(msgSrc.getMessage(successMsg, null, Locale.ENGLISH)));
        return ResponseEntity.ok(resDTO);
    }

    protected <TPageResDTO extends GeneralPageableResDTO> void buildPageableResDTO(TPageResDTO resDTO, Page<?> page) {
        resDTO.setPageable(true);
        resDTO.setPageIdx(page.getNumber());
        resDTO.setTotalPage(page.getTotalPages());
        resDTO.setPageElementSize(page.getTotalElements());
    }

    public static String getClientIpAddressIfServletRequestExist() {
        if (Objects.isNull(RequestContextHolder.getRequestAttributes())) {
            return "0.0.0.0";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (String header: IP_HEADER_CANDIDATES) {
            String ipFromHeader = request.getHeader(header);
            if (Objects.nonNull(ipFromHeader) && ipFromHeader.length() != 0 && !"unknown".equalsIgnoreCase(ipFromHeader)) {
                return ipFromHeader.split(",")[0];
            }
        }
        return request.getRemoteAddr();
    }
}
