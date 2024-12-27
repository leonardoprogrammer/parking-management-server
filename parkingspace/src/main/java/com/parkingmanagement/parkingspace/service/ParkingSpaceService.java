package com.parkingmanagement.parkingspace.service;

import com.parkingmanagement.parkingspace.entity.ParkingSpace;
import com.parkingmanagement.parkingspace.repository.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpaceService {

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpace createParkingSpace(ParkingSpace parkingSpace) {
        return parkingSpaceRepository.save(parkingSpace);
    }

    public List<ParkingSpace> getParkingSpacesByParkingLotId(Long parkingLotId) {
        return parkingSpaceRepository.findByParkingLotId(parkingLotId);
    }
}
