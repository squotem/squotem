package com.leanlogistics.squotem.model;

public class MatrixCostAdjustment {
    private Long id;
    private Matrix matrix;
    private CostAdjustment costAdjustment;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Matrix getMatrix() {
        return matrix;
    }
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
    public CostAdjustment getCostAdjustment() {
        return costAdjustment;
    }
    public void setCostAdjustment(CostAdjustment costAdjustment) {
        this.costAdjustment = costAdjustment;
    }

}
