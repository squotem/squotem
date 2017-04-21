package com.leanlogistics.squotem.model;

import java.util.List;

public class QuoteMTSCosts {
    
    // Configuration value
    private Double baselineWeeklyLoadCount;
    // Calculated value
    private Double scopedWeeklyLoadCount;
    private Double monthlyTotal;
    private Double anualTotal;
    
    List<QuoteMTSRoleCost> roleCosts;
    
    public Double getBaselineWeeklyLoadCount() {
        return baselineWeeklyLoadCount;
    }
    public void setBaselineWeeklyLoadCount(Double baselineWeeklyLoadCount) {
        this.baselineWeeklyLoadCount = baselineWeeklyLoadCount;
    }    
    public Double getScopedWeeklyLoadCount() {
        return scopedWeeklyLoadCount;
    }
    public void setScopedWeeklyLoadCount(Double scopedWeeklyLoadCount) {
        this.scopedWeeklyLoadCount = scopedWeeklyLoadCount;
    }
    public Double getMonthlyTotal() {
        return monthlyTotal;
    }
    public void setMonthlyTotal(Double monthlyTotal) {
        this.monthlyTotal = monthlyTotal;
    }
    public Double getAnualTotal() {
        return anualTotal;
    }
    public void setAnualTotal(Double anualTotal) {
        this.anualTotal = anualTotal;
    }
    public List<QuoteMTSRoleCost> getRoleCosts() {
        return roleCosts;
    }
    public void setRoleCosts(List<QuoteMTSRoleCost> roleCosts) {
        this.roleCosts = roleCosts;
    }
    
}
