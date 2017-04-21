package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.FeeCategoryCO;

public class MatrixCostItem {
    private Long id;
    private CostItem costItem;
    private CostModel costModel;
    private Double simpleCost;
    private Matrix matrix;
    private FeeCategoryCO feeCategory;
    private Boolean forced;
    private String alternateColor;
    private Boolean required;
    
    // Calculated cost to used if this item is enabled
    // If costModel is null, or costModel is not null but costModel.xmlContent is null then it's just a copy of simpleCost    
    // Otherwise, it's the result of evaluating the XML rules based on metrics specific to a Quote 
    private Double calculatedCostForQuote;
    // Stored in case there's a problem in cost calculation
    private String errorMessage;
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Double getSimpleCost() {
        return simpleCost;
    }
    public void setSimpleCost(Double simpleCost) {
        this.simpleCost = simpleCost;
    }
    public FeeCategoryCO getFeeCategory() {
        return feeCategory;
    }
    public void setFeeCategory(FeeCategoryCO feeCategory) {
        this.feeCategory = feeCategory;
    }    
    public Boolean getForced() {
        return forced;
    }
    public void setForced(Boolean forced) {
        this.forced = forced;
    }
    public Matrix getMatrix() {
        return matrix;
    }
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
    public Double getCalculatedCostForQuote() {
        return calculatedCostForQuote;
    }
    public void setCalculatedCostForQuote(Double calculatedCostForQuote) {
        this.calculatedCostForQuote = calculatedCostForQuote;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
	public String getAlternateColor() {
		return alternateColor;
	}
	public void setAlternateColor(String alternateColor) {
		this.alternateColor = alternateColor;
	}
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
}
