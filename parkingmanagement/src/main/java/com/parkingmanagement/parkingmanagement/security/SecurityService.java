package com.parkingmanagement.parkingmanagement.security;

import com.parkingmanagement.parkingmanagement.model.entity.User;
import com.parkingmanagement.parkingmanagement.service.ParkingEmployeeService;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import com.parkingmanagement.parkingmanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityService {

    private final UserService userService;
    private final ParkingService parkingService;
    private final ParkingEmployeeService parkingEmployeeService;

    public SecurityService(UserService userService, ParkingService parkingService, ParkingEmployeeService parkingEmployeeService) {
        this.userService = userService;
        this.parkingService = parkingService;
        this.parkingEmployeeService = parkingEmployeeService;
    }

    public boolean userIsOwnerOrEmployee(String userEmail, UUID parkingId) {
        User user = userService.findByEmail(userEmail).orElse(null);
        if (user == null) {
            return false;
        }

        return parkingService.existsByUserCreatorIdAndId(user.getId(), parkingId)
                || parkingEmployeeService.existsByUserIdAndParkingId(user.getId(), parkingId);
    }
}
