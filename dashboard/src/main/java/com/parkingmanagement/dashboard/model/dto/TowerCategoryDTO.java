package com.parkingmanagement.dashboard.model.dto;

public class TowerCategoryDTO {

    private Integer id;
    private String name;
    private String hexadecimalColor;

    public TowerCategoryDTO(Integer id, String name, String hexadecimalColor) {
        this.id = id;
        this.name = name;
        this.hexadecimalColor = hexadecimalColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHexadecimalColor() {
        return hexadecimalColor;
    }

    public void setHexadecimalColor(String hexadecimalColor) {
        this.hexadecimalColor = hexadecimalColor;
    }
}
