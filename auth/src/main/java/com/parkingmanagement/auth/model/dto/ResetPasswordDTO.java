package com.parkingmanagement.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

public class ResetPasswordDTO {

    @NotBlank(message = "Informe 'email'")
    private String email;

    @NotBlank(message = "Informe 'id'")
    @UUID(message = "'id' é inválido")
    private String id;

    @NotBlank(message = "Informe 'newPassword'")
    @Size(min = 6, message = "'newPassword' deve ter no mínimo 6 caracteres")
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
