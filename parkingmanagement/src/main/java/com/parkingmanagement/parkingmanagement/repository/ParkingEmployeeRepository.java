package com.parkingmanagement.parkingmanagement.repository;

import com.parkingmanagement.parkingmanagement.model.entity.ParkingEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingEmployeeRepository extends JpaRepository<ParkingEmployee, UUID> {

    List<ParkingEmployee> findByUserId(UUID userId);

    List<ParkingEmployee> findByParkingId(UUID parkingId);

    Optional<ParkingEmployee> findByParkingIdAndUserId(UUID parkingId, UUID userId);

    boolean existsByUserIdAndParkingId(UUID userId, UUID parkingId);
}
