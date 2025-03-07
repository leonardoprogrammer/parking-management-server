package com.parkingmanagement.parkedvehicles.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseCheckinParkedVehicleDTO {

    private UUID parkingId;
    private String plate;
    private String model;
    private String color;
    private String space;
    private LocalDateTime entryDate;
    private BigDecimal amountToPay;
    private String checkinEmployeeName;
    private LocalDateTime checkinDate;

    public ResponseCheckinParkedVehicleDTO(UUID parkingId, String plate, String model, String color, String space, LocalDateTime entryDate, BigDecimal amountToPay, String checkinEmployeeName, LocalDateTime checkinDate) {
        this.parkingId = parkingId;
        this.plate = plate;
        this.model = model;
        this.color = color;
        this.space = space;
        this.entryDate = entryDate;
        this.amountToPay = amountToPay;
        this.checkinEmployeeName = checkinEmployeeName;
        this.checkinDate = checkinDate;
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

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getCheckinEmployeeName() {
        return checkinEmployeeName;
    }

    public void setCheckinEmployeeName(String checkinEmployeeName) {
        this.checkinEmployeeName = checkinEmployeeName;
    }

    public LocalDateTime getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDateTime checkinDate) {
        this.checkinDate = checkinDate;
    }
}
