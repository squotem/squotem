package com.leanlogistics.squotem.service.manager;

import java.util.List;

import com.leanlogistics.squotem.model.License;
import com.leanlogistics.squotem.model.PerTransactionPricing;
import com.leanlogistics.squotem.model.SubscriptionPricing;
import com.leanlogistics.squotem.model.TieredPricing;
import com.leanlogistics.squotem.service.dao.PricingDao;

public class PricingManager extends BaseManager {
    
    private PricingDao dao;

    public PricingManager() {
        dao = new PricingDao();
    }
    
    public List<SubscriptionPricing> getSubscriptionPricing() {
        return dao.getSubscriptionPricing(hibernateSession);
    }
    
    public List<License> getLicensePricing() {
        return dao.getLicensePricing(hibernateSession);
    }
    
    public List<TieredPricing> getTieredPricing() {
        return dao.getTieredPricing(hibernateSession);
    }
    
    public List<PerTransactionPricing> getPerTransactionPricing() {
        return dao.getPerTransactionPricing(hibernateSession);
    }
    
}
