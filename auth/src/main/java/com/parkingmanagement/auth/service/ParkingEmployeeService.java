package com.parkingmanagement.auth.service;

import com.parkingmanagement.auth.repository.ParkingEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ParkingEmployeeService {

    private final ParkingEmployeeRepository parkingEmployeeRepository;

    public ParkingEmployeeService(ParkingEmployeeRepository parkingEmployeeRepository) {
        this.parkingEmployeeRepository = parkingEmployeeRepository;
    }

    public boolean existsByParkingIdAndUserId(UUID parkingId, UUID userId) {
        return parkingEmployeeRepository.existsByParkingIdAndUserId(parkingId, userId);
    }
}
