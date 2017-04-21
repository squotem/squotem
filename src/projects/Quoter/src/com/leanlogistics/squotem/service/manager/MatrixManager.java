package com.leanlogistics.squotem.service.manager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.leanlogistics.squotem.co.FeeCategoryCO;
import com.leanlogistics.squotem.co.MetricDataTypeCO;
import com.leanlogistics.squotem.co.MetricGroupCO;
import com.leanlogistics.squotem.costmodelutils.CostModelEvaluationResult;
import com.leanlogistics.squotem.costmodelutils.CostModelEvaluator;
import com.leanlogistics.squotem.costmodelutils.CostModelParser;
import com.leanlogistics.squotem.costmodelutils.CostModelUtils;
import com.leanlogistics.squotem.model.CostModel;
import com.leanlogistics.squotem.model.Matrix;
import com.leanlogistics.squotem.model.MatrixCostAdjustment;
import com.leanlogistics.squotem.model.MatrixCostItem;
import com.leanlogistics.squotem.model.MatrixMetric;
import com.leanlogistics.squotem.model.MatrixRiskAnalysis;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCostAdjustment;
import com.leanlogistics.squotem.model.QuoteCostItem;
import com.leanlogistics.squotem.model.QuoteCosts;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.model.QuoteSubtotal;
import com.leanlogistics.squotem.service.dao.MatrixDao;

public class MatrixManager extends BaseManager {

    private MatrixDao matrixDao;
    
    public MatrixManager() {
        matrixDao = new MatrixDao();        
    }
    
    public Matrix getMatrix(long id) {
        return matrixDao.getMatrix(hibernateSession, id); 
    }
    
    public List<Matrix> getMatrices(boolean forAdmin) {
        return matrixDao.getMatrices(hibernateSession, forAdmin); 
    }
    
    public Matrix getCurrentMatrix() {
        return matrixDao.getCurrentMatrix(hibernateSession);
    }
    
    public List<MatrixCostItem> getMatrixCostItems(Matrix m, ProductCategory pc) {
        return matrixDao.getMatrixCostItems(hibernateSession, m, pc);        
    }
    
    public List<MatrixCostAdjustment> getMatrixCostAdjustments(Matrix m, ProductCategory pc) {
        return matrixDao.getMatrixCostAdjustments(hibernateSession, m, pc);
    }
    
    public List<MatrixMetric> getMatrixMetrics(Matrix m) {
        return getMatrixMetrics(m, null);
    }
    
    public List<MatrixMetric> getMatrixMetrics(Matrix m, MetricGroupCO group) {
        List<MatrixMetric> result = (group != null? matrixDao.getMatrixMetrics(hibernateSession, m, group) : 
                                                    matrixDao.getMatrixMetrics(hibernateSession, m));
        if (result != null) {
            for (MatrixMetric mm : result) {
                processMatrixMetricForDisplay(mm);
            }
        }
        return result;
    }        
    
    protected void processMatrixMetricForDisplay(MatrixMetric mm) {
        // format conversion for combo metrics
        if ( MetricDataTypeCO.BOOL_PLUS_STRING.equals(mm.getMetric().getDataType()) ||
                MetricDataTypeCO.BOOL_PLUS_NUMERIC.equals(mm.getMetric().getDataType())) {
            String value = mm.getDefaultValue();
            if (value != null) {
                StringTokenizer st = new StringTokenizer(value, QuoteManager.COMBO_METRIC_SEPARATOR);
                Boolean booleanVal = Boolean.valueOf(st.nextToken());
                String metricVal = null;
                if (QuoteManager.isTrue(booleanVal)) {
                    metricVal = st.nextToken();
                }
                mm.setDefaultBooleanValue(booleanVal);
                mm.setDefaultValue(metricVal);
            }                    
        }                        
    }
    
    
    public void precalculateMatrixCostItemCosts(List<QuoteMetric> metrics, List<MatrixCostItem> matrixCostItems) {
        for (MatrixCostItem mci : matrixCostItems) {
            CostModel cm = mci.getCostModel();
            if ( (cm == null) || (cm.getXmlContent() == null)) {
                // This item doesn't use a cost model, or it is a (flat) cost model without xml rules
                // Use simple cost...
                mci.setCalculatedCostForQuote(mci.getSimpleCost());
            }
            else {
                if (cm.getCostModelObject() == null) {
                    CostModelParser.setCostModelRulesInCostModel(cm);
                }
                if (cm.getCostModelObject() != null) {
                    CostModelEvaluationResult cmer = CostModelEvaluator.evaluateCostModel(cm.getCostModelObject(), metrics); 
                    mci.setCalculatedCostForQuote(cmer.getValue());
                    mci.setErrorMessage(cmer.getError());
                }
                else {
                    mci.setCalculatedCostForQuote(Double.NaN);
                    mci.setErrorMessage("XML error: " + cm.getErrorMessage());
                }
            }
        }
    }
    
    public void precalculateBudgetaryCosts(Quote q) {
        Matrix m = q.getMatrix();
        // Implementation
        CostModel cm = m.getBudgetaryImplCostModel();
        if (cm != null) {
            if (cm.getCostModelObject() == null) {
                CostModelParser.setCostModelRulesInCostModel(cm);
            }
            if (cm.getCostModelObject() != null) {
                CostModelEvaluationResult cmer = CostModelEvaluator.evaluateCostModel(cm.getCostModelObject(), q.getQuoteMetrics());
                q.setBudgetaryImplCost(cmer.getValue());
                q.setBudgetaryImplCostError(cmer.getError());
            }
            else {
                q.setBudgetaryImplCost(Double.NaN);
                q.setBudgetaryImplCostError("XML error: " + cm.getErrorMessage());
            }
                
        }
        // Monthly
        cm = m.getBudgetaryMonthlyCostMode();
        if (cm != null) {
            if (cm.getCostModelObject() == null) {
                CostModelParser.setCostModelRulesInCostModel(cm);
            }
            if (cm.getCostModelObject() != null) {            
                CostModelEvaluationResult cmer = CostModelEvaluator.evaluateCostModel(cm.getCostModelObject(), q.getQuoteMetrics());
                q.setBudgetaryMonthlyCost(cmer.getValue());
                q.setBudgetaryMonthlyCostError(cmer.getError());
            }
            else {
                q.setBudgetaryMonthlyCost(Double.NaN);
                q.setBudgetaryMonthlyCostError("XML error: " + cm.getErrorMessage());                
            }
        }        
   }
    
        
    public QuoteCosts calculateQuoteCosts(Matrix m, List<QuoteCostItem> quoteCostItems,
            List<QuoteCostAdjustment> quoteCostAdjustments,
            List<QuoteMetric> quoteMetrics,
            List<ProductCategory> productCategories) {
                
        QuoteCosts result = CostModelUtils.createZeroQuoteCosts(productCategories);
        List<QuoteSubtotal> subtotals = result.getSubtotals();
        for (QuoteSubtotal subtotal : subtotals) {
            // This implies querying matrix cost items repeatedly. 
            // The idea is to improve performance by using hibernate caching tools
            List<MatrixCostItem> matrixCostItems = getMatrixCostItems(m, subtotal.getProductCategory());
            calculateProductCategoryCost(subtotal, matrixCostItems, quoteCostItems, quoteMetrics);
            // add to totals
            Map<String, Double> subtotalMap = subtotal.getSubtotals();
            for (String feeCat : subtotalMap.keySet()) {
                addToFeeCategory(result.getTotals(), FeeCategoryCO.valueOf(feeCat), subtotalMap.get(feeCat));
            }
        }
                
        if (quoteCostAdjustments != null) {
            // Calculate adjustments
            for (QuoteCostAdjustment qca : quoteCostAdjustments) {
                addToAdjustments(result.getAdjustments(), qca);            
            }
            // Add adjustments to totals
            for (String feeCat : result.getAdjustments().keySet()) {
                addToFeeCategory(result.getTotals(), FeeCategoryCO.valueOf(feeCat), result.getAdjustments().get(feeCat));
            }                        
        }
        
        return result;
    }
    
    /*
     * Returns a list of definitive cost items, including complete fields and required cost items
     * Merges with an existing partial list of QuoteCostItems 
     */
    public List<QuoteCostItem> fillUpQuoteCostItems(Matrix m, List<QuoteCostItem> currentQuoteCostItems,
            List<QuoteMetric> quoteMetrics,
            List<ProductCategory> productCategories) {
        if (currentQuoteCostItems == null) {
            currentQuoteCostItems = new ArrayList<QuoteCostItem>();
        }
        for (ProductCategory pc : productCategories) {
            List<MatrixCostItem> matrixCostItems = getMatrixCostItems(m, pc);
            fillUpProductCategoryCostItems(matrixCostItems, currentQuoteCostItems, quoteMetrics);            
        }
        
        return currentQuoteCostItems;
    }
    
    protected void calculateProductCategoryCost(QuoteSubtotal subtotal, List<MatrixCostItem> matrixCostItems,
            List<QuoteCostItem> quoteCostItems, List<QuoteMetric> quoteMetrics) {
        for (MatrixCostItem mci : matrixCostItems) {
            // Do we have this cost item enabled in quote?
            QuoteCostItem qci = getQuoteCostItemMatchingMatrixCostItem(mci, quoteCostItems);
            if (qci != null) {
                CostModel cm = mci.getCostModel();
                if ( (cm == null) || (cm.getXmlContent() == null)) {
                    // This item doesn't use a cost model, or it is a (flat) cost model without xml rules
                    // Use simple cost...
                    qci.setCost(mci.getSimpleCost());
                }
                else {
                    if (cm.getCostModelObject() == null) {
                        CostModelParser.setCostModelRulesInCostModel(cm);
                    }
                    if (cm.getCostModelObject() != null) {
                        CostModelEvaluationResult cmer = CostModelEvaluator.evaluateCostModel(cm.getCostModelObject(), quoteMetrics);
                        qci.setCost(cmer.getValue());
                        qci.setErrorMessage(cmer.getError());
                    }
                    else {
                        qci.setCost(Double.NaN);
                        qci.setErrorMessage("XML error: " + cm.getErrorMessage());
                    }
                }
                
                Map<String, Double> feeCatSubtotal = subtotal.getSubtotals();
                addToSubtotal(feeCatSubtotal, qci);                
            }
            
        }
    }
    
    public void fillUpProductCategoryCostItems(List<MatrixCostItem> matrixCostItems, List<QuoteCostItem> quoteCostItems, List<QuoteMetric> quoteMetrics) {
        List<QuoteCostItem> itemsToAdd = new ArrayList<QuoteCostItem>();
        
        for (MatrixCostItem mci : matrixCostItems) {
            // Do we have this cost item enabled in quote?
            QuoteCostItem qci = getQuoteCostItemMatchingMatrixCostItem(mci, quoteCostItems);
            if (qci != null) {
                CostModel cm = mci.getCostModel();
                if ( (cm == null) || (cm.getXmlContent() == null)) {
                    // This item doesn't use a cost model, or it is a (flat) cost model without xml rules
                    // Use simple cost...
                    qci.setCost(mci.getSimpleCost());
                }
                else {
                    if (cm.getCostModelObject() == null) {
                        CostModelParser.setCostModelRulesInCostModel(cm);
                    }
                    if (cm.getCostModelObject() != null) {
                        CostModelEvaluationResult cmer = CostModelEvaluator.evaluateCostModel(cm.getCostModelObject(), quoteMetrics);
                        qci.setCost(cmer.getValue());
                        qci.setErrorMessage(cmer.getError());
                    }
                    else {
                        qci.setCost(Double.NaN);
                        qci.setErrorMessage("XML error: " + cm.getErrorMessage());
                    }
                }
                qci.setCostModel(cm);                
                
                if (qci.getId() == null) {
                    // This is a required cost item, we need to add it to the list
                    itemsToAdd.add(qci);
                }
                else {
                    // Fill up data
                    qci.setRequired(mci.getForced());                    
                }
                
            }            
        }        
        quoteCostItems.addAll(itemsToAdd);
    }
        
    
    protected void addToSubtotal(Map<String, Double> feeCatSubtotal, QuoteCostItem qci) {
        addToFeeCategory(feeCatSubtotal, qci.getFeeCategory(), qci.getCost());
    }
    
    protected void addToAdjustments(Map<String, Double> adjustments, QuoteCostAdjustment adjustment) {
        addToFeeCategory(adjustments, adjustment.getFeeCategory(), adjustment.getCost());        
    }
    
    protected void addToFeeCategory(Map<String, Double> feeCatMap, FeeCategoryCO feeCat, Double val) {
        // Add accumulated value
        Double accVal = feeCatMap.get(feeCat.getName());
        if (! accVal.isNaN()) {
            if (! val.isNaN() ) {
                accVal = accVal + val;
            }
            else {
                accVal = Double.NaN;
            }
            feeCatMap.put(feeCat.getName(), accVal);
        }        
    }

    public QuoteCostItem getQuoteCostItemMatchingMatrixCostItem(MatrixCostItem matrixCostItem, List<QuoteCostItem> quoteCostItems) {
        for (QuoteCostItem qci : quoteCostItems) {
            if (qci.getCostItem().getId().equals(matrixCostItem.getCostItem().getId()) 
                    && qci.getFeeCategory().equals(matrixCostItem.getFeeCategory())
                    && qci.getEnabled()) {
                return qci;                
            }
        }
        
        // Return a match anyway if this is a forced (enabled = yes) matrixCostItem
        Boolean isRequired = matrixCostItem.getForced();
        if  ( (isRequired != null) && (isRequired.booleanValue()) ) {
            QuoteCostItem qci = new QuoteCostItem();
            qci.setCostItem(matrixCostItem.getCostItem());
            qci.setCostModel(matrixCostItem.getCostModel());
            qci.setEnabled(true);
            qci.setFeeCategory(matrixCostItem.getFeeCategory());
            qci.setRequired(true);
            return qci;            
        }
        
        return null;
    }
    
    protected Map<FeeCategoryCO, Double> createPerFeeCategoryCostMap() {
        Map<FeeCategoryCO, Double>  result = new HashMap<FeeCategoryCO, Double>();
        for (FeeCategoryCO feeCat : FeeCategoryCO.values()) {
            result.put(feeCat, 0.0d);
        }
        return result;
    }
    
    public List<MatrixRiskAnalysis> getMatrixRiskAnalysisItems(Matrix m) {
        return  matrixDao.getMatrixRiskAnalysisItems(hibernateSession, m); 
    }        
    
}
