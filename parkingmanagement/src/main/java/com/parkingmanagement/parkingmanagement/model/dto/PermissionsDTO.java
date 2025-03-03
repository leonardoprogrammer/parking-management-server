package com.parkingmanagement.parkingmanagement.model.dto;

public class PermissionsDTO {

    private boolean canCheckinVehicle;
    private boolean canCheckoutVehicle;
    private boolean canAddEmploye;
    private boolean canEditParking;

    public PermissionsDTO() {
    }

    public PermissionsDTO(boolean canCheckinVehicle, boolean canCheckoutVehicle, boolean canAddEmploye, boolean canEditParking) {
        this.canCheckinVehicle = canCheckinVehicle;
        this.canCheckoutVehicle = canCheckoutVehicle;
        this.canAddEmploye = canAddEmploye;
        this.canEditParking = canEditParking;
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

    public boolean isCanAddEmploye() {
        return canAddEmploye;
    }

    public void setCanAddEmploye(boolean canAddEmploye) {
        this.canAddEmploye = canAddEmploye;
    }

    public boolean isCanEditParking() {
        return canEditParking;
    }

    public void setCanEditParking(boolean canEditParking) {
        this.canEditParking = canEditParking;
    }
}
