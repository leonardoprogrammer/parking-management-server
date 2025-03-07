package com.parkingmanagement.parkingmanagement.security;

import com.parkingmanagement.parkingmanagement.model.entity.EmployeePermissions;
import com.parkingmanagement.parkingmanagement.model.entity.ParkingEmployee;
import com.parkingmanagement.parkingmanagement.model.entity.User;
import com.parkingmanagement.parkingmanagement.service.EmployeePermissionsService;
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
    private final EmployeePermissionsService employeePermissionsService;

    public SecurityService(UserService userService, ParkingService parkingService, ParkingEmployeeService parkingEmployeeService, EmployeePermissionsService employeePermissionsService) {
        this.userService = userService;
        this.parkingService = parkingService;
        this.parkingEmployeeService = parkingEmployeeService;
        this.employeePermissionsService = employeePermissionsService;
    }

    public boolean currentUserIsOwnerOrEmployee(UUID parkingId) {
        User user = userService.findByEmail(SecurityUtils.getCurrentUserEmail()).orElse(null);
        if (user == null) {
            return false;
        }

        return parkingService.existsByUserCreatorIdAndId(user.getId(), parkingId)
                || parkingEmployeeService.existsByUserIdAndParkingId(user.getId(), parkingId);
    }

    public User getCurrentUser() {
        return userService.findByEmail(SecurityUtils.getCurrentUserEmail()).orElse(null);
    }

    public boolean currentUserIsOwner(UUID parkingId) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        return parkingService.existsByUserCreatorIdAndId(currentUser.getId(), parkingId);
    }

    public boolean currentUserIsEmployeeAndCanEditParking(UUID parkingId) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        if (!parkingEmployeeService.existsByUserIdAndParkingId(currentUser.getId(), parkingId)) {
            return false;
        }

        ParkingEmployee currentEmployee = parkingEmployeeService.findByParkingIdAndUserId(parkingId, currentUser.getId()).orElse(null);
        EmployeePermissions currentEmployeePermissions = employeePermissionsService.findByEmployeeId(currentEmployee.getId()).orElse(null);

        return currentEmployeePermissions != null && currentEmployeePermissions.isCanEditParking();
    }

    public boolean currentUserIsEmployeeAndCanAddEmployee(UUID parkingId) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        if (!parkingEmployeeService.existsByUserIdAndParkingId(currentUser.getId(), parkingId)) {
            return false;
        }

        ParkingEmployee currentEmployee = parkingEmployeeService.findByParkingIdAndUserId(parkingId, currentUser.getId()).orElse(null);
        EmployeePermissions currentEmployeePermissions = employeePermissionsService.findByEmployeeId(currentEmployee.getId()).orElse(null);

        return currentEmployeePermissions != null && currentEmployeePermissions.isCanAddEmployee();
    }
}
