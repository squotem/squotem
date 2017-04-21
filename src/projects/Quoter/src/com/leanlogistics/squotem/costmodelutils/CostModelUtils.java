package com.leanlogistics.squotem.costmodelutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leanlogistics.squotem.co.FeeCategoryCO;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.QuoteCosts;
import com.leanlogistics.squotem.model.QuoteSubtotal;

public class CostModelUtils {
    
    public static Map<String, Double> getZeroCostMap() {
        Map <String, Double> result = new HashMap<String, Double>();
        for (FeeCategoryCO feeCat : FeeCategoryCO.values()) {
            result.put(feeCat.getName(), 0d);
        }
        return result;
    }
    
    public static QuoteCosts createZeroQuoteCosts(List<ProductCategory> productCategories ) {
        QuoteCosts zeroQuoteCosts = new QuoteCosts();
        
        List<QuoteSubtotal> subtotals = new ArrayList<QuoteSubtotal>();
        if (productCategories != null) {
            for (ProductCategory pc : productCategories) {
                QuoteSubtotal subtotal = new QuoteSubtotal();
                subtotal.setProductCategory(pc);
                subtotal.setSubtotals(CostModelUtils.getZeroCostMap());
                subtotals.add(subtotal);
            }
        }
        zeroQuoteCosts.setSubtotals(subtotals);
        zeroQuoteCosts.setAdjustments(CostModelUtils.getZeroCostMap());
        zeroQuoteCosts.setTotals(CostModelUtils.getZeroCostMap());
        
        return zeroQuoteCosts;        
    }
    

}
