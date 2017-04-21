package com.leanlogistics.squotem.webapp.screenobjects;

import com.leanlogistics.squotem.model.CostAdjustment;
import com.leanlogistics.squotem.model.QuoteCostAdjustment;

public class CostAdjustmentForQuote {
    
    private CostAdjustment costAdjustment;
    private String comment;
    private Double implCost;
    private Double monthlyCost;
    private QuoteCostAdjustment implQuoteCostAdjustment;
    private QuoteCostAdjustment monthlyQuoteCostAdjustment;
    private String id;
    private String action;
    
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
    public Double getImplCost() {
        return implCost;
    }
    public void setImplCost(Double implCost) {
        this.implCost = implCost;
    }
    public Double getMonthlyCost() {
        return monthlyCost;
    }
    public void setMonthlyCost(Double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }
    public QuoteCostAdjustment getImplQuoteCostAdjustment() {
        return implQuoteCostAdjustment;
    }
    public void setImplQuoteCostAdjustment(
            QuoteCostAdjustment implQuoteCostAdjustment) {
        this.implQuoteCostAdjustment = implQuoteCostAdjustment;
    }
    public QuoteCostAdjustment getMonthlyQuoteCostAdjustment() {
        return monthlyQuoteCostAdjustment;
    }
    public void setMonthlyQuoteCostAdjustment(
            QuoteCostAdjustment monthlyQuoteCostAdjustment) {
        this.monthlyQuoteCostAdjustment = monthlyQuoteCostAdjustment;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
    
}
