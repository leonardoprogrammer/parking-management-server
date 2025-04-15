package com.parkingmanagement.dashboard.repository;

import com.parkingmanagement.dashboard.model.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {

    boolean existsByUserCreatorIdAndId(UUID userCreatorId, UUID id);
}
