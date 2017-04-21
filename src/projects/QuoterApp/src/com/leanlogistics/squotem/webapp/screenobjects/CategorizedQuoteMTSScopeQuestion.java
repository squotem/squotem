package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;

import com.leanlogistics.squotem.model.MTSScopeQuestionCategory;

public class CategorizedQuoteMTSScopeQuestion {
    
    private MTSScopeQuestionCategory category;
    private List<MTSScopeQuestionForQuote> categoryQuestions;
    public MTSScopeQuestionCategory getCategory() {
        return category;
    }
    public void setCategory(MTSScopeQuestionCategory category) {
        this.category = category;
    }
    public List<MTSScopeQuestionForQuote> getCategoryQuestions() {
        return categoryQuestions;
    }
    public void setCategoryQuestions(
            List<MTSScopeQuestionForQuote> categoryQuestions) {
        this.categoryQuestions = categoryQuestions;
    }
    
    

}
