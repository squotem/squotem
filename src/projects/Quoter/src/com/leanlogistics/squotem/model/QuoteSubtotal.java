package com.leanlogistics.squotem.model;

import java.util.Map;

/**
 * Quote subtotal for a specific product category
 *
 */
public class QuoteSubtotal {
    
    ProductCategory productCategory;
    Map <String, Double> subtotals;
    public ProductCategory getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    public Map<String, Double> getSubtotals() {
        return subtotals;
    }
    public void setSubtotals(Map<String, Double> subtotals) {
        this.subtotals = subtotals;
    }

}
