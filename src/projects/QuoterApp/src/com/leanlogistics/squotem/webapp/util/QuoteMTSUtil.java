package com.leanlogistics.squotem.webapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leanlogistics.squotem.co.MTSScopeAnswerCO;
import com.leanlogistics.squotem.model.MTSMatrixRoleCost;
import com.leanlogistics.squotem.model.MTSMatrixScopeQuestion;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteMTSRoleCost;
import com.leanlogistics.squotem.model.QuoteMTSScopeAnswer;
import com.leanlogistics.squotem.webapp.screenobjects.CategorizedQuoteMTSScopeQuestion;
import com.leanlogistics.squotem.webapp.screenobjects.MTSRoleForQuote;
import com.leanlogistics.squotem.webapp.screenobjects.MTSScopeQuestionForQuote;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteEntryMTS;

public class QuoteMTSUtil {
    
    public static void populateCategorizedQuoteMTSScopeQuestions(QuoteEntryMTS quoteEntryMTS, Quote q, List<MTSMatrixScopeQuestion> mtsMatrixScopeQuestions) {
        // Get categorized scope questions, including those for which there are no current values
        List<CategorizedQuoteMTSScopeQuestion> categorizedQuestions = getCategorizedQuoteMTSScopeQuestions(q, mtsMatrixScopeQuestions, false);                
        quoteEntryMTS.setCategorizedQuoteMTSScopeQuestions(categorizedQuestions);
    }
    
    public static List<CategorizedQuoteMTSScopeQuestion> getCategorizedExistingQuoteMTSScopeQuestions(Quote q, List<MTSMatrixScopeQuestion> mtsMatrixScopeQuestions) {
        // Get categorized scope questions, including only those for which there are current values
        return getCategorizedQuoteMTSScopeQuestions(q, mtsMatrixScopeQuestions, true);
    }
    
    public static List<CategorizedQuoteMTSScopeQuestion> getCategorizedQuoteMTSScopeQuestions(Quote q, List<MTSMatrixScopeQuestion> mtsMatrixScopeQuestions, boolean includeOnlyCurrentValues) {
        List<CategorizedQuoteMTSScopeQuestion> categorizedQuestions = new ArrayList<CategorizedQuoteMTSScopeQuestion>();
        CategorizedQuoteMTSScopeQuestion cat = null;
        for(MTSMatrixScopeQuestion question : mtsMatrixScopeQuestions) {
            if ((cat == null) || (!question.getCategory().getId().equals(cat.getCategory().getId()))) {
                // Change on category, save the previous category
                if (cat != null) {
                    if ( (! includeOnlyCurrentValues ) || ( ! cat.getCategoryQuestions().isEmpty())) {
                        categorizedQuestions.add(cat);
                    }
                }
                // Create the new category
                cat = new CategorizedQuoteMTSScopeQuestion();
                cat.setCategory(question.getCategory());
                cat.setCategoryQuestions(new ArrayList<MTSScopeQuestionForQuote>());
            }
            MTSScopeQuestionForQuote questionForQuote = new MTSScopeQuestionForQuote();
            questionForQuote.setQuestion(question);
            QuoteMTSScopeAnswer answer = findQuoteMTSScopeAnswer(q.getQuoteMTSScopeAnswers(), question);
            if (answer != null) {
                questionForQuote.setQuoteAnswer(answer);
                questionForQuote.setAnswer(answer.getAnswer().toString());
            }
            else {
                if (includeOnlyCurrentValues) {
                    questionForQuote = null;
                }
            }
            
            if ( (! includeOnlyCurrentValues) || (questionForQuote != null) ) {
                cat.getCategoryQuestions().add(questionForQuote);
            }
        }
        // Add last category
        if (cat != null) {
            if ( (! includeOnlyCurrentValues ) || ( ! cat.getCategoryQuestions().isEmpty())) {
                categorizedQuestions.add(cat);
            }
        }
        
        return categorizedQuestions;
    }
    
    public static void populateMTSRoles(QuoteEntryMTS quoteEntryMTS, Quote q, List<MTSMatrixRoleCost> roleCosts) {
        List<MTSRoleForQuote> rolesForQuote = getMTSRolesForQuote(q, roleCosts);
        quoteEntryMTS.setRolesForQuote(rolesForQuote);        
    }
    
    public static List<MTSRoleForQuote> getMTSRolesForQuote(Quote q, List<MTSMatrixRoleCost> roleCosts) {
        List<MTSRoleForQuote> rolesForQuote = new ArrayList<MTSRoleForQuote>();
        for (MTSMatrixRoleCost roleCost : roleCosts) {
            MTSRoleForQuote roleForQuote = new MTSRoleForQuote();
            roleForQuote.setRole(roleCost.getMtsRole());
            // QuoteMTSRoleCost are obtained from calculated costs. Cost calculation has to be done previously
            QuoteMTSRoleCost quoteMTSRoleCost = findQuoteMTSRoleCost(q.getQuoteMTSCosts().getRoleCosts(), roleCost);
            if (quoteMTSRoleCost != null) {
                roleForQuote.setQuoteRoleCost(quoteMTSRoleCost);
            }
            rolesForQuote.add(roleForQuote);
        }
        return rolesForQuote;
    }
    
    
    // Given an MTSRoleForQuote, are we charging anything for this?
    public static boolean roleIsPresent(MTSRoleForQuote role) {
        if  ( (role.getQuoteRoleCost().getRoleCountCalculated() > 0) || (role.getQuoteRoleCost().getRoleCountAdditional() > 0) ){
            return true;            
        }
        return false;
    }
    
    private static QuoteMTSScopeAnswer findQuoteMTSScopeAnswer(List<QuoteMTSScopeAnswer> quoteMTSScopeAnswers, MTSMatrixScopeQuestion question) {
        for (QuoteMTSScopeAnswer answer : quoteMTSScopeAnswers) {
            if (answer.getMtsScopeQuestion().getId().equals(question.getId())) {
                return answer;
            }
        }
        return null;
    }
    
    private static QuoteMTSRoleCost findQuoteMTSRoleCost(List<QuoteMTSRoleCost> quoteMTSRoleCosts, MTSMatrixRoleCost roleCost) {
        for (QuoteMTSRoleCost quoteRoleCost : quoteMTSRoleCosts) {
            if (quoteRoleCost.getMtsRoleCost().getId().equals(roleCost.getId())) {
                return quoteRoleCost;
            }
        }
        return null;
    }
    
    public static Map<String, List<QuoteMTSScopeAnswer>> processQuoteMTSScopeAnswersChanges(List<CategorizedQuoteMTSScopeQuestion> categorizedQuoteMTSScopeQuestions) {
        List<QuoteMTSScopeAnswer> deleteItems = new ArrayList<QuoteMTSScopeAnswer>();
        List<QuoteMTSScopeAnswer> saveItems = new ArrayList<QuoteMTSScopeAnswer>();
        for (CategorizedQuoteMTSScopeQuestion category : categorizedQuoteMTSScopeQuestions) {
            for (MTSScopeQuestionForQuote questionForQuote : category.getCategoryQuestions()) {
                String answer = questionForQuote.getAnswer();
                if (QuoteMetricUtil.emptyOrNull(answer)) {
                    // If answer is now null and there was previously saved answer for this, we need to delete it
                    if (questionForQuote.getQuoteAnswer() != null) {
                        deleteItems.add(questionForQuote.getQuoteAnswer());
                    }
                }
                else {                    
                    // Since we have an answer, save it
                    QuoteMTSScopeAnswer quoteAnswer = questionForQuote.getQuoteAnswer();
                    if (quoteAnswer == null) {
                        quoteAnswer = new QuoteMTSScopeAnswer();
                    }
                    MTSMatrixScopeQuestion matrixQuestion = questionForQuote.getQuestion();
                    quoteAnswer.setMtsScopeQuestion(matrixQuestion);
                    if (matrixQuestion != null) {
                        quoteAnswer.setBaselineScopeImpact(matrixQuestion.getBaselineScopeImpact());
                        quoteAnswer.setHeadcountImpact(matrixQuestion.getHeadcountImpact());
                    }
                            
                    MTSScopeAnswerCO answerCo = MTSScopeAnswerCO.valueOf(answer);
                    quoteAnswer.setAnswer(answerCo);
                    saveItems.add(quoteAnswer);
                }
            }
        }
        Map<String, List<QuoteMTSScopeAnswer>> result = new HashMap<String, List<QuoteMTSScopeAnswer>>();
        result.put(QuoteCostItemUtil.DELETE_ITEMS_KEY, deleteItems);
        result.put(QuoteCostItemUtil.SAVE_ITEMS_KEY, saveItems);
        return result;        
    }
    
    public static List<QuoteMTSScopeAnswer> getFlatQuoteMTSSCopeAnswersList(List<CategorizedQuoteMTSScopeQuestion> categorizedQuoteMTSScopeQuestions) {
        List<QuoteMTSScopeAnswer> result = new ArrayList<QuoteMTSScopeAnswer>();
        for (CategorizedQuoteMTSScopeQuestion category : categorizedQuoteMTSScopeQuestions) {
            for (MTSScopeQuestionForQuote questionForQuote : category.getCategoryQuestions()) {
                String answer = questionForQuote.getAnswer();
                if (QuoteMetricUtil.notEmptyOrNull(answer)) {
                    // Since we have an answer, save it
                    QuoteMTSScopeAnswer quoteAnswer = questionForQuote.getQuoteAnswer();
                    if (quoteAnswer == null) {
                        quoteAnswer = new QuoteMTSScopeAnswer();
                    }
                    MTSMatrixScopeQuestion matrixQuestion = questionForQuote.getQuestion();
                    quoteAnswer.setMtsScopeQuestion(matrixQuestion);
                    if (matrixQuestion != null) {
                        quoteAnswer.setBaselineScopeImpact(matrixQuestion.getBaselineScopeImpact());
                        quoteAnswer.setHeadcountImpact(matrixQuestion.getHeadcountImpact());
                    }
                    MTSScopeAnswerCO answerCo = MTSScopeAnswerCO.valueOf(answer);
                    quoteAnswer.setAnswer(answerCo);
                    result.add(quoteAnswer);
                }
            }
        }
        return result;
    }
    
    public static List<QuoteMTSRoleCost> getQuoteMTSRoleCostList( List<MTSRoleForQuote> rolesForQuote) {
        List<QuoteMTSRoleCost> result = new ArrayList<QuoteMTSRoleCost>();
        for (MTSRoleForQuote roleForQuote : rolesForQuote) {
            result.add(roleForQuote.getQuoteRoleCost());
        }
        return result;
    }

}
