package com.parkingmanagement.parkedvehicles.repository;

import com.parkingmanagement.parkedvehicles.model.entity.ParkingSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParkingSettingsRepository extends JpaRepository<ParkingSettings, UUID> {

    Optional<ParkingSettings> findByParkingId(UUID parkingId);
}
