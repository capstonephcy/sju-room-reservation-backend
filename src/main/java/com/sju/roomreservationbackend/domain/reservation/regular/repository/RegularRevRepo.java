package com.sju.roomreservationbackend.domain.reservation.regular.repository;

import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularRevRepo extends CrudRepository<RegularRev, Long> {
    Page<RegularRev> findAllByRevOwnerId(Long userId, Pageable pageable);

    Page<RegularRev> findAllByRoomId(Long roomId, Pageable pageable);
}
