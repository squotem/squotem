package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;

public class QuoteEntryQuote {
    
    private Integer terms;
    
    private List<CategorizedQuoteCostItem> categorizedQuoteCostItems;
    private List<CostAdjustmentForQuote> costAdjustments;
    private List<CategorizedQuoteMetric> categorizedQuoteMetrics;

    public List<CategorizedQuoteCostItem> getCategorizedQuoteCostItems() {
        return categorizedQuoteCostItems;
    }

    public void setCategorizedQuoteCostItems(
            List<CategorizedQuoteCostItem> categorizedQuoteCostItems) {
        this.categorizedQuoteCostItems = categorizedQuoteCostItems;
    }

    public List<CostAdjustmentForQuote> getCostAdjustments() {
        return costAdjustments;
    }

    public void setCostAdjustments(List<CostAdjustmentForQuote> costAdjustments) {
        this.costAdjustments = costAdjustments;
    }
    
    public List<CategorizedQuoteMetric> getCategorizedQuoteMetrics() {
        return categorizedQuoteMetrics;
    }

    public void setCategorizedQuoteMetrics(
            List<CategorizedQuoteMetric> categorizedQuoteMetrics) {
        this.categorizedQuoteMetrics = categorizedQuoteMetrics;
    }

    public Integer getTerms() {
        return terms;
    }

    public void setTerms(Integer terms) {
        this.terms = terms;
    }
    
    
}
