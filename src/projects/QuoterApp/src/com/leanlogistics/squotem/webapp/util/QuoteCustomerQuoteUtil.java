package com.leanlogistics.squotem.webapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leanlogistics.squotem.co.FeeCategoryCO;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCosts;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.model.QuoteProduct;
import com.leanlogistics.squotem.model.QuoteSubtotal;
import com.leanlogistics.squotem.webapp.screenobjects.CustomerQuoteItem;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryCustomerQuote;

public class QuoteCustomerQuoteUtil {
    
    public final static Long MTS_ID = -1l;
    
    public static void populateCustomerQuoteForm(QuoteEntryCustomerQuote form, Quote q) {
        Map<FeeCategoryCO, List<CustomerQuoteItem>>  costs = getCustomerQuoteCosts(q);
        form.setMonthlyCosts(costs.get(FeeCategoryCO.MONTHLY));
        form.setImplCosts(costs.get(FeeCategoryCO.IMPL));
        form.setQuoteMetrics(getQuoteMetricsMap(q));
        
        //budgetary
        form.setBudgetaryImplCost(q.getBudgetaryImplCost());
        form.setBudgetaryImplCostError(q.getBudgetaryImplCostError());
        form.setBudgetaryMonthlyCost(q.getBudgetaryMonthlyCost());
        form.setBudgetaryMonthlyCostError(q.getBudgetaryMonthlyCostError());
        
        //market
        form.setMarketImplCost(q.getMarketImplCost());
        form.setMarketMonthlyCost(q.getMarketMonthlyCost());
        
        // terms
        form.setTerms(q.getTerms());
        
    }   
    
    // Creates a map based on Quote Metrics
    public static Map<String,String> getQuoteMetricsMap(Quote q) {
        Map<String, String> result = new HashMap<String,String>();
        List<QuoteMetric> metrics = q.getQuoteMetrics();
        if (metrics != null) {
            for (QuoteMetric m : metrics) {
                result.put(m.getMetric().getMnemonic(), m.getMetricValue());
            }
        }
        return result;        
    }
    
    
    public static Map<FeeCategoryCO, List<CustomerQuoteItem>> getCustomerQuoteCosts(Quote q) {
        Map<FeeCategoryCO, List<CustomerQuoteItem>> result = new HashMap<FeeCategoryCO, List<CustomerQuoteItem>>();
        List<CustomerQuoteItem> monthlyCosts = new ArrayList<CustomerQuoteItem>();
        List<CustomerQuoteItem> implCosts = new ArrayList<CustomerQuoteItem>();
        
        // Fill up regular product categories
        QuoteCosts qCosts = q.getQuoteCosts();
        for (QuoteSubtotal subtotal : qCosts.getSubtotals()) {
            CustomerQuoteItem monthlyItem = createCustomerQuoteItemFromSubtotal(subtotal);
            CustomerQuoteItem implItem = createCustomerQuoteItemFromSubtotal(subtotal);
            
            // Market Quote
            Double marketQuoteMonthly = subtotal.getSubtotals().get(FeeCategoryCO.MONTHLY.getName());
            Double marketQuoteImpl = subtotal.getSubtotals().get(FeeCategoryCO.IMPL.getName());
            monthlyItem.setMarketQuote(marketQuoteMonthly);
            implItem.setMarketQuote(marketQuoteImpl);

            QuoteProduct qp = findQuoteProduct(q.getQuoteProducts(), subtotal.getProductCategory());            
            // Customer Quote, same as market quote if not defined yet
            
            Double custQuoteMonthly = qp.getCustomerQuoteMonthly();
            if (custQuoteMonthly == null) {
                if (! marketQuoteMonthly.isNaN()) {
                    custQuoteMonthly = marketQuoteMonthly;                    
                }
            }
            Double custQuoteImpl = qp.getCustomerQuoteImpl();
            if (custQuoteImpl == null) {
                if (! marketQuoteImpl.isNaN()) {
                    custQuoteImpl = marketQuoteImpl;                    
                }
            }
            
            monthlyItem.setCustomerQuote(custQuoteMonthly);
            monthlyItem.setId(qp.getId());
            implItem.setCustomerQuote(custQuoteImpl);
            implItem.setId(qp.getId());
            
            monthlyCosts.add(monthlyItem);
            implCosts.add(implItem);
        }
        
        /* MTS disabled for now
        
        // If present add MTS
        if (QuoteCostItemUtil.isTrue(q.getHasMts())) {
            CustomerQuoteItem mtsMonthlyItem = createMTSCustomerQuoteItem();
            CustomerQuoteItem mtsImplItem = createMTSCustomerQuoteItem();
            QuoteMTSCosts quoteMtsCosts = q.getQuoteMTSCosts();
            
            mtsMonthlyItem.setMarketQuote(quoteMtsCosts.getMonthlyTotal());
            // Customer Quote, same as market quote if not defined yet
            Double custQuoteMonthly = q.getMtsCustomerQuoteMonthly();
            if (custQuoteMonthly == null) {
                custQuoteMonthly = quoteMtsCosts.getMonthlyTotal();
            }
            mtsMonthlyItem.setCustomerQuote(custQuoteMonthly);
            
            // Market Impl costs for MTS is always 0
            mtsImplItem.setMarketQuote(0d);
            Double custQuoteImpl = q.getMtsCustomerQuoteImpl();
            if (custQuoteImpl == null) {
                custQuoteImpl = 0d;
            }
            mtsImplItem.setCustomerQuote(custQuoteImpl);
            
            monthlyCosts.add(mtsMonthlyItem);
            implCosts.add(mtsImplItem);            
        }
        */
        result.put(FeeCategoryCO.MONTHLY, monthlyCosts);
        result.put(FeeCategoryCO.IMPL, implCosts);        
        
        return result;       
    }
    
    protected static CustomerQuoteItem createCustomerQuoteItemFromSubtotal(QuoteSubtotal qs) {
        CustomerQuoteItem result = new CustomerQuoteItem();
        result.setProductCategory(qs.getProductCategory());
        return result;        
    }
    
    protected static QuoteProduct findQuoteProduct(List<QuoteProduct> quoteProducts, ProductCategory pc) {
        for (QuoteProduct qp : quoteProducts) {
            if (qp.getProductCategory().getId().equals(pc.getId())) {
                return qp;
            }
        }
        return null;
    }
    
    protected static CustomerQuoteItem createMTSCustomerQuoteItem() {
        CustomerQuoteItem result = new CustomerQuoteItem();
        result.setId(MTS_ID);
        // Virtual product category
        ProductCategory mtsProductCategory = new ProductCategory();
        mtsProductCategory.setId(MTS_ID);
        mtsProductCategory.setDescription("MTS");
        result.setProductCategory(mtsProductCategory);
        return result;
    }
    
    public static List<QuoteProduct> processQuoteProductsChanges(QuoteEntryCustomerQuote form) {
        List<QuoteProduct> result = new ArrayList<QuoteProduct>();
        List<CustomerQuoteItem> monthlyCosts = form.getMonthlyCosts();
        List<CustomerQuoteItem> implCosts = form.getImplCosts();
                
        
        // Iterate over both list at once in order to build QuoteProduct list
        for (int i = 0; i < monthlyCosts.size(); i++) {
            CustomerQuoteItem monthlyItem = monthlyCosts.get(i);
            CustomerQuoteItem implItem = implCosts.get(i);
            QuoteProduct qp = new QuoteProduct();
            qp.setId(monthlyItem.getId());
            qp.setProductCategory(monthlyItem.getProductCategory());
            qp.setCustomerQuoteImpl(implItem.getCustomerQuote());
            qp.setCustomerQuoteMonthly(monthlyItem.getCustomerQuote());
            result.add(qp);
        }
        
        return result;        
    }

}
