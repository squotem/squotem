package com.leanlogistics.squotem.model;


public class TieredPricing {
    
    private int id ;
    private Long minTransactions = -1l;
    private Long maxTransactions = -1l;;
    private float feePerTransaction;
    private int annualFee;
    private int costItemId;
    private int tier;
    
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getMinTransactions() {
		return minTransactions;
	}
	public void setMinTransactions(Long minTransactions) {
		this.minTransactions = minTransactions;
	}
	public Long getMaxTransactions() {
		return maxTransactions;
	}
	public void setMaxTransactions(Long maxTransactions) {
		this.maxTransactions = maxTransactions;
	}
	public float getFeePerTransaction() {
		return feePerTransaction;
	}
	public void setFeePerTransaction(float feePerTransaction) {
		this.feePerTransaction = feePerTransaction;
	}
	public int getAnnualFee() {
		return annualFee;
	}
	public void setAnnualFee(int annualFee) {
		this.annualFee = annualFee;
	}
	
    
}
