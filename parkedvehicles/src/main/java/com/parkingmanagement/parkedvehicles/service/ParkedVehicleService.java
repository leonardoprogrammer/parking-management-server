package com.parkingmanagement.parkedvehicles.service;

import com.parkingmanagement.parkedvehicles.model.entity.ParkedVehicle;
import com.parkingmanagement.parkedvehicles.repository.ParkedVehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkedVehicleService {

    private final ParkedVehicleRepository parkedVehicleRepository;

    public ParkedVehicleService(ParkedVehicleRepository parkedVehicleRepository) {
        this.parkedVehicleRepository = parkedVehicleRepository;
    }

    public ParkedVehicle save(ParkedVehicle parkedVehicle) {
        return parkedVehicleRepository.save(parkedVehicle);
    }

    public Optional<ParkedVehicle> findById(UUID id) {
        return parkedVehicleRepository.findById(id);
    }

    public List<ParkedVehicle> findParkedVehiclesByParkingId(UUID parkingId) {
        return parkedVehicleRepository.findParkedVehiclesByParkingId(parkingId);
    }

    public Page<ParkedVehicle> getParkedVehiclesHistoryByParkingId(UUID parkingId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return parkedVehicleRepository.findByParkingId(parkingId, pageable);
    }

    public long getTotalPagesOfParkedVehiclesHistory(UUID parkingId, Integer size) {
        long totalRecords = parkedVehicleRepository.countByParkingId(parkingId);
        return (totalRecords / size);
    }

    public void delete(UUID id) {
        parkedVehicleRepository.deleteById(id);
    }
}
