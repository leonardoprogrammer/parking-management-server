package com.parkingmanagement.parkingmanagement.service;

import com.parkingmanagement.parkingmanagement.repository.ParkedVehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ParkedVehicleService {

    private final ParkedVehicleRepository parkedVehicleRepository;

    public ParkedVehicleService(ParkedVehicleRepository parkedVehicleRepository) {
        this.parkedVehicleRepository = parkedVehicleRepository;
    }

    @Transactional
    public void deleteByParkingId(UUID parkingId) {
        parkedVehicleRepository.deleteByParkingId(parkingId);
    }
}
