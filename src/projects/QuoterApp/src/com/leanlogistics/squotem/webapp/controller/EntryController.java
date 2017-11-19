package com.leanlogistics.squotem.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leanlogistics.squotem.co.CustomerTypeCO;
import com.leanlogistics.squotem.co.FeeCategoryCO;
import com.leanlogistics.squotem.co.MTSScopeAnswerCO;
import com.leanlogistics.squotem.co.MetricGroupCO;
import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.co.SpecialQuoteCostItemCO;
import com.leanlogistics.squotem.co.UserTypeCO;
import com.leanlogistics.squotem.exceptions.QuoteEditException;
import com.leanlogistics.squotem.exceptions.QuoterStatusChangeException;
import com.leanlogistics.squotem.model.License;
import com.leanlogistics.squotem.model.Matrix;
import com.leanlogistics.squotem.model.PerTransactionPricing;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCostAdjustment;
import com.leanlogistics.squotem.model.QuoteCostItem;
import com.leanlogistics.squotem.model.QuoteMTSCosts;
import com.leanlogistics.squotem.model.QuoteMTSRoleCost;
import com.leanlogistics.squotem.model.QuoteMTSScopeAnswer;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.model.QuoteProduct;
import com.leanlogistics.squotem.model.QuoteRiskAnalysis;
import com.leanlogistics.squotem.model.SubscriptionPricing;
import com.leanlogistics.squotem.model.TieredPricing;
import com.leanlogistics.squotem.model.User;
import com.leanlogistics.squotem.service.SquotemService;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteCostItem;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteMetric;
import com.leanlogistics.squotem.webapp.screenobjects.CostItemForQuote;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryCustomer;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryCustomerQuote;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryMTS;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryMetric;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryQuote;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryRiskAnalysis;
import com.leanlogistics.squotem.webapp.util.QuoteCostItemUtil;
import com.leanlogistics.squotem.webapp.util.QuoteCustomerQuoteUtil;
import com.leanlogistics.squotem.webapp.util.QuoteMTSUtil;
import com.leanlogistics.squotem.webapp.util.QuoteMetricUtil;
import com.leanlogistics.squotem.webapp.util.QuoteRiskAnalysisUtil;


@Controller
public class EntryController {
    
    private static final String EDIT_QUOTE_KEY = "editQuote";
    static final String USER_KEY = "user";

    private static final String UPDATE_MATRIX = "updateMatrix";
    private static final String PROCESSED_QUOTE_METRICS_QUALIFICATION     = "processedQuoteMetricsQualification";
    private static final String PROCESSED_QUOTE_METRICS_BUSINESS_SCOPING  = "processedQuoteMetricsBusinessScoping";
    private static final String PROCESSED_CATEGORIZED_QUOTE_COST_ITEM     = "processedCategorizedQuoteCostItem";
    private static final String CATEGORIZED_QUOTE_METRIC_QUALIFICATION    = "categorizedQuoteMetricQualification";
    private static final String CATEGORIZED_QUOTE_METRIC_BUSINESS_SCOPING = "categorizedQuoteMetricBusinessScoping";
    private static final String CATEGORIZED_QUOTE_COST_ITEM               = "categorizedQuoteCostItem";
    private static final String SUBSCRIPTION_PRICING               		  = "subscriptionPricing";
    private static final String LICENSE_PRICING               		  	  = "licensePricing";
    private static final String TIERED_PRICING               		      = "tieredPricing";
    private static final String PER_TRANSACTION_PRICING               	  = "perTransactionPricing";
    private static final String PROCESSED_QUOTE_PRODUCTS                  = "processedQuoteProducts";
    private static final String PROCESSED_QUOTE_ANSWERS                   = "processedQuoteAnswers";
    private static final String QUOTE_MTS_COSTS                           = "mtsCosts";
    private static final int GTN_QUOTE_MATRIX                           = 3;

	
    @Autowired
    private SquotemService service;

    @Autowired
    private MailSender mailSender;
    
    public EntryController() {
    }

    public SquotemService getService() {
        return service;
    }

    public void setService(SquotemService service) {
        this.service = service;
    }
    
    public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@SuppressWarnings("unchecked")
	private void saveQuote(Quote q, HttpSession session) throws QuoteEditException, HibernateException, QuoterStatusChangeException
    {
        //-- validate errors on cost items
		
        Map<String, List<QuoteCostItem>> processedQuoteItems = (Map<String, List<QuoteCostItem>>) session.getAttribute(PROCESSED_CATEGORIZED_QUOTE_COST_ITEM);

        // QuoteCostItemUtil will provide us with two lists: 
        // - QuoteCostItems to delete
        // - QuoteCostItems to save and update
        
        //if (q.getQuoteMetrics() != null && !q.getQuoteMetrics().isEmpty())
        {
	        if (processedQuoteItems != null)
	        {
		        List<CategorizedQuoteCostItem> cqcis = QuoteCostItemUtil.getCategorizedQuoteCostItems(q, service, false);
		        StringBuilder errorMessages = new StringBuilder();
		        for (CategorizedQuoteCostItem cqci : cqcis)
		        {
		        	for (CostItemForQuote cifq : cqci.getEntryCostItems())
		        	{
		        		if (cifq.getEnabled() != null && cifq.getEnabled())
		        		{
		        			if (cifq.getImplQuoteCostError() != null)
		        				errorMessages.append(cifq.getImplQuoteCostError());
		        			if (cifq.getMonthlyQuoteCostError() != null)
		        				errorMessages.append(cifq.getMonthlyQuoteCostError());
		        		}
		        	}
		        }
	
		        if (!errorMessages.toString().isEmpty())
		        {
		            List<CategorizedQuoteMetric> cqm = QuoteMetricUtil.getCategorizedQuoteMetrics(service.getMatrixMetrics(q.getMatrix()), q.getQuoteMetrics(), false);
		            String error = QuoteCostItemUtil.processFreezeErrorMessage(errorMessages.toString(), cqm);
		
		            throw new QuoteEditException(error);            
		        }
	        }
        }
		//--
		
		
		User u = (User) session.getAttribute(USER_KEY);

        //QuoteMailSender mail = new QuoteMailSender(mailSender);
        //mail.sendQuoteNotification("jorge.araya@avantica.net", q);
        
        // CUSTOMER
    	try
    	{
	        if ( (q.getId() == null) || (q.getId() < 0)) {

	        	q.setModifiedBy(u);

	            // if NaN set null, update with real value later
	        	if (q.getBudgetaryImplCost() != null && q.getBudgetaryImplCost().isNaN())
	        		q.setBudgetaryImplCost(null);
	        	if (q.getBudgetaryMonthlyCost() != null && q.getBudgetaryMonthlyCost().isNaN())
	        		q.setBudgetaryMonthlyCost(null);
	        	if (q.getMarketImplCost() != null && q.getMarketImplCost().isNaN())
	        		q.setMarketImplCost(null);
	        	if (q.getMarketMonthlyCost() != null && q.getMarketMonthlyCost().isNaN())
	        		q.setMarketMonthlyCost(null);
	        	// Create a quote...
	            service.createQuote(q);
	        }
	        else {
	            Boolean updateMatrix = (Boolean) session.getAttribute(UPDATE_MATRIX);
	            
	            if (q.getStatus().equals(QuoteWorkflowStatusCO.DRAFT))
	            {
	            	q.setModifiedBy(u);

		        	// Update...
		            service.updateQuote(u, q, (updateMatrix == null ? false : updateMatrix.booleanValue()));
	            }
	            else
	            {
		            //now always create new, 
	            	Quote oldQuote = q;
	            	q = null;
		            try {
						q = service.createRevision(u, oldQuote.getId().longValue());
					} catch (QuoterStatusChangeException e) {
						e.printStackTrace();
						throw e;
					}
		            
		            // now update some quote values
		            q.setModifiedBy(u);
		            q.setMatrix(oldQuote.getMatrix());
		            q.setCustomer(oldQuote.getCustomer());
		            q.setBusinessConsultant(oldQuote.getBusinessConsultant());
		            q.setPartnerReferenced(oldQuote.getPartnerReferenced());
		            q.setEffectiveDate(oldQuote.getEffectiveDate());
		            q.setExpireDate(oldQuote.getExpireDate());
		            
		        	// Update...
		            service.updateQuote(u, q, (updateMatrix == null ? false : updateMatrix.booleanValue()));
	            }
	            
	        }
    	}
        catch (HibernateException e) {
            e.printStackTrace();
            throw e;            
        } 
        catch (QuoteEditException e) {
            e.printStackTrace();
            throw e;            
        }

        // refresh QuoteProducts
    	q.setQuoteProducts(service.getProductsForQuote(q));
    	
    	// refresh QuoteCostItems
    	q.setQuoteCostItems(service.getCostItemsForQuote(q));
        
        
        // process categorized quote metrics for QUALIFICATION page
        Map<String,List<QuoteMetric>> processedQuoteMetrics = (Map<String,List<QuoteMetric>>) session.getAttribute(PROCESSED_QUOTE_METRICS_QUALIFICATION);
        
        if (processedQuoteMetrics != null)
        {
	        List<QuoteMetric> deleteList = processedQuoteMetrics.get(QuoteCostItemUtil.DELETE_ITEMS_KEY); 
	        List<QuoteMetric> saveList   = processedQuoteMetrics.get(QuoteCostItemUtil.SAVE_ITEMS_KEY); 
	        		
	        QuoteMetricUtil.updateQuoteMetricListIds(deleteList, q.getQuoteMetrics());
	        QuoteMetricUtil.updateQuoteMetricListIds(saveList, q.getQuoteMetrics());
	        
        	try {
	        	service.updateMetricsForQuote(q, 
		                deleteList,
		                saveList);
	        }
	        catch (HibernateException e) {
	            e.printStackTrace();
	            throw e;            
	        } 
	        catch (QuoteEditException e) {
	            e.printStackTrace();
	            throw e;            
	        }

        }
        
        processedQuoteMetrics = null; // clean old values
        // process categorized quote metrics for BUSINESS SCOPING page
        processedQuoteMetrics = (Map<String,List<QuoteMetric>>) session.getAttribute(PROCESSED_QUOTE_METRICS_BUSINESS_SCOPING);
        
        /*Map<String, List<QuoteCostItem>>*/ processedQuoteItems = (Map<String, List<QuoteCostItem>>) session.getAttribute(PROCESSED_CATEGORIZED_QUOTE_COST_ITEM);

        // QuoteCostItemUtil will provide us with two lists: 
        // - QuoteCostItems to delete
        // - QuoteCostItems to save and update
        
        if (processedQuoteMetrics != null)
        {
	        List<QuoteMetric> deleteList = processedQuoteMetrics.get(QuoteCostItemUtil.DELETE_ITEMS_KEY); 
	        List<QuoteMetric> saveList   = processedQuoteMetrics.get(QuoteCostItemUtil.SAVE_ITEMS_KEY); 
	        		
	        QuoteMetricUtil.updateQuoteMetricListIds(deleteList, q.getQuoteMetrics());
	        QuoteMetricUtil.updateQuoteMetricListIds(saveList, q.getQuoteMetrics());

	        List<QuoteCostItem> costDeleteList = processedQuoteItems.get(QuoteCostItemUtil.DELETE_ITEMS_KEY); 
	        List<QuoteCostItem> costSaveList = processedQuoteItems.get(QuoteCostItemUtil.SAVE_ITEMS_KEY);
	        
	        // get categorized cost item list to refresh values
            List<CategorizedQuoteCostItem> cqcis = QuoteCostItemUtil.getCategorizedQuoteCostItems(q, service, false); // What if matrix changes???
            // now refresh just in case some metrics have changed and affect cost items
            QuoteCostItemUtil.updateQuoteCostItemListCosts(costSaveList, cqcis);

	        QuoteCostItemUtil.updateQuoteCostItemListIds(costDeleteList, q.getQuoteCostItems());
	        QuoteCostItemUtil.updateQuoteCostItemListIds(costSaveList, q.getQuoteCostItems());
	        
	        try {
	        	// Save all changes in a single transaction
		        service.updateMetricsAndCostItemsForQuote(q, 
		                deleteList, 
		                saveList,
		                costDeleteList,
		                costSaveList);
        	}
            catch (HibernateException e) {
                e.printStackTrace();
                throw e;            
            } 
            catch (QuoteEditException e) {
                e.printStackTrace();
                throw e;            
            }

	        q.setQuoteCostItems(processedQuoteItems.get(QuoteCostItemUtil.SAVE_ITEMS_KEY));

        }
        
        // recalculate cost items base on saved metrics
        q.setQuoteMetrics(service.getMetricsForQuote(q));
        // update quote costs field
        q.setQuoteCosts(service.calculateQuoteCosts(q.getMatrix(), q.getQuoteCostItems(), 
                                                            q.getQuoteCostAdjustments(), q.getQuoteMetrics(), 
                                                            q.getProductCategories()));
        // then save recalculated cost items
        try {
        	// Save all changes in a single transaction
	        service.updateMetricsAndCostItemsForQuote(q, 
	        		new ArrayList<QuoteMetric>(), 
	        		new ArrayList<QuoteMetric>(),
	        		new ArrayList<QuoteCostItem>(),
	                q.getQuoteCostItems());
    	}
        catch (HibernateException e) {
            e.printStackTrace();
            throw e;            
        } 
        catch (QuoteEditException e) {
            e.printStackTrace();
            throw e;            
        }
        
        try {
        	if (q.getQuoteMetrics() != null && !q.getQuoteMetrics().isEmpty())
        	{
	        	// freeze calculated fields
	        	service.freezeQuote(q);
        	}
        }
        catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }
        catch (QuoteEditException e) {
                  
            e.printStackTrace();

            // just for the validation process
            List<CategorizedQuoteMetric> cqm = QuoteMetricUtil.getCategorizedQuoteMetrics(service.getMatrixMetrics(q.getMatrix()), q.getQuoteMetrics(), false);
            String error = QuoteCostItemUtil.processFreezeErrorMessage(e.getMessage(), cqm);

            throw new QuoteEditException(error);            
        }                
        
        // given that we already refresh costs, lets save market values
        // Update market costs
        q.setMarketMonthlyCost(q.getQuoteCosts().getTotals().get(FeeCategoryCO.MONTHLY.getName()).doubleValue());
        if (q.getMarketMonthlyCost() != null && q.getMarketMonthlyCost().isNaN())
        	q.setMarketMonthlyCost(null);
        q.setMarketImplCost(q.getQuoteCosts().getTotals().get(FeeCategoryCO.IMPL.getName()).doubleValue());
        if (q.getMarketImplCost() != null && q.getMarketImplCost().isNaN())
        	q.setMarketImplCost(null);

        try {
			service.updateQuoteMarketCost(q);
		} 
        catch (QuoteEditException e) {
			e.printStackTrace();
            throw e;            
		}
        catch (HibernateException e) {
            e.printStackTrace();
            throw e;            
        } 


        
        // process quote products for PRICING page
        List<QuoteProduct> processedQuoteProducts = (List<QuoteProduct>) session.getAttribute(PROCESSED_QUOTE_PRODUCTS);
        
        if (processedQuoteProducts != null)
        {
	        // if is a new qoute, we need to update id's
        	if (q.getQuoteProducts() != null)
	        {
	        	for (QuoteProduct pqp : processedQuoteProducts)
	        	{
		        	for (QuoteProduct qp : q.getQuoteProducts())
		        	{
		        		if (qp.getProductCategory().getId().equals(pqp.getProductCategory().getId()))
		        		{
		        			pqp.setId(qp.getId());
		        			break;
		        		}
		        	}
	        	}
	        }

        	try {
        	// Save all changes in a single transaction
	        service.saveCustomerQuoteValuesForQuote(q, processedQuoteProducts);
        	}
            catch (HibernateException e) {
                e.printStackTrace();
                throw e;            
            } 
            catch (QuoteEditException e) {
                e.printStackTrace();
                throw e;            
            }

        }
        
        // MTS
        if (q.getHasMts() != null && q.getHasMts().booleanValue())
        {
	        Map<String, List<QuoteMTSScopeAnswer>> processedQuoteAnswers = (Map<String, List<QuoteMTSScopeAnswer>>)session.getAttribute(PROCESSED_QUOTE_ANSWERS);
	        QuoteMTSCosts mtsCosts = (QuoteMTSCosts) session.getAttribute(QUOTE_MTS_COSTS);
	
	        try {
		        service.updateMTSDataForQuote(q,
		                processedQuoteAnswers.get(QuoteCostItemUtil.DELETE_ITEMS_KEY),
		                processedQuoteAnswers.get(QuoteCostItemUtil.SAVE_ITEMS_KEY),
		                mtsCosts.getRoleCosts());
	        }
	        catch (HibernateException e) {
	            e.printStackTrace();
	            throw e;            
	        } 
	        catch (QuoteEditException e) {
	            e.printStackTrace();
	            throw e;            
	        }

        }

        
        // Update budgetary costs
        service.calculateQuoteBudgetaryCosts(q);
        if (q.getBudgetaryImplCost() != null && q.getBudgetaryImplCost().isNaN())
        	q.setBudgetaryImplCost(null);
        if (q.getBudgetaryMonthlyCost() != null && q.getBudgetaryMonthlyCost().isNaN())
        	q.setBudgetaryMonthlyCost(null);
        
        try {
			service.updateQuoteBudgetaryCost(q);
		} 
        catch (QuoteEditException e) {
			e.printStackTrace();
            throw e;            
		}
        catch (HibernateException e) {
            e.printStackTrace();
            throw e;            
        } 
        
        // Refresh quote object after saved...
        q = service.getQuote(u, q.getId()); 
        
        // prepare for refresh
        q.setQuoteMetrics(null);
                        
        loadQuoteData(q, session);
        
        // Store editable quote in session
        session.setAttribute(EDIT_QUOTE_KEY, q);

    }
    
    private void loadQuoteData(Quote q, HttpSession session)
    {
        if ( (q.getId() == null) || (q.getId() < 0)) 
        {
        	User u = (User) session.getAttribute(USER_KEY);
        	// Create a quote...
            q.setAuthor(u);
            //service.createQuote(q);
            // Since this is a recently created quote, we don't have metrics, items or adjustments yet 
            populateEmptyQuoteCostFields(q);
            
        }
        else  
        {
	    	// load all the info here to be available for the other pages
	        populateQuoteCostFields(q, true, true, true, true, true, true);
        }
        
        // Populate quote metric for qualification page
        List<CategorizedQuoteMetric> cqm = QuoteMetricUtil.getCategorizedQuoteMetrics(service.getMatrixMetrics(q.getMatrix(), MetricGroupCO.QUALIFICATION), q.getQuoteMetrics(), false);
        session.setAttribute(CATEGORIZED_QUOTE_METRIC_QUALIFICATION, cqm);
        
        // Populate quote metric for business scoping page
        List<CategorizedQuoteMetric> cqbs = QuoteMetricUtil.getCategorizedQuoteMetrics(service.getMatrixMetrics(q.getMatrix(), MetricGroupCO.BUSINESS_SCOPING), q.getQuoteMetrics(), false);
        session.setAttribute(CATEGORIZED_QUOTE_METRIC_BUSINESS_SCOPING, cqbs);
        
        // move this to here from entryQuote            
        //QuoteCostItemUtil.populateCategorizedQuoteCostItems(form, q, service); 
        // but I can't populates the screen object, so I'll split the proc and save it to session
        List<CategorizedQuoteCostItem> cqcis = QuoteCostItemUtil.getCategorizedQuoteCostItems(q, service, false); // What if matrix changes???
        session.setAttribute(CATEGORIZED_QUOTE_COST_ITEM, cqcis); 

        
        session.setAttribute(EDIT_QUOTE_KEY, q);
        
        session.removeAttribute(PROCESSED_QUOTE_METRICS_QUALIFICATION);
        session.removeAttribute(PROCESSED_QUOTE_METRICS_BUSINESS_SCOPING);
        session.removeAttribute(PROCESSED_QUOTE_PRODUCTS);
        session.removeAttribute(PROCESSED_QUOTE_ANSWERS);
        session.removeAttribute(QUOTE_MTS_COSTS);

    }
    
    // to create Quote Product list
    private void createQuoteProductList(Quote q)
    {
        List<QuoteProduct> productsToInsert = new ArrayList<QuoteProduct>(); 
        
        // to insert new products
        for (ProductCategory n : q.getProductCategories())
        {
    		QuoteProduct product = new QuoteProduct();
    		product.setId(QuoteCustomerQuoteUtil.MTS_ID);  // not sure about this
    		product.setQuote(q);
    		product.setProductCategory(n);
    		productsToInsert.add(product);
        }

        q.setQuoteProducts(productsToInsert);
    }
    
    @RequestMapping(value="entryCustomer.htm",method=RequestMethod.GET)
    protected ModelAndView showEntryCustomer(HttpSession session, HttpServletRequest req) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        QuoteEntryCustomer quoteForm = new QuoteEntryCustomer();
        // Edit an existing quote?
        String qStr = req.getParameter("q");
        if (qStr != null) {
            long qNum = Long.valueOf(qStr);
            if (qNum > 0) {
                // Get quote data
                Quote q = service.getQuote(u, Long.valueOf(qStr));
                if (q != null) {
                    // Edit changes only allowed for DRAFT quotes
                    /* edit allowed for all
                     * if (! QuoteWorkflowStatusCO.DRAFT.equals(q.getStatus())) {
                        modelAndView.setViewName("generalError");
                        return modelAndView;                                                
                    }*/
                    
                    loadQuoteData(q, session);
                }
                else {
                    // No quote found, remove any trace from session
                    session.removeAttribute(EDIT_QUOTE_KEY);
                }
            }
            else {
                // Brand new creation 
                session.removeAttribute(EDIT_QUOTE_KEY);
            }            
        }

        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        if (q != null) {
            quoteForm.setQuote(q);
        } else {
        	quoteForm.setQuote(new Quote()); // here the quote should set some defaults
        	
            // Prefill date fields
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 3);
            // Effective expire default: from now to three months from now
            quoteForm.setEffectiveDate(new Date());
            quoteForm.setExpireDate(cal.getTime());
            quoteForm.setAuthor(u);
            quoteForm.setSalesDirector(u);
        }
        
        
        
        /* Add objects required in model */
        
        // Command object
        modelAndView.addObject("command", quoteForm);
        
        modelAndView.addObject("customers", service.getCustomers());
        
        modelAndView.addObject("countries", service.getCountries());
        
        modelAndView.addObject("states", service.getStates());
        
        modelAndView.addObject("businessSectors", service.getBusinessSectors());
        
        modelAndView.addObject("customerTypes", CustomerTypeCO.values());
        
        // Users list
        modelAndView.addObject("users", service.getUsers());

        List<Matrix> matrices = null;
        if (u.getUserType().equals(UserTypeCO.ADMINISTRATIVE)) {
            // Get list of matrices for admin users
            matrices = service.getMatrices(true);
        }
        else {
            // Get list of matrices for regular users
            matrices = service.getMatrices(false);
        }
        modelAndView.addObject("matrices", matrices);

        // TODO: available product categories should be based on selected matrix
        modelAndView.addObject("productCategories", service.getProductCategories());
        
        modelAndView.addObject("sidebar", "customer");
        
        modelAndView.setViewName("entryCustomer");
        return modelAndView;
    }
    
    @RequestMapping(value="entryCustomer.htm",method=RequestMethod.POST)
    protected ModelAndView saveQuoteCustomer(@ModelAttribute("command") QuoteEntryCustomer quoteData, BindingResult result, HttpSession session, HttpServletRequest req) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        if (q == null)
        {
        	q = quoteData.getQuote();
        	Matrix m = service.getMatrix(q.getMatrix().getId().longValue());
        	q.setMatrix(m);
        }
        else
        	quoteData.getQuote(q); // this return true if is the same object

        if ( (q.getId() == null) || (q.getId() < 0)) {
            /*// Create a quote...
            q.setAuthor(u);
            //service.createQuote(q);
            // Since this is a recently created quote, we don't have metrics, items or adjustments yet 
            populateEmptyQuoteCostFields(q);*/
            loadQuoteData(q, session);
        }
        else 
        {
            boolean updateMatrix = false;
            // Compare previous and current value of matrix to see if it has changed
            if (! q.getMatrix().getId().equals(quoteData.getOldMatrix().getId()) ) {
                updateMatrix = true;
                // reload new matrix info here
                loadQuoteData(q, session);
            }
            session.setAttribute(UPDATE_MATRIX, Boolean.valueOf(updateMatrix));

        }

        // verify if user added mts
        if (q.getHasMts() != null && q.getHasMts().booleanValue())
        {
       		populateMTSMatrix(q);
        }
        
    	// Store editable quote in session
        session.setAttribute(EDIT_QUOTE_KEY, q);

        if (req.getParameter("save").equals("N")) // Next
        {
            modelAndView.setViewName("redirect:entryMetric.htm");
            return modelAndView;
        }
        
        try {
        	saveQuote(q, session);
        }
        catch (HibernateException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryCustomer.htm");
            modelAndView.addObject("quote", q);
        	modelAndView.setViewName("generalError");
        	return modelAndView;
        }
        catch (QuoteEditException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryCustomer.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
        } catch (QuoterStatusChangeException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryCustomer.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
		}
        
        //modelAndView.setViewName("redirect:entryCustomer.htm");
        q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        modelAndView.addObject("backTo", "entryCustomer.htm");
        modelAndView.addObject("quote", q);
        modelAndView.setViewName("saveSuccess");
        return modelAndView;
        
    }
        
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="entryMetric.htm",method=RequestMethod.GET)
    protected ModelAndView showEntryMetric(HttpSession session) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        /* Add objects required in model */
        // Command object
        QuoteEntryMetric form = new QuoteEntryMetric();       

        // getting data from session
        form.setCategorizedQuoteMetrics(  
        		(List<CategorizedQuoteMetric>) session.getAttribute(CATEGORIZED_QUOTE_METRIC_QUALIFICATION)
        	);


        modelAndView.addObject("command", form);
        
        modelAndView.addObject("sidebar", "metric");
        
        modelAndView.setViewName("entryMetric");
        return modelAndView;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="entryMetric.htm",method=RequestMethod.POST)
    protected ModelAndView saveQuoteMetrics(@ModelAttribute("command") QuoteEntryMetric metricsData, BindingResult result, HttpSession session, HttpServletRequest req) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        
        // update metric values
        List<CategorizedQuoteMetric> cqm = (List<CategorizedQuoteMetric>) session.getAttribute(CATEGORIZED_QUOTE_METRIC_QUALIFICATION);
        QuoteMetricUtil.updateQuoteMetricValues(cqm, metricsData.getCategorizedQuoteMetrics());
        session.setAttribute(CATEGORIZED_QUOTE_METRIC_QUALIFICATION, cqm);
        
        Map<String,List<QuoteMetric>> processedQuoteMetrics = QuoteMetricUtil.processQuoteMetricsChanges(metricsData.getCategorizedQuoteMetrics());
                
        session.setAttribute(PROCESSED_QUOTE_METRICS_QUALIFICATION, processedQuoteMetrics);
        
        // update metrics in memory
    	//q.setQuoteMetrics(processedQuoteMetrics.get(QuoteCostItemUtil.SAVE_ITEMS_KEY));

		//append processed quote metrics to the current list
		//maybe add this list to the current one to avoid ovewrite it
		Map<String,List<QuoteMetric>> pqm = (Map<String,List<QuoteMetric>>)session.getAttribute(PROCESSED_QUOTE_METRICS_BUSINESS_SCOPING);
		
		List<QuoteMetric> qm = new ArrayList<QuoteMetric>();
		// to create a new list
		qm.addAll(processedQuoteMetrics.get(QuoteCostItemUtil.SAVE_ITEMS_KEY));
		
		if (pqm != null)
			qm.addAll(pqm.get(QuoteCostItemUtil.SAVE_ITEMS_KEY));
		else
			if (q.getQuoteMetrics() != null)
				QuoteMetricUtil.updateQuoteMetricLists(qm, q.getQuoteMetrics());
		q.setQuoteMetrics(qm);
		
		//
		
    	// Store editable quote in session
        session.setAttribute(EDIT_QUOTE_KEY, q);
        
        if (req.getParameter("save").equals("N")) // Next
        {
            modelAndView.setViewName("redirect:entryQuote.htm");
            return modelAndView;
        }

        try {
        	
        	saveQuote(q, session);
        	
        }
        catch (HibernateException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryMetric.htm");
            modelAndView.addObject("quote", q);
        	modelAndView.setViewName("generalError");
        	return modelAndView;
        }
        catch (QuoteEditException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryMetric.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
        } catch (QuoterStatusChangeException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryMetric.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
		}
                
        //modelAndView.setViewName("redirect:entryMetric.htm");
        q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        modelAndView.addObject("backTo", "entryMetric.htm");
        modelAndView.addObject("quote", q);
        modelAndView.setViewName("saveSuccess");

        return modelAndView;
    }
    
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="entryQuote.htm",method=RequestMethod.GET)
    protected ModelAndView showEntryQuote(HttpSession session) {
            ModelAndView modelAndView  = new ModelAndView();
            User u = (User) session.getAttribute(USER_KEY);
            if (u == null) {
                modelAndView.setViewName("redirect:login.htm");
                return modelAndView;
            }
            
            Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
            
            // Refresh quote from backend, this time in order to calculate costs if they are not already there
            /* not sure if we need this
            QuoteCosts costs  = q.getQuoteCosts();
            if (costs == null && q.getId() != null) { 
                q = service.getQuote(u, q.getId(), true);
            } */
                        
            // Assemble command object
            QuoteEntryQuote form = new QuoteEntryQuote();
            
            form.setTerms(q.getTerms());

    		// getting data from session
            form.setCategorizedQuoteMetrics(
            		(List<CategorizedQuoteMetric>) session.getAttribute(CATEGORIZED_QUOTE_METRIC_BUSINESS_SCOPING)
            	);
             // move this to entryCustomer            
            //QuoteCostItemUtil.populateCategorizedQuoteCostItems(form, q, service); 
            // but I need here the part that populates the screen object, so I'll split the proc and get it from session
            
            // first update lists, then update cost items, I need to populate this list 
            List<CategorizedQuoteCostItem> cqcis = QuoteCostItemUtil.getCategorizedQuoteCostItems(q, service, false); // What if matrix changes???
            session.setAttribute(CATEGORIZED_QUOTE_COST_ITEM, cqcis); 
            
            List<SubscriptionPricing> pricingList = new ArrayList<SubscriptionPricing>();
            pricingList=service.getSubscriptionPricing();
            session.setAttribute(SUBSCRIPTION_PRICING, pricingList);
            
            List<License> licensePricing = new ArrayList<License>();
            licensePricing=service.getLicensePricing();
            session.setAttribute(LICENSE_PRICING, licensePricing);
            
            List<TieredPricing> tieredPricing = new ArrayList<TieredPricing>();
            tieredPricing=service.getTieredPricing();
            session.setAttribute(TIERED_PRICING, tieredPricing);
            
            List<PerTransactionPricing> perTransactionPricing = new ArrayList<PerTransactionPricing>();
            perTransactionPricing=service.getPerTransactionPricing();
            session.setAttribute(PER_TRANSACTION_PRICING, perTransactionPricing);
            
                  
            // just for the validation process
            List<CategorizedQuoteMetric> cqm = QuoteMetricUtil.getCategorizedQuoteMetrics(service.getMatrixMetrics(q.getMatrix()), q.getQuoteMetrics(), false);
            QuoteCostItemUtil.processCategorizedQuoteCostItemsErrorMessages(cqcis, cqm);
            
            form.setCategorizedQuoteCostItems(cqcis);
            form.setSubscriptionPricingList(pricingList);
            form.setLicensePricingList(licensePricing);
            form.setPerTransactionPricingList(perTransactionPricing);
            form.setTieredPricingList(tieredPricing);
            /* Add objects required in model */
            // Command object            
            modelAndView.addObject("command", form);
            modelAndView.addObject("specialValues", SpecialQuoteCostItemCO.values());
                        
            modelAndView.addObject("sidebar", "quote");
            if(q.getMatrix().getId()==GTN_QUOTE_MATRIX)
            {
            	 modelAndView.setViewName("entryGTNQuote");
            }
            else{
            	
                modelAndView.setViewName("entryQuote");
            }
            
            return modelAndView;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="entryQuote.htm",method=RequestMethod.POST)
    protected ModelAndView saveQuoteEntryQuote(@ModelAttribute("command") QuoteEntryQuote quoteData, BindingResult result, HttpSession session, HttpServletRequest req) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);      

        // to update object in session
        List<CategorizedQuoteCostItem> cqcis = (List<CategorizedQuoteCostItem>) session.getAttribute(CATEGORIZED_QUOTE_COST_ITEM);
        QuoteCostItemUtil.updateQuoteCostItemValues(cqcis, quoteData.getCategorizedQuoteCostItems());
        session.setAttribute(CATEGORIZED_QUOTE_COST_ITEM, cqcis);
        
        
        
        // update object in session
        List<CategorizedQuoteMetric> cqm = (List<CategorizedQuoteMetric>) session.getAttribute(CATEGORIZED_QUOTE_METRIC_BUSINESS_SCOPING);
        QuoteMetricUtil.updateQuoteMetricValues(cqm, quoteData.getCategorizedQuoteMetrics());
        session.setAttribute(CATEGORIZED_QUOTE_METRIC_BUSINESS_SCOPING, cqm);

        q.setTerms(quoteData.getTerms());
        
        Map<String, List<QuoteCostItem>> processedQuoteItems = QuoteCostItemUtil.processQuoteItemsChanges(quoteData.getCategorizedQuoteCostItems());
        session.setAttribute(PROCESSED_CATEGORIZED_QUOTE_COST_ITEM, processedQuoteItems);
        
        Map<String,List<QuoteMetric>> processedQuoteMetrics = QuoteMetricUtil.processQuoteMetricsChanges(quoteData.getCategorizedQuoteMetrics());
        session.setAttribute(PROCESSED_QUOTE_METRICS_BUSINESS_SCOPING, processedQuoteMetrics);


        // Associate quote cost items in session
        q.setQuoteCostItems(processedQuoteItems.get(QuoteCostItemUtil.SAVE_ITEMS_KEY));
        
        //append processed quote metrics to the current list
        //maybe maybe recreate the list using all the metrics to avoid to ovewrite it
		Map<String,List<QuoteMetric>> pqm = (Map<String,List<QuoteMetric>>)session.getAttribute(PROCESSED_QUOTE_METRICS_QUALIFICATION);
		
		List<QuoteMetric> qm = new ArrayList<QuoteMetric>();
		// to create a new list
		qm.addAll(processedQuoteMetrics.get(QuoteCostItemUtil.SAVE_ITEMS_KEY));
		
		if (pqm != null)
			qm.addAll(pqm.get(QuoteCostItemUtil.SAVE_ITEMS_KEY));
		else
			if (q.getQuoteMetrics() != null)
				QuoteMetricUtil.updateQuoteMetricLists(qm, q.getQuoteMetrics());
		q.setQuoteMetrics(qm);

        // update quote costs field
        q.setQuoteCosts(service.calculateQuoteCosts(q.getMatrix(), q.getQuoteCostItems(), 
                                                            q.getQuoteCostAdjustments(), q.getQuoteMetrics(), 
                                                            q.getProductCategories()));

        
        session.setAttribute(EDIT_QUOTE_KEY, q);

        if (req.getParameter("save").equals("N")) // Next
        {
            // Where do we go next? To MTS if MTS is included, otherwise, to Customer Quote
            String viewName = null;
            if (mtsSectionEnabled(q)) {
                viewName = "entryMTS.htm";
            }
            else {
                viewName = "customerQuote.htm";
            }
            
            modelAndView.setViewName("redirect:" + viewName);
            return modelAndView;
        }
        else
            if (req.getParameter("save").equals("R")) // Refresh
            {
                modelAndView.setViewName("redirect:entryQuote.htm");
                return modelAndView;
            }

        // QuoteCostItemUtil will provide us with two lists: 
        // - QuoteCostItems to delete
        // - QuoteCostItems to save and update
        try {
            // Save all changes in a single transaction

        	saveQuote(q, session);

        }
        catch (HibernateException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryQuote.htm");
            modelAndView.addObject("quote", q);
        	modelAndView.setViewName("generalError");
        	return modelAndView;
        }
        catch (QuoteEditException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryQuote.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
        } catch (QuoterStatusChangeException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryQuote.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
		}
                       
        //modelAndView.setViewName("redirect:entryQuote.htm");
        q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        modelAndView.addObject("backTo", "entryQuote.htm");
        modelAndView.addObject("quote", q);
        modelAndView.setViewName("saveSuccess");

        return modelAndView;
    }
    
    protected boolean mtsSectionEnabled(Quote q) {
        return ((q.getMatrix().getMtsMatrixId() != null) && (BusinessPlanController.isTrue(q.getHasMts()))); 
    }
    
    @RequestMapping(value="entryMTS.htm",method=RequestMethod.GET)
    protected ModelAndView showEntryMTS(HttpSession session) {
        ModelAndView modelAndView  = new ModelAndView();
        
        modelAndView.addObject("sidebar", "mts");        
        modelAndView.setViewName("entryMTS");
        
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        
        // Make sure we have the MTS Matrix in session
        populateMTSMatrix(q); // not sure if is necessary to move this to the general upload at the beginning
        
        if (q.getMtsMatrix() == null) {
            return modelAndView;
        }
        
        Double currentMTSAnnualLoadCOunt = q.getMtsAnnualLoadCount();
        
        // Calculate and store MTS Costs in session
        q.setQuoteMTSCosts(service.calculateQuoteMTSCosts(q.getMtsMatrix(), 
                                                            currentMTSAnnualLoadCOunt != null ? currentMTSAnnualLoadCOunt : 0d, 
                                                            q.getQuoteMTSScopeAnswers() , q.getQuoteMTSRoleCosts()));
        
        // Store back in session
        session.setAttribute(EDIT_QUOTE_KEY, q);

        // Assemble command object
        QuoteEntryMTS form = new QuoteEntryMTS();
        QuoteMTSUtil.populateCategorizedQuoteMTSScopeQuestions(form, q, q.getMtsMatrix().getScopeQuestions());
        QuoteMTSUtil.populateMTSRoles(form, q, q.getMtsMatrix().getRoleCosts());
        // Additional field to capture: annual load count
        if (currentMTSAnnualLoadCOunt != null) {
            form.setAnnualLoadCount(q.getMtsAnnualLoadCount().toString());            
        }
        
        /* Add objects required in model */
        // Command object            
        modelAndView.addObject("command", form);
        
        modelAndView.addObject("scopeAnswers", MTSScopeAnswerCO.values());
        
        return modelAndView;
    }
    
    @RequestMapping(value="entryMTS.htm",method=RequestMethod.POST)
    protected ModelAndView saveQuoteEntryMTS(@ModelAttribute("command") QuoteEntryMTS quoteData, BindingResult result, HttpSession session, HttpServletRequest req) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);        
        
        // QuoteMTSUtil will provide us with two lists: 
        // - QuoteMTSScopeAnswers to delete
        // - QuoteMTSScopeAnswers to save and update
        Map<String, List<QuoteMTSScopeAnswer>> processedQuoteAnswers = QuoteMTSUtil.processQuoteMTSScopeAnswersChanges(quoteData.getCategorizedQuoteMTSScopeQuestions());
        // List of QuoteMTSRoleCosts to save
        List<QuoteMTSRoleCost> processedQuoteMTSRoleCosts = QuoteMTSUtil.getQuoteMTSRoleCostList(quoteData.getRolesForQuote());
        
        if (quoteData.getAnnualLoadCount() != null) {
            q.setMtsAnnualLoadCount(Double.valueOf(quoteData.getAnnualLoadCount()));        
        }
        
        // Recalculate MTS Costs
        QuoteMTSCosts mtsCosts = service.calculateQuoteMTSCosts(q.getMtsMatrix(), 
                q.getMtsAnnualLoadCount() != null ? q.getMtsAnnualLoadCount() : 0d, 
                        processedQuoteAnswers.get(QuoteCostItemUtil.SAVE_ITEMS_KEY), processedQuoteMTSRoleCosts);
        
        // Update quote with mts calculated costs
        q.setMtsScopedWeeklyLoadCount(mtsCosts.getScopedWeeklyLoadCount());
        q.setMtsAnnualPrice(mtsCosts.getAnualTotal());
        
        // save in session to save it later
        session.setAttribute(PROCESSED_QUOTE_ANSWERS, processedQuoteAnswers);
        session.setAttribute(QUOTE_MTS_COSTS, mtsCosts);
        
        //FIXME: save answers to show up later
        
        // Associate quote scope answers and role costs in session
        q.setQuoteMTSScopeAnswers(processedQuoteAnswers.get(QuoteCostItemUtil.SAVE_ITEMS_KEY));
        q.setQuoteMTSRoleCosts(mtsCosts.getRoleCosts());
        
        // update quote costs field
        q.setQuoteMTSCosts(service.calculateQuoteMTSCosts(q.getMtsMatrix(), q.getMtsAnnualLoadCount() != null ? q.getMtsAnnualLoadCount() : 0d, 
                                                            q.getQuoteMTSScopeAnswers(), q.getQuoteMTSRoleCosts()));
        
        // Store editable quote in session
        session.setAttribute(EDIT_QUOTE_KEY, q);

        if (req.getParameter("save").equals("N")) // Next
        {
            modelAndView.setViewName("redirect:customerQuote.htm");
            return modelAndView;
        }
        
        try {
            // Save all changes in a single transaction
            saveQuote(q, session);
        }
        catch (HibernateException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryMTS.htm");
            modelAndView.addObject("quote", q);
        	modelAndView.setViewName("generalError");
        	return modelAndView;
        }
        catch (QuoteEditException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryMTS.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
        } catch (QuoterStatusChangeException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "entryMTS.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
		}

        
        
        //modelAndView.setViewName("redirect:entryMTS.htm");
        q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        modelAndView.addObject("backTo", "entryMTS.htm");
        modelAndView.addObject("quote", q);
        modelAndView.setViewName("saveSuccess");
        return modelAndView;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="customerQuote.htm",method=RequestMethod.GET)
    protected ModelAndView showEntryCustomerQuote(HttpSession session, HttpServletRequest req) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        
        // Make sure we have the MTS Matrix in session
        populateMTSMatrix(q);
        
        // when create new, it is null
        if (q.getQuoteProducts() == null)
        {
        	List<QuoteProduct> processedQuoteProducts = (List<QuoteProduct>) session.getAttribute(PROCESSED_QUOTE_PRODUCTS);
        	if (processedQuoteProducts == null)
        		createQuoteProductList(q);
        	else
        		q.setQuoteProducts(processedQuoteProducts);
        }
                
        // Make sure we have updated quote MTS scope answers & role costs
        //populateQuoteCostFields(q, true, true, false, true, true, true);
        
        //Double currentMTSAnnualLoadCOunt = q.getMtsAnnualLoadCount();
        
        // Update quote costs
        q.setQuoteCosts(service.calculateQuoteCosts(q.getMatrix(), q.getQuoteCostItems(), 
                                                            q.getQuoteCostAdjustments(), q.getQuoteMetrics(), 
                                                            q.getProductCategories()));        
        
        // Update quote MTS costs
        /*
        if (q.getMtsMatrix() != null) {
            q.setQuoteMTSCosts(service.calculateQuoteMTSCosts(q.getMtsMatrix(), 
                                                                currentMTSAnnualLoadCOunt != null ? currentMTSAnnualLoadCOunt : 0d,                                                                 q.getQuoteMTSScopeAnswers() , q.getQuoteMTSRoleCosts()));
        }*/
        
        // market
        q.setMarketMonthlyCost(q.getQuoteCosts().getTotals().get(FeeCategoryCO.MONTHLY.getName()).doubleValue());
        q.setMarketImplCost(q.getQuoteCosts().getTotals().get(FeeCategoryCO.IMPL.getName()).doubleValue());
        
        // Update budgetary costs
        service.calculateQuoteBudgetaryCosts(q);
        
                
        QuoteEntryCustomerQuote quoteForm = new QuoteEntryCustomerQuote();
        QuoteCustomerQuoteUtil.populateCustomerQuoteForm(quoteForm, q);
        
        // Command object
        modelAndView.addObject("command", quoteForm);
        
        
        modelAndView.addObject("sidebar", "customerQuote");
        
        modelAndView.setViewName("customerQuote");
        return modelAndView;
    }
    
    @RequestMapping(value="customerQuote.htm",method=RequestMethod.POST)
    protected ModelAndView saveEntryCustomerQuote(@ModelAttribute("command") QuoteEntryCustomerQuote quoteData, BindingResult result, HttpSession session, HttpServletRequest req) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);        
        
        // QuoteCustomerQuoteUtil will provide us with the list of QuoteProduct to save,
        // However the last one (MTS) is not really a QuoteProduct, it should be stored as part of the Quote info 
        List<QuoteProduct> processedQuoteProducts = QuoteCustomerQuoteUtil.processQuoteProductsChanges(quoteData);
        
        // Strip any "virtual" quote product for MTS
 /* not sure about this loop
  *        for (Iterator<QuoteProduct> it = processedQuoteProducts.iterator(); it.hasNext(); ) {
            QuoteProduct qp = it.next();
            if (qp.getId().equals(QuoteCustomerQuoteUtil.MTS_ID)) {
                q.setMtsCustomerQuoteImpl(qp.getCustomerQuoteImpl());
                q.setMtsCustomerQuoteMonthly(qp.getCustomerQuoteMonthly());
                it.remove();                
            }
        }
*/        
        session.setAttribute(PROCESSED_QUOTE_PRODUCTS, processedQuoteProducts);
        q.setQuoteProducts(processedQuoteProducts);

        // Store editable quote in session
        session.setAttribute(EDIT_QUOTE_KEY, q);
        
        if (req.getParameter("save").equals("R")) // Refresh
        {
            modelAndView.setViewName("redirect:customerQuote.htm");
            return modelAndView;
        }
        
        try {
            // Save all changes in a single transaction
            //service.saveCustomerQuoteValuesForQuote(q, processedQuoteProducts);
        	saveQuote(q, session);
        	
        }
        catch (HibernateException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "customerQuote.htm");
            modelAndView.addObject("quote", q);
        	modelAndView.setViewName("generalError");
        	return modelAndView;
        }
        catch (QuoteEditException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "customerQuote.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
        } catch (QuoterStatusChangeException e) {
        	modelAndView.addObject("message", e.getMessage());
        	modelAndView.addObject("cause", e.getCause());
            modelAndView.addObject("backTo", "customerQuote.htm");
            modelAndView.addObject("quote", q);
            modelAndView.setViewName("generalError");                
            return modelAndView;
		}
            
        //modelAndView.setViewName("redirect:customerQuote.htm");
        q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        modelAndView.addObject("backTo", "customerQuote.htm");
        modelAndView.addObject("quote", q);
        modelAndView.setViewName("saveSuccess");

        return modelAndView;
    }    
    
    @RequestMapping(value="entryRisk.htm",method=RequestMethod.GET)
    protected ModelAndView showEntryRisk(HttpSession session) {
            ModelAndView modelAndView  = new ModelAndView();
            User u = (User) session.getAttribute(USER_KEY);
            if (u == null) {
                modelAndView.setViewName("redirect:login.htm");
                return modelAndView;
            }

            // Command object
            QuoteEntryRiskAnalysis form = new QuoteEntryRiskAnalysis();                   
            Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
            
            // Make sure we have risk analysis items in quote
            populateQuoteRiskAnalysisItems(q);
            
            // Prepopulate current quote metrics based on metric categories and current values
            QuoteRiskAnalysisUtil.populateCategorizedQuoteRiskAnalysisItems(form, service.getMatrixRiskAnalysisItems(q.getMatrix()), q.getQuoteRiskAnalysisItems());
            
            /* Add objects required in model */
            // Command object            
            modelAndView.addObject("command", form);
                        
            modelAndView.addObject("sidebar", "risk");
            
            modelAndView.setViewName("entryRisk");
            return modelAndView;
    }
    
    @RequestMapping(value="entryRisk.htm",method=RequestMethod.POST)
    protected ModelAndView saveQuoteRisk(@ModelAttribute("command") QuoteEntryRiskAnalysis riskData, BindingResult result, HttpSession session) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }
        
        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        Map<String,List<QuoteRiskAnalysis>> processedQuoteMetrics = QuoteRiskAnalysisUtil.processQuoteRiskAnalysisChanges(riskData);
        
        try {
            service.updateRiskAnalysisItemsForQuote(q, 
                    processedQuoteMetrics.get(QuoteCostItemUtil.DELETE_ITEMS_KEY),
                    processedQuoteMetrics.get(QuoteCostItemUtil.SAVE_ITEMS_KEY));
        }
        catch (QuoteEditException e) {
            modelAndView.setViewName("generalError");                
            return modelAndView;
        }
                
        // Reload risk analysis items in session
        q.setQuoteRiskAnalysisItems(service.getRiskAnalysisItemsForQuote(q));
        
        // Store editable quote in session
        session.setAttribute(EDIT_QUOTE_KEY, q);
                
        modelAndView.setViewName("redirect:entryCustomer.htm");
        return modelAndView;
    }
    
    @RequestMapping(value="entryPricing.htm",method=RequestMethod.GET)
    protected ModelAndView showEntryPricing(@ModelAttribute("command") QuoteEntryRiskAnalysis riskData, BindingResult result, HttpSession session) {
        ModelAndView modelAndView  = new ModelAndView();
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }

        QuoteEntryCustomerQuote quoteForm = new QuoteEntryCustomerQuote();
        
        // Command object
        modelAndView.addObject("command", quoteForm);
                    
        modelAndView.addObject("sidebar", "pricing");
        
        modelAndView.setViewName("entryPricing");
        return modelAndView;
    }
    
    @RequestMapping(value = "/whatIfForMTS.json")
    public @ResponseBody QuoteMTSCosts getWhatIfScenarioForMTSChange(@ModelAttribute("command") QuoteEntryMTS quoteData, HttpSession session) {
        User u = (User) session.getAttribute(USER_KEY);
        if (u == null) {
            // TODO: Handle session expired for JSON Calls
            return null;
        }
        
        Quote q = (Quote) session.getAttribute(EDIT_QUOTE_KEY);
        if (q == null) {
            return null;
        }
        
        List<QuoteMTSScopeAnswer> processedQuoteScopeAnswers = QuoteMTSUtil.getFlatQuoteMTSSCopeAnswersList(quoteData.getCategorizedQuoteMTSScopeQuestions());
        List<QuoteMTSRoleCost> processedQuoteRoleCosts = QuoteMTSUtil.getQuoteMTSRoleCostList(quoteData.getRolesForQuote());
        return service.calculateQuoteMTSCosts(q.getMtsMatrix(), 
                                            QuoteMetricUtil.notEmptyOrNull(quoteData.getAnnualLoadCount()) ?  Double.valueOf(quoteData.getAnnualLoadCount()) : 0d, 
                                            processedQuoteScopeAnswers, processedQuoteRoleCosts);        
    }
        
    
    
    // Complete any missing fields related to quote cost calculation (metrics, items, adjustments and scope questions)
    public void populateQuoteCostFields(Quote q, boolean populateMetrics, boolean populateCostItems, 
                                                 boolean populateCostAdjustments, boolean populateMTSScopeAnswers,
                                                 boolean populateMTSRoleCosts, boolean populateQuoteProducts) {
        if (populateMetrics) {
            if (q.getQuoteMetrics() == null) {
                q.setQuoteMetrics(service.getMetricsForQuote(q));                
            }
        }
        
        if (populateCostItems) {
            if (q.getQuoteCostItems() == null) {
                q.setQuoteCostItems(service.getCostItemsForQuote(q));
            }
        }
        
        if (populateCostAdjustments) {
            if (q.getQuoteCostAdjustments() == null) {
                q.setQuoteCostAdjustments(service.getCostAdjustmentsForQuote(q));
            }
        }
        
        if (populateMTSScopeAnswers) {
            if (q.getQuoteMTSScopeAnswers() == null) {
                q.setQuoteMTSScopeAnswers(service.getMTSScopeAnswersForQuote(q));
            }            
        }
        
        if (populateMTSRoleCosts) {
            if (q.getQuoteMTSRoleCosts() == null) {
                q.setQuoteMTSRoleCosts(service.getMTSRoleCostsForQuote(q));
            }
        }
        
        if (populateQuoteProducts) {
            if (q.getQuoteProducts() == null) {
                q.setQuoteProducts(service.getProductsForQuote(q));
            }            
        }
    }
    
    public void populateMTSMatrix(Quote q) {
        if (q.getMtsMatrix() == null) {
            if (q.getMatrix().getMtsMatrixId() != null) {
                q.setMtsMatrix(service.getMTSMatrix(q.getMatrix().getMtsMatrixId()));
            }
        }
    }
    
    public void populateQuoteRiskAnalysisItems(Quote q) {
        if (q.getQuoteRiskAnalysisItems() == null) {
            q.setQuoteRiskAnalysisItems(service.getRiskAnalysisItemsForQuote(q));
        }
    }
    
    public void populateEmptyQuoteCostFields(Quote q) {
        q.setQuoteMetrics(new ArrayList<QuoteMetric>());
        q.setQuoteCostItems(new ArrayList<QuoteCostItem>());
        q.setQuoteCostAdjustments(new ArrayList<QuoteCostAdjustment>());
        q.setQuoteMTSScopeAnswers(new ArrayList<QuoteMTSScopeAnswer>());
        
        //--
        //q.setQuoteProducts(new ArrayList<QuoteProduct>());
    }
    
    public void populateQuoteCostFieldsFromObject(Quote source, Quote dest) {
        dest.setQuoteMetrics(source.getQuoteMetrics());
        dest.setQuoteCostItems(source.getQuoteCostItems());
        dest.setQuoteCostAdjustments(source.getQuoteCostAdjustments());
        dest.setQuoteMTSScopeAnswers(source.getQuoteMTSScopeAnswers());
    }
    
}
