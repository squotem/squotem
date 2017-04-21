package com.leanlogistics.squotem.webapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.leanlogistics.squotem.co.FeeCategoryCO;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCostAdjustment;
import com.leanlogistics.squotem.webapp.screenobjects.CostAdjustmentForQuote;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryQuote;

public class QuoteCostAdjustmentUtil {
    protected static final String ACTION_DELETE = "delete";
    protected static final String ACTION_ADD = "new";
    
    /** Prepopulate current quote cost adjustments based on current values */
    public static void populateCostAdjustments(QuoteEntryQuote quoteEntryQuote, Quote q) {
        List<QuoteCostAdjustment> currentAdjustments = q.getQuoteCostAdjustments();
        List<CostAdjustmentForQuote> cafqs = new ArrayList<CostAdjustmentForQuote>();
        CostAdjustmentForQuote curEntry = null;
        if (currentAdjustments != null) {
            for (QuoteCostAdjustment qca : currentAdjustments) {
                if (curEntry == null) {
                    curEntry = new CostAdjustmentForQuote();
                    curEntry.setComment(qca.getComment());
                    curEntry.setCostAdjustment(qca.getCostAdjustment());
                    setCostInQuoteEntryCostAdjustment(qca, curEntry);                
                }
                else {
                    setCostInQuoteEntryCostAdjustment(qca, curEntry);
                    // Ready to add at this point...
                    cafqs.add(curEntry);
                    curEntry = null;
                }
            }
        }
        quoteEntryQuote.setCostAdjustments(cafqs);                
    }
    
    private static void setCostInQuoteEntryCostAdjustment(QuoteCostAdjustment qca, CostAdjustmentForQuote cafq) {
        if (qca.getFeeCategory().equals(FeeCategoryCO.IMPL)) {
            cafq.setImplCost(qca.getCost());
            cafq.setImplQuoteCostAdjustment(qca);
        }
        else {
            cafq.setMonthlyCost(qca.getCost());
            cafq.setMonthlyQuoteCostAdjustment(qca);
        }
    }
    
    public static Map<String, List<QuoteCostAdjustment>> processQuoteAdjustmentsChanges(QuoteEntryQuote quoteEntryQuote) {
        List<QuoteCostAdjustment> deleteItems = new ArrayList<QuoteCostAdjustment>();
        List<QuoteCostAdjustment> saveItems = new ArrayList<QuoteCostAdjustment>();
        List<CostAdjustmentForQuote> cafqs = quoteEntryQuote.getCostAdjustments();
        if (cafqs != null ) {
            for (CostAdjustmentForQuote cafq : cafqs) {
                String action = cafq.getAction();
                if ((action != null) && action.equals(ACTION_DELETE) ) {
                    // Get ids to delete
                    String id = cafq.getId();
                    if (id != null) {
                        StringTokenizer st = new StringTokenizer(id, ",");
                        while (st.hasMoreTokens()) {
                            String idToDelete = st.nextToken();
                            QuoteCostAdjustment qca = new QuoteCostAdjustment();
                            qca.setId(Long.valueOf(idToDelete));
                            deleteItems.add(qca);
                        }
                    }
                }
                else if ((action != null) && action.equals(ACTION_ADD) ) {
                    // Save Quote cost adjustment for these
                    if (cafq.getImplQuoteCostAdjustment() == null) {
                        cafq.setImplQuoteCostAdjustment(new QuoteCostAdjustment());
                        cafq.setMonthlyQuoteCostAdjustment(new QuoteCostAdjustment());
                    }
                    
                    QuoteCostAdjustment implQuoteCostAdjustment = cafq.getImplQuoteCostAdjustment();
                    QuoteCostAdjustment monthlyQuoteCostAdjustment = cafq.getMonthlyQuoteCostAdjustment();
                    
                    // Populate values
                    implQuoteCostAdjustment.setComment(cafq.getComment());
                    monthlyQuoteCostAdjustment.setComment(cafq.getComment());
                    implQuoteCostAdjustment.setCost(cafq.getImplCost());
                    monthlyQuoteCostAdjustment.setCost(cafq.getMonthlyCost());
                    implQuoteCostAdjustment.setCostAdjustment(cafq.getCostAdjustment());
                    monthlyQuoteCostAdjustment.setCostAdjustment(cafq.getCostAdjustment());
                    implQuoteCostAdjustment.setFeeCategory(FeeCategoryCO.IMPL);
                    monthlyQuoteCostAdjustment.setFeeCategory(FeeCategoryCO.MONTHLY);
                    
                    saveItems.add(implQuoteCostAdjustment);
                    saveItems.add(monthlyQuoteCostAdjustment);                    
                }
            }            
        }
        
        Map<String, List<QuoteCostAdjustment>> result = new HashMap<String, List<QuoteCostAdjustment>>();
        result.put(QuoteCostItemUtil.DELETE_ITEMS_KEY, deleteItems);
        result.put(QuoteCostItemUtil.SAVE_ITEMS_KEY, saveItems);
        return result;
    }

}
