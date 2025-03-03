package com.parkingmanagement.parkingmanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public class RequestUpdateEmployeePermissionsDTO {

    @NotNull(message = "'canCheckinVehicle' é obrigatório")
    private boolean canCheckinVehicle;

    @NotNull(message = "'canCheckoutVehicle' é obrigatório")
    private boolean canCheckoutVehicle;

    @NotNull(message = "'canAddEmployee' é obrigatório")
    private boolean canAddEmployee;

    @NotNull(message = "'canEditParking' é obrigatório")
    private boolean canEditParking;

    @NotBlank(message = "'updateUserId' é obrigatório")
    @UUID(message = "updateUserId é inválido")
    private String updateUserId;

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

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}
