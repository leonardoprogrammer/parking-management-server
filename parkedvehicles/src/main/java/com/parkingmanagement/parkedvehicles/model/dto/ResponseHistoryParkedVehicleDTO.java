package com.parkingmanagement.parkedvehicles.model.dto;

import java.time.LocalDateTime;

public class ResponseHistoryParkedVehicleDTO {

    private String plate;
    private String model;
    private String color;
    private LocalDateTime entryDate;
    private LocalDateTime createdAt;
    private boolean checkedOut;

    public ResponseHistoryParkedVehicleDTO(String plate, String model, String color, LocalDateTime entryDate, LocalDateTime createdAt, boolean checkedOut) {
        this.plate = plate;
        this.model = model;
        this.color = color;
        this.entryDate = entryDate;
        this.createdAt = createdAt;
        this.checkedOut = checkedOut;
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

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
}
