package com.leanlogistics.squotem.model;

import java.util.List;
import java.util.Map;


/** Subtotals, adjustments and totals costs for a quote */
public class QuoteCosts {
    
    List<QuoteSubtotal> subtotals;
    // The key is the FeeCategoryCO name for each fee category
    Map <String, Double> adjustments;
    Map <String, Double> totals;
    
    public List<QuoteSubtotal> getSubtotals() {
        return subtotals;
    }
    public void setSubtotals(List<QuoteSubtotal> subtotals) {
        this.subtotals = subtotals;
    }
    public Map<String, Double> getAdjustments() {
        return adjustments;
    }
    public void setAdjustments(Map<String, Double> adjustments) {
        this.adjustments = adjustments;
    }
    public Map<String, Double> getTotals() {
        return totals;
    }
    public void setTotals(Map<String, Double> totals) {
        this.totals = totals;
    }    

}
