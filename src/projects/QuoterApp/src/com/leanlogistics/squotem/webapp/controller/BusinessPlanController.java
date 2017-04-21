package com.leanlogistics.squotem.webapp.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leanlogistics.squotem.co.FeeCategoryCO;
import com.leanlogistics.squotem.co.MetricGroupCO;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.User;
import com.leanlogistics.squotem.service.SquotemService;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteCostItem;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteMTSScopeQuestion;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteMetric;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteRiskAnalysis;
import com.leanlogistics.squotem.webapp.screenobjects.CustomerQuoteItem;
import com.leanlogistics.squotem.webapp.screenobjects.MTSRoleForQuote;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryCustomerQuote;
import com.leanlogistics.squotem.webapp.util.QuoteCostItemUtil;
import com.leanlogistics.squotem.webapp.util.QuoteCustomerQuoteUtil;
import com.leanlogistics.squotem.webapp.util.QuoteMTSUtil;
import com.leanlogistics.squotem.webapp.util.QuoteMetricUtil;
import com.leanlogistics.squotem.webapp.util.QuoteRiskAnalysisUtil;

@Controller
public class BusinessPlanController {

    @Autowired
    private SquotemService service;

	public SquotemService getService() {
		return service;
	}

	public void setService(SquotemService service) {
		this.service = service;
	}

	public BusinessPlanController() {

	}

    @RequestMapping("businessPlan.htm")
    protected ModelAndView showBusinessPlan(HttpSession session,HttpServletRequest req) {
        // Check if we already have a logged in user
        ModelAndView modelAndView = new ModelAndView();

        User u = (User) session.getAttribute("user");
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }   

        long qNum = 0;
        String qStr = req.getParameter("q");
        if (qStr != null) {
            try {
                qNum = Long.valueOf(qStr);                
            }
            catch (NumberFormatException e) {}
        }
        if (qNum == 0) {
            modelAndView.setViewName("generalError");
            return modelAndView;
        }
        
        // Get quote data
        // Master data
        Quote q = service.getQuote(u, Long.valueOf(qStr), true);
        if (q == null) {
            modelAndView.setViewName("generalError");
            return modelAndView;
        }
        
        modelAndView.addObject("quote", q);
        
        q.setQuoteCostItems(service.getCostItemsForQuote(q));

        // Existing metrics
        List<CategorizedQuoteMetric> categorizedMetrics = QuoteMetricUtil.getCategorizedExistingQuoteMetrics(service.getMatrixMetrics(q.getMatrix(), MetricGroupCO.QUALIFICATION), q.getQuoteMetrics());
        modelAndView.addObject("categorizedMetrics", categorizedMetrics);

        List<CategorizedQuoteMetric> categorizedMetricsBS = QuoteMetricUtil.getCategorizedExistingQuoteMetrics(service.getMatrixMetrics(q.getMatrix(), MetricGroupCO.BUSINESS_SCOPING), q.getQuoteMetrics());
        modelAndView.addObject("categorizedMetricsBS", categorizedMetricsBS);

        // Existing cost items
        List<CategorizedQuoteCostItem> categorizedCostItems = QuoteCostItemUtil.getCategorizedExistingQuoteCostItems(q, service);
        modelAndView.addObject("categorizedCostItems", categorizedCostItems);
        
        
        /*
        boolean hasMts = false;
        // MTS Matrix
        if (q.getMtsMatrix() == null) {
            if (q.getMatrix().getMtsMatrixId() != null && isTrue(q.getHasMts())) {
                hasMts = true;
                q.setMtsMatrix(service.getMTSMatrix(q.getMatrix().getMtsMatrixId()));
            }
        }
        
        if (hasMts) {
            // MTS Scope answers
            if (q.getQuoteMTSScopeAnswers() == null) {
                q.setQuoteMTSScopeAnswers(service.getMTSScopeAnswersForQuote(q));
            }        
            // MTS Role costs
            if (q.getQuoteMTSRoleCosts() == null) {
                q.setQuoteMTSRoleCosts(service.getMTSRoleCostsForQuote(q));
            }

            Double currentMTSAnnualLoadCOunt = q.getMtsAnnualLoadCount();
            
            // Calculate and store MTS Costs in session
            q.setQuoteMTSCosts(service.calculateQuoteMTSCosts(q.getMtsMatrix(), 
                                                                currentMTSAnnualLoadCOunt != null ? currentMTSAnnualLoadCOunt : 0d, 
                                                                q.getQuoteMTSScopeAnswers() , q.getQuoteMTSRoleCosts()));     
            
            // MTS Scope questions
            List<CategorizedQuoteMTSScopeQuestion> categorizedMTSQuestions = QuoteMTSUtil.getCategorizedExistingQuoteMTSScopeQuestions(q, q.getMtsMatrix().getScopeQuestions());
            modelAndView.addObject("categorizedMTSQuestions", categorizedMTSQuestions);

            
            //  MTS Roles
            List<MTSRoleForQuote> mtsRoles = QuoteMTSUtil.getMTSRolesForQuote(q, q.getMtsMatrix().getRoleCosts());
            for (Iterator<MTSRoleForQuote> it = mtsRoles.iterator(); it.hasNext(); ) {
                MTSRoleForQuote role = it.next();
                if (! QuoteMTSUtil.roleIsPresent(role)){
                    it.remove();                
                }
            }
            
            modelAndView.addObject("mtsRoles", mtsRoles);
        }
        */
        
        // Quote products
        if (q.getQuoteProducts() == null) {
            q.setQuoteProducts(service.getProductsForQuote(q));
        }
        
        // Quote notes
        if (q.getQuoteNotes() == null) {
            q.setQuoteNotes(service.getNotesForQuote(q));
        }
        
        // Existing risk analysis items
        if (q.getQuoteRiskAnalysisItems() == null) {
            q.setQuoteRiskAnalysisItems(service.getRiskAnalysisItemsForQuote(q));
        }
        
        List<CategorizedQuoteRiskAnalysis> categorizedRiskAnalysis = 
                QuoteRiskAnalysisUtil.getCategorizedExistingQuoteRiskAnalysisItems(
                        service.getMatrixRiskAnalysisItems(q.getMatrix()), 
                        q.getQuoteRiskAnalysisItems()); 
                
        modelAndView.addObject("categorizedRiskAnalysis", categorizedRiskAnalysis);
        
        
        // Market and Customer quote costs    --  this info is now in the quote    
        //Map<FeeCategoryCO, List<CustomerQuoteItem>>  costs = QuoteCustomerQuoteUtil.getCustomerQuoteCosts(q);
        //modelAndView.addObject("monthlyCosts", costs.get(FeeCategoryCO.MONTHLY));
        //modelAndView.addObject("implCosts", costs.get(FeeCategoryCO.IMPL));
        
        //service.calculateQuoteBudgetaryCosts(q);

        QuoteEntryCustomerQuote quoteForm = new QuoteEntryCustomerQuote();
        QuoteCustomerQuoteUtil.populateCustomerQuoteForm(quoteForm, q);
        
        // Command object
        modelAndView.addObject("command", quoteForm);

       
        modelAndView.setViewName("businessPlan");
        return modelAndView;        
    }    
    
    public static boolean isTrue(Boolean val) {
        return ( (val != null) && (val.booleanValue()) );
    }
    

}
