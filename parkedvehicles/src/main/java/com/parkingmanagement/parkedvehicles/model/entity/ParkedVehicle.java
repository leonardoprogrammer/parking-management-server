package com.parkingmanagement.parkedvehicles.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parked_vehicle")
public class ParkedVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID parkingId;

    @Column(length = 20, nullable = false)
    private String plate;

    @Column(nullable = false)
    private String model;

    @Column(length = 50)
    private String color;

    @Column(length = 50)
    private String space;

    @Column(nullable = false)
    private LocalDateTime entryDate;

    @Column(nullable = false)
    private UUID checkinEmployeeId;

    private LocalDateTime checkoutDate;

    private UUID checkoutEmployeeId;

    @Column(nullable = false)
    private boolean paid;

    private String paymentMethod;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public ParkedVehicle() {
    }

    public ParkedVehicle(UUID parkingId, String plate, String model, String color, String space, LocalDateTime entryDate, UUID checkinEmployeeId) {
        this.parkingId = parkingId;
        this.plate = plate;
        this.model = model;
        this.color = color;
        this.space = space;
        this.entryDate = entryDate;
        this.checkinEmployeeId = checkinEmployeeId;
    }

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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public UUID getCheckinEmployeeId() {
        return checkinEmployeeId;
    }

    public void setCheckinEmployeeId(UUID checkinEmployeeId) {
        this.checkinEmployeeId = checkinEmployeeId;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDateTime checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public UUID getCheckoutEmployeeId() {
        return checkoutEmployeeId;
    }

    public void setCheckoutEmployeeId(UUID checkoutEmployeeId) {
        this.checkoutEmployeeId = checkoutEmployeeId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
