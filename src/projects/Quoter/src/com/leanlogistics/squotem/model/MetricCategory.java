package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.MetricDisplayOptionTypeCO;
import com.leanlogistics.squotem.co.MetricGroupCO;

public class MetricCategory {
    
    private Long id;
    private String description;
    private MetricDisplayOptionTypeCO optionType;
    private Integer sortOrder;
    private MetricGroupCO metricGroup;
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public MetricDisplayOptionTypeCO getOptionType() {
        return optionType;
    }
    public void setOptionType(MetricDisplayOptionTypeCO optionType) {
        this.optionType = optionType;
    }
    public Integer getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    public MetricGroupCO getMetricGroup() {
        return metricGroup;
    }
    public void setMetricGroup(MetricGroupCO metricGroup) {
        this.metricGroup = metricGroup;
    }   
    
}
