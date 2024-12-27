package com.parkingmanagement.vehiclemanagement.repository;

import com.parkingmanagement.vehiclemanagement.entity.VehicleRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRecordRepository extends JpaRepository<VehicleRecord, Long> {
    List<VehicleRecord> findByParkingLotId(Long parkingLotId);
    List<VehicleRecord> findByLicensePlate(String licensePlate);
}
