package com.parkingmanagement.dashboard.model.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkedVehicleBasicVO {

    private LocalDateTime entryDate;
    private BigDecimal amountPaid;

    public ParkedVehicleBasicVO(LocalDateTime entryDate, BigDecimal amountPaid) {
        this.entryDate = entryDate;
        this.amountPaid = amountPaid;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
}
