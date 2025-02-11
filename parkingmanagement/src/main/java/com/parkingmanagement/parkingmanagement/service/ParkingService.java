package com.parkingmanagement.parkingmanagement.service;

import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import com.parkingmanagement.parkingmanagement.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    public Optional<Parking> findById(UUID id) {
        return parkingRepository.findById(id);
    }

    public Parking save(Parking parking) {
        return parkingRepository.save(parking);
    }

    public void delete(UUID id) {
        parkingRepository.deleteById(id);
    }
}
