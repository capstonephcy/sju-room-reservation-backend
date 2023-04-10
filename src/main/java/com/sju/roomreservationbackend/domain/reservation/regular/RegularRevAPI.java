package com.sju.roomreservationbackend.domain.reservation.regular;

import com.sju.roomreservationbackend.domain.reservation.regular.dto.request.*;
import com.sju.roomreservationbackend.domain.reservation.regular.dto.response.*;
import com.sju.roomreservationbackend.domain.reservation.regular.service.RegularRevCrudServ;

public class RegularRevAPI {
    private RegularRevCrudServ reservationCrudServ;

    public CreateRegularRevResDTO createRegularRev(CreateRegularRevReqDTO reqDTO) {
        return new CreateRegularRevResDTO();
    }

    public FetchRegularRevResDTO fetchRegularRev(FetchRegularRevReqDTO reqDTO, FetchRegularRevReqOptionType optionType) {
        return new FetchRegularRevResDTO();
    }

    public UpdateRegularRevResDTO updateRegularRev(UpdateRegularRevReqDTO reqDTO) {
        return new UpdateRegularRevResDTO();
    }

    public DeleteRegularRevResDTO deleteRegularRev(DeleteRegularRevReqDTO reqDTO) {
        return new DeleteRegularRevResDTO();
    }

    public CheckInRegularRevResDTO confirmRegularRev(CheckInRegularRevReqDTO reqDTO) {
        return new CheckInRegularRevResDTO();
    }
}
