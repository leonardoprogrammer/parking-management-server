package com.parkingmanagement.parkingmanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

public class RequestRegisterParkingDTO {

    @NotBlank(message = "'userCreatorId' é obrigatório")
    @UUID(message = "'userCreatorId' é inválido")
    private String userCreatorId;

    @NotBlank(message = "'name' é obrigatório")
    @Size(min = 3, max = 50, message = "'name' deve ter entre 3 e 50 caracteres")
    private String name;

    @NotBlank(message = "'address' é obrigatório")
    @Size(min = 3, max = 100, message = "'address' deve ter entre 3 e 100 caracteres")
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
