package com.leanlogistics.squotem.model;

/** A metric value entered for a Quote */

public class QuoteMetric {
    private Long id;
    private Quote quote;
    private Metric metric;
    private String metricValue;
    // As defined by the Matrix
    private Boolean required;
    // Only used for Combo metrics, to hold the yes/no answer
    private Boolean booleanValue;
    
    
    // Empty constructor
    public QuoteMetric() {
    }
    
    // Copy constructor
    public QuoteMetric(QuoteMetric source) {
        metric = source.metric;
        metricValue = source.metricValue;
        booleanValue = source.booleanValue;
    }    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Quote getQuote() {
        return quote;
    }
    public void setQuote(Quote quote) {
        this.quote = quote;
    }
    public Metric getMetric() {
        return metric;
    }
    public void setMetric(Metric metric) {
        this.metric = metric;
    }
    public String getMetricValue() {
        return metricValue;
    }
    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }
    public Boolean getRequired() {
        return required;
    }
    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
    
    
}
