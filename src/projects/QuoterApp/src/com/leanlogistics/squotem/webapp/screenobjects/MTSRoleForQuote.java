package com.leanlogistics.squotem.webapp.screenobjects;

import com.leanlogistics.squotem.model.MTSRole;
import com.leanlogistics.squotem.model.QuoteMTSRoleCost;

public class MTSRoleForQuote {
    private MTSRole role;
    private QuoteMTSRoleCost quoteRoleCost;
    
    public MTSRole getRole() {
        return role;
    }
    public void setRole(MTSRole role) {
        this.role = role;
    }
    public QuoteMTSRoleCost getQuoteRoleCost() {
        return quoteRoleCost;
    }
    public void setQuoteRoleCost(QuoteMTSRoleCost quoteRoleCost) {
        this.quoteRoleCost = quoteRoleCost;
    }
    

}
