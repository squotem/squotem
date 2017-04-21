package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.FeeCategoryCO;

/** A cost adjustment added for a Quote */

public class QuoteCostAdjustment {
    private Long id;
    private Quote quote;
    private CostAdjustment costAdjustment;
    private String comment;
    private FeeCategoryCO feeCategory;
    private Double cost;
    
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
    public CostAdjustment getCostAdjustment() {
        return costAdjustment;
    }
    public void setCostAdjustment(CostAdjustment costAdjustment) {
        this.costAdjustment = costAdjustment;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public FeeCategoryCO getFeeCategory() {
        return feeCategory;
    }
    public void setFeeCategory(FeeCategoryCO feeCategory) {
        this.feeCategory = feeCategory;
    }
    public Double getCost() {
        return cost;
    }
    public void setCost(Double cost) {
        this.cost = cost;
    }
    
}
