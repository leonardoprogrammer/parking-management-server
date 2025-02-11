package com.parkingmanagement.parkingmanagement.repository;

import com.parkingmanagement.parkingmanagement.model.entity.ParkingEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParkingEmployeeRepository extends JpaRepository<ParkingEmployee, UUID> {

    Optional<ParkingEmployee> findByParkingId(UUID parkingId);
}
