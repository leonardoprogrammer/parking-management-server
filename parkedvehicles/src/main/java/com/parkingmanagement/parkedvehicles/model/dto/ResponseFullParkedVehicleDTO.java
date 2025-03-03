package com.parkingmanagement.parkedvehicles.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseFullParkedVehicleDTO {

    private UUID parkedVehicleId;
    private UUID parkingId;
    private String plate;
    private String model;
    private String color;
    private String space;
    private LocalDateTime entryDate;
    private UUID checkinEmployeeId;
    private String checkinEmployeeName;
    private LocalDateTime checkoutDate;
    private UUID checkoutEmployeeId;
    private String checkoutEmployeeName;
    private boolean paid;
    private String paymentMethod;
    private LocalDateTime createdAt;

    public ResponseFullParkedVehicleDTO() {
    }

    public ResponseFullParkedVehicleDTO(UUID parkedVehicleId, UUID parkingId, String plate, String model, String color, String space, LocalDateTime entryDate, UUID checkinEmployeeId, String checkinEmployeeName, LocalDateTime checkoutDate, UUID checkoutEmployeeId, String checkoutEmployeeName, boolean paid, String paymentMethod, LocalDateTime createdAt) {
        this.parkedVehicleId = parkedVehicleId;
        this.parkingId = parkingId;
        this.plate = plate;
        this.model = model;
        this.color = color;
        this.space = space;
        this.entryDate = entryDate;
        this.checkinEmployeeId = checkinEmployeeId;
        this.checkinEmployeeName = checkinEmployeeName;
        this.checkoutDate = checkoutDate;
        this.checkoutEmployeeId = checkoutEmployeeId;
        this.checkoutEmployeeName = checkoutEmployeeName;
        this.paid = paid;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
    }

    public UUID getParkedVehicleId() {
        return parkedVehicleId;
    }

    public void setParkedVehicleId(UUID parkedVehicleId) {
        this.parkedVehicleId = parkedVehicleId;
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

    public String getCheckinEmployeeName() {
        return checkinEmployeeName;
    }

    public void setCheckinEmployeeName(String checkinEmployeeName) {
        this.checkinEmployeeName = checkinEmployeeName;
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

    public String getCheckoutEmployeeName() {
        return checkoutEmployeeName;
    }

    public void setCheckoutEmployeeName(String checkoutEmployeeName) {
        this.checkoutEmployeeName = checkoutEmployeeName;
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
}
