package com.ipstresser.app.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurityAccessChecker")
public class UserSecurityAccessChecker {

    public boolean canAccess(Authentication authentication, String userId) {
        String username = authentication.getName();
        return username.equals(userId);
    }
}
