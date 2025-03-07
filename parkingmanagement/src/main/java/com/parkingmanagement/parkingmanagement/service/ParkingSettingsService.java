package com.parkingmanagement.parkingmanagement.service;

import com.parkingmanagement.parkingmanagement.model.entity.ParkingSettings;
import com.parkingmanagement.parkingmanagement.repository.ParkingSettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSettingsService {

    private final ParkingSettingsRepository parkingSettingsRepository;

    public ParkingSettingsService(ParkingSettingsRepository parkingSettingsRepository) {
        this.parkingSettingsRepository = parkingSettingsRepository;
    }

    public ParkingSettings save(ParkingSettings parkingSettings) {
        return parkingSettingsRepository.save(parkingSettings);
    }

    public Optional<ParkingSettings> findByParkingId(UUID parkingId) {
        return parkingSettingsRepository.findByParkingId(parkingId);
    }

    @Transactional
    public void deleteByParkingId(UUID parkingId) {
        parkingSettingsRepository.deleteByParkingId(parkingId);
    }
}
