package com.leanlogistics.squotem.model;

public class MatrixRiskAnalysis {
    
    private Long id;
    private RiskAnalysis riskAnalysis;
    private Matrix matrix;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public RiskAnalysis getRiskAnalysis() {
        return riskAnalysis;
    }
    public void setRiskAnalysis(RiskAnalysis riskAnalysis) {
        this.riskAnalysis = riskAnalysis;
    }
    public Matrix getMatrix() {
        return matrix;
    }
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
    
}
