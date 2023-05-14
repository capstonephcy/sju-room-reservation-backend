package com.sju.roomreservationbackend.domain.reservation.regular.service;

import com.sju.roomreservationbackend.domain.reservation.regular.dto.request.CreateRegularRevReqDTO;
import com.sju.roomreservationbackend.domain.reservation.regular.dto.request.UpdateRegularRevReqDTO;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegularRevCrudServ extends RegularRevLogicServ{
    public RegularRev createRegularRev(Authentication auth, CreateRegularRevReqDTO reqDTO) {
        return new RegularRev();
    }

    public RegularRev fetchRegularRevById(Authentication auth, Long id) {
        return new RegularRev();
    }

    public List<RegularRev> fetchRegularRevsByUser(Authentication auth) {
        return new ArrayList<>();
    }

    public List<RegularRev> fetchRegularRevsByUser(Authentication auth, Long userId) {
        return new ArrayList<>();
    }

    public List<RegularRev> fetchRegularRevsByRoom(Authentication auth, Long roomId) {
        return new ArrayList<>();
    }

    public RegularRev updateRegularRev(Authentication auth, UpdateRegularRevReqDTO reqDTO) {
        return new RegularRev();
    }

    public RegularRev confirmRegularRev(Authentication auth, Long regularRevId) {
        return new RegularRev();
    }

    public RegularRev deleteRegularRev(Authentication auth, Long regularRevId) {
        return new RegularRev();
    }
}
