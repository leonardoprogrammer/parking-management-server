package com.parkingmanagement.parkedvehicles.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public class RequestCheckoutParkedVehicleDTO {

    @NotBlank(message = "'parkedVehicleId' é obrigatório")
    @UUID(message = "'parkedVehicleId' é inválido")
    private String parkedVehicleId;

    @NotBlank(message = "'checkoutDate' é obrigatório")
    private String checkoutDate;

    @NotBlank(message = "'checkoutEmployeeId' é obrigatório")
    @UUID(message = "'checkoutEmployeeId' é inválido")
    private String checkoutEmployeeId;

    @NotNull(message = "'paid' é obrigatório")
    private boolean paid;

    private String paymentMethod;

    public String getParkedVehicleId() {
        return parkedVehicleId;
    }

    public void setParkedVehicleId(String parkedVehicleId) {
        this.parkedVehicleId = parkedVehicleId;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getCheckoutEmployeeId() {
        return checkoutEmployeeId;
    }

    public void setCheckoutEmployeeId(String checkoutEmployeeId) {
        this.checkoutEmployeeId = checkoutEmployeeId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
