package com.leanlogistics.squotem.model;

public class MTSMatrixRoleRelation {
    
    private Long id;
    private MTSMatrix mtsMatrix;
    private MTSRole mtsRole;
    private Integer rangeStart;
    private Integer rangeEnd;
    private Double roleCount;
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
    public Integer getRangeStart() {
        return rangeStart;
    }
    public void setRangeStart(Integer rangeStart) {
        this.rangeStart = rangeStart;
    }
    public Integer getRangeEnd() {
        return rangeEnd;
    }
    public void setRangeEnd(Integer rangeEnd) {
        this.rangeEnd = rangeEnd;
    }
    public Double getRoleCount() {
        return roleCount;
    }
    public void setRoleCount(Double roleCount) {
        this.roleCount = roleCount;
    }
    
}
