package com.leanlogistics.squotem.model;

import java.util.Date;
import java.util.List;

public class QuoteQuery {
    
    private String quoteNumber;
    private String status;
    private Long customerId;
    private List<Long> productCategories;
    private Long ownerId;
    private Long salesDirector;
    private Boolean hasMts;
    private Boolean onlyActive;
    private Date from;
    private Date to;
    // The User who does the query
    private User queryUser;   

    public QuoteQuery() {
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<Long> productCategories) {
        this.productCategories = productCategories;
    }


    public User getQueryUser() {
        return queryUser;
    }

    public void setQueryUser(User queryUser) {
        this.queryUser = queryUser;
    }

    public Boolean getHasMts() {
        return hasMts;
    }

    public void setHasMts(Boolean hasMts) {
        this.hasMts = hasMts;
    }

    public String getQuoteNumber() {
        return quoteNumber;
    }

    public void setQuoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public Boolean getOnlyActive() {
        return onlyActive;
    }

    public void setOnlyActive(Boolean onlyActive) {
        this.onlyActive = onlyActive;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

	public Long getSalesDirector() {
		return salesDirector;
	}

	public void setSalesDirector(Long salesDirector) {
		this.salesDirector = salesDirector;
	}

}
