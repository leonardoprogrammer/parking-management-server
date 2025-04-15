package com.parkingmanagement.dashboard.service;

import com.parkingmanagement.dashboard.repository.ParkedVehicleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ParkedVehicleService {

    private final ParkedVehicleRepository parkedVehicleRepository;

    public ParkedVehicleService(ParkedVehicleRepository parkedVehicleRepository) {
        this.parkedVehicleRepository = parkedVehicleRepository;
    }

    public BigDecimal getRevenue(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate) {
        return parkedVehicleRepository.calculateRevenue(parkingId, startDate, endDate);
    }

    public Integer getCheckInQuantity(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate) {
        return parkedVehicleRepository.countCheckIn(parkingId, startDate, endDate);
    }

    public Integer getCheckOutQuantity(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate) {
        return parkedVehicleRepository.countCheckOut(parkingId, startDate, endDate);
    }

    public List<Object[]> getCheckInDataGroupedByDate(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate) {
        return parkedVehicleRepository.getCheckInDataGroupedByDate(parkingId, startDate, endDate);
    }

    public List<Object[]> getRevenueDataGroupedByDate(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate) {
        return parkedVehicleRepository.getRevenueDataGroupedByDate(parkingId, startDate, endDate);
    }
}
