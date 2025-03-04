package com.parkingmanagement.auth.repository;

import com.parkingmanagement.auth.model.entity.ParkingEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingEmployeeRepository extends JpaRepository<ParkingEmployee, UUID> {

    boolean existsByParkingIdAndUserId(UUID parkingId, UUID userId);
}
