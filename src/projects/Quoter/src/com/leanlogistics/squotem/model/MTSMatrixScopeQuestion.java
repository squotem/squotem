package com.leanlogistics.squotem.model;

public class MTSMatrixScopeQuestion {
    private Long id;
    private MTSScopeQuestionCategory category;
    private MTSMatrix mtsMatrix;
    private String question;
    private Double baselineScopeImpact;
    private Double headcountImpact;
    private Integer sortOrder;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public MTSScopeQuestionCategory getCategory() {
        return category;
    }
    public void setCategory(MTSScopeQuestionCategory category) {
        this.category = category;
    }
    public MTSMatrix getMtsMatrix() {
        return mtsMatrix;
    }
    public void setMtsMatrix(MTSMatrix mtsMatrix) {
        this.mtsMatrix = mtsMatrix;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
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
    public Integer getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
