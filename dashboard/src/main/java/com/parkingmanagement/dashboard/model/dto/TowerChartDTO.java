package com.parkingmanagement.dashboard.model.dto;

import java.util.List;

public class TowerChartDTO {

    private List<TowerCategoryDTO> towerCategories;
    private List<TowerChartItemDTO> items;

    public TowerChartDTO() {
    }

    public TowerChartDTO(List<TowerCategoryDTO> towerCategories, List<TowerChartItemDTO> items) {
        this.towerCategories = towerCategories;
        this.items = items;
    }

    public List<TowerCategoryDTO> getTowerCategories() {
        return towerCategories;
    }

    public void setTowerCategories(List<TowerCategoryDTO> towerCategories) {
        this.towerCategories = towerCategories;
    }

    public List<TowerChartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<TowerChartItemDTO> items) {
        this.items = items;
    }
}
