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
    private boolean canCheckinVehicle;

    @Column(nullable = false)
    private boolean canCheckoutVehicle;

    @Column(nullable = false)
    private boolean canAddEmployee;

    @Column(nullable = false)
    private boolean canEditParking;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private UUID updateUserId;

    public EmployeePermissions() {
    }

    public EmployeePermissions(UUID employeeId, boolean canCheckinVehicle, boolean canCheckoutVehicle, boolean canAddEmployee, boolean canEditParking) {
        this.employeeId = employeeId;
        this.canCheckinVehicle = canCheckinVehicle;
        this.canCheckoutVehicle = canCheckoutVehicle;
        this.canAddEmployee = canAddEmployee;
        this.canEditParking = canEditParking;
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

    public boolean isCanCheckinVehicle() {
        return canCheckinVehicle;
    }

    public void setCanCheckinVehicle(boolean canCheckinVehicle) {
        this.canCheckinVehicle = canCheckinVehicle;
    }

    public boolean isCanCheckoutVehicle() {
        return canCheckoutVehicle;
    }

    public void setCanCheckoutVehicle(boolean canCheckoutVehicle) {
        this.canCheckoutVehicle = canCheckoutVehicle;
    }

    public boolean isCanAddEmployee() {
        return canAddEmployee;
    }

    public void setCanAddEmployee(boolean canAddEmployee) {
        this.canAddEmployee = canAddEmployee;
    }

    public boolean isCanEditParking() {
        return canEditParking;
    }

    public void setCanEditParking(boolean canEditParking) {
        this.canEditParking = canEditParking;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(UUID updateUserId) {
        this.updateUserId = updateUserId;
    }
}
