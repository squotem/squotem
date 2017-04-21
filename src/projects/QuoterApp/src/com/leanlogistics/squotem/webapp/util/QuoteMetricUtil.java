package com.leanlogistics.squotem.webapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leanlogistics.squotem.co.MetricDataTypeCO;
import com.leanlogistics.squotem.co.MetricDisplayOptionTypeCO;
import com.leanlogistics.squotem.model.MatrixMetric;
import com.leanlogistics.squotem.model.Metric;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteMetric;


public class QuoteMetricUtil {
    
    public static List<CategorizedQuoteMetric> getCategorizedExistingQuoteMetrics(List<MatrixMetric> metrics, List<QuoteMetric> currentValues) {
        // Get categorized metrics, including only those for which there are current values
        return getCategorizedQuoteMetrics(metrics, currentValues, true);        
    }
        
    public static List<CategorizedQuoteMetric> getCategorizedQuoteMetrics(List<MatrixMetric> metrics, List<QuoteMetric> currentValues, boolean includeOnlyCurrentValues) {
        List<CategorizedQuoteMetric> categorizedQuoteMetrics = new ArrayList<CategorizedQuoteMetric>();
        CategorizedQuoteMetric cqm = null;       
        for(MatrixMetric matrixMetric : metrics) {
            if ((cqm == null) || (!matrixMetric.getMetric().getCategory().getId().equals(cqm.getId()))) {
                // Change on category, save the previous category
                if (cqm != null) {
                    if ( (! includeOnlyCurrentValues ) || ( ! cqm.getQuoteMetrics().isEmpty())) {
                        categorizedQuoteMetrics.add(cqm);
                    }
                }
                // Create the new category
                cqm = new CategorizedQuoteMetric();
                cqm.setId(matrixMetric.getMetric().getCategory().getId());
                cqm.setDescription(matrixMetric.getMetric().getCategory().getDescription());
                cqm.setOptionType(matrixMetric.getMetric().getCategory().getOptionType());
                cqm.setQuoteMetrics(new ArrayList<QuoteMetric>());
            }

            QuoteMetric qm = findMetric(currentValues, matrixMetric.getMetric());
            if ( (qm == null) && (! includeOnlyCurrentValues) ) {
                qm = new QuoteMetric();
                qm.setMetric(matrixMetric.getMetric());
                // Default value
                qm.setMetricValue(matrixMetric.getDefaultValue());
                qm.setBooleanValue(matrixMetric.getDefaultBooleanValue());
                Boolean required = matrixMetric.getRequired();
                if (required == null) {
                    required = false;
                }
                qm.setRequired(required);
                    
             }
            
             if ( (! includeOnlyCurrentValues ) || (qm != null)) { 
               cqm.getQuoteMetrics().add(qm);
             }
        }
        // Add last category
        if (cqm != null) {
            if ( (! includeOnlyCurrentValues ) || ( ! cqm.getQuoteMetrics().isEmpty())) {
                categorizedQuoteMetrics.add(cqm);
            }
        }
        return categorizedQuoteMetrics;
    }
    
        
    protected static QuoteMetric findMetric(List<QuoteMetric> quoteMetrics, Metric m) {
        if (quoteMetrics != null) {
            for (QuoteMetric qm : quoteMetrics) {
                if (qm.getMetric().getId().equals(m.getId())) {
                    qm.setMetric(m);
                    return qm;
                }
            }
        }
        return null;
    }
    
    protected static QuoteMetric findMetricByMnemonic(List<QuoteMetric> quoteMetrics, String mnemonic) {
        QuoteMetric metric = null;
    	if (quoteMetrics != null) {
            for (QuoteMetric qm : quoteMetrics) {
                if (qm.getMetric().getMnemonic().equals(mnemonic)) {
                    metric = qm;
                    return metric;
                }
            }
        }
        return null;
    }
    
    public static Map<String,List<QuoteMetric>> processQuoteMetricsChanges(List<CategorizedQuoteMetric> quoteMetrics) {
        // Preprocess to calculate totals
        updateMetricTotals(quoteMetrics);
        
        // Flatten quoteMetrics and determine metrics to delete or update        
        List<QuoteMetric> saveItems = new ArrayList<QuoteMetric>();
        List<QuoteMetric> deleteItems = new ArrayList<QuoteMetric>();
        if (quoteMetrics !=  null) {
            for (CategorizedQuoteMetric cqm : quoteMetrics) {
                for (QuoteMetric qm : cqm.getQuoteMetrics()) {
                	if (qm.getMetric().getDataType().equals(MetricDataTypeCO.BOOL_PLUS_NUMERIC) || qm.getMetric().getDataType().equals(MetricDataTypeCO.BOOL_PLUS_STRING))
                	{
	                    if ( (qm.getId() != null) &&
		                      (qm.getBooleanValue() == null)) {
		                        deleteItems.add(qm);
		                }
		                else if (qm.getBooleanValue() != null) {
		                        saveItems.add(qm);                    
		                }                
                	}
                	else
                	{
	                    if ( (emptyOrNull(qm.getMetricValue()) &&  
	                         (qm.getId() != null))) {
	                        deleteItems.add(qm);
	                    }
	                    else if (notEmptyOrNull(qm.getMetricValue()) ) {
	                        saveItems.add(qm);                    
	                    }                
                	}
                }
            }
        }
        Map<String,List<QuoteMetric>> result = new HashMap<String,List<QuoteMetric>>();
        result.put(QuoteCostItemUtil.DELETE_ITEMS_KEY, deleteItems);
        result.put(QuoteCostItemUtil.SAVE_ITEMS_KEY, saveItems);
        return result;        
    }
    
    // Pre-process metrics in order to calculate metric totals and, if necessary, delete some metric total values
    protected static void updateMetricTotals(List<CategorizedQuoteMetric> quoteMetrics) {
        if (quoteMetrics == null) { 
            return;
        }
        
        for (CategorizedQuoteMetric cqm : quoteMetrics) {
            if (cqm.getOptionType().equals(MetricDisplayOptionTypeCO.TOTAL) || 
                    cqm.getOptionType().equals(MetricDisplayOptionTypeCO.TOTAL_AND_PCT)) {
                boolean hasTotal = false;
                double total = 0.0;
                for (QuoteMetric qm : cqm.getQuoteMetrics()) {
                    if (! qm.getMetric().getIsTotal() ) {
                        // Regular metric
                        String metricVal = qm.getMetricValue();
                        if (notEmptyOrNull(metricVal)) {
                            hasTotal = true;
                            total += Double.valueOf(metricVal);
                        }                                            
                    }
                    else {
                        // Total metric
                        if (hasTotal) {
                            qm.setMetricValue(String.valueOf(total));
                        }
                        else {
                            // Set value to null, so it get's removed if necessary
                            qm.setMetricValue(null);
                        }
                    }
                }                
            }
        }
        
    }
    
    
    public static List<QuoteMetric> getFlatQuoteMetricsList(List<CategorizedQuoteMetric> quoteMetrics) {
        // Pre-process to calculate totals
        updateMetricTotals(quoteMetrics);
        
        List<QuoteMetric> result = new ArrayList<QuoteMetric>();
        for (CategorizedQuoteMetric cqm : quoteMetrics) {
            for (QuoteMetric qm : cqm.getQuoteMetrics()) {
                if (qm.getMetricValue() != null) {
                    result.add(qm);
                }
            }
        }
        return result;
    }
    
    public static List<QuoteMetric> getFlatFullQuoteMetricsList(List<CategorizedQuoteMetric> quoteMetrics) {
        // Pre-process to calculate totals
        updateMetricTotals(quoteMetrics);
        
        List<QuoteMetric> result = new ArrayList<QuoteMetric>();
        for (CategorizedQuoteMetric cqm : quoteMetrics) {
            for (QuoteMetric qm : cqm.getQuoteMetrics()) {
                result.add(qm);
            }
        }
        return result;
    }
    
    public static boolean notEmptyOrNull(String val) {
        return (val != null) && (! val.trim().isEmpty());
    }
    
    public static boolean emptyOrNull(String val) {
        return (val == null) || (val.trim().isEmpty());
    }
    
    // to update quoteMetricDest with the values in quoteMetricsValues
    public static void updateQuoteMetricValues(List<CategorizedQuoteMetric> quoteMetricsDest, List<CategorizedQuoteMetric> quoteMetricsValues){
    	if (quoteMetricsValues != null)
    	{
	        for (CategorizedQuoteMetric cqmv : quoteMetricsValues) 
	        {
	        	for (QuoteMetric qmv: cqmv.getQuoteMetrics())
	        	{
		            for (CategorizedQuoteMetric cqmd : quoteMetricsDest) 
		            {
	            		for (QuoteMetric qmd: cqmd.getQuoteMetrics())
	            		{
		            		if (qmv.getMetric().getId().equals(qmd.getMetric().getId()))
		            		{
		            			qmd.setBooleanValue(qmv.getBooleanValue());
		            			
			                    if ( emptyOrNull(qmv.getMetricValue() ) )
			                    	qmd.setMetricValue(null);
			                    else
			                    	qmd.setMetricValue(qmv.getMetricValue());
		            			break;
		            		}
	            		}
		            	
		            }
	        	}
	        }
    	}
    }
    
    // add new values to the list, if equal then ignore
    public static void updateQuoteMetricLists(List<QuoteMetric> quoteMetricDest, List<QuoteMetric> quoteMetricOrigin)
    {
		boolean found = false;
    	if (quoteMetricOrigin != null)
    	{
    		for (QuoteMetric qmo : quoteMetricOrigin)
    		{
    			for (QuoteMetric qmd : quoteMetricDest)
    			{
    				if (qmo.getMetric().getId().equals(qmd.getMetric().getId()))
    				{
    					// ignore them if they are equal
    					/*qmd.setBooleanValue(qmo.getBooleanValue());
    					qmd.setMetricValue(qmo.getMetricValue());*/
    					found = true;
    					break;
    				}
    			}
    			if (found)
    				found = false;
    			else
    				quoteMetricDest.add(qmo);
    		}
    	}
    }
    
    public static void updateQuoteMetricListIds(List<QuoteMetric> quoteMetricListDest, List<QuoteMetric> quoteMetricListOrigin)
    {
    	if (quoteMetricListOrigin != null)
    	{
    		for (QuoteMetric qmo : quoteMetricListOrigin)
    		{
    			for (QuoteMetric qmd : quoteMetricListDest)
    			{
    				if (qmo.getMetric().getId().equals(qmd.getMetric().getId()))
    				{
    					qmd.setId(qmo.getId());
    					
    					break;
    				}
    			}
    		}
    	}
    }
    
}
