package com.sju.roomreservationbackend.domain.metrics.dto.response;

import com.sju.roomreservationbackend.common.http.GeneralResDTO;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchUserMetricsResDTO extends GeneralResDTO {

    private Double noShowRate;
    private Integer reserveCnt;
    private Integer noShowCnt;

    public void setUserMetrics(UserProfile userProfile) {
        this.noShowRate = userProfile.getNoShowRate();
        this.reserveCnt = userProfile.getReserveCnt();
        this.noShowCnt = userProfile.getNoShowCnt();
    }
}
