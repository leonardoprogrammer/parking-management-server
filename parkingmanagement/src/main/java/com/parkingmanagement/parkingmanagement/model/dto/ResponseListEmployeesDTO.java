package com.parkingmanagement.parkingmanagement.model.dto;

import java.util.List;

public class ResponseListEmployeesDTO {

    private String ownerName;
    private List<BasicEmployeeDTO> employees;

    public ResponseListEmployeesDTO() {
    }

    public ResponseListEmployeesDTO(String ownerName, List<BasicEmployeeDTO> employees) {
        this.ownerName = ownerName;
        this.employees = employees;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<BasicEmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<BasicEmployeeDTO> employees) {
        this.employees = employees;
    }
}
