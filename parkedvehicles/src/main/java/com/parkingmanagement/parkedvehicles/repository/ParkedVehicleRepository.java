package com.parkingmanagement.parkedvehicles.repository;

import com.parkingmanagement.parkedvehicles.model.entity.ParkedVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ParkedVehicleRepository extends JpaRepository<ParkedVehicle, UUID> {

    @Query("SELECT pv FROM ParkedVehicle pv " +
            "WHERE pv.parkingId = :parkingId " +
            "AND pv.checkoutDate IS NULL")
    List<ParkedVehicle> findParkedVehiclesByParkingId(@Param("parkingId") UUID parkingId);

    Page<ParkedVehicle> findByParkingId(UUID parkingId, Pageable pageable);

    long countByParkingId(UUID parkingId);
}
