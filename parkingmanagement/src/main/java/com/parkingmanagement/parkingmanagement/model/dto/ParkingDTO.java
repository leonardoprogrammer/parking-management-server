package com.parkingmanagement.parkingmanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;

public class ParkingDTO {

    @NotBlank(message = "'userCreatorId' é obrigatório")
    @UUID(message = "'userCreatorId' é inválido")
    private String userCreatorId;

    @NotBlank(message = "'name' é obrigatório")
    private String name;

    @NotBlank(message = "'address' é obrigatório")
    private String address;

    public String getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(String userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
