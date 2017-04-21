package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;

public class QuoteEntryRiskAnalysis {
    
    private List<CategorizedQuoteRiskAnalysis> categorizedQuoteRiskAnalysisItems;

    public List<CategorizedQuoteRiskAnalysis> getCategorizedQuoteRiskAnalysisItems() {
        return categorizedQuoteRiskAnalysisItems;
    }

    public void setCategorizedQuoteRiskAnalysisItems(
            List<CategorizedQuoteRiskAnalysis> categorizedQuoteRiskAnalysisItems) {
        this.categorizedQuoteRiskAnalysisItems = categorizedQuoteRiskAnalysisItems;
    }
    
}
