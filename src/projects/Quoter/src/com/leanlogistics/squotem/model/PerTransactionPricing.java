package com.leanlogistics.squotem.model;


public class PerTransactionPricing {
    
    private int id ;
    private Long minFreight = -1l;
    private Long maxFreight = -1l;;
    private int monthlyMinimum;
    private int annualTransactionAllowance;
    private Long annualPrice;
    private int costItemId;
    private int tier;
    private double pricePerTransaction;
    
    
	public double getPricePerTransaction() {
		return pricePerTransaction;
	}
	public void setPricePerTransaction(double pricePerTransaction) {
		this.pricePerTransaction = pricePerTransaction;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getMinFreight() {
		return minFreight;
	}
	public void setMinFreight(Long minFreight) {
		this.minFreight = minFreight;
	}
	public Long getMaxFreight() {
		return maxFreight;
	}
	public void setMaxFreight(Long maxFreight) {
		this.maxFreight = maxFreight;
	}
	public int getMonthlyMinimum() {
		return monthlyMinimum;
	}
	public void setMonthlyMinimum(int monthlyMinimum) {
		this.monthlyMinimum = monthlyMinimum;
	}
	public int getAnnualTransactionAllowance() {
		return annualTransactionAllowance;
	}
	public void setAnnualTransactionAllowance(int annualTransactionAllowance) {
		this.annualTransactionAllowance = annualTransactionAllowance;
	}
	public Long getAnnualPrice() {
		return annualPrice;
	}
	public void setAnnualPrice(Long annualPrice) {
		this.annualPrice = annualPrice;
	}
	public int getCostItemId() {
		return costItemId;
	}
	public void setCostItemId(int costItemId) {
		this.costItemId = costItemId;
	}
	public int getTier() {
		return tier;
	}
	public void setTier(int tier) {
		this.tier = tier;
	}
    
	
    
}
