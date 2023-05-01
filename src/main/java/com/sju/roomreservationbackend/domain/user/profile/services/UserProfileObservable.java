package com.sju.roomreservationbackend.domain.user.profile.services;

import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;

public interface UserProfileObservable {
    UserProfile fetchUserProfileById(Long userId) throws Exception;
}
