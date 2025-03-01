package com.parkingmanagement.parkedvehicles.service;

import com.parkingmanagement.parkedvehicles.repository.ParkingEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ParkingEmployeeService {

    private final ParkingEmployeeRepository parkingEmployeeRepository;

    public ParkingEmployeeService(ParkingEmployeeRepository parkingEmployeeRepository) {
        this.parkingEmployeeRepository = parkingEmployeeRepository;
    }

    public boolean existsByUserIdAndParkingId(UUID userId, UUID parkingId) {
        return parkingEmployeeRepository.existsByUserIdAndParkingId(userId, parkingId);
    }
}
