package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.MetricDataTypeCO;

public class Metric {
    private Long id;
    private String description;
    private String mnemonic;
    private MetricDataTypeCO dataType;
    private MetricCategory category;
    private Integer sortOrder;
    private Boolean isTotal;
    // Secondary description for Combo metrics
    private String secDescription;
    
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
    public String getSecDescription() {
        return secDescription;
    }
    public void setSecDescription(String secDescription) {
        this.secDescription = secDescription;
    }
    public String getMnemonic() {
        return mnemonic;
    }
    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
    public MetricDataTypeCO getDataType() {
        return dataType;
    }
    public void setDataType(MetricDataTypeCO dataType) {
        this.dataType = dataType;
    }
    public MetricCategory getCategory() {
        return category;
    }
    public void setCategory(MetricCategory category) {
        this.category = category;
    }
    public Integer getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    public Boolean getIsTotal() {
        return isTotal;
    }
    public void setIsTotal(Boolean isTotal) {
        this.isTotal = isTotal;
    }    
}
