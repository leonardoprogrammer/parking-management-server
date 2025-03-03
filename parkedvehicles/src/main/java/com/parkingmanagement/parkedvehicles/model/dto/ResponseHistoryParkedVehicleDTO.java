package com.parkingmanagement.parkedvehicles.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseHistoryParkedVehicleDTO {

    private UUID parkedVehicleId;
    private String plate;
    private String model;
    private String color;
    private LocalDateTime entryDate;
    private LocalDateTime createdAt;
    private boolean checkedOut;

    public ResponseHistoryParkedVehicleDTO(UUID parkedVehicleId, String plate, String model, String color, LocalDateTime entryDate, LocalDateTime createdAt, boolean checkedOut) {
        this.parkedVehicleId = parkedVehicleId;
        this.plate = plate;
        this.model = model;
        this.color = color;
        this.entryDate = entryDate;
        this.createdAt = createdAt;
        this.checkedOut = checkedOut;
    }

    public UUID getParkedVehicleId() {
        return parkedVehicleId;
    }

    public void setParkedVehicleId(UUID parkedVehicleId) {
        this.parkedVehicleId = parkedVehicleId;
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
