package com.parkingmanagement.parkingmanagement.repository;

import com.parkingmanagement.parkingmanagement.model.entity.ParkingSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParkingSettingsRepository extends JpaRepository<ParkingSettings, UUID> {

    Optional<ParkingSettings> findByParkingId(UUID parkingId);

    void deleteByParkingId(UUID parkingId);
}
