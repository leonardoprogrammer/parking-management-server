package com.parkingmanagement.parkingmanagement.model.dto;

import jakarta.validation.constraints.NotNull;

public class RequestUpdateEmployeePermissionsDTO {

    @NotNull(message = "'canCheckinVehicle' é obrigatório")
    private boolean canCheckinVehicle;

    @NotNull(message = "'canCheckoutVehicle' é obrigatório")
    private boolean canCheckoutVehicle;

    @NotNull(message = "'canAddEmployee' é obrigatório")
    private boolean canAddEmployee;

    @NotNull(message = "'canEditParking' é obrigatório")
    private boolean canEditParking;

    public RequestUpdateEmployeePermissionsDTO() {
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
}
