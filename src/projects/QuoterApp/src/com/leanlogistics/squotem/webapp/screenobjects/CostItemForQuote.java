package com.leanlogistics.squotem.webapp.screenobjects;

import com.leanlogistics.squotem.model.CostItem;
import com.leanlogistics.squotem.model.CostModel;
import com.leanlogistics.squotem.model.QuoteCostItem;

public class CostItemForQuote {
    private Boolean enabled;
    private Boolean forced;
    private String special;
    private CostItem costItem;
    private Double implCost;
    private Double monthlyCost;
    private QuoteCostItem implQuoteCostItem;
    private QuoteCostItem monthlyQuoteCostItem;
    private String implQuoteCostError;
    private String monthlyQuoteCostError;
    private String implQuoteCostErrorDisplay;
    private String monthlyQuoteCostErrorDisplay;
    private CostModel implCostModel;
    private CostModel monthlyCostModel;
    private String alternateColor;
    private Boolean required;
    
    
    public CostItemForQuote() {
		super();
	}
    
	public CostItem getCostItem() {
        return costItem;
    }
    public void setCostItem(CostItem costItem) {
        this.costItem = costItem;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public Boolean getForced() {
        return forced;
    }
    public void setForced(Boolean forced) {
        this.forced = forced;
    }
    public String getSpecial() {
        return special;
    }
    public void setSpecial(String special) {
        this.special = special;
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
    public QuoteCostItem getImplQuoteCostItem() {
        return implQuoteCostItem;
    }
    public void setImplQuoteCostItem(QuoteCostItem implQuoteCostItem) {
        this.implQuoteCostItem = implQuoteCostItem;
    }
    public QuoteCostItem getMonthlyQuoteCostItem() {
        return monthlyQuoteCostItem;
    }
    public void setMonthlyQuoteCostItem(QuoteCostItem monthlyQuoteCostItem) {
        this.monthlyQuoteCostItem = monthlyQuoteCostItem;
    }
    public CostModel getImplCostModel() {
        return implCostModel;
    }
    public void setImplCostModel(CostModel implCostModel) {
        this.implCostModel = implCostModel;
    }
    public CostModel getMonthlyCostModel() {
        return monthlyCostModel;
    }
    public void setMonthlyCostModel(CostModel monthlyCostModel) {
        this.monthlyCostModel = monthlyCostModel;
    }
    public String getImplQuoteCostError() {
        return implQuoteCostError;
    }
    public void setImplQuoteCostError(String implQuoteCostError) {
        this.implQuoteCostError = implQuoteCostError;
    }
    public String getMonthlyQuoteCostError() {
        return monthlyQuoteCostError;
    }
    public void setMonthlyQuoteCostError(String monthlyQuoteCostError) {
        this.monthlyQuoteCostError = monthlyQuoteCostError;
    }
	public String getAlternateColor() {
		return alternateColor;
	}
	public void setAlternateColor(String alternateColor) {
		this.alternateColor = alternateColor;
	}
	public String getImplQuoteCostErrorDisplay() {
		return implQuoteCostErrorDisplay;
	}
	public void setImplQuoteCostErrorDisplay(String implQuoteCostErrorDisplay) {
		this.implQuoteCostErrorDisplay = implQuoteCostErrorDisplay;
	}
	public String getMonthlyQuoteCostErrorDisplay() {
		return monthlyQuoteCostErrorDisplay;
	}
	public void setMonthlyQuoteCostErrorDisplay(
			String monthlyQuoteCostErrorDisplay) {
		this.monthlyQuoteCostErrorDisplay = monthlyQuoteCostErrorDisplay;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}
}
