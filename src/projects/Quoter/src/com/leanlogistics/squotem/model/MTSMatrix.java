package com.leanlogistics.squotem.model;

import java.util.List;

public class MTSMatrix {
    
    private Long id;
    private String name;
    private String description;
    private Long baselineWeeklyLoadCount;
    
    // Following fields are manually populated
    List<MTSMatrixScopeQuestion> scopeQuestions;
    List<MTSMatrixRoleCost> roleCosts;
    List<MTSMatrixRoleRelation> roleRelations;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getBaselineWeeklyLoadCount() {
        return baselineWeeklyLoadCount;
    }
    public void setBaselineWeeklyLoadCount(Long baselineWeeklyLoadCount) {
        this.baselineWeeklyLoadCount = baselineWeeklyLoadCount;
    }
    public List<MTSMatrixScopeQuestion> getScopeQuestions() {
        return scopeQuestions;
    }
    public void setScopeQuestions(List<MTSMatrixScopeQuestion> scopeQuestions) {
        this.scopeQuestions = scopeQuestions;
    }
    public List<MTSMatrixRoleCost> getRoleCosts() {
        return roleCosts;
    }
    public void setRoleCosts(List<MTSMatrixRoleCost> roleCosts) {
        this.roleCosts = roleCosts;
    }
    public List<MTSMatrixRoleRelation> getRoleRelations() {
        return roleRelations;
    }
    public void setRoleRelations(List<MTSMatrixRoleRelation> roleRelations) {
        this.roleRelations = roleRelations;
    }
    
    
    
}
