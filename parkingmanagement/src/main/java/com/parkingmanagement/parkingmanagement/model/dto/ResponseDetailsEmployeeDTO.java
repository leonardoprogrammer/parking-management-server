package com.parkingmanagement.parkingmanagement.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseDetailsEmployeeDTO {

    private UUID employeeId;
    private UUID parkingId;
    private UUID userId;
    private String employeeName;
    private String cpf;
    private String email;
    private String telephone;
    private String adderUserName;
    private LocalDateTime dateAdded;
    private Object permissions;

    public ResponseDetailsEmployeeDTO() {
    }

    public ResponseDetailsEmployeeDTO(UUID employeeId, UUID parkingId, UUID userId, String employeeName, String cpf, String email, String telephone, String adderUserName, LocalDateTime dateAdded, Object permissions) {
        this.employeeId = employeeId;
        this.parkingId = parkingId;
        this.userId = userId;
        this.employeeName = employeeName;
        this.cpf = cpf;
        this.email = email;
        this.telephone = telephone;
        this.adderUserName = adderUserName;
        this.dateAdded = dateAdded;
        this.permissions = permissions;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getParkingId() {
        return parkingId;
    }

    public void setParkingId(UUID parkingId) {
        this.parkingId = parkingId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdderUserName() {
        return adderUserName;
    }

    public void setAdderUserName(String adderUserName) {
        this.adderUserName = adderUserName;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Object getPermissions() {
        return permissions;
    }

    public void setPermissions(Object permissions) {
        this.permissions = permissions;
    }
}
