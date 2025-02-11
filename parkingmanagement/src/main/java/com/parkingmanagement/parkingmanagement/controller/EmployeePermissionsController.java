package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.entity.EmployeePermissions;
import com.parkingmanagement.parkingmanagement.service.EmployeePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employee-permissions")
public class EmployeePermissionsController {

    @Autowired
    private EmployeePermissionsService employeePermissionsService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeePermissions> getById(@PathVariable UUID id) {
        return employeePermissionsService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeePermissions> getByParkingEmployeeId(@PathVariable UUID parkingEomployeeId) {
        return employeePermissionsService.findByParkingEmployeeId(parkingEomployeeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployeePermissions create(@RequestBody EmployeePermissions employeePermissions) {
        return employeePermissionsService.save(employeePermissions);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        employeePermissionsService.delete(id);
    }
}
