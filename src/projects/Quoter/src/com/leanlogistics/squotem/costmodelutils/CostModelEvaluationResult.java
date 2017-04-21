package com.leanlogistics.squotem.costmodelutils;

public class CostModelEvaluationResult {
    
    private Double value = null;
    private String error = null;
    
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    
}
