package com.leanlogistics.squotem.model;


public class SubscriptionPricing {
    
    private int id ;
    private Long minAnnualFrieghtSpend = -1l;
    private Long maxAnnualFrieghtSpend = -1l;;
    private int monthlyFee;
    private int annualFee;
    private int costItemId;
    private int tier;
    
	public int getId() {
		return id;
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
	public void setId(int id) {
		this.id = id;
	}
	public Long getMinAnnualFrieghtSpend() {
		return minAnnualFrieghtSpend;
	}
	public void setMinAnnualFrieghtSpend(Long minAnnualFrieghtSpend) {
		this.minAnnualFrieghtSpend = minAnnualFrieghtSpend;
	}
	public Long getMaxAnnualFrieghtSpend() {
		return maxAnnualFrieghtSpend;
	}
	public void setMaxAnnualFrieghtSpend(Long maxAnnualFrieghtSpend) {
		this.maxAnnualFrieghtSpend = maxAnnualFrieghtSpend;
	}
	public int getMonthlyFee() {
		return monthlyFee;
	}
	public void setMonthlyFee(int monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
	public int getAnnualFee() {
		return annualFee;
	}
	public void setAnnualFee(int annualFee) {
		this.annualFee = annualFee;
	}
	
    
}
