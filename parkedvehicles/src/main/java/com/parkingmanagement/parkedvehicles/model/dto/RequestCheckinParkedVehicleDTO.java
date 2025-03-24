package com.parkingmanagement.parkedvehicles.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

public class RequestCheckinParkedVehicleDTO {

    @NotBlank(message = "'parkingId' é obrigatório")
    @UUID(message = "'parkingId' é inválido")
    private String parkingId;

    @NotBlank(message = "'plate' é obrigatório")
    @Size(min = 7, max = 8, message = "'plate' deve ter 7 ou 8 caracteres")
    private String plate;

    @NotBlank(message = "'model' é obrigatório")
    private String model;

    @NotBlank
    @Size(max = 50, message = "'color' deve ter no máximo 50 caracteres")
    private String color;

    @Size(max = 50, message = "'space' deve ter no máximo 50 caracteres")
    private String space;

    @NotBlank(message = "'entryDate' é obrigatório")
    private String entryDate;

    @NotBlank(message = "'checkinEmployeeId' é obrigatório")
    @UUID(message = "'checkinEmployeeId' é inválido")
    private String checkinEmployeeId;

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
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

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getCheckinEmployeeId() {
        return checkinEmployeeId;
    }

    public void setCheckinEmployeeId(String checkinEmployeeId) {
        this.checkinEmployeeId = checkinEmployeeId;
    }
}
