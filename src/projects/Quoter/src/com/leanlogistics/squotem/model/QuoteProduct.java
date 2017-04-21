package com.leanlogistics.squotem.model;

public class QuoteProduct {
	private Long id;
	private Quote quote;
	private ProductCategory productCategory;
	private Double customerQuoteImpl;
	private Double customerQuoteMonthly;
	
    // Empty constructor
    public QuoteProduct() {}
    
    // Copy constructor
    public QuoteProduct(QuoteProduct source) {
        productCategory = source.productCategory;
        customerQuoteImpl = source.customerQuoteImpl;
        customerQuoteMonthly = source.customerQuoteMonthly;
    }	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
    public Quote getQuote() {
        return quote;
    }
    public void setQuote(Quote quote) {
        this.quote = quote;
    }
    public ProductCategory getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    public Double getCustomerQuoteImpl() {
        return customerQuoteImpl;
    }
    public void setCustomerQuoteImpl(Double customerQuoteImpl) {
        this.customerQuoteImpl = customerQuoteImpl;
    }
    public Double getCustomerQuoteMonthly() {
        return customerQuoteMonthly;
    }
    public void setCustomerQuoteMonthly(Double customerQuoteMonthly) {
        this.customerQuoteMonthly = customerQuoteMonthly;
    }

}
