package com.leanlogistics.squotem.webapp.screenobjects;

import java.util.ArrayList;
import java.util.List;

import com.leanlogistics.squotem.co.CustomerTypeCO;
import com.leanlogistics.squotem.model.Customer;
import com.leanlogistics.squotem.model.Matrix;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;

public class QuoteEntryCustomer extends Quote {
    
    
    private List<Long> productCategoryIds;
    // String representation of customer type
    private String customerTypeStr;
    // Reference to original matrix in order to see if it was changed by the user
    private Matrix oldMatrix;

    public List<Long> getProductCategoryIds() {
        return productCategoryIds;
    }

    public void setProductCategoryIds(List<Long> productCategoryIds) {
        this.productCategoryIds = productCategoryIds;
    }
    
    public String getCustomerTypeStr() {
        return customerTypeStr;
    }

    public void setCustomerTypeStr(String customerTypeStr) {
        this.customerTypeStr = customerTypeStr;
    }
    
    public Matrix getOldMatrix() {
        return oldMatrix;
    }

    public void setOldMatrix(Matrix oldMatrix) {
        this.oldMatrix = oldMatrix;
    }

    // Create a Quote representation from this QuoteEntry
    public Quote getQuote() {
        Quote q = new Quote();
        q.setId(getId());
        Customer c = getCustomer();
        if (c != null) {
            // Set customer type based on string representation
            CustomerTypeCO custType = CustomerTypeCO.SHIPPER;
            if (customerTypeStr != null) {
                custType = CustomerTypeCO.valueOf(customerTypeStr);
            }            
            c.setCustomerType(custType);
        }        
        q.setCustomer(c);
        q.setSalesDirector(getSalesDirector());
        q.setBusinessConsultant(getBusinessConsultant());
        q.setPartnerReferenced(getPartnerReferenced());
        q.setMatrix(getMatrix());
        q.setCreateDate(getCreateDate());
        q.setEffectiveDate(getEffectiveDate());
        q.setExpireDate(getExpireDate());
        q.setTerms(getTerms());
        q.setHasMts(getHasMts());
       
        
        // Create product categories from Ids
        List<ProductCategory> pcs = new ArrayList<ProductCategory>();
        for (Long pcId : getProductCategoryIds()) {
            ProductCategory pc = new ProductCategory();
            pc.setId(pcId);
            pcs.add(pc);
        }
        q.setProductCategories(pcs);
        
        return q;        
    }

    // Update a Quote representation from this QuoteEntry
    public void getQuote(Quote q) {
        Customer c = getCustomer();
        if (c != null) {
            // Set customer type based on string representation
            CustomerTypeCO custType = CustomerTypeCO.SHIPPER;
            if (customerTypeStr != null) {
                custType = CustomerTypeCO.valueOf(customerTypeStr);
            }            
            c.setCustomerType(custType);
        }        
        q.setCustomer(c);
        q.setSalesDirector(getSalesDirector());
        q.setBusinessConsultant(getBusinessConsultant());
        q.setPartnerReferenced(getPartnerReferenced());
        if (! q.getMatrix().getId().equals(getMatrix().getId()))
        	q.setMatrix(getMatrix());
        q.setCreateDate(getCreateDate());
        q.setEffectiveDate(getEffectiveDate());
        q.setExpireDate(getExpireDate());
        //q.setTerms(getTerms());
        q.setHasMts(getHasMts());
       
        
        // Create product categories from Ids
        List<ProductCategory> pcs = new ArrayList<ProductCategory>();
        for (Long pcId : getProductCategoryIds()) {
            ProductCategory pc = new ProductCategory();
            pc.setId(pcId);
            pcs.add(pc);
        }
        q.setProductCategories(pcs);

    }

    // Populates the form object based on an existing quote
    public void setQuote(Quote q) {
       setId(q.getId());
       Customer c = q.getCustomer();
       if (c != null) {
           // Initialize string representation based on customerTypeStr
           customerTypeStr = c.getCustomerType().getName();
           
       }
       setCustomer(c);
       setSalesDirector(q.getSalesDirector());
       setBusinessConsultant(q.getBusinessConsultant());
       setPartnerReferenced(q.getPartnerReferenced());
       setMatrix(q.getMatrix());
       // Also store reference to "old matrix" that will be used to see if this changed
       setOldMatrix(q.getMatrix());
       setCreateDate(q.getCreateDate());
       setEffectiveDate(q.getEffectiveDate());
       setExpireDate(q.getExpireDate());
       setTerms(q.getTerms());
       setAuthor(q.getAuthor());
       
       /*Boolean hasMts = q.getHasMts();
       if (hasMts == null) {
           hasMts = Boolean.FALSE;
       };*/
       Boolean hasMts = Boolean.FALSE;
       setHasMts(hasMts);
       
       // Create product category ids from product categories
       productCategoryIds = new ArrayList<Long>();
       List<ProductCategory> pcs = q.getProductCategories();
       if (pcs != null) {
           for (ProductCategory pc : pcs) {
               productCategoryIds.add(pc.getId());
           }
       }
    }    

}
