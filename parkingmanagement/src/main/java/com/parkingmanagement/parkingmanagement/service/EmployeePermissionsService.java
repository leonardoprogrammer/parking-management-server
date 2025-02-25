package com.parkingmanagement.parkingmanagement.service;

import com.parkingmanagement.parkingmanagement.model.entity.EmployeePermissions;
import com.parkingmanagement.parkingmanagement.repository.EmployeePermissionsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeePermissionsService {

    private final EmployeePermissionsRepository employeePermissionsRepository;

    public EmployeePermissionsService(EmployeePermissionsRepository employeePermissionsRepository) {
        this.employeePermissionsRepository = employeePermissionsRepository;
    }

    public Optional<EmployeePermissions> findById(UUID id) {
        return employeePermissionsRepository.findById(id);
    }

    public Optional<EmployeePermissions> findByEmployeeId(UUID employeeId) {
        return employeePermissionsRepository.findByEmployeeId(employeeId);
    }

    public EmployeePermissions save(EmployeePermissions employeePermissions) {
        return employeePermissionsRepository.save(employeePermissions);
    }

    public void delete(UUID id) {
        employeePermissionsRepository.deleteById(id);
    }

    public void deleteByEmployeeId(UUID employeeId) {
        employeePermissionsRepository.deleteByEmployeeId(employeeId);
    }
}
