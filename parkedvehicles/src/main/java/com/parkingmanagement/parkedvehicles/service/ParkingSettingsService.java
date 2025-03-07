package com.parkingmanagement.parkedvehicles.service;

import com.parkingmanagement.parkedvehicles.model.entity.ParkingSettings;
import com.parkingmanagement.parkedvehicles.repository.ParkingSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSettingsService {

    private final ParkingSettingsRepository parkingSettingsRepository;

    public ParkingSettingsService(ParkingSettingsRepository parkingSettingsRepository) {
        this.parkingSettingsRepository = parkingSettingsRepository;
    }

    public Optional<ParkingSettings> findByParkingId(UUID parkingId) {
        return parkingSettingsRepository.findByParkingId(parkingId);
    }
}
