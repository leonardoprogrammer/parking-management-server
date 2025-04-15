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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Retorna as permissões do usuário atual para um estacionamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissões retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Estacionamento ou usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar as permissões do estacionamento")
    })
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

        if (!securityservice.currentUserIsOwnerOrEmployee(parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        PermissionsDTO permissionsDTO;

        if (parking.getUserCreatorId().equals(currentUser.getId())) {
            permissionsDTO = new PermissionsDTO(
                    true,
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
                        employeePermissions.isCanEditParking(),
                        false
                );
            } else {
                permissionsDTO = new PermissionsDTO(
                        false,
                        false,
                        false,
                        false,
                        false
                );
            }
        }

        return ResponseEntity.ok(permissionsDTO);
    }

    @Operation(summary = "Atualiza as permissões de um funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissões atualizadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Funcionário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para atualizar as permissões do funcionário")
    })
    @PutMapping
    public ResponseEntity<Object> updateEmployeePermissions(@Valid @RequestBody RequestEmployeePermissionsDTO requestEmployeePermissionsDTO,
                                                            @RequestParam UUID employeeId) {
        ParkingEmployee parkingEmployee = parkingEmployeeService.findById(employeeId).orElse(null);
        if (parkingEmployee == null) {
            return ResponseEntity.badRequest().body("Não há funcionário com este ID");
        }

        User currentUser = securityservice.getCurrentUser();

        if (!securityservice.currentUserIsOwner(parkingEmployee.getParkingId())
                && !securityservice.currentUserIsEmployeeAndCanAddEmployee(parkingEmployee.getParkingId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
