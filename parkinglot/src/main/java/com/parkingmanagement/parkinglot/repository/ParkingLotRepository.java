package com.parkingmanagement.parkinglot.repository;

import com.parkingmanagement.parkinglot.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    List<ParkingLot> findByOwnerId(Long ownerId);
}