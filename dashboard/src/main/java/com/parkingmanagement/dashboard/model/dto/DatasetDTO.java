package com.parkingmanagement.dashboard.model.dto;

import java.util.List;

public class DatasetDTO {

    private String label;
    private List<Object> data;
    private String backgroundColor;

    public DatasetDTO() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
