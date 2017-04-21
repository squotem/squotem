package com.leanlogistics.squotem.model;

public class QuoteMTSRoleCost {
    private Long id;
    private MTSMatrixRoleCost  mtsRoleCost;
    private Quote quote;
    private MTSRole mtsRole;
    private Double roleCountCalculated;
    private Double roleCountAdditional;
    private Double roleCost;
    
    // Empty constructor
    public QuoteMTSRoleCost() {}
    
    // Copy constructor
    public QuoteMTSRoleCost(QuoteMTSRoleCost source) {
        mtsRoleCost = source.mtsRoleCost;
        mtsRole = source.mtsRole;
        roleCountCalculated = source.roleCountCalculated;
        roleCountAdditional = source.roleCountAdditional;
        roleCost = source.roleCost;
    }
        
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public MTSMatrixRoleCost getMtsRoleCost() {
        return mtsRoleCost;
    }
    public void setMtsRoleCost(MTSMatrixRoleCost mtsRoleCost) {
        this.mtsRoleCost = mtsRoleCost;
    }
    public Quote getQuote() {
        return quote;
    }
    public void setQuote(Quote quote) {
        this.quote = quote;
    }
    public MTSRole getMtsRole() {
        return mtsRole;
    }
    public void setMtsRole(MTSRole mtsRole) {
        this.mtsRole = mtsRole;
    }
    public Double getRoleCountCalculated() {
        return roleCountCalculated;
    }
    public void setRoleCountCalculated(Double roleCountCalculated) {
        this.roleCountCalculated = roleCountCalculated;
    }
    public Double getRoleCountAdditional() {
        return roleCountAdditional;
    }
    public void setRoleCountAdditional(Double roleCountAdditional) {
        this.roleCountAdditional = roleCountAdditional;
    }
    public Double getRoleCost() {
        return roleCost;
    }
    public void setRoleCost(Double roleCost) {
        this.roleCost = roleCost;
    }    

}
