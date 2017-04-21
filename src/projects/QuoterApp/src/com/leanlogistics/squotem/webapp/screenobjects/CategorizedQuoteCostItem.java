package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.List;

import com.leanlogistics.squotem.model.ProductCategory;

public class CategorizedQuoteCostItem {
    
    private ProductCategory  productCategory;   
    private List<CostItemForQuote> entryCostItems;
    
    public List<CostItemForQuote> getEntryCostItems() {
        return entryCostItems;
    }
    public void setEntryCostItems(List<CostItemForQuote> entryCostItems) {
        this.entryCostItems = entryCostItems;
    }
    public ProductCategory getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }    

}
