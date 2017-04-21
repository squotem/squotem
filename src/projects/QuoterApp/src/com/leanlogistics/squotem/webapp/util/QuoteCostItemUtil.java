package com.leanlogistics.squotem.webapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.leanlogistics.squotem.co.FeeCategoryCO;
import com.leanlogistics.squotem.co.SpecialQuoteCostItemCO;
import com.leanlogistics.squotem.model.CostItem;
import com.leanlogistics.squotem.model.Matrix;
import com.leanlogistics.squotem.model.MatrixCostItem;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCostItem;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.service.SquotemService;
import com.leanlogistics.squotem.service.manager.MatrixManager;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteCostItem;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteMetric;
import com.leanlogistics.squotem.webapp.screenobjects.CostItemForQuote;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryQuote;

public class QuoteCostItemUtil {
    public static final String SAVE_ITEMS_KEY = "saveItems";
    public static final String DELETE_ITEMS_KEY = "deleteItems";
    
    public static List<CategorizedQuoteCostItem> getCategorizedExistingQuoteCostItems(Quote q, SquotemService service) {
        // Get categorized cost items, including only those for which there are current values
        return getCategorizedQuoteCostItems(q, service, true, false);        
    }
    
    /** Prepopulate current quote cost items based on matrix cost items and current values */ 
    public static void populateCategorizedQuoteCostItems(QuoteEntryQuote quoteEntryQuote, Quote q, SquotemService service) {
        // Get categorized cost items, including those for which there are no current values
        List<CategorizedQuoteCostItem> cqcis = getCategorizedQuoteCostItems(q, service, false);
        quoteEntryQuote.setCategorizedQuoteCostItems(cqcis);        
    }
    
    public static List<CategorizedQuoteCostItem> getCategorizedQuoteCostItems(Quote q, SquotemService service, boolean includeOnlyCurrentValues) {
        return getCategorizedQuoteCostItems(q, service, includeOnlyCurrentValues, true);
    }
    
    public static List<CategorizedQuoteCostItem> getCategorizedQuoteCostItems(Quote q, SquotemService service, boolean includeOnlyCurrentValues, boolean precalculateMatrixCostItemsCosts) {
        List<CategorizedQuoteCostItem> cqcis = new ArrayList<CategorizedQuoteCostItem>();
        Matrix m = q.getMatrix();
        // List of current QuoteCostItem values
        List<QuoteCostItem> currentValues = q.getQuoteCostItems();
        for (ProductCategory pc : q.getProductCategories()) {
            CategorizedQuoteCostItem cqci = new CategorizedQuoteCostItem();
            cqci.setProductCategory(pc);
            List<CostItemForQuote> cifqs = new ArrayList<CostItemForQuote>(); 
            CostItemForQuote curEntry = null;
            List<MatrixCostItem> costItems = service.getMatrixCostItems(m, pc, q.getQuoteMetrics(), precalculateMatrixCostItemsCosts);
            for (MatrixCostItem mci : costItems) {
                // Assuming related cost item are always paired
                if (curEntry == null) {
                    curEntry = new CostItemForQuote();
                    curEntry.setCostItem(mci.getCostItem());
                    setCostInQuoteEntryCostItem(mci, curEntry);                        
                }
                else {
                    setCostInQuoteEntryCostItem(mci, curEntry);
                    // Do we already have a QuoteCostItem for this entry?
                    QuoteCostItem currentQuoteCostItem = findQuoteCostItem(currentValues, mci.getCostItem(), FeeCategoryCO.IMPL);
                    if (currentQuoteCostItem != null) {
                        // Take enabled and special values from here
                        curEntry.setEnabled(currentQuoteCostItem.getEnabled());
                        if (currentQuoteCostItem.getSpecial() != null) {
                            curEntry.setSpecial(currentQuoteCostItem.getSpecial().getName());                            
                        }
                        curEntry.setImplQuoteCostItem(currentQuoteCostItem);
                        curEntry.setMonthlyQuoteCostItem(findQuoteCostItem(currentValues, mci.getCostItem(), FeeCategoryCO.MONTHLY));
                        
                        if(!precalculateMatrixCostItemsCosts)
                        {
                        	curEntry.setImplCost(curEntry.getImplQuoteCostItem().getCost());
                        	curEntry.setMonthlyCost(curEntry.getMonthlyQuoteCostItem().getCost());
                        }
                        
                    }
                    else {                        
                        if (includeOnlyCurrentValues && ! isTrue(curEntry.getForced())) { // Required cost items are included anyway...
                            curEntry = null;
                        }
                    }

                    // Ready to add at this point...
                    if ( (! includeOnlyCurrentValues) || (curEntry != null) ) {
                        cifqs.add(curEntry);                        
                    }
                    curEntry = null;
                }
            }
            
            cqci.setEntryCostItems(cifqs);
            if ( (! includeOnlyCurrentValues) || (! cqci.getEntryCostItems().isEmpty()) ) {
                cqcis.add(cqci);                
            }
        }
        return cqcis;
    }
    
    public static void processCategorizedQuoteCostItemsErrorMessages(List<CategorizedQuoteCostItem> categorizedQuoteCostItems, List<CategorizedQuoteMetric> categorizedQuoteMetrics)
    {
	    //(["'])(?:(?=(\\?))\2.)*?\1 - Gets a quoted substring from a string 
    	String pattern = "([\"'])(?:(?=(\\\\?))\\2.)*?\\1";
    	List<QuoteMetric> metricList = QuoteMetricUtil.getFlatFullQuoteMetricsList(categorizedQuoteMetrics);
    	
    	for (CategorizedQuoteCostItem cqci :categorizedQuoteCostItems)
    	{
    		for (CostItemForQuote cifq : cqci.getEntryCostItems())
    		{
    			Pattern p = Pattern.compile(pattern);
    			if (cifq.getImplQuoteCostError() != null)
    			{
    				Matcher m = p.matcher(cifq.getImplQuoteCostError());
	    			if (m.find())
	    			{
	    				String mnemonic = m.group().replaceAll("\"", "");
	    				//mnemonic = mnemonic.replaceAll("\"", "");
	    				cifq.setImplQuoteCostErrorDisplay("Missing metric:" + QuoteMetricUtil.findMetricByMnemonic(metricList, mnemonic).getMetric().getDescription());
	    			}
    			}
    			
    			if (cifq.getMonthlyQuoteCostError() != null)
    			{
    				Matcher m = p.matcher(cifq.getMonthlyQuoteCostError());
    				if (m.find())
	    			{
	    				String mnemonic = m.group().replaceAll("\"", "");
	    				//mnemonic.replaceAll("\"", "");
    					cifq.setMonthlyQuoteCostErrorDisplay("Missing metric:" + QuoteMetricUtil.findMetricByMnemonic(metricList, mnemonic).getMetric().getDescription());
	    			}
    			}

    		}
    	}
    }
    
    public static String processFreezeErrorMessage(String freezeErrorMessage, List<CategorizedQuoteMetric> categorizedQuoteMetrics)
    {
	    //(["'])(?:(?=(\\?))\2.)*?\1 - Gets a quoted substring from a string 
    	String pattern = "([\"'])(?:(?=(\\\\?))\\2.)*?\\1";
    	//StringBuilder result = new StringBuilder();
		List<String> errorList = new ArrayList<String>();
    	List<QuoteMetric> metricList = QuoteMetricUtil.getFlatFullQuoteMetricsList(categorizedQuoteMetrics);
    	
		Pattern p = Pattern.compile(pattern);
		if (freezeErrorMessage != null)
		{
			Matcher m = p.matcher(freezeErrorMessage);
			//boolean addComma = false;
			boolean strNotFound;
			String error;
			while (m.find())
			{
				String mnemonic = m.group().replaceAll("\"", "");
				/*if (addComma)
					result.append(", Missing metric:");
				else
					result.append("Missing metric:");
				result.append(QuoteMetricUtil.findMetricByMnemonic(metricList, mnemonic).getMetric().getDescription());
				addComma = true;*/
				
				error = "Missing metric:" + QuoteMetricUtil.findMetricByMnemonic(metricList, mnemonic).getMetric().getDescription();
				
        		strNotFound = true;
				for (String s : errorList)
        		{
        			if (s.equals(error))
        			{
        				strNotFound = false;
        				break;
        			}
        		}
        		if (strNotFound)
        			errorList.add(error);

			}
		}

    	//return result.toString();
		return errorList.toString();
    }
    
    private static void setCostInQuoteEntryCostItem(MatrixCostItem mci, CostItemForQuote qeci) {
        if (mci.getFeeCategory().equals(FeeCategoryCO.IMPL)) {
            qeci.setImplCost(mci.getCalculatedCostForQuote());
            qeci.setImplCostModel(mci.getCostModel());
            qeci.setImplQuoteCostError(mci.getErrorMessage());
        }
        else {
            qeci.setMonthlyCost(mci.getCalculatedCostForQuote());
            qeci.setMonthlyCostModel(mci.getCostModel());
            qeci.setMonthlyQuoteCostError(mci.getErrorMessage());
        }
        // Is this a force YES or force NO MatrixCostItem?
        Boolean isForced = mci.getForced();
        if ( (isForced != null)) {
            qeci.setForced(isForced);
            if (isForced.booleanValue()) {
                qeci.setEnabled(true);
            }
            else {
                qeci.setEnabled(false);
            }
        }
        
        // set alternate color
        String alternateColor = mci.getAlternateColor();
        if ( alternateColor != null)
        {
        	qeci.setAlternateColor(alternateColor);
        }
        
        Boolean isRequired = mci.getRequired();
        if (isRequired == null)
        	qeci.setRequired(false);
        else
        	qeci.setRequired(isRequired);
    }
    
    private static QuoteCostItem findQuoteCostItem(List<QuoteCostItem> list, CostItem costItem, FeeCategoryCO feeCategory) {
        if (list != null)
        {
	    	for (QuoteCostItem quoteCostItem : list) {
	            if (quoteCostItem.getCostItem().getId().equals(costItem.getId()) && quoteCostItem.getFeeCategory().equals(feeCategory)) {
	                return quoteCostItem;
	            }
	        }
        }
        return null;
    }
    
    public static  Map<String, List<QuoteCostItem>> processQuoteItemsChanges(List<CategorizedQuoteCostItem> categorizedQuoteCostItems) {
        List<QuoteCostItem> deleteItems = new ArrayList<QuoteCostItem>();
        List<QuoteCostItem> saveItems = new ArrayList<QuoteCostItem>();
        
        for (CategorizedQuoteCostItem cqci : categorizedQuoteCostItems) {
            for (CostItemForQuote cifq : cqci.getEntryCostItems()) {
                Boolean enabled = cifq.getEnabled();
                if (enabled == null) {
                    // If enabled is now null and there was previously saved entries for this, we need to delete them
                    if (cifq.getImplQuoteCostItem() != null) {
                        QuoteCostItem implQuoteCostItem = cifq.getImplQuoteCostItem();
                        QuoteCostItem monthlyQuoteCostItem = cifq.getMonthlyQuoteCostItem();

                        implQuoteCostItem.setCostItem(cifq.getCostItem());
                        monthlyQuoteCostItem.setCostItem(cifq.getCostItem());
                        
                        implQuoteCostItem.setCostModel(cifq.getImplCostModel());
                        monthlyQuoteCostItem.setCostModel(cifq.getMonthlyCostModel());
                        
                        implQuoteCostItem.setFeeCategory(FeeCategoryCO.IMPL);
                        monthlyQuoteCostItem.setFeeCategory(FeeCategoryCO.MONTHLY);

                        deleteItems.add(implQuoteCostItem);
                        deleteItems.add(monthlyQuoteCostItem);
                    }
                }
                else {
                    // Exclude required cost items. We don't save these as explicit entries in DB at this time, only
                    // on final states (APPROVED, CANCELLED)
                    if (! isTrue(cifq.getForced())) {
                        // Since we have a setting for enabled, save QuoteCostItems for this
                        if (cifq.getImplQuoteCostItem() == null) {
                            cifq.setImplQuoteCostItem(new QuoteCostItem());
                        }
                        if (cifq.getMonthlyQuoteCostItem() == null) {
                            cifq.setMonthlyQuoteCostItem(new QuoteCostItem());                        
                        }
                        QuoteCostItem implQuoteCostItem = cifq.getImplQuoteCostItem();
                        QuoteCostItem monthlyQuoteCostItem = cifq.getMonthlyQuoteCostItem();
                        
                        // Populate values
                        implQuoteCostItem.setCostItem(cifq.getCostItem());
                        monthlyQuoteCostItem.setCostItem(cifq.getCostItem());
                        
                        implQuoteCostItem.setCostModel(cifq.getImplCostModel());
                        monthlyQuoteCostItem.setCostModel(cifq.getMonthlyCostModel());
                        
                        if ((cifq.getSpecial() != null) && (!cifq.getSpecial().isEmpty() ) ) {
                            implQuoteCostItem.setSpecial(SpecialQuoteCostItemCO.valueOf(cifq.getSpecial()));
                            monthlyQuoteCostItem.setSpecial(SpecialQuoteCostItemCO.valueOf(cifq.getSpecial()));                        
                        }
                        implQuoteCostItem.setFeeCategory(FeeCategoryCO.IMPL);
                        monthlyQuoteCostItem.setFeeCategory(FeeCategoryCO.MONTHLY);
                        implQuoteCostItem.setEnabled(enabled);
                        monthlyQuoteCostItem.setEnabled(enabled);
                        // Costs should not be saved at this point                        
                        implQuoteCostItem.setCost(null);
                        monthlyQuoteCostItem.setCost(null);
                        saveItems.add(implQuoteCostItem);
                        saveItems.add(monthlyQuoteCostItem);                        
                    }                    
                }
            }
        }
        
        Map<String, List<QuoteCostItem>> result = new HashMap<String, List<QuoteCostItem>>();
        result.put(DELETE_ITEMS_KEY, deleteItems);
        result.put(SAVE_ITEMS_KEY, saveItems);
        return result;
    }
    
    public static boolean isTrue(Boolean val) {
        return ( (val != null) && (val.booleanValue()) );
    }
    
    public static void updateQuoteCostItemValues(List<CategorizedQuoteCostItem> quoteCostItemsDest, List<CategorizedQuoteCostItem> quoteCostItemsValue){
    	for (CategorizedQuoteCostItem cqciv : quoteCostItemsValue)
    	{
    		for (CostItemForQuote cifqv : cqciv.getEntryCostItems())
    		{
    			for (CategorizedQuoteCostItem cqcid : quoteCostItemsDest)
    			{
    				for (CostItemForQuote cifqd : cqcid.getEntryCostItems() )
    				{
    	    			if(cifqv.getCostItem().getId().equals(cifqd.getCostItem().getId()))
    	    			{
    	    				cifqd.setEnabled(cifqv.getEnabled());
    	    				break;
    	    			}
    				}
    			}
    		}
    	}
    }
    
    public static void updateQuoteCostItemListIds(List<QuoteCostItem> quoteCostItemDest, List<QuoteCostItem> quoteCostItemOrigin)
    {
    	if (quoteCostItemOrigin != null)
    	{
    		for (QuoteCostItem qcio : quoteCostItemOrigin)
    		{
    			for (QuoteCostItem qcid : quoteCostItemDest)
    			{
    				if (qcio.getCostItem().getId().equals(qcid.getCostItem().getId()) && qcio.getFeeCategory().equals(qcid.getFeeCategory()))
    				{
    					qcid.setId(qcio.getId());
    					
    					break;
    				}
    			}
    		}
    	}
    }
    
    public static void updateQuoteCostItemListCosts(List<QuoteCostItem> quoteCostItemDest, List<CategorizedQuoteCostItem> categorizedQuoteCostItemOrigin)
    {
    	if (categorizedQuoteCostItemOrigin != null && quoteCostItemDest != null)
    	{
    		for (QuoteCostItem qci : quoteCostItemDest)
    		{
    			for (CategorizedQuoteCostItem cqci : categorizedQuoteCostItemOrigin)
    			{
    				for (CostItemForQuote cifq : cqci.getEntryCostItems())
    				{
    					if (cifq.getCostItem().getId().equals(qci.getCostItem().getId()))
    					{
    						if (qci.getFeeCategory().equals(FeeCategoryCO.IMPL))
    						{	
    							if (cifq.getImplCost() != null && !cifq.getImplCost().isNaN())
    								qci.setCost(cifq.getImplCost());
    							//else I could just set to 0 and recalculate later
    						}
    						else
    						{
    							if (cifq.getMonthlyCost() != null && !cifq.getMonthlyCost().isNaN())
    								qci.setCost(cifq.getMonthlyCost());
    							//else I could just set to 0 and recalculate later
    						}
    						break;
    					}
    				}
    			}
    		}
    	}
    }


}
