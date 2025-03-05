package com.parkingmanagement.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RequestChangePasswordDTO {

    @NotBlank(message = "Informe 'currentPassword'")
    private String currentPassword;

    @NotBlank(message = "Informe 'newPassword'")
    @Size(min = 6, message = "'newPassword' deve ter no mínimo 6 caracteres")
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
