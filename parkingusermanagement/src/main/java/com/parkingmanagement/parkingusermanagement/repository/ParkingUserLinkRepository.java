package com.parkingmanagement.parkingusermanagement.repository;

import com.parkingmanagement.parkingusermanagement.entity.ParkingUserLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingUserLinkRepository extends JpaRepository<ParkingUserLink, Long> {
    List<ParkingUserLink> findByParkingLotId(Long parkingLotId);
}
