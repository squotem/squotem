package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;

import com.leanlogistics.squotem.model.License;
import com.leanlogistics.squotem.model.PerTransactionPricing;
import com.leanlogistics.squotem.model.SubscriptionPricing;
import com.leanlogistics.squotem.model.TieredPricing;

public class QuoteEntryQuote {
    
    private Integer terms;
    
    private List<CategorizedQuoteCostItem> categorizedQuoteCostItems;
    private List<CostAdjustmentForQuote> costAdjustments;
    private List<CategorizedQuoteMetric> categorizedQuoteMetrics;
    private List<SubscriptionPricing> subscriptionPricingList;
    private List<License> licensePricingList;
    private List<TieredPricing> tieredPricingList;
    private List<PerTransactionPricing> perTransactionPricingList;
    

    
    public List<TieredPricing> getTieredPricingList() {
		return tieredPricingList;
	}

	public void setTieredPricingList(List<TieredPricing> tieredPricingList) {
		this.tieredPricingList = tieredPricingList;
	}

	public List<PerTransactionPricing> getPerTransactionPricingList() {
		return perTransactionPricingList;
	}

	public void setPerTransactionPricingList(
			List<PerTransactionPricing> perTransactionPricingList) {
		this.perTransactionPricingList = perTransactionPricingList;
	}

	public List<License> getLicensePricingList() {
		return licensePricingList;
	}

	public void setLicensePricingList(List<License> licensePricingList) {
		this.licensePricingList = licensePricingList;
	}

	public List<SubscriptionPricing> getSubscriptionPricingList() {
		return subscriptionPricingList;
	}

	public void setSubscriptionPricingList(
			List<SubscriptionPricing> subscriptionPricingList) {
		this.subscriptionPricingList = subscriptionPricingList;
	}

	public List<CategorizedQuoteCostItem> getCategorizedQuoteCostItems() {
        return categorizedQuoteCostItems;
    }

    public void setCategorizedQuoteCostItems(
            List<CategorizedQuoteCostItem> categorizedQuoteCostItems) {
        this.categorizedQuoteCostItems = categorizedQuoteCostItems;
    }

    public List<CostAdjustmentForQuote> getCostAdjustments() {
        return costAdjustments;
    }

    public void setCostAdjustments(List<CostAdjustmentForQuote> costAdjustments) {
        this.costAdjustments = costAdjustments;
    }
    
    public List<CategorizedQuoteMetric> getCategorizedQuoteMetrics() {
        return categorizedQuoteMetrics;
    }

    public void setCategorizedQuoteMetrics(
            List<CategorizedQuoteMetric> categorizedQuoteMetrics) {
        this.categorizedQuoteMetrics = categorizedQuoteMetrics;
    }

    public Integer getTerms() {
        return terms;
    }

    public void setTerms(Integer terms) {
        this.terms = terms;
    }
    
    
}
