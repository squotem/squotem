package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.MTSScopeAnswerCO;

/** A scope question, answered as a "YES" or a "NO" for a Quote */

public class QuoteMTSScopeAnswer {
    
    private Long id;
    private Quote quote;
    private MTSMatrixScopeQuestion mtsScopeQuestion;
    private Double baselineScopeImpact;
    private Double headcountImpact;
    private MTSScopeAnswerCO answer;
    
    
    // Empty constructor
    public QuoteMTSScopeAnswer() {}
    
    // Copy constructor
    public QuoteMTSScopeAnswer(QuoteMTSScopeAnswer source) {
        mtsScopeQuestion = source.mtsScopeQuestion;
        baselineScopeImpact = source.baselineScopeImpact;
        headcountImpact = source.headcountImpact;
        answer = source.answer;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Quote getQuote() {
        return quote;
    }
    public void setQuote(Quote quote) {
        this.quote = quote;
    }
    public MTSMatrixScopeQuestion getMtsScopeQuestion() {
        return mtsScopeQuestion;
    }
    public void setMtsScopeQuestion(MTSMatrixScopeQuestion mtsScopeQuestion) {
        this.mtsScopeQuestion = mtsScopeQuestion;
    }
    public Double getBaselineScopeImpact() {
        return baselineScopeImpact;
    }
    public void setBaselineScopeImpact(Double baselineScopeImpact) {
        this.baselineScopeImpact = baselineScopeImpact;
    }
    public Double getHeadcountImpact() {
        return headcountImpact;
    }
    public void setHeadcountImpact(Double headcountImpact) {
        this.headcountImpact = headcountImpact;
    }
    public MTSScopeAnswerCO getAnswer() {
        return answer;
    }
    public void setAnswer(MTSScopeAnswerCO answer) {
        this.answer = answer;
    }
    

}
