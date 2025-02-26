package com.parkingmanagement.parkingmanagement.repository;

import com.parkingmanagement.parkingmanagement.model.entity.ParkedVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkedVehicleRepository extends JpaRepository<ParkedVehicle, UUID> {

    void deleteByParkingId(UUID parkingId);
}
