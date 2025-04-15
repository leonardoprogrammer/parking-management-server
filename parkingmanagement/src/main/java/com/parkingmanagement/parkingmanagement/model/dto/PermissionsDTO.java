package com.parkingmanagement.parkingmanagement.model.dto;

public class PermissionsDTO {

    private boolean canCheckinVehicle;
    private boolean canCheckoutVehicle;
    private boolean canAddEmployee;
    private boolean canEditParking;
    private boolean canViewDashboard;

    public PermissionsDTO() {
    }

    public PermissionsDTO(boolean canCheckinVehicle, boolean canCheckoutVehicle, boolean canAddEmployee, boolean canEditParking, boolean canViewDashboard) {
        this.canCheckinVehicle = canCheckinVehicle;
        this.canCheckoutVehicle = canCheckoutVehicle;
        this.canAddEmployee = canAddEmployee;
        this.canEditParking = canEditParking;
        this.canViewDashboard = canViewDashboard;
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

    public boolean isCanViewDashboard() {
        return canViewDashboard;
    }

    public void setCanViewDashboard(boolean canViewDashboard) {
        this.canViewDashboard = canViewDashboard;
    }
}
