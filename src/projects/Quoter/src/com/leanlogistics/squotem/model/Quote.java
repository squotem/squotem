package com.leanlogistics.squotem.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;

public class Quote {
    
    private Customer customer;    
    private Long id;   
    private Long quoteNumber;    
    private Long revisionNumber;
    private User author;
    private User salesDirector;
    private String businessConsultant;
    private String partnerReferenced;
    private Matrix matrix;
    private List<ProductCategory> productCategories;
    private Date createDate;
    private Date effectiveDate;
    private Date expireDate;
    private Integer terms;
    private Date modifiedDate;
    private Double mtsAnnualLoadCount;
    private Double mtsScopedWeeklyLoadCount;
    private Double mtsAnnualPrice;
    private Double mtsCustomerQuoteImpl;
    private Double mtsCustomerQuoteMonthly;
    private Boolean hasMts;
    private Boolean activeRevision;
    private QuoteWorkflowStatusCO status;
    private User modifiedBy;
            
    
    // Transient fields to hold quote costs
    private QuoteCosts quoteCosts;
    private QuoteMTSCosts quoteMTSCosts;
    
    // Manually populated (on demand)
    private List<QuoteMetric> quoteMetrics;
    private List<QuoteCostItem> quoteCostItems;
    private List<QuoteCostAdjustment> quoteCostAdjustments;
    private List<QuoteMTSScopeAnswer> quoteMTSScopeAnswers;
    private List<QuoteMTSRoleCost> quoteMTSRoleCosts;
    private List<QuoteProduct> quoteProducts;
    private List<QuoteNote> quoteNotes;
    private List<QuoteRiskAnalysis> quoteRiskAnalysisItems;
    
    // Populated only when working with MTS quote
    private MTSMatrix mtsMatrix;
    
    // Budgetary costs
    private Double budgetaryImplCost;
    private String budgetaryImplCostError;
    private Double budgetaryMonthlyCost;
    private String budgetaryMonthlyCostError;
    
    // Market costs
    private Double marketImplCost;
    private Double marketMonthlyCost;
    
    // Empty constructor
	public Quote() {
    }
	
	// Copy constructor
	public Quote (Quote source) {
	    // Does not copy id, revision, createDate, modifiedDate, status
	    customer = source.customer;
	    quoteNumber = source.quoteNumber;
	    // Does not copy revision
	    revisionNumber = null;
	    author = source.author;
	    salesDirector = source.salesDirector;
	    businessConsultant = source.businessConsultant;
	    partnerReferenced = source.partnerReferenced;
	    matrix = source.matrix;
	    if (source.productCategories != null) {
	        productCategories = new ArrayList<ProductCategory>();
	        for (ProductCategory pc : source.productCategories) {
	            productCategories.add(new ProductCategory(pc));
	        }
	    }
	    
	    effectiveDate = source.effectiveDate;
	    expireDate = source.expireDate;
	    terms = source.terms;
	    mtsAnnualLoadCount = source.mtsAnnualLoadCount;
	    mtsScopedWeeklyLoadCount = source.mtsScopedWeeklyLoadCount;
	    mtsAnnualPrice = source.mtsAnnualPrice;
	    mtsCustomerQuoteImpl = source.mtsCustomerQuoteImpl;
	    mtsCustomerQuoteMonthly = source.mtsCustomerQuoteMonthly;
	    hasMts = source.hasMts;
	    activeRevision = source.activeRevision;
	    mtsMatrix = source.mtsMatrix;
	    
	    budgetaryImplCost = source.budgetaryImplCost;
	    budgetaryMonthlyCost = source.budgetaryMonthlyCost;
	    
	    marketImplCost = source.marketImplCost;
	    marketMonthlyCost = source.marketMonthlyCost;
	    
	    if (source.quoteMetrics != null) {
	        quoteMetrics = new ArrayList<QuoteMetric>();
	        for (QuoteMetric qm : source.quoteMetrics) {
	            quoteMetrics.add(new QuoteMetric(qm));
	        }
	    }
	    
	    if (source.quoteCostItems != null) {
	        quoteCostItems = new ArrayList<QuoteCostItem>();
	        for (QuoteCostItem qci : source.quoteCostItems) {
	            quoteCostItems.add(new QuoteCostItem(qci));
	        }
	    }
	    
	    
	}
	
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getQuoteNumber() {
        return quoteNumber;
    }
    public void setQuoteNumber(Long quoteNumber) {
        this.quoteNumber = quoteNumber;
    }
    public Long getRevisionNumber() {
        return revisionNumber;
    }
    public void setRevisionNumber(Long revisionNumber) {
        this.revisionNumber = revisionNumber;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public User getSalesDirector() {
        return salesDirector;
    }
    public void setSalesDirector(User salesDirector) {
        this.salesDirector = salesDirector;
    }
    public String getBusinessConsultant() {
        return businessConsultant;
    }
    public void setBusinessConsultant(String businessConsultant) {
        this.businessConsultant = businessConsultant;
    }
    public String getPartnerReferenced() {
        return partnerReferenced;
    }
    public void setPartnerReferenced(String partnerReferenced) {
        this.partnerReferenced = partnerReferenced;
    }
    public Matrix getMatrix() {
        return matrix;
    }
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }
    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public QuoteWorkflowStatusCO getStatus() {
        return status;
    }
    public void setStatus(QuoteWorkflowStatusCO status) {
        this.status = status;
    }
    public List<QuoteMetric> getQuoteMetrics() {
        return quoteMetrics;
    }
    public void setQuoteMetrics(List<QuoteMetric> quoteMetrics) {
        this.quoteMetrics = quoteMetrics;
    }
    public List<QuoteCostItem> getQuoteCostItems() {
        return quoteCostItems;
    }
    public void setQuoteCostItems(List<QuoteCostItem> quoteCostItems) {
        this.quoteCostItems = quoteCostItems;
    }
    public List<QuoteCostAdjustment> getQuoteCostAdjustments() {
        return quoteCostAdjustments;
    }
    public void setQuoteCostAdjustments(
            List<QuoteCostAdjustment> quoteCostAdjustments) {
        this.quoteCostAdjustments = quoteCostAdjustments;
    }
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    public Date getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }        
    public Date getModifiedDate() {
        return modifiedDate;
    }    
    public Integer getTerms() {
        return terms;
    }
    public void setTerms(Integer terms) {
        this.terms = terms;
    }
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    public Double getMtsAnnualLoadCount() {
        return mtsAnnualLoadCount;
    }
    public void setMtsAnnualLoadCount(Double mtsAnnualLoadCount) {
        this.mtsAnnualLoadCount = mtsAnnualLoadCount;
    }
    public QuoteCosts getQuoteCosts() {
        return quoteCosts;
    }
    public void setQuoteCosts(QuoteCosts quoteCosts) {
        this.quoteCosts = quoteCosts;
    }    
    public QuoteMTSCosts getQuoteMTSCosts() {
        return quoteMTSCosts;
    }
    public void setQuoteMTSCosts(QuoteMTSCosts quoteMTSCosts) {
        this.quoteMTSCosts = quoteMTSCosts;
    }
    public List<QuoteMTSScopeAnswer> getQuoteMTSScopeAnswers() {
        return quoteMTSScopeAnswers;
    }
    public void setQuoteMTSScopeAnswers(
            List<QuoteMTSScopeAnswer> quoteMTSScopeAnswers) {
        this.quoteMTSScopeAnswers = quoteMTSScopeAnswers;
    }    
    public List<QuoteMTSRoleCost> getQuoteMTSRoleCosts() {
        return quoteMTSRoleCosts;
    }
    public void setQuoteMTSRoleCosts(List<QuoteMTSRoleCost> quoteMTSRoleCosts) {
        this.quoteMTSRoleCosts = quoteMTSRoleCosts;
    }
    
    public List<QuoteProduct> getQuoteProducts() {
        return quoteProducts;
    }
    public void setQuoteProducts(List<QuoteProduct> quoteProducts) {
        this.quoteProducts = quoteProducts;
    }    
    public List<QuoteNote> getQuoteNotes() {
        return quoteNotes;
    }
    public void setQuoteNotes(List<QuoteNote> quoteNotes) {
        this.quoteNotes = quoteNotes;
    }    
    public List<QuoteRiskAnalysis> getQuoteRiskAnalysisItems() {
        return quoteRiskAnalysisItems;
    }
    public void setQuoteRiskAnalysisItems(
            List<QuoteRiskAnalysis> quoteRiskAnalysisItems) {
        this.quoteRiskAnalysisItems = quoteRiskAnalysisItems;
    }
    public MTSMatrix getMtsMatrix() {
        return mtsMatrix;
    }
    public void setMtsMatrix(MTSMatrix mtsMatrix) {
        this.mtsMatrix = mtsMatrix;
    }
    public Double getMtsScopedWeeklyLoadCount() {
        return mtsScopedWeeklyLoadCount;
    }
    public void setMtsScopedWeeklyLoadCount(Double mtsScopedWeeklyLoadCount) {
        this.mtsScopedWeeklyLoadCount = mtsScopedWeeklyLoadCount;
    }
    public Double getMtsAnnualPrice() {
        return mtsAnnualPrice;
    }
    public void setMtsAnnualPrice(Double mtsAnnualPrice) {
        this.mtsAnnualPrice = mtsAnnualPrice;
    }    
    public Double getMtsCustomerQuoteImpl() {
        return mtsCustomerQuoteImpl;
    }
    public void setMtsCustomerQuoteImpl(Double mtsCustomerQuoteImpl) {
        this.mtsCustomerQuoteImpl = mtsCustomerQuoteImpl;
    }
    public Double getMtsCustomerQuoteMonthly() {
        return mtsCustomerQuoteMonthly;
    }
    public void setMtsCustomerQuoteMonthly(Double mtsCustomerQuoteMonthly) {
        this.mtsCustomerQuoteMonthly = mtsCustomerQuoteMonthly;
    }
    public Boolean getHasMts() {
        return hasMts;
    }
    public void setHasMts(Boolean hasMts) {
        this.hasMts = hasMts;
    }    
    public Boolean getActiveRevision() {
        return activeRevision;
    }
    public void setActiveRevision(Boolean activeRevision) {
        this.activeRevision = activeRevision;
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

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
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
