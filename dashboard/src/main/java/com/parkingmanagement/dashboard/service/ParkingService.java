package com.parkingmanagement.dashboard.service;

import com.parkingmanagement.dashboard.repository.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public boolean existsByUserCreatorIdAndId(UUID userId, UUID parkingId) {
        return parkingRepository.existsByUserCreatorIdAndId(userId, parkingId);
    }
}
