package com.parkingmanagement.parkingmanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RequestUpdateParkingDTO {

    @NotBlank(message = "Informe 'parkingName'")
    @Size(min = 3, max = 50, message = "'parkingName' deve ter entre 3 e 50 caracteres")
    private String parkingName;

    @NotBlank(message = "Informe 'parkingAddress'")
    @Size(min = 3, max = 100, message = "'parkingAddress' deve ter entre 3 e 100 caracteres")
    private String parkingAddress;

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }
}
