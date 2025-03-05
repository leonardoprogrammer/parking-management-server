package com.parkingmanagement.auth.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RequestChangeEmailDTO {

    @NotBlank(message = "Informe 'newEmail'")
    @Email(message = "'newEmail' é inválido")
    private String newEmail;

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
