package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.FeeCategoryCO;

public class CostAdjustment {
    private Long id;
    private ProductCategory productCategory;
    private String description;
    private FeeCategoryCO feeCategory;
    private Boolean active;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ProductCategory getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public FeeCategoryCO getFeeCategory() {
        return feeCategory;
    }
    public void setFeeCategory(FeeCategoryCO feeCategory) {
        this.feeCategory = feeCategory;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }    
}
