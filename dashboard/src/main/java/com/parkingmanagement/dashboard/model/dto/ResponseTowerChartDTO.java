package com.parkingmanagement.dashboard.model.dto;

import java.util.List;

public class ResponseTowerChartDTO {

    private List<String> labels;
    private List<DatasetDTO> datasets;

    public ResponseTowerChartDTO(List<String> labels, List<DatasetDTO> datasets) {
        this.labels = labels;
        this.datasets = datasets;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<DatasetDTO> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DatasetDTO> datasets) {
        this.datasets = datasets;
    }
}
