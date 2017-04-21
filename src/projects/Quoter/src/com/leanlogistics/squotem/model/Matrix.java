package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.MatrixStatusCO;
import java.util.Date;

public class Matrix {

    private Long id;
    private String name;
    private String description;
    private Long mtsMatrixId;
    private MatrixStatusCO status;
    private CostModel budgetaryImplCostModel;
    private CostModel budgetaryMonthlyCostMode;
    private Date createDate;
    
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
    public Long getMtsMatrixId() {
        return mtsMatrixId;
    }
    public void setMtsMatrixId(Long mtsMatrixId) {
        this.mtsMatrixId = mtsMatrixId;
    }
    public MatrixStatusCO getStatus() {
        return status;
    }
    public void setStatus(MatrixStatusCO status) {
        this.status = status;
    }
    public CostModel getBudgetaryImplCostModel() {
        return budgetaryImplCostModel;
    }
    public void setBudgetaryImplCostModel(CostModel budgetaryImplCostModel) {
        this.budgetaryImplCostModel = budgetaryImplCostModel;
    }
    public CostModel getBudgetaryMonthlyCostMode() {
        return budgetaryMonthlyCostMode;
    }
    public void setBudgetaryMonthlyCostMode(CostModel budgetaryMonthlyCostMode) {
        this.budgetaryMonthlyCostMode = budgetaryMonthlyCostMode;
    }
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
