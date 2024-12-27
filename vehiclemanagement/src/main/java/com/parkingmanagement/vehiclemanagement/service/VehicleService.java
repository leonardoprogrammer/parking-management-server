package com.parkingmanagement.vehiclemanagement.service;

import com.parkingmanagement.vehiclemanagement.entity.VehicleRecord;
import com.parkingmanagement.vehiclemanagement.repository.VehicleRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRecordRepository vehicleRecordRepository;

    public VehicleRecord registerEntry(VehicleRecord vehicleRecord) {
        vehicleRecord.setEntryTime(LocalDateTime.now());
        return vehicleRecordRepository.save(vehicleRecord);
    }

    public VehicleRecord registerExit(Long id) {
        VehicleRecord record = vehicleRecordRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle record not found"));
        record.setExitTime(LocalDateTime.now());
        record.setFee(calculateFee(record.getEntryTime(), record.getExitTime()));
        return vehicleRecordRepository.save(record);
    }

    public List<VehicleRecord> getRecordsByParkingLot(Long parkingLotId) {
        return vehicleRecordRepository.findByParkingLotId(parkingLotId);
    }

    private Double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = Duration.between(entryTime, exitTime).toMinutes();
        return minutes * 0.10; // Example: $0.10 per minute
    }
}
