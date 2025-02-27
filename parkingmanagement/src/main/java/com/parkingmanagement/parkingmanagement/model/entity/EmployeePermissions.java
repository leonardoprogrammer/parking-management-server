package com.parkingmanagement.parkingmanagement.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee_permissions")
public class EmployeePermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID employeeId;

    @Column(nullable = false)
    private boolean checkinVehicle;

    @Column(nullable = false)
    private boolean checkoutVehicle;

    @Column(nullable = false)
    private boolean addEmployee;

    @Column(nullable = false)
    private boolean changePermissions;

    @Column(nullable = false)
    private boolean editParking;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private UUID updateUser;

    public EmployeePermissions() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isCheckinVehicle() {
        return checkinVehicle;
    }

    public void setCheckinVehicle(boolean checkinVehicle) {
        this.checkinVehicle = checkinVehicle;
    }

    public boolean isCheckoutVehicle() {
        return checkoutVehicle;
    }

    public void setCheckoutVehicle(boolean checkoutVehicle) {
        this.checkoutVehicle = checkoutVehicle;
    }

    public boolean isAddEmployee() {
        return addEmployee;
    }

    public void setAddEmployee(boolean addEmployee) {
        this.addEmployee = addEmployee;
    }

    public boolean isChangePermissions() {
        return changePermissions;
    }

    public void setChangePermissions(boolean changePermissions) {
        this.changePermissions = changePermissions;
    }

    public boolean isEditParking() {
        return editParking;
    }

    public void setEditParking(boolean editParking) {
        this.editParking = editParking;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(UUID updateUser) {
        this.updateUser = updateUser;
    }
}
