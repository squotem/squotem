package com.leanlogistics.squotem.webapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.leanlogistics.squotem.model.MatrixRiskAnalysis;
import com.leanlogistics.squotem.model.QuoteRiskAnalysis;
import com.leanlogistics.squotem.model.RiskAnalysis;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteRiskAnalysis;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryRiskAnalysis;

public class QuoteRiskAnalysisUtil {
    
    public static List<CategorizedQuoteRiskAnalysis> getCategorizedExistingQuoteRiskAnalysisItems(List<MatrixRiskAnalysis> riskAnalysisItems, List<QuoteRiskAnalysis> currentItems) {
        // Get categorized risk analysis items, including only those for which there are current values
        return getCategorizedRiskAnalysisItems(riskAnalysisItems, currentItems, true);
    }
    
    
    public static void populateCategorizedQuoteRiskAnalysisItems(QuoteEntryRiskAnalysis quoteEntryRiskAnalysis, List<MatrixRiskAnalysis> riskAnalysisItems, List<QuoteRiskAnalysis> currenItems) {
        // Get categorized risk analysis items, including those for which there are no current values
        List<CategorizedQuoteRiskAnalysis> categorizedItems = getCategorizedRiskAnalysisItems(riskAnalysisItems, currenItems, false);            
        quoteEntryRiskAnalysis.setCategorizedQuoteRiskAnalysisItems(categorizedItems);        
    }
    
    public static List<CategorizedQuoteRiskAnalysis> getCategorizedRiskAnalysisItems(List<MatrixRiskAnalysis> riskAnalysisItems, List<QuoteRiskAnalysis> currenItems, boolean includeOnlyCurrentValues) {
        List<CategorizedQuoteRiskAnalysis> categorizedQuoteRiskAnalysisItems = new ArrayList<CategorizedQuoteRiskAnalysis>();
        CategorizedQuoteRiskAnalysis  cra = null;
        for (MatrixRiskAnalysis matrixRiskAnalysis : riskAnalysisItems) {
            if ((cra == null) || (!matrixRiskAnalysis.getRiskAnalysis().getRiskCategory().getId().equals(cra.getId()))) {
             // Change on category, save the previous category
                if (cra != null) {
                    if ( (! includeOnlyCurrentValues ) || ( ! cra.getQuoteRiskAnalysisItems().isEmpty())) {
                        categorizedQuoteRiskAnalysisItems.add(cra);
                    }
                }
                // Create the new category
                cra = new CategorizedQuoteRiskAnalysis();
                cra.setId(matrixRiskAnalysis.getRiskAnalysis().getRiskCategory().getId());
                cra.setDescription(matrixRiskAnalysis.getRiskAnalysis().getRiskCategory().getDescription());
                cra.setQuoteRiskAnalysisItems(new ArrayList<QuoteRiskAnalysis>());
            }
            
            QuoteRiskAnalysis qra = findRiskAnalysis(currenItems, matrixRiskAnalysis.getRiskAnalysis());
            if ( (qra == null) && (! includeOnlyCurrentValues) ) {
                qra = new QuoteRiskAnalysis();
                qra.setRiskAnalysis(matrixRiskAnalysis.getRiskAnalysis());                    
             }
            
            if ( (! includeOnlyCurrentValues ) || (qra != null)) { 
                cra.getQuoteRiskAnalysisItems().add(qra);
              }
            
        }
        // Add last category
        if (cra != null) {
            if ( (! includeOnlyCurrentValues ) || ( ! cra.getQuoteRiskAnalysisItems().isEmpty())) {
                categorizedQuoteRiskAnalysisItems.add(cra);
            }
        }
        return categorizedQuoteRiskAnalysisItems;
        
    }
    
    protected static QuoteRiskAnalysis findRiskAnalysis(List<QuoteRiskAnalysis> quoteRiskAnalysisItems, RiskAnalysis ra) {
        if (quoteRiskAnalysisItems != null) {
            for (QuoteRiskAnalysis qra : quoteRiskAnalysisItems) {
                if (qra.getRiskAnalysis().getId().equals(ra.getId())) {
                    qra.setRiskAnalysis(ra);
                    return qra;
                }
            }
        }
        return null;
    }
    
    public static Map<String,List<QuoteRiskAnalysis>> processQuoteRiskAnalysisChanges(QuoteEntryRiskAnalysis riskData) {
        
        // Flatten quoteRiskAnalysis items and determine items to delete or update        
        List<QuoteRiskAnalysis> saveItems = new ArrayList<QuoteRiskAnalysis>();
        List<QuoteRiskAnalysis> deleteItems = new ArrayList<QuoteRiskAnalysis>();
        for (CategorizedQuoteRiskAnalysis cra : riskData.getCategorizedQuoteRiskAnalysisItems()) {
            for (QuoteRiskAnalysis qra : cra.getQuoteRiskAnalysisItems()) {                
                if ( (qra.getId() != null) &&
                     (qra.getEnabled() == null)) {
                    deleteItems.add(qra);
                }
                else if ( qra.getEnabled() != null) {
                    saveItems.add(qra);                    
                }                
            }
        }
        Map<String,List<QuoteRiskAnalysis>> result = new HashMap<String,List<QuoteRiskAnalysis>>();
        result.put(QuoteCostItemUtil.DELETE_ITEMS_KEY, deleteItems);
        result.put(QuoteCostItemUtil.SAVE_ITEMS_KEY, saveItems);
        return result;        
    }
    

}
