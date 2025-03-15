package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.dto.*;
import com.parkingmanagement.parkingmanagement.model.entity.EmployeePermissions;
import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import com.parkingmanagement.parkingmanagement.model.entity.ParkingEmployee;
import com.parkingmanagement.parkingmanagement.model.entity.User;
import com.parkingmanagement.parkingmanagement.security.SecurityService;
import com.parkingmanagement.parkingmanagement.security.SecurityUtils;
import com.parkingmanagement.parkingmanagement.service.EmployeePermissionsService;
import com.parkingmanagement.parkingmanagement.service.ParkingEmployeeService;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import com.parkingmanagement.parkingmanagement.service.UserService;
import com.parkingmanagement.parkingmanagement.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class ParkingEmployeeController {

    private final ParkingEmployeeService parkingEmployeeService;
    private final EmployeePermissionsService employeePermissionsService;
    private final ParkingService parkingService;
    private final UserService userService;
    private final SecurityService securityService;

    public ParkingEmployeeController(ParkingEmployeeService parkingEmployeeService, EmployeePermissionsService employeePermissionsService, ParkingService parkingService, UserService userService, SecurityService securityService) {
        this.parkingEmployeeService = parkingEmployeeService;
        this.employeePermissionsService = employeePermissionsService;
        this.parkingService = parkingService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        ParkingEmployee parkingEmployee = parkingEmployeeService.findById(id).orElse(null);
        if (parkingEmployee == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.currentUserIsOwnerOrEmployee(parkingEmployee.getParkingId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User employeeUser = userService.findById(parkingEmployee.getUserId()).orElse(null);
        User adderUser = userService.findById(parkingEmployee.getAdderUserId()).orElse(null);

        User userToken = securityService.getCurrentUser();

        boolean isOwner = parkingService.existsByUserCreatorIdAndId(userToken.getId(), parkingEmployee.getParkingId());
        boolean seeDetails;

        if (isOwner) {
            seeDetails = true;
        } else {
            ParkingEmployee parkingEmployeeToken = parkingEmployeeService.findByParkingIdAndUserId(parkingEmployee.getParkingId(), userToken.getId()).orElse(null);
            EmployeePermissions employeePermissionsToken = employeePermissionsService.findByEmployeeId(parkingEmployeeToken.getId()).orElse(null);
            seeDetails = employeePermissionsToken != null && employeePermissionsToken.isCanAddEmployee();
        }

        EmployeePermissions employeePermissions = seeDetails ? employeePermissionsService.findByEmployeeId(parkingEmployee.getId()).orElse(null) : null;

        ResponseDetailsEmployeeDTO responseDetailsEmployeeDTO = new ResponseDetailsEmployeeDTO(
                parkingEmployee.getId(),
                parkingEmployee.getParkingId(),
                parkingEmployee.getUserId(),
                employeeUser.getName(),
                Utils.maskCpf(employeeUser.getCpf()),
                Utils.maskEmail(employeeUser.getEmail()),
                Utils.maskPhone(employeeUser.getTelephone()),
                adderUser.getName(),
                parkingEmployee.getCreatedAt(),
                employeePermissions
        );

        return ResponseEntity.ok(responseDetailsEmployeeDTO);
    }

    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<Object> getEmployeesByParkingId(@PathVariable UUID parkingId) {
        Parking parking = parkingService.findById(parkingId).orElse(null);
        if (parking == null) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        if (!securityService.currentUserIsOwnerOrEmployee(parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<ParkingEmployee> employees = parkingEmployeeService.findEmployeesByParkingId(parkingId);

        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<BasicEmployeeDTO> basicEmployeeDTOS = new ArrayList<>();
        for (ParkingEmployee employee : employees) {
            User userEmployee = userService.findById(employee.getUserId()).orElse(null);

            BasicEmployeeDTO basicEmployeeDTO = new BasicEmployeeDTO(
                    employee.getId(),
                    userEmployee.getName()
            );
            basicEmployeeDTOS.add(basicEmployeeDTO);
        }

        User owner = userService.findById(parking.getUserCreatorId()).orElse(null);

        ResponseListEmployeesDTO responseListEmployeesDTO = new ResponseListEmployeesDTO(
                owner.getName(),
                basicEmployeeDTOS
        );

        return ResponseEntity.ok(responseListEmployeesDTO);
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

        if (!securityService.currentUserIsOwner(parkingId) && !securityService.currentUserIsEmployeeAndCanAddEmployee(parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ParkingEmployee newParkingEmployee = new ParkingEmployee(parkingId, userId, securityService.getCurrentUser().getId());
        ParkingEmployee savedParkingEmployee = parkingEmployeeService.save(newParkingEmployee);

        EmployeePermissions newEmployeePermissions = new EmployeePermissions(
                savedParkingEmployee.getId(),
                false,
                false,
                false,
                false
        );
        employeePermissionsService.save(newEmployeePermissions);

        return ResponseEntity.ok(savedParkingEmployee);
    }

    @PostMapping("/leave")
    public ResponseEntity<Object> leave(@RequestParam UUID parkingId, @RequestParam UUID userId) {
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        if (!SecurityUtils.isCurrentUser(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ParkingEmployee parkingEmployee = parkingEmployeeService.findByParkingIdAndUserId(parkingId, userId).orElse(null);
        if (parkingEmployee != null) {
            return ResponseEntity.badRequest().body("Este usuário não está associado a este estacionamento");
        }

        parkingEmployeeService.deleteById(parkingEmployee.getId());

        return ResponseEntity.ok("Usuário removido do estacionamento");
    }

    @DeleteMapping("/{parkingEmployeeId}")
    public ResponseEntity<Object> delete(@PathVariable UUID parkingEmployeeId) {
        ParkingEmployee parkingEmployee = parkingEmployeeService.findById(parkingEmployeeId).orElse(null);
        if (parkingEmployee == null) {
            return ResponseEntity.badRequest().body("Não há funcionário com este ID");
        }

        Parking parking = parkingService.findById(parkingEmployee.getParkingId()).orElse(null);

        if (parking == null || (!securityService.currentUserIsOwner(parking.getId())
                && !securityService.currentUserIsEmployeeAndCanAddEmployee(parking.getId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        employeePermissionsService.deleteByEmployeeId(parkingEmployeeId);
        parkingEmployeeService.deleteById(parkingEmployeeId);

        return ResponseEntity.ok().build();
    }
}
