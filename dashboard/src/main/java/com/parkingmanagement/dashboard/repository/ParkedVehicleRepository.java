package com.parkingmanagement.dashboard.repository;

import com.parkingmanagement.dashboard.model.entity.ParkedVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ParkedVehicleRepository extends JpaRepository<ParkedVehicle, UUID> {

    @Query("SELECT SUM(p.amountPaid) FROM ParkedVehicle p WHERE p.parkingId = :parkingId AND p.checkoutDate BETWEEN :startDate AND :endDate AND p.paid = true")
    BigDecimal calculateRevenue(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(p) FROM ParkedVehicle p WHERE p.parkingId = :parkingId AND p.entryDate BETWEEN :startDate AND :endDate")
    Integer countCheckIn(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(p) FROM ParkedVehicle p WHERE p.parkingId = :parkingId AND p.checkoutDate BETWEEN :startDate AND :endDate")
    Integer countCheckOut(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT DATE(p.entryDate), COUNT(p) FROM ParkedVehicle p WHERE p.parkingId = :parkingId AND p.entryDate BETWEEN :startDate AND :endDate GROUP BY DATE(p.entryDate)")
    List<Object[]> getCheckInDataGroupedByDate(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT DATE(p.checkoutDate), SUM(p.amountPaid) FROM ParkedVehicle p WHERE p.parkingId = :parkingId AND p.checkoutDate BETWEEN :startDate AND :endDate AND p.paid = true GROUP BY DATE(p.checkoutDate)")
    List<Object[]> getRevenueDataGroupedByDate(UUID parkingId, LocalDateTime startDate, LocalDateTime endDate);
}
