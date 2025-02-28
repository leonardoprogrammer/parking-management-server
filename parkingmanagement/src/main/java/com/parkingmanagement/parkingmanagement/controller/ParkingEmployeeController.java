package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.entity.EmployeePermissions;
import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import com.parkingmanagement.parkingmanagement.model.entity.ParkingEmployee;
import com.parkingmanagement.parkingmanagement.model.entity.User;
import com.parkingmanagement.parkingmanagement.security.SecuritytUtils;
import com.parkingmanagement.parkingmanagement.service.EmployeePermissionsService;
import com.parkingmanagement.parkingmanagement.service.ParkingEmployeeService;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import com.parkingmanagement.parkingmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class ParkingEmployeeController {

    private final ParkingEmployeeService parkingEmployeeService;
    private final EmployeePermissionsService employeePermissionsService;
    private final ParkingService parkingService;
    private final UserService userService;

    public ParkingEmployeeController(ParkingEmployeeService parkingEmployeeService, EmployeePermissionsService employeePermissionsService, ParkingService parkingService, UserService userService) {
        this.parkingEmployeeService = parkingEmployeeService;
        this.employeePermissionsService = employeePermissionsService;
        this.parkingService = parkingService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingEmployee> getById(@PathVariable UUID id) {
        return parkingEmployeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<List<ParkingEmployee>> getByParkingId(@PathVariable UUID parkingId) {
        List<ParkingEmployee> employees = parkingEmployeeService.findByParkingId(parkingId);

        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestParam UUID parkingId, @RequestParam UUID userId) {
        Parking parking = parkingService.findById(parkingId).orElse(null);

        if (parking == null) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        User userEmployee = userService.findById(userId).orElse(null);
        if (userEmployee == null) {
            return ResponseEntity.badRequest().body("Não há usuário com este ID");
        }

        User userToken = userService.findByEmail(SecuritytUtils.getCurrentUserEmail()).orElse(null);
        if (!parking.getUserCreatorId().equals(userToken.getId())) {
            ParkingEmployee parkingEmployee = parkingEmployeeService.findByParkingIdAndUserId(parkingId, userToken.getId()).orElse(null);
            if (parkingEmployee == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else {
                EmployeePermissions employeePermissions = employeePermissionsService.findByEmployeeId(parkingEmployee.getId()).orElse(null);
                if (employeePermissions == null || !employeePermissions.isAddEmployee()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
        }

        ParkingEmployee parkingEmployee = new ParkingEmployee(parkingId, userId, userToken.getName());
        ParkingEmployee newParkingEmployee = parkingEmployeeService.save(parkingEmployee);

        return ResponseEntity.ok(newParkingEmployee);
    }

    @PostMapping("/leave")
    public ResponseEntity<Object> leave(@RequestParam UUID parkingId, @RequestParam UUID userId) {
        if (!parkingService.existsById(parkingId)) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        ParkingEmployee parkingEmployee = parkingEmployeeService.findByParkingIdAndUserId(parkingId, userId).orElse(null);
        if (parkingEmployee != null) {
            return ResponseEntity.badRequest().body("Este usuário não está associado a este estacionamento");
        }

        parkingEmployeeService.deleteById(parkingEmployee.getId());

        return ResponseEntity.ok("Usuário removido do estacionamento");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        ParkingEmployee parkingEmployee = parkingEmployeeService.findById(id).orElse(null);
        if (parkingEmployee == null) {
            return ResponseEntity.badRequest().body("Não há funcionário com este ID");
        }

        Parking parking = parkingService.findById(parkingEmployee.getParkingId()).orElse(null);
        User user = userService.findById(parking.getUserCreatorId()).orElse(null);
        if (!SecuritytUtils.isCurrentUser(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        parkingEmployeeService.deleteById(id);

        return ResponseEntity.ok("Funcionário removido do estacionamento");
    }
}
