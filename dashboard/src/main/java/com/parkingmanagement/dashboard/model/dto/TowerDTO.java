package com.parkingmanagement.dashboard.model.dto;

public class TowerDTO {

    private Integer categoryId;
    private Object value;

    public TowerDTO(Integer categoryId, Object value) {
        this.categoryId = categoryId;
        this.value = value;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
