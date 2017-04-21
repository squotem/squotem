package com.leanlogistics.squotem.webapp.screenobjects;

import com.leanlogistics.squotem.model.MTSMatrixScopeQuestion;
import com.leanlogistics.squotem.model.QuoteMTSScopeAnswer;

public class MTSScopeQuestionForQuote {
    private MTSMatrixScopeQuestion question;
    // Value obtained from MTSScopeAnswerCO
    private String answer;
    private QuoteMTSScopeAnswer quoteAnswer;
    
    public MTSMatrixScopeQuestion getQuestion() {
        return question;
    }
    public void setQuestion(MTSMatrixScopeQuestion question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public QuoteMTSScopeAnswer getQuoteAnswer() {
        return quoteAnswer;
    }
    public void setQuoteAnswer(QuoteMTSScopeAnswer quoteAnswer) {
        this.quoteAnswer = quoteAnswer;
    }
    
}
