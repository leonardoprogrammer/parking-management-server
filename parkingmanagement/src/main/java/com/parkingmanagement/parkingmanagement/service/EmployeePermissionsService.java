package com.parkingmanagement.parkingmanagement.service;

import com.parkingmanagement.parkingmanagement.model.entity.EmployeePermissions;
import com.parkingmanagement.parkingmanagement.repository.EmployeePermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeePermissionsService {

    @Autowired
    private EmployeePermissionsRepository employeePermissionsRepository;

    public Optional<EmployeePermissions> findById(UUID id) {
        return employeePermissionsRepository.findById(id);
    }

    public Optional<EmployeePermissions> findByParkingEmployeeId(UUID parkingEmployeeId) {
        return employeePermissionsRepository.findByParkingEmployeeId(parkingEmployeeId);
    }

    public EmployeePermissions save(EmployeePermissions employeePermissions) {
        return employeePermissionsRepository.save(employeePermissions);
    }

    public void delete(UUID id) {
        employeePermissionsRepository.deleteById(id);
    }
}
