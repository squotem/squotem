package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;

public class QuoteEntryMTS {
    
    private List<CategorizedQuoteMTSScopeQuestion> categorizedQuoteMTSScopeQuestions;
    private List<MTSRoleForQuote> rolesForQuote;
    private String annualLoadCount;

    public List<CategorizedQuoteMTSScopeQuestion> getCategorizedQuoteMTSScopeQuestions() {
        return categorizedQuoteMTSScopeQuestions;
    }

    public void setCategorizedQuoteMTSScopeQuestions(
            List<CategorizedQuoteMTSScopeQuestion> categorizedQuoteMTSScopeQuestions) {
        this.categorizedQuoteMTSScopeQuestions = categorizedQuoteMTSScopeQuestions;
    }

    public List<MTSRoleForQuote> getRolesForQuote() {
        return rolesForQuote;
    }

    public void setRolesForQuote(List<MTSRoleForQuote> rolesForQuote) {
        this.rolesForQuote = rolesForQuote;
    }

    public String getAnnualLoadCount() {
        return annualLoadCount;
    }

    public void setAnnualLoadCount(String annualLoadCount) {
        this.annualLoadCount = annualLoadCount;
    }        

}
