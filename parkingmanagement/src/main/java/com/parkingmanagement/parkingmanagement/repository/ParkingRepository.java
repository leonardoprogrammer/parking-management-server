package com.parkingmanagement.parkingmanagement.repository;

import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {

    List<Parking> findByUserCreatorId(UUID userCreatorId);
}
