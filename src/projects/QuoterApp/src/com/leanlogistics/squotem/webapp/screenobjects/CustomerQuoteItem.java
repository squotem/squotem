package com.leanlogistics.squotem.webapp.screenobjects;

import com.leanlogistics.squotem.model.ProductCategory;

public class CustomerQuoteItem {
    private Long id; // if od the QuoteProductEntry
    private ProductCategory productCategory;
    private Double marketQuote;
    private Double customerQuote;
    // For now, this is used in UI only
    private Double variance;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ProductCategory getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    public Double getMarketQuote() {
        return marketQuote;
    }
    public void setMarketQuote(Double marketQuote) {
        this.marketQuote = marketQuote;
    }
    public Double getCustomerQuote() {
        return customerQuote;
    }
    public void setCustomerQuote(Double customerQuote) {
        this.customerQuote = customerQuote;
    }
    public Double getVariance() {
        return variance;
    }
    public void setVariance(Double variance) {
        this.variance = variance;
    }
    
}
