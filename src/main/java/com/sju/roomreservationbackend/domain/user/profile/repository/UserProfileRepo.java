package com.sju.roomreservationbackend.domain.user.profile.repository;

import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepo extends CrudRepository<UserProfile, Long> {
    Optional<UserProfile> findByEmail(String email);
    Optional<UserProfile> findByUsername(String username);
    List<UserProfile> findAllByName(String name);
    List<UserProfile> findAllByPermissionsContainingAndActive(Permission permission, Boolean active);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
}
