package com.parkingmanagement.parkingmanagement.model.dto;

import java.util.UUID;

public class BasicEmployeeDTO {

    private UUID employeeId;
    private String employeeName;

    public BasicEmployeeDTO() {
    }

    public BasicEmployeeDTO(UUID employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
