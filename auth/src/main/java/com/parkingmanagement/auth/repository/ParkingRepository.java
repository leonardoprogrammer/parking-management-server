package com.parkingmanagement.auth.repository;

import com.parkingmanagement.auth.model.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {
}
