package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;


public class QuoteEntryMetric {
        
    private List<CategorizedQuoteMetric> categorizedQuoteMetrics;
       
    public List<CategorizedQuoteMetric> getCategorizedQuoteMetrics() {
        return categorizedQuoteMetrics;
    }

    public void setCategorizedQuoteMetrics(
            List<CategorizedQuoteMetric> categorizedQuoteMetrics) {
        this.categorizedQuoteMetrics = categorizedQuoteMetrics;
    }

}
