package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;

import com.leanlogistics.squotem.co.MetricDisplayOptionTypeCO;
import com.leanlogistics.squotem.model.QuoteMetric;

public class CategorizedQuoteMetric {
    private Long id;
    private String description;
    private MetricDisplayOptionTypeCO optionType;
    private List<QuoteMetric> quoteMetrics;
    
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
    public List<QuoteMetric> getQuoteMetrics() {
        return quoteMetrics;
    }
    public void setQuoteMetrics(List<QuoteMetric> quoteMetrics) {
        this.quoteMetrics = quoteMetrics;
    }
    public MetricDisplayOptionTypeCO getOptionType() {
        return optionType;
    }
    public void setOptionType(MetricDisplayOptionTypeCO optionType) {
        this.optionType = optionType;
    }
        
}
