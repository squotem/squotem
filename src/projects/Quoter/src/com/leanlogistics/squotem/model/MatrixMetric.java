package com.leanlogistics.squotem.model;

public class MatrixMetric {
    private Long id;
    private Matrix matrix;
    private Metric metric;
    private Boolean required;
    private String defaultValue;
    // Only used for Combo metrics, to hold the default yes/no answer
    private Boolean defaultBooleanValue;
    
    
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
    public Metric getMetric() {
        return metric;
    }
    public void setMetric(Metric metric) {
        this.metric = metric;
    }
    public Boolean getRequired() {
        return required;
    }
    public void setRequired(Boolean required) {
        this.required = required;
    }
    public String getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    public Boolean getDefaultBooleanValue() {
        return defaultBooleanValue;
    }
    public void setDefaultBooleanValue(Boolean defaultBooleanValue) {
        this.defaultBooleanValue = defaultBooleanValue;
    }    
}
