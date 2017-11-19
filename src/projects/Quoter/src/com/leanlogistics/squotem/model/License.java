package com.leanlogistics.squotem.model;


public class License {
    
    private int id ;
    private Long minAnnualFrieghtSpend = -1l;
    private Long maxAnnualFrieghtSpend = -1l;;
    private int licenseFee;
    private int maintenanceFee;
    private Long firstYearPayment;
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
	public int getMaintenanceFee() {
		return maintenanceFee;
	}
	public void setMaintenanceFee(int maintenanceFee) {
		this.maintenanceFee = maintenanceFee;
	}
	public Long getFirstYearPayment() {
		return firstYearPayment;
	}
	public void setFirstYearPayment(Long firstYearPayment) {
		this.firstYearPayment = firstYearPayment;
	}
	public int getLicenseFee() {
		return licenseFee;
	}
	public void setLicenseFee(int licenseFee) {
		this.licenseFee = licenseFee;
	}
	
    
}
