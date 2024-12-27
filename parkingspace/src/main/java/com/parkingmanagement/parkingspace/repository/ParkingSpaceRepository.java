package com.parkingmanagement.parkingspace.repository;

import com.parkingmanagement.parkingspace.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    List<ParkingSpace> findByParkingLotId(Long parkingLotId);
}
