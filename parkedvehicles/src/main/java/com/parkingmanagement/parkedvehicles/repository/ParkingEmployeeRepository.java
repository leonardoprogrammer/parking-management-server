package com.parkingmanagement.parkedvehicles.repository;

import com.parkingmanagement.parkedvehicles.model.entity.ParkingEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingEmployeeRepository extends JpaRepository<ParkingEmployee, UUID> {

    boolean existsByUserIdAndParkingId(UUID userId, UUID parkingId);
}
