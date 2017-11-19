package com.leanlogistics.squotem.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.leanlogistics.squotem.co.MetricGroupCO;
import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.exceptions.QuoteEditException;
import com.leanlogistics.squotem.exceptions.QuoterStatusChangeException;
import com.leanlogistics.squotem.model.BusinessSector;
import com.leanlogistics.squotem.model.Country;
import com.leanlogistics.squotem.model.Customer;
import com.leanlogistics.squotem.model.License;
import com.leanlogistics.squotem.model.MTSMatrix;
import com.leanlogistics.squotem.model.Matrix;
import com.leanlogistics.squotem.model.MatrixCostAdjustment;
import com.leanlogistics.squotem.model.MatrixCostItem;
import com.leanlogistics.squotem.model.MatrixMetric;
import com.leanlogistics.squotem.model.MatrixRiskAnalysis;
import com.leanlogistics.squotem.model.PerTransactionPricing;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCostAdjustment;
import com.leanlogistics.squotem.model.QuoteCostItem;
import com.leanlogistics.squotem.model.QuoteCosts;
import com.leanlogistics.squotem.model.QuoteMTSCosts;
import com.leanlogistics.squotem.model.QuoteMTSRoleCost;
import com.leanlogistics.squotem.model.QuoteMTSScopeAnswer;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.model.QuoteNote;
import com.leanlogistics.squotem.model.QuoteProduct;
import com.leanlogistics.squotem.model.QuoteQuery;
import com.leanlogistics.squotem.model.QuoteRiskAnalysis;
import com.leanlogistics.squotem.model.State;
import com.leanlogistics.squotem.model.SubscriptionPricing;
import com.leanlogistics.squotem.model.TieredPricing;
import com.leanlogistics.squotem.model.User;
import com.leanlogistics.squotem.service.manager.CatalogsManager;
import com.leanlogistics.squotem.service.manager.CustomerManager;
import com.leanlogistics.squotem.service.manager.MTSMatrixManager;
import com.leanlogistics.squotem.service.manager.MatrixManager;
import com.leanlogistics.squotem.service.manager.PricingManager;
import com.leanlogistics.squotem.service.manager.QuoteManager;
import com.leanlogistics.squotem.service.manager.UserManager;

public class SquotemServiceBean implements SquotemService {
    // TODO: Set up dependencies using Spring DI
    
    // Hibernate SessionFactory object. This is set up once for the application
    private static SessionFactory sessionFactory;
    
    static {
        Configuration config = new Configuration();
        config.configure("com/leanlogistics/squotem/resources/hibernate.cfg.xml");
        ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        sessionFactory = config.buildSessionFactory(registry);
    }

    @Override
    public User getUser(String userName, String password) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        User u = null;    
        try {
            
            tx = session.beginTransaction();        
        
            UserManager mgr = new UserManager();
            mgr.setHibernateSession(session);        
            u = mgr.getUser(userName, password);
        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        return u;        
    }

    @Override
    public List<SubscriptionPricing> getSubscriptionPricing( ) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<SubscriptionPricing> pricingList = new ArrayList<SubscriptionPricing>();    
        try {
            
            tx = session.beginTransaction();        
        
            PricingManager mgr = new PricingManager();
            mgr.setHibernateSession(session);        
            pricingList = mgr.getSubscriptionPricing();
        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        return pricingList;        
    }
    
    @Override
    public List<License> getLicensePricing( ) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<License> pricingList = new ArrayList<License>();    
        try {
            
            tx = session.beginTransaction();        
        
            PricingManager mgr = new PricingManager();
            mgr.setHibernateSession(session);        
            pricingList = mgr.getLicensePricing();
        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        return pricingList;        
    }
    
    @Override
    public List<TieredPricing> getTieredPricing() {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<TieredPricing> pricingList = new ArrayList<TieredPricing>();    
        try {
            
            tx = session.beginTransaction();        
        
            PricingManager mgr = new PricingManager();
            mgr.setHibernateSession(session);        
            pricingList = mgr.getTieredPricing();
        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        return pricingList;        
    }
    
    @Override
    public List<PerTransactionPricing> getPerTransactionPricing() {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<PerTransactionPricing> pricingList = new ArrayList<PerTransactionPricing>();    
        try {
            
            tx = session.beginTransaction();        
        
            PricingManager mgr = new PricingManager();
            mgr.setHibernateSession(session);        
            pricingList = mgr.getPerTransactionPricing();
        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        return pricingList;        
    }
    @Override
    public List<Customer> getCustomers() {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<Customer> result = null;
        
        try {
            tx = session.beginTransaction();
        
            CustomerManager mgr = new CustomerManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getCustomers();

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;
    }

    @Override
    public List<ProductCategory> getProductCategories() {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<ProductCategory> result = null;
        
        try {
            tx = session.beginTransaction();
        
            CatalogsManager mgr = new CatalogsManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getProductCategories();

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;
    }
    
    
    // Get matrix identified by id
    @Override
    public Matrix getMatrix(long id)
    {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        Matrix result = null;
        
        try {
            tx = session.beginTransaction();
        
            MatrixManager mgr = new MatrixManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getMatrix(id);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;
    }


    @Override
    public List<Matrix> getMatrices() {
        return getMatrices(false);
    }

    @Override
    public List<Matrix> getMatrices(boolean forAdmin) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<Matrix> result = null;
        
        try {
            tx = session.beginTransaction();
        
            MatrixManager mgr = new MatrixManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getMatrices(forAdmin);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;
    }
    
    

    @Override
    public List<MatrixCostItem> getMatrixCostItems(Matrix m, ProductCategory pc) {
        return getMatrixCostItems(m, pc, null);
    }
    
    
    @Override
    public List<MatrixCostItem> getMatrixCostItems(Matrix m, ProductCategory pc, List<QuoteMetric> metrics) {
        return getMatrixCostItems(m, pc, metrics, true);
    }
    
    @Override
    public List<MatrixCostItem> getMatrixCostItems(Matrix m, ProductCategory pc, List<QuoteMetric> metrics, boolean precalculateMatrixCostItemCosts) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<MatrixCostItem> result = null;
        MatrixManager mgr = new MatrixManager();
        
        try {
            tx = session.beginTransaction();
        
            mgr.setHibernateSession(session);
        
            result = mgr.getMatrixCostItems(m, pc);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        
        if (metrics != null && precalculateMatrixCostItemCosts) {
            mgr.precalculateMatrixCostItemCosts(metrics, result);
        }

        return result;                    
    }
    
    @Override
    public List<MatrixMetric> getMatrixMetrics(Matrix m) {
        return getMatrixMetrics(m, null);                    
    }
    
    @Override
    public List<MatrixMetric> getMatrixMetrics(Matrix m,
            MetricGroupCO metricGroup) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<MatrixMetric> result = null;
        MatrixManager mgr = new MatrixManager();
        
        try {
            tx = session.beginTransaction();
        
            mgr.setHibernateSession(session);
        
            result = mgr.getMatrixMetrics(m, metricGroup);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        
        return result;                    
    }

    @Override
    public List<MatrixCostAdjustment> getMatrixCostAdjustments(Matrix m,
            ProductCategory pc) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<MatrixCostAdjustment> result = null;
        
        try {
            tx = session.beginTransaction();
        
            MatrixManager mgr = new MatrixManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getMatrixCostAdjustments(m, pc);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;            
    }

    @Override
    public List<User> getUsers() {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<User> result = null;
        
        try {
            tx = session.beginTransaction();
        
            UserManager mgr = new UserManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getUsers();

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;
    }

    @Override
    public Matrix getCurrentMatrix() {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        Matrix m = null;    
        try {
            
            tx = session.beginTransaction();        
        
            MatrixManager mgr = new MatrixManager();
            mgr.setHibernateSession(session);        
            m = mgr.getCurrentMatrix();
        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        return m;        
    }

    @Override
    public void createQuote(Quote q) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            // If required, create Customer
            if (q.getCustomer().getId() < 0) {
                CustomerManager custMgr = new CustomerManager();
                custMgr.setHibernateSession(session);
                custMgr.createCustomer(q.getCustomer());                
            }
            
            if (q.getId() != null)
            	quoteMgr.deactivateQuoteRevision(q);
            
            quoteMgr.createQuote(q);        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();            
        }
    }
    
    
    
    @Override
    public void updateQuote(User usr, Quote q)  throws QuoteEditException {
        updateQuote(usr, q, false);
    }
    

    @Override
    public void updateQuote(User usr, Quote q, boolean updateMatrix)  throws QuoteEditException, HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);
            
            // If required, create Customer
            if (q.getCustomer().getId() < 0) {
                CustomerManager custMgr = new CustomerManager();
                custMgr.setHibernateSession(session);
                custMgr.createCustomer(q.getCustomer());                
            }
            
                        
            quoteMgr.updateQuote(usr, q, updateMatrix);        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;
        } 
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }
        finally {
            session.close();            
        }        
    }
    


    @Override
    public void saveMetricsForQuote(Quote q, List<QuoteMetric> quoteMetrics) throws QuoteEditException, HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.saveMetricsForQuote(q, quoteMetrics);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;
        } 
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;
        } 
        finally {
            session.close();            
        }        
    }
    
    

    @Override
    public List<QuoteMetric> getMetricsForQuote(Quote q) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<QuoteMetric> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getMetricsForQuote(q);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;    
    }

    
    @Override
    public List<QuoteCostItem> getCostItemsForQuote(Quote q) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<QuoteCostItem> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getCostItemsForQuote(q);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;      
    }

    @Override
    public List<Quote> getDraftQuotesForUser(User u) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<Quote> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getDraftQuotesForUser(u);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;
    }
    
    

    @Override
    public List<Quote> queryQuotes(QuoteQuery query) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<Quote> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.queryQuotes(query);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;
    }

    @Override
    public Quote getQuote(User usr, long id) {
        return getQuote(usr, id, false);
    }

    @Override
    public Quote getQuote(User usr, long id, boolean calculateCosts) {
        Session session = sessionFactory.openSession();
        // Prevent any update of changed data
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        Quote result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getQuote(usr, id, calculateCosts);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;
    }
    
    

    @Override
    public void saveCostItemsForQuote(Quote q,
            List<QuoteCostItem> quoteCostItems) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.saveCostItemsForQuote(q, quoteCostItems);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }           
    }
    
    @Override
    public void updateMetricsForQuote(Quote q,
            List<QuoteMetric> deleteMetrics,
            List<QuoteMetric> saveMetrics) throws QuoteEditException, HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.deleteMetricsForQuote(q, deleteMetrics);
            quoteMgr.saveMetricsForQuote(q, saveMetrics);
            quoteMgr.updateQuoteModifiedDate(q);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;
        }
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }                
        finally {
            session.close();            
        }                     
    }
    
    
    

    @Override
    public void updateMetricsAndCostItemsForQuote(Quote q,
            List<QuoteMetric> deleteMetrics, List<QuoteMetric> saveMetrics,
            List<QuoteCostItem> deleteCostItems,
            List<QuoteCostItem> saveCostItems) throws QuoteEditException, HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.deleteMetricsForQuote(q, deleteMetrics);
            quoteMgr.saveMetricsForQuote(q, saveMetrics);
            
            quoteMgr.deleteCostItemsForQuote(q, deleteCostItems);
            quoteMgr.saveCostItemsForQuote(q, saveCostItems);
            
            // Also update terms at this point
            quoteMgr.updateQuoteTerms(q);
            
            quoteMgr.updateQuoteModifiedDate(q);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;
        }
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }                
        finally {
            session.close();            
        }                             
    }

    @Override
    public void deleteCostItemsForQuote(Quote q,
            List<QuoteCostItem> quoteCostItems) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.deleteCostItemsForQuote(q, quoteCostItems);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }            
    }

    @Override
    public void updateCostItemsForQuote(Quote q,
            List<QuoteCostItem> deleteCostItems,
            List<QuoteCostItem> saveCostItems) throws QuoteEditException {
        // Checking Quote status in this layer, since deleting / save cost items is allowed for 
        // Quotes in statuses other that DRAFT, for freezing, unfreezing
        if (! QuoteWorkflowStatusCO.DRAFT.equals(q.getStatus())) {
            throw new QuoteEditException("Invalid operation");
        }
        
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.deleteCostItemsForQuote(q, deleteCostItems);
            quoteMgr.saveCostItemsForQuote(q, saveCostItems);
            quoteMgr.updateQuoteModifiedDate(q);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }             
    }

    @Override
    // freeze calculated fields
    public void freezeQuote(Quote q) throws QuoteEditException, HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            //freeze quote cost items, this was before on changing status
            quoteMgr.freezeQuote(q);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;
        }
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }                
        finally {
            session.close();            
        }                             
    }

    @Override
    public List<QuoteCostAdjustment> getCostAdjustmentsForQuote(Quote q) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<QuoteCostAdjustment> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getCostAdjustmentsForQuote(q);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;      
    }
    
    

    @Override
    public List<QuoteProduct> getProductsForQuote(Quote q) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<QuoteProduct> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getProductsForQuote(q);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;      
    }

    @Override
    public void updateCostAdjustmentsForQuote(Quote q,
            List<QuoteCostAdjustment> deleteCostAdjustments,
            List<QuoteCostAdjustment> saveCostAdjustments) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.deleteCostAdjustmentsForQuote(q, deleteCostAdjustments);
            quoteMgr.saveCostAdjustmentsForQuote(q, saveCostAdjustments);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }                     
    }

    @Override
    public void updateCostItemsAndAdjustmentsForQuote(Quote q,
            List<QuoteCostItem> deleteCostItems,
            List<QuoteCostItem> saveCostItems,
            List<QuoteCostAdjustment> deleteCostAdjustments,
            List<QuoteCostAdjustment> saveCostAdjustments) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    

            quoteMgr.deleteCostItemsForQuote(q, deleteCostItems);
            quoteMgr.saveCostItemsForQuote(q, saveCostItems);           
            quoteMgr.deleteCostAdjustmentsForQuote(q, deleteCostAdjustments);
            quoteMgr.saveCostAdjustmentsForQuote(q, saveCostAdjustments);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }                     
    }

	@Override
	public List<Country> getCountries() {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<Country> result = null;
        
        try {
            tx = session.beginTransaction();
        
            CatalogsManager mgr = new CatalogsManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getCountries();

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;      
	}

	@Override
	public List<State> getCountryStates(Country country) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<State> result = null;
        
        try {
            tx = session.beginTransaction();
        
            CatalogsManager mgr = new CatalogsManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getCountryStates(country);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;      
	}
    
	@Override
	public List<State> getStates() {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<State> result = null;
        
        try {
            tx = session.beginTransaction();
        
            CatalogsManager mgr = new CatalogsManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getStates();

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;      
	}

    @Override
    public List<BusinessSector> getBusinessSectors() {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<BusinessSector> result = null;
        
        try {
            tx = session.beginTransaction();
        
            CatalogsManager mgr = new CatalogsManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getBusinessSectors();

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;      
    }

    @Override
    public QuoteCosts calculateQuoteCosts(Matrix m, List<QuoteCostItem> quoteCostItems,
            List<QuoteCostAdjustment> quoteCostAdjustments,
            List<QuoteMetric> quoteMetrics,
            List<ProductCategory> productCategories) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        QuoteCosts result = null;
        
        try {
            tx = session.beginTransaction();
        
            MatrixManager mgr = new MatrixManager();
            mgr.setHibernateSession(session);
        
            result = mgr.calculateQuoteCosts(m, quoteCostItems, quoteCostAdjustments, quoteMetrics, productCategories);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;          
    }


    @Override
    public List<QuoteMTSScopeAnswer> getMTSScopeAnswersForQuote(Quote q) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<QuoteMTSScopeAnswer> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getMTSScopeAnswersForQuote(q);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;      
    }

    @Override
    public List<QuoteMTSRoleCost> getMTSRoleCostsForQuote(Quote q) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<QuoteMTSRoleCost> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getMTSRoleCostsForQuote(q);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;      
    }
    

    @Override
    public MTSMatrix getMTSMatrix(long mtsMatrixId) {        
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        MTSMatrix result = null;
        MTSMatrixManager mgr = new MTSMatrixManager();
        
        try {
            tx = session.beginTransaction();
        
            mgr.setHibernateSession(session);
        
            result = mgr.getMTSMatrix(mtsMatrixId);
            if (result != null) {                
                result.setScopeQuestions(mgr.getMTSMatrixScopeQuestions(mtsMatrixId));
                result.setRoleCosts(mgr.getMTSMatrixRoleCosts(mtsMatrixId));
                result.setRoleRelations(mgr.getMTSMatrixRoleRelations(mtsMatrixId));
            }

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        
        return result;                            
    }

    @Override
    public QuoteMTSCosts calculateQuoteMTSCosts(MTSMatrix mtsMatrix, Double annualLoadCount,
            List<QuoteMTSScopeAnswer> scopeAnswers,
            List<QuoteMTSRoleCost> roleCosts) {
        
        MTSMatrixManager mgr = new MTSMatrixManager();
        return mgr.calculateQuoteMTSCosts(mtsMatrix, annualLoadCount, scopeAnswers, roleCosts);
    }

    @Override
    public void updateMTSDataForQuote(Quote q,
                                        List<QuoteMTSScopeAnswer> deleteMTSScopeAnswers,
                                        List<QuoteMTSScopeAnswer> saveMTSScopeAnswers,
                                        List<QuoteMTSRoleCost> saveMTSRoleCosts) throws QuoteEditException, HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);
            
            quoteMgr.updateMTSFieldsForQuote(q);
            quoteMgr.deleteMTSScopeAnswersForQuote(q, deleteMTSScopeAnswers);
            quoteMgr.saveMTSScopeAnswersForQuote(q, saveMTSScopeAnswers);
            quoteMgr.saveMTSRoleCostsForQuote(q,saveMTSRoleCosts);
            quoteMgr.updateQuoteModifiedDate(q);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;
        }
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }        
        finally {
            session.close();            
        }                             
    }

    @Override
    public void saveCustomerQuoteValuesForQuote(Quote q,
            List<QuoteProduct> quoteProducts) throws QuoteEditException, HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);
            
            quoteMgr.updateQuoteMtsCustomerQuote(q);
            quoteMgr.saveProductsForQuote(q, quoteProducts);
                        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;
        }
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }                
        finally {
            session.close();            
        }                             
        
    }

    @Override
    public Quote submitQuoteForReview(User usr, long id)
            throws QuoterStatusChangeException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Quote result = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);
            
            result = quoteMgr.submitQuoteForReview(usr, id); 
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            
        }
        catch (QuoterStatusChangeException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        } catch (QuoteEditException e) {
			e.printStackTrace();
            if (tx!=null) tx.rollback();
		}        
        finally {
            session.close();            
        }    
        return result;
    }
    
    

    @Override
    public Quote updateQuoteStatus(User usr, long id,
            QuoteWorkflowStatusCO newStatus) throws QuoterStatusChangeException {
        return updateQuoteStatus(usr, id, newStatus, null);
    }

    @Override
    public Quote updateQuoteStatus(User usr, long id,
            QuoteWorkflowStatusCO newStatus, String comment) throws QuoterStatusChangeException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Quote result = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);
            
            result = quoteMgr.updateQuoteStatus(usr, id, newStatus, comment);
                        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } 
        catch (QuoterStatusChangeException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        } catch (QuoteEditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            if (tx!=null) tx.rollback();
		}                
        finally {
            session.close();            
        }    
        return result;
    }

    @Override
    public void updateQuoteBudgetaryCost(Quote q) throws QuoteEditException, HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.updateQuoteBudgetaryCost(q);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }                
        finally {
            session.close();            
        }                     
        
    }

    @Override
    public void updateQuoteMarketCost(Quote q) throws QuoteEditException, HibernateException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.updateQuoteMarketCost(q);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }                
        finally {
            session.close();            
        }                     
        
    }

    @Override
    public Quote createRevision(User usr, long baseQuoteId)
            throws QuoterStatusChangeException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Quote result = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);
            
            result = quoteMgr.createRevision(usr, baseQuoteId);
                        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } 
        catch (QuoterStatusChangeException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }                
        finally {
            session.close();            
        }    
        return result;
    }

    @Override
    public Quote saveNoteForQuote(User usr, long quoteId, String comment) throws QuoteEditException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Quote result = null;
        try {
            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);
            
            result = quoteMgr.saveNoteForQuote(usr, quoteId, comment);
                        
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } 
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }                
        finally {
            session.close();            
        }    
        return result;
    }

    @Override
    public List<QuoteNote> getNotesForQuote(Quote q) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<QuoteNote> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getNotesForQuote(q);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;              
    }

    @Override
    public List<QuoteRiskAnalysis> getRiskAnalysisItemsForQuote(Quote q) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<QuoteRiskAnalysis> result = null;
        
        try {
            tx = session.beginTransaction();
        
            QuoteManager mgr = new QuoteManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getRiskAnalysisItemsForQuote(q);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;    
    }

    @Override
    public List<MatrixRiskAnalysis> getMatrixRiskAnalysisItems(Matrix m) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<MatrixRiskAnalysis> result = null;
        MatrixManager mgr = new MatrixManager();
        
        try {
            tx = session.beginTransaction();
        
            mgr.setHibernateSession(session);
        
            result = mgr.getMatrixRiskAnalysisItems(m);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }
        
        return result;                    
    }

    @Override
    public void updateRiskAnalysisItemsForQuote(Quote q,
            List<QuoteRiskAnalysis> deleteItems,
            List<QuoteRiskAnalysis> saveItems) throws QuoteEditException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {            
            tx = session.beginTransaction();        
        
            QuoteManager quoteMgr = new QuoteManager();
            quoteMgr.setHibernateSession(session);    
            
            quoteMgr.deleteRiskAnalysisItemsForQuote(q, deleteItems);
            quoteMgr.saveRiskAnalysisItemsFor(q, saveItems);
            quoteMgr.updateQuoteModifiedDate(q);
            
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }
        catch (QuoteEditException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            throw e;            
        }                
        finally {
            session.close();            
        }                     
        
    }

    @Override
    public void calculateQuoteBudgetaryCosts(Quote q) {        
        MatrixManager mgr = new MatrixManager();
        mgr.precalculateBudgetaryCosts(q);        
    }

    @Override
    public List<User> getNotificationList(QuoteWorkflowStatusCO status) {
        Session session = sessionFactory.openSession();
        session.setDefaultReadOnly(true);
        Transaction tx = null;
        List<User> result = null;
        
        try {
            tx = session.beginTransaction();
        
            UserManager mgr = new UserManager();
            mgr.setHibernateSession(session);
        
            result = mgr.getNotificationList(status);

            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        } finally {
            session.close();            
        }

        return result;
    }


}
