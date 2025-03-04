package com.parkingmanagement.auth.model.dto;

import java.util.UUID;

public class ResponseSearchUserDTO {

    private UUID userId;
    private String userName;
    private String userEmail;
    private String userCpf;

    public ResponseSearchUserDTO() {
    }

    public ResponseSearchUserDTO(UUID userId, String userName, String userEmail, String userCpf) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCpf = userCpf;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCpf() {
        return userCpf;
    }

    public void setUserCpf(String userCpf) {
        this.userCpf = userCpf;
    }
}
