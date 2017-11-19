package com.leanlogistics.squotem.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.leanlogistics.squotem.co.MetricGroupCO;
import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.exceptions.QuoteEditException;
import com.leanlogistics.squotem.exceptions.QuoterStatusChangeException;
import com.leanlogistics.squotem.model.BusinessSector;
import com.leanlogistics.squotem.model.Country;
import com.leanlogistics.squotem.model.License;
import com.leanlogistics.squotem.model.MTSMatrix;
import com.leanlogistics.squotem.model.MatrixMetric;
import com.leanlogistics.squotem.model.MatrixRiskAnalysis;
import com.leanlogistics.squotem.model.PerTransactionPricing;
import com.leanlogistics.squotem.model.QuoteCosts;
import com.leanlogistics.squotem.model.QuoteMTSCosts;
import com.leanlogistics.squotem.model.QuoteMTSRoleCost;
import com.leanlogistics.squotem.model.QuoteMTSScopeAnswer;
import com.leanlogistics.squotem.model.QuoteNote;
import com.leanlogistics.squotem.model.QuoteProduct;
import com.leanlogistics.squotem.model.QuoteQuery;
import com.leanlogistics.squotem.model.QuoteRiskAnalysis;
import com.leanlogistics.squotem.model.State;
import com.leanlogistics.squotem.model.Customer;
import com.leanlogistics.squotem.model.Matrix;
import com.leanlogistics.squotem.model.MatrixCostAdjustment;
import com.leanlogistics.squotem.model.MatrixCostItem;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCostAdjustment;
import com.leanlogistics.squotem.model.QuoteCostItem;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.model.SubscriptionPricing;
import com.leanlogistics.squotem.model.TieredPricing;
import com.leanlogistics.squotem.model.User;

public interface SquotemService {
    
    User getUser(String userName, String password);
    
    List<Customer> getCustomers();
    
    List<ProductCategory> getProductCategories();

    List<User> getUsers();
    
    // Get matrix identified by id
    Matrix getMatrix(long id);

    // Get matrices available for a regular user. Same as getMatrices(false)
    List<Matrix> getMatrices();
    
    // If forAdmin = false, gets matrices for a regular user. Otherwise, gets matrices for admin users
    List<Matrix> getMatrices(boolean forAdmin);
    
    /* Retrieves cost items only for a specific matrix and product category */        
    List<MatrixCostItem> getMatrixCostItems(Matrix m, ProductCategory pc);
    /* Retrieves cost items only for a specific matrix and product category. If provided quote metrics are not null,
     * precalculates costs for quote based on quote metrics */
    List<MatrixCostItem> getMatrixCostItems(Matrix m, ProductCategory pc, List<QuoteMetric> metrics);
    
    /* Retrieves cost items only for a specific matrix and product category. If provided quote metrics are not null,
     * precalculates costs for quote based on quote metrics 
     * give the option of precalculate costs or not*/
    List<MatrixCostItem> getMatrixCostItems(Matrix m, ProductCategory pc, List<QuoteMetric> metrics, boolean precalculateMatrixCostItemCosts);

    /* Retrieves cost adjustment items only for a specific matrix and any of the provided product categories */
    List<MatrixCostAdjustment> getMatrixCostAdjustments(Matrix m, ProductCategory pc);
    
    /* Retrieves metrics only for a specific matrix */        
    List<MatrixMetric> getMatrixMetrics(Matrix m);
    
    /* Retrieves metrics only for a specific matrix */
    List<MatrixMetric> getMatrixMetrics(Matrix m, MetricGroupCO metricGroup);
       
    Matrix getCurrentMatrix();
    
    // Creates a quote from scratch (revision #1)
    void createQuote(Quote q);
    
    // Same as updateQuote(q, false)
    void updateQuote(User usr, Quote q) throws QuoteEditException;
    
    /**
     * 
     * Update fields:
     * customer, salesDirector, businessConsultant, partnerReferenced, effectiveDate, expireDate and optionally matrix
     */
    void updateQuote(User usr, Quote q, boolean updateMatrix) throws QuoteEditException;
    
    
    // Listing of active draft quotes for a user, to show by default
    List<Quote> getDraftQuotesForUser(User u);
    
    // Listing of quotes using a query input
    List<Quote> queryQuotes(QuoteQuery query);
    
    void saveMetricsForQuote(Quote q, List<QuoteMetric> quoteMetrics) throws QuoteEditException ;
    
    List<QuoteMetric> getMetricsForQuote(Quote q);
    
    List<QuoteCostItem> getCostItemsForQuote(Quote q);
    
    List<QuoteCostAdjustment> getCostAdjustmentsForQuote(Quote q);
    
    List<QuoteProduct> getProductsForQuote(Quote q);
    
    // Same as getQuote(id, false)
    Quote getQuote(User usr, long id);
    
    // Get a quote based on id, optionally calculating quote costs 
    // if costs are calculated, other fields such as quoteMetrics, quoteCostItems and quoteCostAdjustments
    // are populated into the Quote
    Quote getQuote(User usr, long id, boolean calculateCosts);
    
    void saveCostItemsForQuote(Quote q, List<QuoteCostItem> quoteCostItems);
    
    void deleteCostItemsForQuote(Quote q, List<QuoteCostItem> quoteCostItems);
    
    // In a single transaction, deletes and saves QuoteCostItems for a Quote
    void updateCostItemsForQuote(Quote q, List<QuoteCostItem> deleteCostItems, List<QuoteCostItem> saveCostItems) throws QuoteEditException;
    
    // In a single transaction, deletes and saves QuoteMetrics for a Quote
    void updateMetricsForQuote(Quote q, List<QuoteMetric> deleteMetrics, List<QuoteMetric> saveMetrics) throws QuoteEditException;
    
    // In a single transaction, deletes and saves QuoteMetrics and QuoteCostItems for a Quote
    void updateMetricsAndCostItemsForQuote(Quote q, List<QuoteMetric> deleteMetrics, List<QuoteMetric> saveMetrics,
                                            List<QuoteCostItem> deleteCostItems, List<QuoteCostItem> saveCostItems) throws QuoteEditException;

    // In a single transaction, deletes and saves QuoteCostAdjustments for a Quote
    void updateCostAdjustmentsForQuote(Quote q, List<QuoteCostAdjustment> deleteCostAdjustments, List<QuoteCostAdjustment> saveCostAdjustments);
    
    // In a single transaction, deletes and saves QuoteCostItems and QuoteCostAdjustments for a Quote
    void updateCostItemsAndAdjustmentsForQuote(Quote q, List<QuoteCostItem> deleteCostItems, List<QuoteCostItem> saveCostItems,
            List<QuoteCostAdjustment> deleteCostAdjustments, List<QuoteCostAdjustment> saveCostAdjustments);
    
    List <Country> getCountries();
    
    List <State> getCountryStates(Country countryCode);
    
    List<State> getStates();
    
    List<BusinessSector> getBusinessSectors();
    
    // Calculates quote costs based on provided cost items, adjustments and metrics
    QuoteCosts calculateQuoteCosts(Matrix m, List<QuoteCostItem> quoteCostItems, List<QuoteCostAdjustment> quoteCostAdjustments, 
                                   List<QuoteMetric> quoteMetrics, List<ProductCategory> productCategories);
    
    
    /* Retrieves an MTS Matrix including all relevant information */
    MTSMatrix getMTSMatrix(long mtsMatrixId);
        
    List<QuoteMTSScopeAnswer> getMTSScopeAnswersForQuote(Quote q);
    
    List<QuoteMTSRoleCost> getMTSRoleCostsForQuote(Quote q);
    
    // Calcualtes quote mts costs based on provided scope answers and role costs
    QuoteMTSCosts calculateQuoteMTSCosts(MTSMatrix mtsMatrix, Double annualLoadCount, List<QuoteMTSScopeAnswer> scopeAnswers, List<QuoteMTSRoleCost> roleCosts);
    
    // In a single transaction, deletes and saves QuoteMTSScopeAnswers and QuoteMTSRoleCosts for a Quote, and updates mts annual load count
    void updateMTSDataForQuote(Quote q,
                                List<QuoteMTSScopeAnswer> deleteMTSScopeAnswers, 
                                List<QuoteMTSScopeAnswer> saveMTSScopeAnswers,
                                List<QuoteMTSRoleCost> saveMTSRoleCosts) throws QuoteEditException;
    
    // In a single transaction, update customer quote values for MTS and the regular product categories
    void saveCustomerQuoteValuesForQuote(Quote q, List<QuoteProduct> quoteProducts) throws QuoteEditException;
    
    // Changes a quote from draft mode to submitted mode
    Quote submitQuoteForReview(User usr, long id) throws QuoterStatusChangeException;
    
    Quote updateQuoteStatus(User usr, long id, QuoteWorkflowStatusCO newStatus) throws QuoterStatusChangeException;
    
    // Update a Quote Status, also adding a comment in the process
    Quote updateQuoteStatus(User usr, long id, QuoteWorkflowStatusCO newStatus, String comment) throws QuoterStatusChangeException;
    
    // Creates a new revision from an existing quote. Returns the newly created revision
    Quote createRevision(User usr, long baseQuoteId) throws QuoterStatusChangeException;
    
    // Adds a note to a Quote
    Quote saveNoteForQuote(User usr, long quoteId, String comment) throws QuoteEditException;
    
    List<QuoteNote> getNotesForQuote(Quote q);
    
    List<QuoteRiskAnalysis> getRiskAnalysisItemsForQuote(Quote q);
    
    /* Retrieves risk analysis items only for a specific matrix */        
    List<MatrixRiskAnalysis> getMatrixRiskAnalysisItems(Matrix m);
    

    // In a single transaction, deletes and saves QuoteRiskAnalysis items for a Quote
    void updateRiskAnalysisItemsForQuote(Quote q, List<QuoteRiskAnalysis> deleteItems, List<QuoteRiskAnalysis> saveItems) throws QuoteEditException;
    
    // Uses Quote's matrix and metrics to calculate budgetary costs based on cost models
    void calculateQuoteBudgetaryCosts(Quote q);

	void updateQuoteBudgetaryCost(Quote q) throws QuoteEditException;

	void updateQuoteMarketCost(Quote q) throws QuoteEditException,
			HibernateException;
	
	List<User> getNotificationList(QuoteWorkflowStatusCO status);

	void freezeQuote(Quote q) throws QuoteEditException, HibernateException;

	List<SubscriptionPricing> getSubscriptionPricing();

	List<License> getLicensePricing();

	List<TieredPricing> getTieredPricing();

	List<PerTransactionPricing> getPerTransactionPricing();
                
}
