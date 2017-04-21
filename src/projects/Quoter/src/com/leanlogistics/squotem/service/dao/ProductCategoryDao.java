package com.leanlogistics.squotem.service.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.leanlogistics.squotem.model.ProductCategory;

public class ProductCategoryDao {
    
    @SuppressWarnings("unchecked")
    public List<ProductCategory> getProductCategories(Session ses) { 
        Query q = ses.createQuery("from ProductCategory pc where pc.active = :active")
                .setParameter("active", Boolean.TRUE);
        return (List<ProductCategory>) q.list();
    }

}
