package com.leanlogistics.squotem.service.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.leanlogistics.squotem.co.MetricDataTypeCO;
import com.leanlogistics.squotem.co.QuoteActionLevelCO;
import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.co.UserTypeCO;
import com.leanlogistics.squotem.costmodelutils.CostModelUtils;
import com.leanlogistics.squotem.exceptions.QuoteEditException;
import com.leanlogistics.squotem.exceptions.QuoterStatusChangeException;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCostAdjustment;
import com.leanlogistics.squotem.model.QuoteCostItem;
import com.leanlogistics.squotem.model.QuoteCosts;
import com.leanlogistics.squotem.model.QuoteMTSRoleCost;
import com.leanlogistics.squotem.model.QuoteMTSScopeAnswer;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.model.QuoteNote;
import com.leanlogistics.squotem.model.QuoteProduct;
import com.leanlogistics.squotem.model.QuoteQuery;
import com.leanlogistics.squotem.model.QuoteRiskAnalysis;
import com.leanlogistics.squotem.model.User;
import com.leanlogistics.squotem.service.dao.QuoteDao;

public class QuoteManager extends BaseManager {
    
    protected static final String COMBO_METRIC_SEPARATOR = "||";
    
    private static class QuoteStatusTransition {
        QuoteWorkflowStatusCO from;
        QuoteWorkflowStatusCO to;
        
        public QuoteStatusTransition(QuoteWorkflowStatusCO from,
                QuoteWorkflowStatusCO to) {
            this.from = from;
            this.to = to;
        }

    }
    
    private static Set<QuoteStatusTransition> baselineValidTransitions = new HashSet<QuoteStatusTransition>();
    static {
        // Valid quote status transitions available for admin users
    	baselineValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.DRAFT, QuoteWorkflowStatusCO.SUBMITTED));
    	baselineValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.SUBMITTED, QuoteWorkflowStatusCO.APPROVED));
    }

    private static Set<QuoteStatusTransition> businessPlanValidTransitions = new HashSet<QuoteStatusTransition>();
    static {
        // Valid quote status transitions available for admin users
    	businessPlanValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.DRAFT, QuoteWorkflowStatusCO.SUBMITTED));
    	businessPlanValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.SUBMITTED, QuoteWorkflowStatusCO.APPROVED));
    }
    private static Set<QuoteStatusTransition> allValidTransitions = new HashSet<QuoteStatusTransition>();
    static {
        // Valid quote status transitions available for admin users
    	allValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.DRAFT, QuoteWorkflowStatusCO.SUBMITTED));
    	allValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.SUBMITTED, QuoteWorkflowStatusCO.APPROVED));
    }

    private static Set<QuoteStatusTransition> adminValidTransitions = new HashSet<QuoteStatusTransition>();
    static {
    	// this probably is not needed anymore
        // Valid quote status transitions available for admin users
        //adminValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.DRAFT, QuoteWorkflowStatusCO.CANCELED));
        adminValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.DRAFT, QuoteWorkflowStatusCO.SUBMITTED));
        //adminValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.SUBMITTED, QuoteWorkflowStatusCO.CANCELED));
        //adminValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.SUBMITTED, QuoteWorkflowStatusCO.DENIED));
        adminValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.SUBMITTED, QuoteWorkflowStatusCO.APPROVED));
        //adminValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.CANCELED, QuoteWorkflowStatusCO.SUBMITTED));
        //adminValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.DENIED, QuoteWorkflowStatusCO.SUBMITTED));
        //adminValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.APPROVED, QuoteWorkflowStatusCO.SUBMITTED));
    }
    

    private static Set<QuoteStatusTransition> regularValidTransitions = new HashSet<QuoteStatusTransition>();
    static {
        // Valid quote status transitions availabe for regular users over their quotes
        //regularValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.DRAFT, QuoteWorkflowStatusCO.CANCELED));
        regularValidTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.DRAFT, QuoteWorkflowStatusCO.SUBMITTED));
    }
    
    private static Set<QuoteStatusTransition> freezeTransitions = new HashSet<QuoteStatusTransition>();
    static {
    	System.out.println("Doing freezeTransactions");
        // Transitions that require "freezing" of the quote        
        //freezeTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.SUBMITTED, QuoteWorkflowStatusCO.DENIED));
        freezeTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.SUBMITTED, QuoteWorkflowStatusCO.APPROVED));
    }
    
    private static Set<QuoteStatusTransition> unfreezeTransitions = new HashSet<QuoteStatusTransition>();
    static {
    	System.out.println("Doing unfreezeTransactions");
        // Transitions that require "freezing" of the quote        
        //unfreezeTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.DENIED, QuoteWorkflowStatusCO.SUBMITTED));
        unfreezeTransitions.add(new QuoteStatusTransition(QuoteWorkflowStatusCO.APPROVED, QuoteWorkflowStatusCO.SUBMITTED));
    }
    
    private boolean setContainsTransition(Set<QuoteStatusTransition> set, QuoteWorkflowStatusCO from, QuoteWorkflowStatusCO to) {
        for (QuoteStatusTransition trans : set) {
            if (trans.from.equals(from) && (trans.to.equals(to))) {
                return true;
            }
        }
        return false;
    }

    
    private QuoteDao dao;

    public QuoteManager() {
        dao = new QuoteDao();
    }
    
    // Creates a quote from scratch (revision #1)
    public void createQuote(Quote q) {
        // Fill out fields that may not have a value at this point
        // quote number and revision number are automatically populated by a DB trigger
        Date now = new Date();
        q.setCreateDate(now);
        q.setModifiedDate(now);
        if (q.getStatus() == null)
        	q.setStatus(QuoteWorkflowStatusCO.DRAFT);
        
        // All new quotes are by definition the active revisions
        q.setActiveRevision(true);
        dao.createQuote(hibernateSession, q);
        
        // Since this is a recently created quote, setup (empty) information for quote costs
        q.setQuoteCosts(CostModelUtils.createZeroQuoteCosts(q.getProductCategories()));
    }
    
    
    public void updateQuote(User usr, Quote q, boolean updateMatrix) throws QuoteEditException {
        
    	Quote quote = dao.getQuote(hibernateSession, usr, q.getId());
    	if (quote == null) {
    	    throw new QuoteEditException("No quote to update...");    	    
    	}
    	
    	        
    	List<ProductCategory> oldProducts = quote.getProductCategories();
    	List<ProductCategory> newProducts = q.getProductCategories();
    	
    	q.setModifiedDate(new Date());
        dao.updateQuote(hibernateSession, q);
        
        if (updateMatrix) {
            dao.deleteQuoteCostItemsNotInMatrix(hibernateSession, q, q.getMatrix());
            dao.deleteQuoteMetricsNotInMatrix(hibernateSession, q, q.getMatrix());
            dao.deleteQuoteCostAdjNotInMatrix(hibernateSession, q, q.getMatrix());
            
            dao.updateQuoteMatrix(hibernateSession, q);            
        }
        
        if (q.getHasMts() != quote.getHasMts())
        {
        	// delete
        	dao.deleteQuoteMTSScopeAnswers(hibernateSession, q);
        	dao.deleteQuoteMTSRoleCosts(hibernateSession, q);
        }
        
        // Compare previous list of Quote_Product entries against new list
        // Delete items that are not longer there and also Quote_Cost_Item entries associated to the product no longer present
        // Add new entries of Quote_Product
        
        boolean found = false;
        // to delete old products
        for (ProductCategory o : oldProducts)
        {
        	found = false;
        	for (ProductCategory n : newProducts)
        	{
        		if (n.getId() == o.getId())
        		{
        			found = true;
        			break;
        		}
        	}
        	if (!found) // delete
        	{
        		dao.deleteQuoteCostItemsInProductCategory(hibernateSession, quote, o);
        		dao.deleteQuoteCostAdjInProductCategory(hibernateSession, quote, o);
        		
        		dao.deleteQuoteProductCategory(hibernateSession, quote, o);
        	}
        }
        
        List<QuoteProduct> productsToInsert = new ArrayList<QuoteProduct>(); 
        
        // to insert new products
        for (ProductCategory n : newProducts)
        {
        	found = false;
        	for (ProductCategory o : oldProducts)
        	{
        		if (n.getId() == o.getId())
        		{
        			found = true;
        			break;
        		}
        	}
        	if (!found) // insert
        	{
        		QuoteProduct product = new QuoteProduct();
        		product.setQuote(q);
        		product.setProductCategory(n);
        		productsToInsert.add(product);
        	}
        }
        
        if (!productsToInsert.isEmpty()) {
            dao.saveQuoteProducts(hibernateSession, productsToInsert);
        }
    }  
    
    public void updateQuoteBudgetaryCost(Quote q) throws QuoteEditException {
        dao.updateBudgetaryCost(hibernateSession, q);
    }
        
    public void updateQuoteMarketCost(Quote q) throws QuoteEditException {
        dao.updateMarketCost(hibernateSession, q);
    }
        
    public void updateQuoteTerms(Quote q) throws QuoteEditException {
        dao.updateQuoteTerms(hibernateSession, q);
    }
        
    public void updateQuoteModifiedDate(Quote q) {
        q.setModifiedDate(new Date());
        dao.updateQuoteModifiedDate(hibernateSession, q);
        
    }
    
    public List<Quote> getDraftQuotesForUser(User u) {
        List<Quote> result = dao.getDraftQuotesForUser(hibernateSession, u);
        
        for (Quote q : result) {
            removeInactiveProductCategories(q);
        }
        
        return result; 
    }
    
    public List<Quote> queryQuotes(QuoteQuery query) {
        List<Quote> result = dao.queryQuotes(hibernateSession, query);
        
        for (Quote q : result) {
            removeInactiveProductCategories(q);
        }
        
        return result;         
    }
    
    /*
    protected void processQuoteMetricForSaving(QuoteMetric qm) {
        // format conversion for combo metrics...
        if ( MetricDataTypeCO.BOOL_PLUS_STRING.equals(qm.getMetric().getDataType()) ||
             MetricDataTypeCO.BOOL_PLUS_NUMERIC.equals(qm.getMetric().getDataType())) {
            Boolean bool = qm.getBooleanValue();
            if (bool == null) { // NULL
                qm.setMetricValue(null);
            }
            else if (bool.booleanValue()) { // TRUE
               qm.setMetricValue(bool.toString() + COMBO_METRIC_SEPARATOR + qm.getMetricValue()); 
            }
            else { // FALSE
               qm.setMetricValue(bool.toString()); 
            }
        }        
    }
    
    protected void processQuoteMetricForDisplay(QuoteMetric qm) {
        // format conversion for combo metrics
        if ( MetricDataTypeCO.BOOL_PLUS_STRING.equals(qm.getMetric().getDataType()) ||
                MetricDataTypeCO.BOOL_PLUS_NUMERIC.equals(qm.getMetric().getDataType())) {
            String value = qm.getMetricValue();
            if (value != null) {
                StringTokenizer st = new StringTokenizer(value, COMBO_METRIC_SEPARATOR );
                Boolean booleanVal = Boolean.valueOf(st.nextToken());
                String metricVal = null;
                if (isTrue(booleanVal)) {
                    metricVal = st.nextToken();
                }
                qm.setBooleanValue(booleanVal);
                qm.setMetricValue(metricVal);
            }                    
        }                        
    }
    */
    
    public void saveMetricsForQuote(Quote q, List<QuoteMetric> quoteMetrics) throws QuoteEditException {
        
        // Populate quote in each quote metrics object
        for (QuoteMetric qm : quoteMetrics) {
            qm.setQuote(q);
            //processQuoteMetricForSaving(qm);
        }
        // batch save...
        dao.saveQuoteMetrics(hibernateSession, quoteMetrics);        
    }
    
    
    public List<QuoteMetric> getMetricsForQuote(Quote q) {
        List<QuoteMetric> result = dao.getMetricsForQuote(hibernateSession, q);
        if (result != null) {
            for (QuoteMetric qm : result) {
                //processQuoteMetricForDisplay(qm);
            }
        }
        
        return result;
    }
    
    public List<QuoteCostItem> getCostItemsForQuote(Quote q) {
        return dao.getCostItemsForQuote(hibernateSession, q);
    }
    
    public List<QuoteCostAdjustment> getCostAdjustmentsForQuote(Quote q) {
        return dao.getCostAdjustmentsForQuote(hibernateSession, q);
    }
    
    private void removeInactiveProductCategories(Quote q) {
        // Remove inactive product categories
        List<ProductCategory> pcs = q.getProductCategories();
        if (pcs != null) {
            Iterator<ProductCategory> it = pcs.iterator();
            while (it.hasNext()) {
                ProductCategory pc = it.next();
                if (! isTrue(pc.getActive())) {
                    it.remove();
                }
            }
        }
        
    }
    
    public Quote getQuote(User usr, long id, boolean calculateCosts) {
        Quote result = dao.getQuote(hibernateSession, usr, id);
        if (result == null) {
            return result;
        }
        
        removeInactiveProductCategories(result);
        

        if (calculateCosts) {
            // Calculate current quote costs
            MatrixManager matrixMgr = new MatrixManager();
            matrixMgr.setHibernateSession(hibernateSession);
            result.setQuoteCostItems(getCostItemsForQuote(result));
            result.setQuoteCostAdjustments(getCostAdjustmentsForQuote(result));
            result.setQuoteMetrics(getMetricsForQuote(result));
            QuoteCosts quoteCosts = matrixMgr.calculateQuoteCosts(result.getMatrix(), 
                    result.getQuoteCostItems(), result.getQuoteCostAdjustments(), result.getQuoteMetrics(), 
                    result.getProductCategories());
            result.setQuoteCosts(quoteCosts);
        }
        return result;
    }
    
    public void saveCostItemsForQuote(Quote q, List<QuoteCostItem> quoteCostItems){
        
        // Populate quote in each quote metrics object
        for (QuoteCostItem qci : quoteCostItems) {
            qci.setQuote(q);
        }
        // batch save...
        dao.saveQuoteCostItems(hibernateSession, quoteCostItems);
    }    
    
    public void deleteCostItemsForQuote(Quote q, List<QuoteCostItem> quoteCostItems) {
        // batch delete...
        dao.deleteQuoteCostItems(hibernateSession, quoteCostItems);
    }
    
    public void deleteMetricsForQuote(Quote q, List<QuoteMetric> quoteMetrics) throws QuoteEditException {
        // batch delete...
        dao.deleteQuoteMetrics(hibernateSession, quoteMetrics);
    }
    
    public void saveCostAdjustmentsForQuote(Quote q, List<QuoteCostAdjustment> quoteCostAdjustments) {
        // Populate quote in each quote metrics object
        for (QuoteCostAdjustment qca : quoteCostAdjustments) {
            qca.setQuote(q);
        }
        // batch save...   
        dao.saveQuoteCostAdjustments(hibernateSession, quoteCostAdjustments);
    }
    
    public void deleteCostAdjustmentsForQuote(Quote q, List<QuoteCostAdjustment> quoteCostAdjustments) {
        // batch delete...
        dao.deleteQuoteCostAdjustments(hibernateSession, quoteCostAdjustments);        
    }

    public List<QuoteMTSScopeAnswer> getMTSScopeAnswersForQuote(Quote q) {
        return dao.getMTSScopeAnswersForQuote(hibernateSession, q);
    }
    
    public List<QuoteMTSRoleCost> getMTSRoleCostsForQuote(Quote q) {
        return dao.getMTSRoleCostsForQuote(hibernateSession, q);
    }
    
    public void deleteMTSScopeAnswersForQuote(Quote q, List<QuoteMTSScopeAnswer> quoteScopeAnswers) throws QuoteEditException {
        // batch delete...
        dao.deleteQuoteMTSScopeAnswers(hibernateSession, quoteScopeAnswers);
    }
    
    public void saveMTSScopeAnswersForQuote(Quote q, List<QuoteMTSScopeAnswer> quoteScopeAnswers) throws QuoteEditException {
        
        // Populate quote in each quote scope answer object
        for (QuoteMTSScopeAnswer scopeAnswer : quoteScopeAnswers) {
            scopeAnswer.setQuote(q);
        }
        // batch save...
        dao.saveQuoteMTSScopeAnswers(hibernateSession, quoteScopeAnswers);        
    }
    
    public void saveMTSRoleCostsForQuote(Quote q, List<QuoteMTSRoleCost> quoteMTSRoleCosts) throws QuoteEditException {
        
        // Populate quote in each quote role cost object
        for (QuoteMTSRoleCost roleCost : quoteMTSRoleCosts) {
            roleCost.setQuote(q);
        }
        // batch save...
        dao.saveQuoteMTSRoleCosts(hibernateSession, quoteMTSRoleCosts);        
    }
    
    public void updateMTSFieldsForQuote(Quote q) throws QuoteEditException {
        
        dao.updateQuoteMTSFields(hibernateSession, q);        
    }
    
    public List<QuoteProduct> getProductsForQuote(Quote q) {
        return dao.getProductsForQuote(hibernateSession, q);
    }
    
    public void saveProductsForQuote(Quote q, List<QuoteProduct> quoteProducts) {
        // Populate quote in each quote product object
        for (QuoteProduct qp : quoteProducts) {
            qp.setQuote(q);
        }
        
        // batch save...
        dao.saveQuoteProducts(hibernateSession, quoteProducts);
    }

    public void updateQuoteMtsCustomerQuote(Quote q) throws QuoteEditException {
        
        dao.updateQuoteMtsCustomerQuote(hibernateSession, q);
        
    }
    
    private void doUpdateQuoteStatus(User usr, Quote q, QuoteWorkflowStatusCO newStatus) throws QuoterStatusChangeException, QuoteEditException {
        // Only allowed over active revisions
        if (! isTrue(q.getActiveRevision())) {
            QuoterStatusChangeException ex = new QuoterStatusChangeException("Invalid operation");
            throw ex;                            
        }
        
        /*if (! UserTypeCO.ADMINISTRATIVE.equals(usr.getUserType())) {
            // A regular user can only change his own quotes
            if (! usr.getId().equals(q.getAuthor().getId())) {
                QuoterStatusChangeException ex = new QuoterStatusChangeException("Insufficient privileges to perform this action");
                throw ex;
            }
            // A regular user can only apply some valid transitions
            if (! setContainsTransition(regularValidTransitions, q.getStatus(), newStatus)) {
                QuoterStatusChangeException ex = new QuoterStatusChangeException("Invalid operation");
                throw ex;                
            }
            
        }
        else {
            // An admin user can only apply some valid transitions
            if (! setContainsTransition(adminValidTransitions, q.getStatus(), newStatus)) {
                QuoterStatusChangeException ex = new QuoterStatusChangeException("Invalid operation");
                throw ex;                                
            }
        }*/
        
        if (usr.getApprovalLevel().equals(QuoteActionLevelCO.ALL)) // all is valid
        {
            if (! setContainsTransition(allValidTransitions, q.getStatus(), newStatus)) {
                QuoterStatusChangeException ex = new QuoterStatusChangeException("Invalid operation");
                throw ex;                                
            }
        }
        else
        {
            if (usr.getApprovalLevel().equals(QuoteActionLevelCO.BASELINE)) 
            {
                if (! setContainsTransition(baselineValidTransitions, q.getStatus(), newStatus)) {
                    QuoterStatusChangeException ex = new QuoterStatusChangeException("Invalid operation");
                    throw ex;                                
                }
            }
            else
            {
                if (usr.getApprovalLevel().equals(QuoteActionLevelCO.BUSINESS_PLAN)) 
                {
                    if (! setContainsTransition(businessPlanValidTransitions, q.getStatus(), newStatus)) {
                        QuoterStatusChangeException ex = new QuoterStatusChangeException("Invalid operation");
                        throw ex;                                
                    }
                }
            }
        }
        	
        /* doing freeze all the time now, seems that is not required to unfreeze now, but keep the code here just in case
        // Do we require a freezing of the Quote?
    	System.out.println("verify freeze state");
        if (setContainsTransition(freezeTransitions, q.getStatus(), newStatus)) {
            freezeQuote(q);            
        }
        // Do we require an unfreezing of the Quote?
        if (setContainsTransition(unfreezeTransitions, q.getStatus(), newStatus)) {
            unfreezeQuote(q);            
        }
        */
        
        dao.updateQuoteStatus(hibernateSession, q, newStatus);
        q.setStatus(newStatus);
        updateQuoteModifiedDate(q);        
    }
    
    public void freezeQuote(Quote q) throws QuoteEditException {
    	System.out.println("Freezing Quote");
    	List<String> errorList = new ArrayList<String>();
    	boolean strFound = false;
        MatrixManager matrixMgr = new MatrixManager();
        matrixMgr.setHibernateSession(hibernateSession);
        List<QuoteCostItem> frozenQuoteCostItems = matrixMgr.fillUpQuoteCostItems(q.getMatrix(),q.getQuoteCostItems(), q.getQuoteMetrics(), q.getProductCategories());
        // validate errorMessage here
        // generate a list
        for (QuoteCostItem qci : frozenQuoteCostItems)
        {
        	if (qci.getErrorMessage() != null)
        	{
        		strFound = false;
        		for (String s : errorList)
        		{
        			if (s.equals(qci.getErrorMessage()))
        			{
        				strFound = true;
        				break;
        			}
        		}
        		if (strFound)
        			continue;
        		errorList.add(qci.getErrorMessage());
        	}
        }
        
        if (!errorList.isEmpty())
        	throw new QuoteEditException(errorList.toString());

        saveCostItemsForQuote(q, frozenQuoteCostItems);        
    }
    
    private void unfreezeQuote(Quote q) {
    	System.out.println("Unfreezing Quote");
        List<QuoteCostItem> itemsToDelete = new ArrayList<QuoteCostItem>();
        for (QuoteCostItem qci : q.getQuoteCostItems()) {
            if (isTrue(qci.getRequired())) {
                itemsToDelete.add(qci);
            }
        }
        deleteCostItemsForQuote(q, itemsToDelete);
        dao.nullifyQuoteCostItemsCostData(hibernateSession, q);
    }

    public Quote submitQuoteForReview(User usr, long id)
            throws QuoterStatusChangeException, QuoteEditException {
        // Get quote data
        Quote q = getQuote(usr, id, true);
        if (q == null) {
            QuoterStatusChangeException ex = new QuoterStatusChangeException("Quote not found or insufficient privileges to perform this action");
            throw ex;            
        }
        
        /* this shouldn't happen anymore
        // given that quote is "dirty" as a result of processQuoteMetricsForDisplay, we need to get metrics to a save state to save
        try {
			saveMetricsForQuote(q, q.getQuoteMetrics());
		} catch (QuoteEditException e) {
			e.printStackTrace();
			throw e;
		}*/
        
        return doSubmitQuoteForReview(usr, q);
    }
    
    protected Quote doSubmitQuoteForReview(User usr, Quote q)
            throws QuoterStatusChangeException, QuoteEditException {        
        // Validate required fields
        List<String> errorMessages = new ArrayList<String>();
        // Customer
        if ((q.getCustomer() == null) || (q.getCustomer().getName() == null) || (q.getCustomer().getName().isEmpty())) {
            errorMessages.add("Customer / customer name not set");
        }
        
        // Terms
        if (q.getTerms() == null || q.getTerms() <= 0) {
            errorMessages.add("Quote terms (months) not set");            
        }
        
        // Metrics for Quote, used in Quote Cost Items
        List<QuoteCostItem> costItems = q.getQuoteCostItems();
        if (costItems != null) {
            for (QuoteCostItem qci : costItems) {
                String errorMessage = qci.getErrorMessage();
                if ( (errorMessage != null) && ( ! errorMessage.isEmpty())) {
                    if (! errorMessages.contains(errorMessage)) {
                        errorMessages.add(errorMessage);
                    }                    
                }                
            }
        }
        
        if ( isTrue(q.getHasMts())) {
            if ( (q.getMtsAnnualLoadCount() == null) || (q.getMtsAnnualLoadCount() <= 0d) ) {
                errorMessages.add("Annual load count (MTS) not set");
            }
        }
        
        if (errorMessages.isEmpty()) {
        	if (q.getStatus().equals(QuoteWorkflowStatusCO.DRAFT))
        		doUpdateQuoteStatus(usr, q, QuoteWorkflowStatusCO.SUBMITTED);
        	else 
            	if (q.getStatus().equals(QuoteWorkflowStatusCO.SUBMITTED))
            		doUpdateQuoteStatus(usr, q, QuoteWorkflowStatusCO.APPROVED);
        	
            return q;
        }
        else {
            QuoterStatusChangeException ex = new QuoterStatusChangeException("Quote information is incomplete. You need to complete or adjust metrics before sending for review. "
                    + "Please check Customer Profile and Market Quote sections.");
            ex.setDetailMesssages(errorMessages);
            throw ex;
        }        
    }
    
    public Quote updateQuoteStatus(User usr, long id, QuoteWorkflowStatusCO newStatus, String comment) throws QuoterStatusChangeException, QuoteEditException {
        // Get quote data
        Quote q = getQuote(usr, id, true);
        if (q == null) {
            QuoterStatusChangeException ex = new QuoterStatusChangeException("Quote not found or insufficient privileges to perform this action");
            throw ex;            
        }
        
        // If a comment was added, save it
        if (comment != null) {
            doSaveNoteForQuote(usr, q, comment);
        }
        
        if (q.getStatus().equals(QuoteWorkflowStatusCO.DRAFT) && newStatus.equals(QuoteWorkflowStatusCO.SUBMITTED)) {
            // Changes from Draft to Submitted are only allowed using submitQuoteForReview()            
            return doSubmitQuoteForReview(usr, q);
        }
        
        doUpdateQuoteStatus(usr, q, newStatus);
        
        return q;
    }
    
    public Quote createRevision(User usr, long baseQuoteId) throws QuoterStatusChangeException {
        // Get base quote data
        Quote baseQuote = getQuote(usr, baseQuoteId, true);
        if (baseQuote == null) {
            QuoterStatusChangeException ex = new QuoterStatusChangeException("Quote not found or insufficient privileges to perform this action");
            throw ex;            
        }
        
        if ( (! isTrue(baseQuote.getActiveRevision())) /*|| ( ! QuoteWorkflowStatusCO.DENIED.equals(baseQuote.getStatus()))*/ ) {
            QuoterStatusChangeException ex = new QuoterStatusChangeException("Invalid operation");
            throw ex;                                    
        }
        
        Quote newQuote = new Quote(baseQuote);
        newQuote.setModifiedBy(usr);
        newQuote.setStatus(baseQuote.getStatus());
        
        // Get all important data members
        List<QuoteMetric> metrics = newQuote.getQuoteMetrics();
        List<QuoteCostItem> costItems = newQuote.getQuoteCostItems();
        
        /* this shouldn't happen anymore
        // stupid hibernate, I need to save metrics because if not hibernate save it for me in the wrong way because of its dirtiness
        try {
			saveMetricsForQuote(baseQuote, baseQuote.getQuoteMetrics());
		} catch (QuoteEditException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
        
        List<QuoteMTSScopeAnswer> mtsScopeAnswers = null;
        List<QuoteMTSScopeAnswer> baseQuoteMtsScopeAnswers =  getMTSScopeAnswersForQuote(baseQuote);
        if (baseQuoteMtsScopeAnswers != null) {
            mtsScopeAnswers = new ArrayList<QuoteMTSScopeAnswer>();
            for (QuoteMTSScopeAnswer answer : baseQuoteMtsScopeAnswers) {
                mtsScopeAnswers.add(new QuoteMTSScopeAnswer(answer));                
            }
        }
        
        
        List <QuoteMTSRoleCost> mtsRoleCosts = null;
        List <QuoteMTSRoleCost> baseQuoteMtsRoleCosts =  getMTSRoleCostsForQuote(baseQuote);
        if (baseQuoteMtsRoleCosts != null) {
            mtsRoleCosts = new ArrayList<QuoteMTSRoleCost>();
            for (QuoteMTSRoleCost roleCost : baseQuoteMtsRoleCosts) {
                mtsRoleCosts.add(new QuoteMTSRoleCost(roleCost));
            }
        }
        
        createQuote(newQuote);

        
        // Merge with existing quote products
        List<QuoteProduct> quoteProducts = getProductsForQuote(newQuote);
        List<QuoteProduct> baseQuoteProducts = getProductsForQuote(baseQuote);
        if (baseQuoteProducts != null) {
            for (QuoteProduct product : baseQuoteProducts) {
                QuoteProduct newQuoteProduct = findQuoteProduct(quoteProducts, product);
                if (newQuoteProduct != null) {
                    newQuoteProduct.setCustomerQuoteImpl(product.getCustomerQuoteImpl());
                    newQuoteProduct.setCustomerQuoteMonthly(product.getCustomerQuoteMonthly());
                }
            }
        }
                
                
        
        // Save references
        // 1. Metrics
        if (metrics != null) {
            try {
                saveMetricsForQuote(newQuote, metrics);
            }
            catch (QuoteEditException e) {}; // Ignoring, since we are sure this is a Draft quote
        }
        
        
        // 2. Cost Items
        if (costItems != null) {
            saveCostItemsForQuote(newQuote, costItems);
        }
        
        
        // 3. MTS Scope Answers
        if (mtsScopeAnswers != null) {
            try {
                saveMTSScopeAnswersForQuote(newQuote, mtsScopeAnswers);                
            }
            catch (QuoteEditException e) {}; // Ignoring, since we are sure this is a Draft quote            
        }
        
        
        // 4. MTS Role Costs
        if (mtsRoleCosts != null) {
            try {            
                saveMTSRoleCostsForQuote(newQuote, mtsRoleCosts);
            }
            catch (QuoteEditException e) {}; // Ignoring, since we are sure this is a Draft quote            
                
        }
        
        
        // 5. Quote products (customer quotes)
        if (quoteProducts != null) {
            saveProductsForQuote(newQuote, quoteProducts);            
        }
        
        // Unfreeze, so that saved required cost items are removed and cost data is cleaned up
        //unfreezeQuote(newQuote);
        
        // Deactivate previous revision
        deactivateQuoteRevision(baseQuote);
                
        // TODO: Copy any quote adjustments, quote risk analysis and any other Quote related table
        // (They are not being used for now)
        
        return newQuote;
    }
    
    public void deactivateQuoteRevision(Quote q) {
        dao.deactivateQuoteRevision(hibernateSession, q);        
    }
    
    
    public static boolean isTrue(Boolean val) {
        return ( (val != null) && (val.booleanValue()) );
    }
    
    public static QuoteProduct findQuoteProduct(List<QuoteProduct> quoteProducts, QuoteProduct quoteProd) {
        if (quoteProducts != null) {
            for (QuoteProduct qp : quoteProducts) {
                if (qp.getProductCategory().getId().equals(quoteProd.getProductCategory().getId())) {
                    return qp;
                }
            }
        }
        return null;
    }
    
    public Quote saveNoteForQuote(User usr, long id, String comment)
            throws QuoteEditException {
        // Get quote data
        Quote q = getQuote(usr, id, false);
        if (q == null) {
            throw new QuoteEditException("Quote not found or insufficient privileges to perform this action");
        }
        
        doSaveNoteForQuote(usr, q, comment);
        
        return q;        
    }
    
    
    public void doSaveNoteForQuote(User usr, Quote q, String comment) {
        QuoteNote note = new QuoteNote();
        note.setQuote(q);
        note.setComment(comment);
        note.setUser(usr);
        // Created: now
        note.setCreateDate(new Date());
        
        dao.saveQuoteNote(hibernateSession, note);        
    }
    
    public List<QuoteNote> getNotesForQuote(Quote q) {
        return dao.getNotesForQuote(hibernateSession, q);
    }
    
    public List<QuoteRiskAnalysis> getRiskAnalysisItemsForQuote(Quote q) {
        return dao.getRiskAnalysisItemsForQuote(hibernateSession, q);
    }
    
    public void deleteRiskAnalysisItemsForQuote(Quote q, List<QuoteRiskAnalysis> quoteRiskAnalysisItems) throws QuoteEditException {
        // batch delete...
        dao.deleteQuoteRiskAnalysisItems(hibernateSession, quoteRiskAnalysisItems);
    }
    
    public void saveRiskAnalysisItemsFor(Quote q, List<QuoteRiskAnalysis> quoteRiskAnalysisItems) throws QuoteEditException {
        
        // Populate quote in each quote metrics object
        for (QuoteRiskAnalysis qra : quoteRiskAnalysisItems) {
            qra.setQuote(q);
        }
        // batch save...        
        dao.saveQuoteRiskAnalysisItems(hibernateSession, quoteRiskAnalysisItems);
    }
    

    
}
