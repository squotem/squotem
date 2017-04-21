package com.leanlogistics.squotem.service.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leanlogistics.squotem.co.MTSScopeAnswerCO;
import com.leanlogistics.squotem.model.MTSMatrix;
import com.leanlogistics.squotem.model.MTSMatrixRoleCost;
import com.leanlogistics.squotem.model.MTSMatrixRoleRelation;
import com.leanlogistics.squotem.model.MTSMatrixScopeQuestion;
import com.leanlogistics.squotem.model.MTSRole;
import com.leanlogistics.squotem.model.QuoteMTSCosts;
import com.leanlogistics.squotem.model.QuoteMTSRoleCost;
import com.leanlogistics.squotem.model.QuoteMTSScopeAnswer;
import com.leanlogistics.squotem.service.dao.MTSMatrixDao;

public class MTSMatrixManager extends BaseManager  {
    
    // Constants used in MTS Costs calculation
    protected static final String SCOPE_ADJUSTMENT = "ScopeAdj";
    protected static final String HEAD_COUNT_ADJUSTMENT = "HeadCountAdj";
    protected static final double WEEKS_PER_YEAR = 52d;
    // Id of master role "Logistics Coordinator"
    protected static final long LC_ROLE_ID = 1l;
    
    
    private MTSMatrixDao mtsMatrixDao;

    public MTSMatrixManager() {
        mtsMatrixDao = new MTSMatrixDao();
    }
    
    public MTSMatrix getMTSMatrix(long id) {
        return mtsMatrixDao.getMTSMatrix(hibernateSession, id);
    }
    
    public List<MTSMatrixScopeQuestion> getMTSMatrixScopeQuestions(Long mtsMatrixId) {
        return mtsMatrixDao.getMTSMatrixScopeQuestions(hibernateSession, mtsMatrixId);        
    }
    
    public List<MTSMatrixRoleCost> getMTSMatrixRoleCosts(Long mtsMatrixId) {
        return mtsMatrixDao.getMTSMatrixRoleCosts(hibernateSession, mtsMatrixId);
    }
    
    public List<MTSMatrixRoleRelation> getMTSMatrixRoleRelations(Long mtsMatrixId) {
        return mtsMatrixDao.getMTSMatrixRoleRelations(hibernateSession, mtsMatrixId);
    }
    
    public QuoteMTSCosts calculateQuoteMTSCosts(MTSMatrix mtsMatrix, Double annualLoadCountVal,
            List<QuoteMTSScopeAnswer> scopeAnswers,
            List<QuoteMTSRoleCost> roleCosts) {
        
        QuoteMTSCosts result = new QuoteMTSCosts();
        result.setBaselineWeeklyLoadCount(mtsMatrix.getBaselineWeeklyLoadCount().doubleValue());
                
        
        double annualLoadCount = annualLoadCountVal.doubleValue();
        if (annualLoadCount > 0) {
            // Proceed with the calculations
        
            double weeklyLoadCount = annualLoadCount / WEEKS_PER_YEAR;
            
            // Calculate adjustment values from scope question answers
            Map<String, Double> adjustments = getScopeAnswerAdjustments(scopeAnswers);
            
            double scopedWeeklyLoadCount = ((double)mtsMatrix.getBaselineWeeklyLoadCount()) * adjustments.get(SCOPE_ADJUSTMENT);
            result.setScopedWeeklyLoadCount(scopedWeeklyLoadCount);
            
            // LC = Logistic Coordinator role
            double loadCountByLC = ((double) mtsMatrix.getBaselineWeeklyLoadCount()) - scopedWeeklyLoadCount;
            double lcHeadCountDouble = (weeklyLoadCount / loadCountByLC) + adjustments.get(HEAD_COUNT_ADJUSTMENT);
            
            // Round up if decimal part >= 0.4, otherwise round down
            long lcHeadCountLong = Math.round(lcHeadCountDouble + 0.1d);
            
            fillUpQuoteRoleCosts(result, mtsMatrix, (double) lcHeadCountLong, roleCosts);
        }
        else {
            // We don't have data for annual load count yet, fill up structure with zero costs
            fillUpQuoteZeroRoleCosts(result, mtsMatrix, roleCosts);
        }
        
        return result;
    }
    
    protected Map<String, Double> getScopeAnswerAdjustments(List<QuoteMTSScopeAnswer> scopeAnswers) {
        double totalScopeAdj = 0d;
        double totalHeadCountAdj = 0d;
        for (QuoteMTSScopeAnswer quoteScopeAnswer : scopeAnswers) {
            // Add if answer was "yes"
            if (MTSScopeAnswerCO.YES.equals(quoteScopeAnswer.getAnswer())) {
                totalScopeAdj += quoteScopeAnswer.getBaselineScopeImpact();
                totalHeadCountAdj += quoteScopeAnswer.getHeadcountImpact();
            }
        }
        Map<String, Double> result = new HashMap<String, Double>();
        result.put(SCOPE_ADJUSTMENT, totalScopeAdj);
        result.put(HEAD_COUNT_ADJUSTMENT, totalHeadCountAdj);
        return result;
    }
    
    protected void fillUpQuoteRoleCosts(QuoteMTSCosts quoteMTSCosts, MTSMatrix mtsMatrix, double lcHeadCount, List<QuoteMTSRoleCost> currentRoleCosts) {
        List<QuoteMTSRoleCost> quoteMTSRoleCosts = new ArrayList<QuoteMTSRoleCost>();
        double totalAnnualCost = 0;
        for (MTSMatrixRoleCost matrixRoleCost : mtsMatrix.getRoleCosts()) {
            QuoteMTSRoleCost quoteRoleCost = findQuoteMTSRoleCost(currentRoleCosts, matrixRoleCost);
            // If there's no current record for it, create new
            if (quoteRoleCost == null) {
                quoteRoleCost = new QuoteMTSRoleCost();
                quoteRoleCost.setMtsRoleCost(matrixRoleCost);
                quoteRoleCost.setMtsRole(matrixRoleCost.getMtsRole());
                quoteRoleCost.setRoleCountAdditional(0d);
            }
            
            if (matrixRoleCost.getMtsRole().getId().longValue() == LC_ROLE_ID) {
                quoteRoleCost.setRoleCountCalculated(lcHeadCount);
            }
            else {
                quoteRoleCost.setRoleCountCalculated(getRoleQuantity(lcHeadCount, matrixRoleCost.getMtsRole(), mtsMatrix.getRoleRelations()));                
            }
            
            double totalRoleHeadCount = quoteRoleCost.getRoleCountCalculated() + quoteRoleCost.getRoleCountAdditional();
            double totalRoleCost = (matrixRoleCost.getCostPer() * (1 + matrixRoleCost.getMargin())) * totalRoleHeadCount;
            quoteRoleCost.setRoleCost(totalRoleCost);
            totalAnnualCost += totalRoleCost;            
            quoteMTSRoleCosts.add(quoteRoleCost);
        }
        // Store results in costs object
        quoteMTSCosts.setAnualTotal(totalAnnualCost);
        quoteMTSCosts.setMonthlyTotal(totalAnnualCost / 12);
        quoteMTSCosts.setRoleCosts(quoteMTSRoleCosts);
    }
    
    protected void fillUpQuoteZeroRoleCosts(QuoteMTSCosts quoteMTSCosts, MTSMatrix mtsMatrix, List<QuoteMTSRoleCost> currentRoleCosts) {
        List<QuoteMTSRoleCost> quoteMTSRoleCosts = new ArrayList<QuoteMTSRoleCost>();
        for (MTSMatrixRoleCost matrixRoleCost : mtsMatrix.getRoleCosts()) {
            QuoteMTSRoleCost quoteRoleCost = findQuoteMTSRoleCost(currentRoleCosts, matrixRoleCost);
            // If there's no current record for it, create new
            if (quoteRoleCost == null) {
                quoteRoleCost = new QuoteMTSRoleCost();
                quoteRoleCost.setMtsRoleCost(matrixRoleCost);
                quoteRoleCost.setMtsRole(matrixRoleCost.getMtsRole());
                quoteRoleCost.setRoleCountAdditional(0d);
            }
            quoteRoleCost.setRoleCountCalculated(0d);            
            quoteRoleCost.setRoleCost(0d);
            quoteMTSRoleCosts.add(quoteRoleCost);
        }
        // Store results in costs object
        quoteMTSCosts.setAnualTotal(0d);
        quoteMTSCosts.setMonthlyTotal(0d);
        quoteMTSCosts.setRoleCosts(quoteMTSRoleCosts);        
    }
    
    
    // Calculates role quantity for roles other than LC
    protected double getRoleQuantity(double lcHeadCount, MTSRole mtsRole, List<MTSMatrixRoleRelation> roleRelations ) {
        for (MTSMatrixRoleRelation roleRelation : roleRelations) {
            if (roleRelation.getMtsRole().getId().equals(mtsRole.getId())) {
                // Same role, lookup in range
                if ( (lcHeadCount >= ((double) roleRelation.getRangeStart())) &
                     (lcHeadCount <= ((double) roleRelation.getRangeEnd())) ) {
                    return roleRelation.getRoleCount();
                }
            }            
        }
        return 0d;        
    }
    
    
    
    
    private  QuoteMTSRoleCost findQuoteMTSRoleCost(List<QuoteMTSRoleCost> quoteMTSRoleCosts, MTSMatrixRoleCost roleCost) {
        for (QuoteMTSRoleCost quoteRoleCost : quoteMTSRoleCosts) {
            if (quoteRoleCost.getMtsRoleCost().getId().equals(roleCost.getId())) {
                return quoteRoleCost;
            }
        }
        return null;
    }
    
    

}
