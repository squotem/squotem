package com.leanlogistics.squotem.model;

public class QuoteRiskAnalysis {
    
    /** A risk analysis item selected as a "YES" or a "NO" for a Quote */
    private Long id;
    private Quote quote;
    private RiskAnalysis riskAnalysis;
    private String comment;
    private Integer riskLevel;
    // Enabled: yes or no?
    private Boolean enabled;
    
    
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
    public RiskAnalysis getRiskAnalysis() {
        return riskAnalysis;
    }
    public void setRiskAnalysis(RiskAnalysis riskAnalysis) {
        this.riskAnalysis = riskAnalysis;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getRiskLevel() {
        return riskLevel;
    }
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }    
}
