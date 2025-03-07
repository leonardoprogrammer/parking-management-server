package com.parkingmanagement.parkedvehicles.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "parking_settings")
public class ParkingSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID parkingId;

    @Column(nullable = false)
    private boolean chargeFromCheckIn;

    @Column
    private LocalTime minimumTimeToCharge;

    @Column(nullable = false)
    private LocalTime period;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valuePerPeriod;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParkingId() {
        return parkingId;
    }

    public void setParkingId(UUID parkingId) {
        this.parkingId = parkingId;
    }

    public boolean isChargeFromCheckIn() {
        return chargeFromCheckIn;
    }

    public void setChargeFromCheckIn(boolean chargeFromCheckIn) {
        this.chargeFromCheckIn = chargeFromCheckIn;
    }

    public LocalTime getMinimumTimeToCharge() {
        return minimumTimeToCharge;
    }

    public void setMinimumTimeToCharge(LocalTime minimumTimeToCharge) {
        this.minimumTimeToCharge = minimumTimeToCharge;
    }

    public LocalTime getPeriod() {
        return period;
    }

    public void setPeriod(LocalTime period) {
        this.period = period;
    }

    public BigDecimal getValuePerPeriod() {
        return valuePerPeriod;
    }

    public void setValuePerPeriod(BigDecimal valuePerPeriod) {
        this.valuePerPeriod = valuePerPeriod;
    }
}
