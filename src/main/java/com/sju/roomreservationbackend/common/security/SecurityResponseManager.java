package com.sju.roomreservationbackend.common.security;

import com.sju.roomreservationbackend.common.http.DTOMetadata;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletResponse;

/* 스프링 시큐리티 에러시 반환값 만들어주는 클래스 */
public class SecurityResponseManager {
    public void setResponseHeader(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public String makeResponseBody(Exception e) {
        JSONObject rawDTOMetadata = new JSONObject(new DTOMetadata(e.getMessage(), e.getLocalizedMessage()));
        JSONObject rawResponseDTO = new JSONObject();
        rawResponseDTO.put("_metadata", rawDTOMetadata);
        return rawResponseDTO.toString();
    }
}
