package com.parkingmanagement.dashboard.model.dto;

public class ResponseFilteredCardsDTO {

    private String revenue;
    private Integer checkInQuantity;
    private Integer checkOutQuantity;

    public ResponseFilteredCardsDTO() {
    }

    public ResponseFilteredCardsDTO(String revenue, Integer checkInQuantity, Integer checkOutQuantity) {
        this.revenue = revenue;
        this.checkInQuantity = checkInQuantity;
        this.checkOutQuantity = checkOutQuantity;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
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
