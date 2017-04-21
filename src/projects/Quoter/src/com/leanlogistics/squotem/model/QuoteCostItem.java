package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.FeeCategoryCO;
import com.leanlogistics.squotem.co.SpecialQuoteCostItemCO;

/** A cost item selected as a "YES" or a "NO" for a Quote */

public class QuoteCostItem {
    
    private Long id;
    private Quote quote;
    private CostItem costItem;
    private CostModel costModel;
    private SpecialQuoteCostItemCO special;
    private FeeCategoryCO feeCategory;
    private Double cost;
    // Enabled: yes or no?
    private Boolean enabled;
    private Boolean required;
    // Stored in case there's a problem in cost calculation
    private String errorMessage;
        
    // Empty constructor
    public QuoteCostItem() {
    }
    
    // Copy constructor
    public QuoteCostItem(QuoteCostItem source) {
        costItem = source.costItem;
        costModel = source.costModel;
        special = source.special;
        feeCategory = source.feeCategory;
        cost = source.cost;
        enabled = source.enabled;
        required = source.required;
        errorMessage = source.errorMessage;
    }
    
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
    public CostItem getCostItem() {
        return costItem;
    }
    public void setCostItem(CostItem costItem) {
        this.costItem = costItem;
    }
    public CostModel getCostModel() {
        return costModel;
    }
    public void setCostModel(CostModel costModel) {
        this.costModel = costModel;
    }
    public SpecialQuoteCostItemCO getSpecial() {
        return special;
    }
    public void setSpecial(SpecialQuoteCostItemCO special) {
        this.special = special;
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
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }    
    public Boolean getRequired() {
        return required;
    }
    public void setRequired(Boolean required) {
        this.required = required;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
