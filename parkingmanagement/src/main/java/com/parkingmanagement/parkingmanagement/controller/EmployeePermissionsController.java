package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.dto.PermissionsDTO;
import com.parkingmanagement.parkingmanagement.model.dto.RequestEmployeePermissionsDTO;
import com.parkingmanagement.parkingmanagement.model.entity.EmployeePermissions;
import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import com.parkingmanagement.parkingmanagement.model.entity.ParkingEmployee;
import com.parkingmanagement.parkingmanagement.model.entity.User;
import com.parkingmanagement.parkingmanagement.security.SecurityService;
import com.parkingmanagement.parkingmanagement.service.EmployeePermissionsService;
import com.parkingmanagement.parkingmanagement.service.ParkingEmployeeService;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/employee-permissions")
public class EmployeePermissionsController {

    private final EmployeePermissionsService employeePermissionsService;
    private final ParkingEmployeeService parkingEmployeeService;
    private final ParkingService parkingService;
    private final SecurityService securityservice;

    public EmployeePermissionsController(EmployeePermissionsService employeePermissionsService, ParkingEmployeeService parkingEmployeeService, ParkingService parkingService, SecurityService securityservice) {
        this.employeePermissionsService = employeePermissionsService;
        this.parkingEmployeeService = parkingEmployeeService;
        this.parkingService = parkingService;
        this.securityservice = securityservice;
    }

    @GetMapping("/currentUser")
    public ResponseEntity<Object> getCurrentUserPermissions(@RequestParam UUID parkingId) {
        Parking parking = parkingService.findById(parkingId).orElse(null);
        if (parking == null) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        User currentUser = securityservice.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }

        if (!securityservice.userIsOwnerOrEmployee(parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PermissionsDTO permissionsDTO;

        if (parking.getUserCreatorId().equals(currentUser.getId())) {
            permissionsDTO = new PermissionsDTO(
                    true,
                    true,
                    true,
                    true
            );
        } else {
            ParkingEmployee parkingEmployee = parkingEmployeeService.findByParkingIdAndUserId(parkingId, currentUser.getId()).orElse(null);
            EmployeePermissions employeePermissions = employeePermissionsService.findByEmployeeId(parkingEmployee.getId()).orElse(null);
            if (employeePermissions != null) {
                permissionsDTO = new PermissionsDTO(
                        employeePermissions.isCanCheckinVehicle(),
                        employeePermissions.isCanCheckoutVehicle(),
                        employeePermissions.isCanAddEmployee(),
                        employeePermissions.isCanEditParking()
                );
            } else {
                permissionsDTO = new PermissionsDTO(
                        false,
                        false,
                        false,
                        false
                );
            }
        }

        return ResponseEntity.ok(permissionsDTO);
    }

    @PutMapping
    public ResponseEntity<Object> updateEmployeePermissions(@Valid @RequestBody RequestEmployeePermissionsDTO requestEmployeePermissionsDTO,
                                                            @RequestParam UUID employeeId) {
        ParkingEmployee parkingEmployee = parkingEmployeeService.findById(employeeId).orElse(null);
        if (parkingEmployee == null) {
            return ResponseEntity.badRequest().body("Não há funcionário com este ID");
        }

        User currentUser = securityservice.getCurrentUser();

        if (!parkingService.existsByUserCreatorIdAndId(currentUser.getId(), parkingEmployee.getParkingId())) {
            if (!parkingEmployeeService.existsByUserIdAndParkingId(currentUser.getId(), parkingEmployee.getParkingId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else {
                ParkingEmployee currentEmployee = parkingEmployeeService.findByParkingIdAndUserId(parkingEmployee.getParkingId(), currentUser.getId()).orElse(null);
                EmployeePermissions currentEmployeePermissions = employeePermissionsService.findByEmployeeId(currentEmployee.getId()).orElse(null);
                if (currentEmployeePermissions == null || !currentEmployeePermissions.isCanAddEmployee()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
        }

        EmployeePermissions employeePermissions = employeePermissionsService.findByEmployeeId(parkingEmployee.getId()).orElse(null);
        if (employeePermissions == null) {
            employeePermissions = new EmployeePermissions();
            employeePermissions.setEmployeeId(parkingEmployee.getId());
        }

        employeePermissions.setCanCheckinVehicle(requestEmployeePermissionsDTO.isCanCheckinVehicle());
        employeePermissions.setCanCheckoutVehicle(requestEmployeePermissionsDTO.isCanCheckoutVehicle());
        employeePermissions.setCanAddEmployee(requestEmployeePermissionsDTO.isCanAddEmployee());
        employeePermissions.setCanEditParking(requestEmployeePermissionsDTO.isCanEditParking());
        employeePermissions.setUpdatedAt(LocalDateTime.now());
        employeePermissions.setUpdateUserId(currentUser.getId());
        employeePermissionsService.save(employeePermissions);

        return ResponseEntity.ok().build();
    }
}
