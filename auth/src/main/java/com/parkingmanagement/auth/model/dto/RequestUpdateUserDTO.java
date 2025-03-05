package com.parkingmanagement.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RequestUpdateUserDTO {

    @NotBlank(message = "'name' é obrigatório")
    @Size(min = 3, message = "'name' deve ter no mínimo 3 caracteres")
    private String name;

    @Size(min = 11, max = 11, message = "'telephone' deve ter 11 caracteres")
    private String telephone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
