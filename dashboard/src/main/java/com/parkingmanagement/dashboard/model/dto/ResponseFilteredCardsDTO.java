package com.parkingmanagement.dashboard.model.dto;

import java.math.BigDecimal;

public class ResponseFilteredCardsDTO {

    private BigDecimal revenue;
    private Integer checkInQuantity;
    private Integer checkOutQuantity;

    public ResponseFilteredCardsDTO() {
    }

    public ResponseFilteredCardsDTO(BigDecimal revenue, Integer checkInQuantity, Integer checkOutQuantity) {
        this.revenue = revenue;
        this.checkInQuantity = checkInQuantity;
        this.checkOutQuantity = checkOutQuantity;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public Integer getCheckInQuantity() {
        return checkInQuantity;
    }

    public void setCheckInQuantity(Integer checkInQuantity) {
        this.checkInQuantity = checkInQuantity;
    }

    public Integer getCheckOutQuantity() {
        return checkOutQuantity;
    }

    public void setCheckOutQuantity(Integer checkOutQuantity) {
        this.checkOutQuantity = checkOutQuantity;
    }
}
