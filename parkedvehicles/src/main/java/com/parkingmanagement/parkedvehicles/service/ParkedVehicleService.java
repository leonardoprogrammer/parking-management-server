package com.parkingmanagement.parkedvehicles.service;

import com.parkingmanagement.parkedvehicles.model.dto.ResponseHistoryParkedVehicleDTO;
import com.parkingmanagement.parkedvehicles.model.entity.ParkedVehicle;
import com.parkingmanagement.parkedvehicles.repository.ParkedVehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<ResponseHistoryParkedVehicleDTO> getParkedVehiclesHistoryByParkingId(UUID parkingId, Integer page, Integer sizePage) {
        Pageable pageable = PageRequest.of((page - 1), sizePage, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ParkedVehicle> parkedVehiclesPage = parkedVehicleRepository.findByParkingId(parkingId, pageable);
        return parkedVehiclesPage.map(this::convertToResponseHistoryParkedVehicleDTO);
    }

    public long getTotalPagesOfParkedVehiclesHistory(UUID parkingId, Integer sizePage) {
        long totalRecords = parkedVehicleRepository.countByParkingId(parkingId);
        return (totalRecords / sizePage) + 1;
    }

    private ResponseHistoryParkedVehicleDTO convertToResponseHistoryParkedVehicleDTO(ParkedVehicle parkedVehicle) {
        return new ResponseHistoryParkedVehicleDTO(
                parkedVehicle.getPlate(),
                parkedVehicle.getModel(),
                parkedVehicle.getColor(),
                parkedVehicle.getEntryDate(),
                parkedVehicle.getCreatedAt(),
                parkedVehicle.getCheckoutDate() != null
        );
    }

    public void delete(UUID id) {
        parkedVehicleRepository.deleteById(id);
    }
}
