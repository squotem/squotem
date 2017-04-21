package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;

import com.leanlogistics.squotem.model.QuoteRiskAnalysis;

public class CategorizedQuoteRiskAnalysis {
    private Long id;
    private String description;
    private List<QuoteRiskAnalysis> quoteRiskAnalysisItems;
    
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
    public List<QuoteRiskAnalysis> getQuoteRiskAnalysisItems() {
        return quoteRiskAnalysisItems;
    }
    public void setQuoteRiskAnalysisItems(
            List<QuoteRiskAnalysis> quoteRiskAnalysisItems) {
        this.quoteRiskAnalysisItems = quoteRiskAnalysisItems;
    }

}
