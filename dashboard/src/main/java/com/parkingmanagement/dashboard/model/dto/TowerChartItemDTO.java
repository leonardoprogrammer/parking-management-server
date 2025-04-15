package com.parkingmanagement.dashboard.model.dto;

import java.util.List;

public class TowerChartItemDTO {

    private String name;
    private List<TowerDTO> towers;

    public TowerChartItemDTO(String name, List<TowerDTO> towers) {
        this.name = name;
        this.towers = towers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TowerDTO> getTowers() {
        return towers;
    }

    public void setTowers(List<TowerDTO> towers) {
        this.towers = towers;
    }
}
