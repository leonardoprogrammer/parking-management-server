package com.parkingmanagement.parkingmanagement.model.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalTime;

public class RequestUpdateParkingSettingsDTO {

    @NotNull(message = "Informe 'chargeFromCheckIn'")
    private boolean chargeFromCheckIn;

    private LocalTime minimumTimeToCharge;

    @NotNull(message = "Informe 'period'")
    private LocalTime period;

    @NotNull(message = "Informe 'valuePerPeriod'")
    private BigDecimal valuePerPeriod;

    public boolean isChargeFromCheckIn() {
        return chargeFromCheckIn;
    }

    public void setChargeFromCheckIn(boolean chargeFromCheckIn) {
        this.chargeFromCheckIn = chargeFromCheckIn;
    }

    public LocalTime getMinimumTimeToCharge() {
        return minimumTimeToCharge;
    }

    public void setMinimumTimeToCharge(LocalTime minimumTimeToCharge) {
        this.minimumTimeToCharge = minimumTimeToCharge;
    }

    public LocalTime getPeriod() {
        return period;
    }

    public void setPeriod(LocalTime period) {
        this.period = period;
    }

    public BigDecimal getValuePerPeriod() {
        return valuePerPeriod;
    }

    public void setValuePerPeriod(BigDecimal valuePerPeriod) {
        this.valuePerPeriod = valuePerPeriod;
    }
}
