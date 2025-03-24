package com.parkingmanagement.auth.model.dto;

import jakarta.validation.constraints.NotBlank;

public class RequestRefreshTokenDTO {

    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
