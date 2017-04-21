package com.leanlogistics.squotem.model;

public class MTSMatrixRoleCost {
    private Long id;
    private MTSMatrix mtsMatrix;
    private MTSRole mtsRole;
    private Double costPer;
    private Double margin;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public MTSMatrix getMtsMatrix() {
        return mtsMatrix;
    }
    public void setMtsMatrix(MTSMatrix mtsMatrix) {
        this.mtsMatrix = mtsMatrix;
    }
    public MTSRole getMtsRole() {
        return mtsRole;
    }
    public void setMtsRole(MTSRole mtsRole) {
        this.mtsRole = mtsRole;
    }
    public Double getCostPer() {
        return costPer;
    }
    public void setCostPer(Double costPer) {
        this.costPer = costPer;
    }
    public Double getMargin() {
        return margin;
    }
    public void setMargin(Double margin) {
        this.margin = margin;
    }
    

}
