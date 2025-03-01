package com.parkingmanagement.parkedvehicles.security;

import com.parkingmanagement.parkedvehicles.model.entity.User;
import com.parkingmanagement.parkedvehicles.service.ParkingEmployeeService;
import com.parkingmanagement.parkedvehicles.service.ParkingService;
import com.parkingmanagement.parkedvehicles.service.UserService;
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
