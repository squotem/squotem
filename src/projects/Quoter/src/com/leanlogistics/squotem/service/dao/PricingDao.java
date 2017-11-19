package com.leanlogistics.squotem.service.dao;

import java.util.List;

import org.hibernate.Session;

import com.leanlogistics.squotem.model.License;
import com.leanlogistics.squotem.model.PerTransactionPricing;
import com.leanlogistics.squotem.model.SubscriptionPricing;
import com.leanlogistics.squotem.model.TieredPricing;

public class PricingDao {
    
    /** Get all customers */
    @SuppressWarnings("unchecked")
    public List<SubscriptionPricing> getSubscriptionPricing(Session ses) {
        return (List<SubscriptionPricing>) ses.createQuery("from SubscriptionPricing").list();
    }
    
    /** Get all customers */
    @SuppressWarnings("unchecked")
    public List<License> getLicensePricing(Session ses) {
        return (List<License>) ses.createQuery("from License").list();
    }
    
    
    @SuppressWarnings("unchecked")
    public List<TieredPricing> getTieredPricing(Session ses) {
        return (List<TieredPricing>) ses.createQuery("from TieredPricing").list();
    }
    
    /** Get all customers */
    @SuppressWarnings("unchecked")
    public List<PerTransactionPricing> getPerTransactionPricing(Session ses) {
        return (List<PerTransactionPricing>) ses.createQuery("from PerTransactionPricing").list();
    }

}
