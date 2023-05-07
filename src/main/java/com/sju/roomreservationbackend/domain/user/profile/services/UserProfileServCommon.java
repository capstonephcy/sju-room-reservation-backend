package com.sju.roomreservationbackend.domain.user.profile.services;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;

public interface UserProfileServCommon {
    MessageSource userMsgSrc = MessageConfig.getUserMsgSrc();

    // Common utility function for handling User Profile
    // Restriction: The code which has to be dependent with repository can't place here.
    default UserProfile fetchCurrentUser(Authentication auth) {
        return (UserProfile) auth.getPrincipal();
    }

    // Distinguish whether user is Admin or general User
    default boolean checkCurrentUserIsAdmin(Authentication auth) {
        UserProfile profile = this.fetchCurrentUser(auth);
        return profile.getPermissions().contains(Permission.ADMIN);
    }
    default boolean checkCurrentUserIsUser(Authentication auth) {
        UserProfile profile = this.fetchCurrentUser(auth);
        return profile.getPermissions().contains(Permission.STUDENT);
    }
}
