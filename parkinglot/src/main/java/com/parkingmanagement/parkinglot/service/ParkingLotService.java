package com.parkingmanagement.parkinglot.service;

import com.parkingmanagement.parkinglot.entity.ParkingLot;
import com.parkingmanagement.parkinglot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLot createParkingLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public List<ParkingLot> getParkingLotsByOwnerId(Long ownerId) {
        return parkingLotRepository.findByOwnerId(ownerId);
    }
}