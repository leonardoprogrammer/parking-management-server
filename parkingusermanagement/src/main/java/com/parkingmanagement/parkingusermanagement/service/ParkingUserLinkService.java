package com.parkingmanagement.parkingusermanagement.service;

import com.parkingmanagement.parkingusermanagement.entity.ParkingUserLink;
import com.parkingmanagement.parkingusermanagement.repository.ParkingUserLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingUserLinkService {

    @Autowired
    private ParkingUserLinkRepository parkingUserLinkRepository;

    public ParkingUserLink createParkingUserLink(ParkingUserLink parkingUserLink) {
        return parkingUserLinkRepository.save(parkingUserLink);
    }

    public List<ParkingUserLink> getUsersByParkingLotId(Long parkingLotId) {
        return parkingUserLinkRepository.findByParkingLotId(parkingLotId);
    }

    public void deleteParkingUserLink(Long id) {
        parkingUserLinkRepository.deleteById(id);
    }
}
