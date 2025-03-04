package com.parkingmanagement.auth.service;

import com.parkingmanagement.auth.model.entity.Parking;
import com.parkingmanagement.auth.repository.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public Optional<Parking> findById(UUID id) {
        return parkingRepository.findById(id);
    }
}
