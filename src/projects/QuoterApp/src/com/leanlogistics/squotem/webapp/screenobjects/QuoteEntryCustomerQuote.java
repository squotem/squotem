package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;
import java.util.Map;

public class QuoteEntryCustomerQuote {
    private List<CustomerQuoteItem> monthlyCosts;
    private List<CustomerQuoteItem> implCosts;
    private Map<String,String> quoteMetrics;
    
    // Budgetary costs
    private Double budgetaryImplCost;
    private String budgetaryImplCostError;
    private Double budgetaryMonthlyCost;
    private String budgetaryMonthlyCostError;
    
    // market costs
    private Double marketImplCost;
    private Double marketMonthlyCost;

    // contract length
    private Integer terms;


    public List<CustomerQuoteItem> getMonthlyCosts() {
        return monthlyCosts;
    }
    public void setMonthlyCosts(List<CustomerQuoteItem> monthlyCosts) {
        this.monthlyCosts = monthlyCosts;
    }
    public List<CustomerQuoteItem> getImplCosts() {
        return implCosts;
    }
    public void setImplCosts(List<CustomerQuoteItem> implCosts) {
        this.implCosts = implCosts;
    }
    public Map<String, String> getQuoteMetrics() {
        return quoteMetrics;
    }
    public void setQuoteMetrics(Map<String, String> quoteMetrics) {
        this.quoteMetrics = quoteMetrics;
    }
	public Double getBudgetaryImplCost() {
		return budgetaryImplCost;
	}
	public void setBudgetaryImplCost(Double budgetaryImplCost) {
		this.budgetaryImplCost = budgetaryImplCost;
	}
	public String getBudgetaryImplCostError() {
		return budgetaryImplCostError;
	}
	public void setBudgetaryImplCostError(String budgetaryImplCostError) {
		this.budgetaryImplCostError = budgetaryImplCostError;
	}
	public Double getBudgetaryMonthlyCost() {
		return budgetaryMonthlyCost;
	}
	public void setBudgetaryMonthlyCost(Double budgetaryMonthlyCost) {
		this.budgetaryMonthlyCost = budgetaryMonthlyCost;
	}
	public String getBudgetaryMonthlyCostError() {
		return budgetaryMonthlyCostError;
	}
	public void setBudgetaryMonthlyCostError(String budgetaryMonthlyCostError) {
		this.budgetaryMonthlyCostError = budgetaryMonthlyCostError;
	}
	public Integer getTerms() {
		return terms;
	}
	public void setTerms(Integer terms) {
		this.terms = terms;
	}
	public Double getMarketImplCost() {
		return marketImplCost;
	}
	public void setMarketImplCost(Double marketImplCost) {
		this.marketImplCost = marketImplCost;
	}
	public Double getMarketMonthlyCost() {
		return marketMonthlyCost;
	}
	public void setMarketMonthlyCost(Double marketMonthlyCost) {
		this.marketMonthlyCost = marketMonthlyCost;
	}           
}
