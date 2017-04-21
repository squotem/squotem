package com.leanlogistics.squotem.costmodelutils;

import java.util.List;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import com.leanlogistics.squotem.co.MetricDataTypeCO;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.model.costmodel.CostModelRules;
import com.leanlogistics.squotem.model.costmodel.CostModelTier;
import com.leanlogistics.squotem.model.costmodel.CostModelValue;

public class CostModelEvaluator {
    
    public static CostModelEvaluationResult evaluateCostModel(CostModelRules costModel, List<QuoteMetric> metrics) {
        CostModelEvaluationResult result = new CostModelEvaluationResult();
        List<CostModelValue> cmValues = costModel.getValues();
        if (cmValues == null) {
            // TODO: Proper warning
            System.out.println("****WARNING: No value elements in cost model!");
            setErrorCost(result, "Invalid XML: No value elements");
            return result;
        }
        // Define ELProcessor
        Double accValue = new Double(0d);        
        Evaluator evaluator = new Evaluator();
        // Add accumulated value as variable
        evaluator.putVariable("_VAL",accValue.toString());        
        // Add all metrics as variables
        if (metrics != null) {
            for (QuoteMetric qm : metrics) {
                String metricValue = qm.getMetricValue();
                // Wrap quotes over non numeric values
                MetricDataTypeCO dataType = qm.getMetric().getDataType();
                if ( (dataType != null) ) {
                    if ( MetricDataTypeCO.STRING.equals(dataType) ||
                         MetricDataTypeCO.BOOLEAN.equals(dataType) ||
                         MetricDataTypeCO.BOOL_PLUS_STRING.equals(dataType)) {
                        metricValue = "'" + metricValue + "'";
                    }
                }
                evaluator.putVariable(qm.getMetric().getMnemonic(),metricValue);                
            }
        }
        
        
        for (CostModelValue cmValue : cmValues) {
            String operator = cmValue.getOperator();
            if (operator == null) {
                // By default, add to current result
                operator = "+";
            }
            
            
            // Condition is optional, if present, and it evaluates to false, value becomes 0. By default it's true
            boolean applyValue = true;
            String ifCondition = cmValue.getConditionExpresion();
            if (ifCondition != null) {
                Boolean ifConditionResult = evaluateBooleanExpression(result, evaluator, ifCondition);
                if (ifConditionResult == null) {
                    return result;
                }
                applyValue = ifConditionResult.booleanValue();
            }
            Double itemValue = null;
            if (applyValue ) {
            
                
                // Is this a single expression value?
                String expr = cmValue.getExpression();
                if (expr != null) {
                    itemValue = evaluateExpression(result, evaluator, expr); 
                }
                else {
                    // Tiered expression?
                    String evalExp = cmValue.getEvalExpression();
                    List<CostModelTier> tiers = cmValue.getTiers();
                    if ( (evalExp != null) && (tiers != null) && (tiers.size() > 0) ) {
                        itemValue = evaluateTieredExpression(result, evaluator, evalExp, tiers);
                    }                
                }
                if (itemValue == null) {
                    return result;
                }
            }
            else {
                itemValue = 0d;
            }
            // Combine with accumulated value...
            accValue = combineWithValue(operator, accValue, itemValue);
            evaluator.putVariable("_VAL",accValue.toString());
        }
        
        // Successful value calculated 
        result.setValue(accValue);
        return result;
    }
    
    protected static void setErrorCost(CostModelEvaluationResult cmer, String errorMessage) {
        cmer.setValue(Double.NaN);
        cmer.setError(errorMessage);
    }
    
    protected static Double combineWithValue(String operator, Double accValue, Double value) {
        if (operator.equals("+")) {
            accValue = accValue + value;
        }
        else if (operator.equals("-")) {
            accValue = accValue - value;
        }
        else if (operator.equals("/")) {
            accValue = accValue / value;
        }
        else if (operator.equals("*")) {
            accValue = accValue * value;
        }
        return accValue;        
    }
    
    protected static Double evaluateTieredExpression(CostModelEvaluationResult cmer, Evaluator evaluator, String evalExpre, List<CostModelTier> tiers) {
        Double compareValue = evaluateExpression(cmer, evaluator, evalExpre);
        if (compareValue == null) {
            return null;
        }
        
        for(CostModelTier tier : tiers) {
            if (tierMatchValue(cmer, evaluator, tier, compareValue)) {
                return evaluateExpression(cmer, evaluator, tier.getExpression());
            }            
        }
        
        // At this point, we didn't find a result
        setErrorCost(cmer, "Expression evaluation error. Value (" + compareValue +") doesn't match for any of the expression tiers");
        return null;
    }
    
    protected static boolean tierMatchValue(CostModelEvaluationResult cmer, Evaluator evaluator, CostModelTier tier, Double value) {
        boolean result = false;
        Double minRange = Double.MIN_VALUE;
        Double maxRange = Double.MAX_VALUE;
        String strFrom = tier.getFrom();
        if (strFrom != null) {
            minRange = evaluateExpression(cmer, evaluator, strFrom);
        }
        String strTo = tier.getTo();
        if (strTo != null ){
            maxRange = evaluateExpression(cmer, evaluator, strTo);
        }
        if ( (minRange != null) && (maxRange != null)) { 
            if ( (value >= minRange) && (value <= maxRange)) {
                result = true;
            }
        }
        return result;
    }
    
    protected static Double evaluateExpression(CostModelEvaluationResult cmer, Evaluator evaluator, String expr) {
        String strResult = null;
        try {
            strResult = evaluator.evaluate(expr);
        }
        catch (EvaluationException ee) {
            System.out.println("!!Expression evaluation error: " + ee.getMessage());            
            setErrorCost(cmer, "Expression evaluation error. One or more metrics are missing for this quote - " + ee.getMessage());
            return null;
        }
        if (strResult != null) {
            return Double.valueOf(strResult);
        }
        setErrorCost(cmer, "Expression evaluation error. No value obtained for expression...");
        return null;
    }
    
    protected static Boolean evaluateBooleanExpression(CostModelEvaluationResult cmer, Evaluator evaluator, String expr) {
        boolean result;
        try {
           result = evaluator.getBooleanResult(expr);
        }
        catch (EvaluationException ee) {
            System.out.println("!!Expression evaluation error: " + ee.getMessage());            
            setErrorCost(cmer, "Expression evaluation error. One or more metrics are missing for this quote - " + ee.getMessage());
            return null;
        }
        return result;        
    }
 
}
