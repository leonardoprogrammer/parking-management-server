package com.parkingmanagement.dashboard.security;

import com.parkingmanagement.dashboard.model.entity.User;
import com.parkingmanagement.dashboard.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final UserService userService;

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser() {
        return userService.findByEmail(SecurityUtils.getCurrentUserEmail()).orElse(null);
    }
}
