package com.leanlogistics.squotem.model;

public class RiskAnalysis {
    
    private Long id;
    private RiskAnalysisCategory riskCategory;
    private String description;
    private Integer sortOrder;
    private Boolean active;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }        
    public RiskAnalysisCategory getRiskCategory() {
        return riskCategory;
    }
    public void setRiskCategory(RiskAnalysisCategory riskCategory) {
        this.riskCategory = riskCategory;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }    
    public Integer getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    
}
