package com.parkingmanagement.parkingmanagement.repository;

import com.parkingmanagement.parkingmanagement.model.entity.EmployeePermissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeePermissionsRepository extends JpaRepository<EmployeePermissions, UUID> {

    Optional<EmployeePermissions> findByParkingEmployeeId(UUID parkingEmployeeId);
}
