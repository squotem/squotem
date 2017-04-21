package com.leanlogistics.squotem.test.service;


import java.util.ArrayList;
import java.util.List;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import com.leanlogistics.squotem.co.MetricDataTypeCO;
import com.leanlogistics.squotem.co.MetricGroupCO;
import com.leanlogistics.squotem.costmodelutils.CostModelEvaluationResult;
import com.leanlogistics.squotem.costmodelutils.CostModelEvaluator;
import com.leanlogistics.squotem.costmodelutils.CostModelParser;
import com.leanlogistics.squotem.exceptions.QuoteEditException;
import com.leanlogistics.squotem.model.BusinessSector;
import com.leanlogistics.squotem.model.CostModel;
import com.leanlogistics.squotem.model.Country;
import com.leanlogistics.squotem.model.MTSMatrix;
import com.leanlogistics.squotem.model.MTSMatrixRoleCost;
import com.leanlogistics.squotem.model.MTSMatrixRoleRelation;
import com.leanlogistics.squotem.model.MTSMatrixScopeQuestion;
import com.leanlogistics.squotem.model.MatrixMetric;
import com.leanlogistics.squotem.model.MatrixRiskAnalysis;
import com.leanlogistics.squotem.model.Metric;
import com.leanlogistics.squotem.model.QuoteMTSRoleCost;
import com.leanlogistics.squotem.model.QuoteMTSScopeAnswer;
import com.leanlogistics.squotem.model.QuoteNote;
import com.leanlogistics.squotem.model.QuoteProduct;
import com.leanlogistics.squotem.model.QuoteRiskAnalysis;
import com.leanlogistics.squotem.model.State;
import com.leanlogistics.squotem.model.Customer;
import com.leanlogistics.squotem.model.Matrix;
import com.leanlogistics.squotem.model.MatrixCostAdjustment;
import com.leanlogistics.squotem.model.MatrixCostItem;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCostAdjustment;
import com.leanlogistics.squotem.model.QuoteCostItem;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.model.User;
import com.leanlogistics.squotem.service.SquotemServiceBean;

import junit.framework.TestCase;

public class SquotemServiceBeanTest extends TestCase {
    
    // private SessionFactory sessionFactory;

    public void setUp() throws Exception {
        /*Configuration config = new Configuration();
        config.configure("com/leanlogistics/squotem/resources/hibernate.cfg.xml");
        ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        sessionFactory = config.buildSessionFactory(registry);*/        
    }

    public void tearDown() throws Exception {
        /*if ( sessionFactory != null ) {
            sessionFactory.close();
        }  */      
    }
    
    public void testJevalEvaluator() {
        System.out.println("***Running testJevalEvaluator()");
        Evaluator evaluator = new Evaluator();
        try {
            evaluator.putVariable("FOO", "10");
            String result = evaluator.evaluate("(2 + #{FOO}) * 2");
            System.out.println("***Result: " + result);
        }
        catch (EvaluationException e) {
            e.printStackTrace(System.out);
        }
    }
    
    public void testCostModelEvaluator() {
        System.out.println("***Running testCostModelEvaluator()");
        String cmContent = "<cost-model>" 
                          + " <value eval=\"#{FS_TOTAL}\"> "
                          + "    <tier from=\"0\" to=\"25\" expr=\"10 * 2\" /> "
                          + "    <tier from=\"26\" to=\"50\" expr=\"12 * 2\" /> "
                          + " </value> "
                          + " <value eval=\"#{FS_TOTAL}\" operator=\"+\" if=\"#{INBOUND} == 'TRUE'\">"
                          + "    <tier from=\"0\" to=\"25\" expr=\"20 * 2\" /> "
                          + "    <tier from=\"26\" to=\"50\" expr=\"22 * 2\" /> "
                          + " </value> "                          
                          + "</cost-model>";
        
        CostModel cm = new CostModel();
        cm.setXmlContent(cmContent);
        CostModelParser.setCostModelRulesInCostModel(cm);
        List<QuoteMetric> quoteMetrics = new ArrayList<QuoteMetric>();
        // FS_TOTAL = 26
        Metric m = new Metric();
        m.setMnemonic("FS_TOTAL");
        // Important, for testing purposes!
        m.setDataType(MetricDataTypeCO.NUMERIC);        
        QuoteMetric qm = new QuoteMetric();
        qm.setMetric(m);
        qm.setMetricValue("16");
        quoteMetrics.add(qm);
        
        // INBOUND = TRUE
        m = new Metric();
        m.setMnemonic("INBOUND");
        // Important, for testing purposes!
        m.setDataType(MetricDataTypeCO.BOOLEAN);
        qm = new QuoteMetric();
        qm.setMetric(m);
        qm.setMetricValue("TRUE");
        quoteMetrics.add(qm);
        
        // Add here any additional variable used in evaluation
        CostModelEvaluationResult cmer = CostModelEvaluator.evaluateCostModel(cm.getCostModelObject(), quoteMetrics);
        System.out.println("***Result: " + cmer.getValue());
        System.out.println("***Error Message: " + cmer.getError());
        
    }

    public void testGetUser() {
        System.out.println("***Running testGetUser()");
        SquotemServiceBean service = new SquotemServiceBean();
        User u = service.getUser("carias", "carias");
        assertNotNull("User not found!", u);
        if (u != null) {
            System.out.println("***User: " + u.getFirstName() + " " + u.getLastName() + " , type: " + u.getUserType());
        }
    }
    
    public void testGetAllCustomers() {
        System.out.println("***Running testGetAllCustomers()");
        SquotemServiceBean service = new SquotemServiceBean();
        List<Customer> entries = service.getCustomers();
        assertNotNull("Customers not found", entries);
        for (Customer e : entries) {
            System.out.println("***Customer: " + e.getId() + " , " + e.getName()  + " , "  + e.getBusinessSector() + " , "  + e.getCustomerType());
            BusinessSector bs = e.getBusinessSector();
            if (bs != null) {
                System.out.println("... Business Sector: " + bs.getDescription());
            }
        
        }        
    }
    
    public void testGetAllProductCategories() {
        System.out.println("***Running testGetAllProductCategories()");
        SquotemServiceBean service = new SquotemServiceBean();
        List<ProductCategory> entries = service.getProductCategories();
        assertNotNull("Product Categories not found", entries);
        for (ProductCategory e : entries) {
            System.out.println("***ProductCategory: " + e.getId() + " , " + e.getDescription());
        }        
    }
    
    public void testGetAllUsers() {
        System.out.println("***Running testGetAllUsers()");
        SquotemServiceBean service = new SquotemServiceBean();
        List<User> entries = service.getUsers();
        assertNotNull("Users not found", entries);
        for (User e : entries) {
            System.out.println("***User: " + e.getFirstName() + " " + e.getLastName() + " , type: " + e.getUserType());
        }                
    }
    
    public void testGetRegularUserMatrices() {
        System.out.println("***Running testGetRegularUserMatrices()");
        SquotemServiceBean service = new SquotemServiceBean();
        List<Matrix> entries = service.getMatrices(false);
        assertNotNull("Matrices not found", entries);
        for (Matrix e : entries) {
            System.out.println("***Matrix: " + e.getName() + " , type: " + e.getDescription() + " ,  status: " + e.getStatus());
        }                        
    }
    
    public void testGetAdminMatrices() {
        System.out.println("***Running testGetAdminMatrices()");
        SquotemServiceBean service = new SquotemServiceBean();
        List<Matrix> entries = service.getMatrices(true);
        assertNotNull("Matrices not found", entries);
        for (Matrix e : entries) {
            System.out.println("***Matrix: " + e.getName() + " , type: " + e.getDescription() + " ,  status: " + e.getStatus());
            CostModel cm = e.getBudgetaryImplCostModel();
            if (cm != null) {
                System.out.println("*** Impl Budgetary Cost Model: " + cm.getName() + ", xml: " + cm.getXmlContent());
            }
            cm = e.getBudgetaryMonthlyCostMode();
            if (cm != null) {
                System.out.println("*** Mobnthly Budgetary Cost Model: " + cm.getName() + ", xml: " + cm.getXmlContent());
            }            
        }                        
    }
    
    
    public void testGetMatrixCostItems() {
        System.out.println("***Running testGetMatrixCostItems()");
        SquotemServiceBean service = new SquotemServiceBean();
        Matrix m = new Matrix();
        m.setId(1l); // 2013 Matrix...
        ProductCategory pc = new ProductCategory();
        pc.setId(1l); // TMS
        List<MatrixCostItem> entries = service.getMatrixCostItems(m, pc);
        assertNotNull("Matrix cost items not found", entries);
        for (MatrixCostItem e : entries) {
            System.out.println("***MatrixCostItem: category "  + e.getCostItem().getItemCategory().getDescription() 
                    + " , description: " + e.getCostItem().getDescription() 
                    + " , feeCategory: " + e.getFeeCategory().getName()
                    + " , simpleCost: " + e.getSimpleCost());
            if (e.getCostModel() != null) {
                System.out.println("... and costModel: " + e.getCostModel().getName());
                System.out.println("... costModel XML content: " + e.getCostModel().getXmlContent());
                
            }
        }                                
    }
    
    public void testGetMatrixCostAdjustments() {
        System.out.println("***Running testGetMatrixCostAdjustments()");
        SquotemServiceBean service = new SquotemServiceBean();
        Matrix m = new Matrix();
        m.setId(1l); // 2013 Matrix...
        ProductCategory pc = new ProductCategory();
        pc.setId(2l); // TMS
        List<MatrixCostAdjustment> entries = service.getMatrixCostAdjustments(m, pc);
        assertNotNull("Matrix cost adjustments not found", entries);
        for (MatrixCostAdjustment e : entries) {
            System.out.println("***MatrixCostAdjustment: description: " + e.getCostAdjustment().getDescription() );
        }                                        
    }
    
    public void testGetMatrixMetrics() {
        System.out.println("***Running testGetMatrixMetrics()");
        SquotemServiceBean service = new SquotemServiceBean();
        Matrix m = new Matrix();
        m.setId(2l);         
        List<MatrixMetric> entries = service.getMatrixMetrics(m);
        assertNotNull("Matrix Metrics not found", entries);
        for (MatrixMetric e : entries) {
            System.out.println("***MatrixMetric: category "  + e.getMetric().getCategory().getDescription()
                    + " , group: " + e.getMetric().getCategory().getMetricGroup()
                    + " , description: " + e.getMetric().getDescription() 
                    + " , dataType: " + e.getMetric().getDataType()
                    + " , total?: " + e.getMetric().getIsTotal()
                    + " , defaultValue: " + e.getDefaultValue()
                    + " , required?: " + e.getRequired()
                    );            
        }                        
    }
       
    public void testGetMatrixMetricsForGroup() {
        System.out.println("***Running testGetMatrixMetricsForGroup()");
        SquotemServiceBean service = new SquotemServiceBean();
        Matrix m = new Matrix();
        m.setId(2l);         
        List<MatrixMetric> entries = service.getMatrixMetrics(m, MetricGroupCO.BUSINESS_SCOPING);
        assertNotNull("Matrix Metrics not found", entries);
        for (MatrixMetric e : entries) {
            System.out.println("***MatrixMetric: category "  + e.getMetric().getCategory().getDescription()
                    + " , group: " + e.getMetric().getCategory().getMetricGroup()
                    + " , description: " + e.getMetric().getDescription() 
                    + " , dataType: " + e.getMetric().getDataType()
                    + " , total?: " + e.getMetric().getIsTotal()
                    + " , defaultValue: " + e.getDefaultValue()
                    + " , required?: " + e.getRequired()
                    );            
        }                        
    }

    
    public void testGetQuotesForUser() {
        System.out.println("***Running testGetQuotesForUser()");
        SquotemServiceBean service = new SquotemServiceBean();
        User u = new User();
        u.setId(2l); // carias
        List<Quote> entries = service.getDraftQuotesForUser(u);
        assertNotNull("Quotes for user not found", entries);
        for (Quote e : entries) {
            System.out.println("***Quote, customer: " + e.getCustomer().getName() + " , author: " + e.getAuthor().getFirstName() 
                    + " ,  date: " + e.getCreateDate()
                    + " ,  revision: " + e.getRevisionNumber()
                    + " , productCategories: " + e.getProductCategories()
                    + " , status: " + e.getStatus()
                    + " , effectiveDate: " + e.getEffectiveDate()
                    + " , expireDate: " + e.getExpireDate()
                    + " , terms: " + e.getTerms());
        }                        
    }    
    
    public void testGetQuote() {
        System.out.println("***Running testGetQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        User u = new User();
        u.setId(2l);
        Quote e = service.getQuote(u, 6l);
        assertNotNull("Quotes not found", e);
        System.out.println("***Quote, customer: " + e.getCustomer().getName() + " , author: " + e.getAuthor().getFirstName() 
                + " ,  date: " + e.getCreateDate()
                + " ,  revision: " + e.getRevisionNumber()
                + " , productCategories: " + e.getProductCategories()
                + " , partnerReferenced: " + e.getPartnerReferenced()
                + " , status: " + e.getStatus()
                + " , effectiveDate: " + e.getEffectiveDate()
                + " , expireDate: " + e.getExpireDate()
                + " , terms: " + e.getTerms());
    }
    
    /* public void testSaveMetricsForQuote() {
        System.out.println("***Running testSaveMetricsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(1l);
        List<QuoteMetric> qms = new ArrayList<QuoteMetric>();
        QuoteMetric qm = new QuoteMetric();
        Metric m = new Metric();
        m.setId(16l); // LTL
        qm.setMetric(m);
        qm.setMetricValue(5.1);
        qms.add(qm);
        
        qm = new QuoteMetric();
        m = new Metric();
        m.setId(18l); // IM
        qm.setMetric(m);
        qm.setMetricValue(18.2);
        qms.add(qm);
        
        service.saveMetricsForQuote(q, qms);
        
    }*/
    
    public void testGetMetricsForQuote() {
        System.out.println("***Running testGetMetricsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(1l);
        List<QuoteMetric> entries = service.getMetricsForQuote(q);
        assertNotNull("Quote metrics for quote not found", entries);
        for (QuoteMetric e : entries) {
            System.out.println("***Quote Metrics: " + e.getMetric().getDescription() + " , value: " + e.getMetricValue());
        }                        
    }
    
    public void testGetCostItemsForQuote() {
        System.out.println("***Running testGetCostItemsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(1l);
        List<QuoteCostItem> entries = service.getCostItemsForQuote(q);
        assertNotNull("Quote items for quote not found", entries);
        for (QuoteCostItem e : entries) {
            System.out.println("***Quote Items: " + e.getCostItem().getDescription() + " , enabled: " + e.getEnabled());
        }                                
    }
    
    public void testGetCostAdjustmentsForQuote() {
        System.out.println("***Running testGetCostAdjustmentsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(15l); // 2013-12-11 11:05:24.0
        List<QuoteCostAdjustment> entries = service.getCostAdjustmentsForQuote(q);
        assertNotNull("Quote items for quote not found", entries);
        for (QuoteCostAdjustment e : entries) {
            System.out.println("***Quote Adjustment: " + e.getCostAdjustment().getDescription() + " , comment: " + e.getComment());
        }                                        
    }
    
    public void testGetProductsForQuote() {
        System.out.println("***Running testGetProductsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(1l);
        List<QuoteProduct> entries = service.getProductsForQuote(q);
        assertNotNull("Products for quote not found", entries);
        for (QuoteProduct e : entries) {
            System.out.println("***Quote Product: " + e.getProductCategory().getDescription() );
        }                                
    }
    
    
    /*public void testCreateCostAdjustmentsForQuote() {
        System.out.println("***Running testCreateCostAdjustmentsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(15l); // 2013-12-11 11:05:24.0
        // Empty list to delete... 
        List<QuoteCostAdjustment> emptyList = new ArrayList<QuoteCostAdjustment>();
        // Add a couple of cost adjustments
        List<QuoteCostAdjustment> updateList = new ArrayList<QuoteCostAdjustment>();
        QuoteCostAdjustment qca = new QuoteCostAdjustment();
        CostAdjustment ca = new CostAdjustment();
        ca.setId(1l);
        qca.setComment("My first adjustment from unit test");
        qca.setCost(10d);
        qca.setFeeCategory(FeeCategoryCO.IMPL);
        qca.setCostAdjustment(ca);
        updateList.add(qca);
        qca = new QuoteCostAdjustment();
        qca.setComment("My first adjustment from unit test");
        qca.setCost(2d);
        qca.setFeeCategory(FeeCategoryCO.MONTHLY);
        qca.setCostAdjustment(ca);
        updateList.add(qca);
        qca = new QuoteCostAdjustment();
        ca = new CostAdjustment();
        ca.setId(2l);
        qca.setComment("My second adjustment from unit test");
        qca.setCost(20d);
        qca.setFeeCategory(FeeCategoryCO.IMPL);
        qca.setCostAdjustment(ca);
        updateList.add(qca);
        qca = new QuoteCostAdjustment();
        qca.setComment("My second adjustment from unit test");
        qca.setCost(4d);
        qca.setFeeCategory(FeeCategoryCO.MONTHLY);
        qca.setCostAdjustment(ca);
        updateList.add(qca);
        service.updateCostAdjustmentsForQuote(q, emptyList, updateList);
    }*/
    
    
    /*public void testUpdateCostAdjustmentsForQuote() {
        System.out.println("***Running testUpdateCostAdjustmentsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(15l); // 2013-12-11 11:05:24.0
        // list to delete... 
        List<QuoteCostAdjustment> deleteList = new ArrayList<QuoteCostAdjustment>();
        QuoteCostAdjustment qca = new QuoteCostAdjustment();
        qca.setId(3l);
        deleteList.add(qca);
        qca = new QuoteCostAdjustment();
        qca.setId(4l);
        deleteList.add(qca);
        
        // Add a third couple of cost adjustments
        List<QuoteCostAdjustment> updateList = new ArrayList<QuoteCostAdjustment>();
        qca = new QuoteCostAdjustment();
        CostAdjustment ca = new CostAdjustment();
        ca.setId(3l);
        qca.setComment("My third adjustment from unit test");
        qca.setCost(30d);
        qca.setFeeCategory(FeeCategoryCO.IMPL);
        qca.setCostAdjustment(ca);
        updateList.add(qca);
        qca = new QuoteCostAdjustment();
        qca.setComment("My third adjustment from unit test");
        qca.setCost(3d);
        qca.setFeeCategory(FeeCategoryCO.MONTHLY);
        qca.setCostAdjustment(ca);
        updateList.add(qca);
        service.updateCostAdjustmentsForQuote(q, deleteList, updateList);
    } */  
    
    /*public void testCreateQuoteWithExistingCustomer() {
    System.out.println("***Running testCreateQuoteWithExistingCustomer()");
    SquotemServiceBean service = new SquotemServiceBean();
    Quote q = new Quote();
    Customer c = new Customer();
    // Lanko
    c.setId(2l);
    q.setCustomer(c);
    User u = new User();
    // Carias 
    u.setId(2l);
    q.setAuthor(u);
    q.setSalesDirector("John Jones SD");
    q.setBusinessConsultant("Walter Homes BC");
    q.setPartnerReferenced("Joyce Beaber PR");
    // 2013 Test Matrix
    Matrix m = new Matrix();
    m.setId(1l);
    q.setMatrix(m);
    // TODO: effective and expire date
    List<ProductCategory> pcs = new ArrayList<ProductCategory>();
    ProductCategory pc = new ProductCategory();
    pc.setId(1l); // Only TMS
    pcs.add(pc);
    q.setProductCategories(pcs);
    service.createQuote(q);
}*/    
    
   
   
   /*public void testCreateQuoteWithNewCustomer() {
        System.out.println("***Running testCreateQuoteWithNewCustomer()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        Customer c = new Customer();
        // A new customer
        c.setName("Customer Created from Unit Test");
        // TODO: fix
        c.setBusinessSector("Dynamite production");
        c.setCity("Genesis");
        // TODO: Fix
        c.setState("IL");
        // TODO: Add customer type
        c.setProjectSponsor("Phil Colins");
        c.setSponsorPhone("202-555-0160");
        
        q.setCustomer(c);
        User u = new User();
        // Carias 
        u.setId(2l);
        q.setAuthor(u);
        q.setSalesDirector("Thalis Hearts SD");
        q.setBusinessConsultant("Billy Jean BC");
        q.setPartnerReferenced("Leonard Bernstein PR");
        // 2013 Test Matrix
        Matrix m = new Matrix();
        m.setId(1l);
        q.setMatrix(m);
        // TODO: effective and expire date
        List<ProductCategory> pcs = new ArrayList<ProductCategory>();
        ProductCategory pc = new ProductCategory();
        pc.setId(1l); // TMS ...
        pcs.add(pc);
        pc = new ProductCategory();
        pc.setId(2l); // ... and Procurement
        pcs.add(pc);
        
        q.setProductCategories(pcs);
        service.createQuote(q);
   }*/
    

  /*  public void testUpdateQuote() {
         System.out.println("***Running testUpdateQuote()");
         SquotemServiceBean service = new SquotemServiceBean();
         Quote q = new Quote();
         q.setId(6l);
         q.setSalesDirector("Thalis Hearts SD - ufut2");
         q.setBusinessConsultant("Billy Jean BC - ufut2");
         q.setPartnerReferenced("Leonard Bernstein PR - ufut2");
         service.updateQuote(q);
     }*/
         
   /*
    public void  testCreateRevisionForExistingQuote() {
       System.out.println("***Running testCreateRevisionForExistingQuote()");
       SquotemServiceBean service = new SquotemServiceBean();
       Quote q = new Quote();
       // Existing quote
       q.setQuoteNumber(3l);
       Customer c = new Customer();
       // Lanko
       c.setId(2l);
       q.setCustomer(c);
       User u = new User();
       // Carias 
       u.setId(2l);
       q.setAuthor(u);
       q.setSalesDirector("John Jones SD");
       q.setBusinessConsultant("Walter Homes BC");
       q.setPartnerReferenced("Joyce Beaber PR");
       // 2013 Test Matrix
       Matrix m = new Matrix();
       m.setId(1l);
       q.setMatrix(m);
       List<ProductCategory> pcs = new ArrayList<ProductCategory>();
       ProductCategory pc = new ProductCategory();
       pc.setId(1l); // Only TMS
       pcs.add(pc);
       q.setProductCategories(pcs);
       service.createQuote(q);              
   }*/

    /*
    public void testCreateUsers(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        User u1 = new User();
        u1.setUserName("sqadmin");
        u1.setFirstName("Admin");
        u1.setLastName("User");
        u1.setUserType((long) 1);
        u1.setIsActive(true);
        session.save(u1);
        

        User u2 = new User();
        u2.setUserName("sqbasic");
        u2.setFirstName("Basic");
        u2.setLastName("User");
        u2.setUserType((long) 0);
        u2.setIsActive(true);
        session.save(u2);
        
        session.getTransaction().commit();
        session.close();
        assert(true);        
    } 
    */
    
    /*
    public void testDeleteCostItemsForQuote() {
        System.out.println("***Running testDeleteCostItemsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(15l);
        List<QuoteCostItem> qcis = new ArrayList<QuoteCostItem>();
        QuoteCostItem qci = new QuoteCostItem();
        qci.setId(3l);
        qcis.add(qci);
        qci = new QuoteCostItem();
        qci.setId(4l);
        qcis.add(qci);
        
        service.deleteCostItemsForQuote(q, qcis);        
    }*/

    /*public void testSaveCostItemsForQuote() {
        System.out.println("***Running testSaveCostItemsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(15l); // 2013-12-11 11:05:24.0
        List<QuoteCostItem> qcis = new ArrayList<QuoteCostItem>();
        // TPT - (via Transportation Planning Tool), Impl
        QuoteCostItem qci = new QuoteCostItem();
        CostItem ci = new CostItem();
        ci.setId(1l);       
        qci.setCost(33d);
        qci.setCostItem(ci);
        qci.setEnabled(true);
        qci.setFeeCategory(FeeCategoryCO.IMPL);
        qci.setSpecial(SpecialQuoteCostItemCO.YES);
        qcis.add(qci);
        // TPT - (via Transportation Planning Tool), Monthly
        qci = new QuoteCostItem();
        qci.setCost(3d);
        qci.setCostItem(ci);
        qci.setEnabled(true);
        qci.setFeeCategory(FeeCategoryCO.MONTHLY);
        qci.setSpecial(SpecialQuoteCostItemCO.YES);
        qcis.add(qci);
        
        // Shipment Consolidation Optimization: Not enabled!
        // IMPL...
        qci = new QuoteCostItem();
        ci = new CostItem();
        ci.setId(2l);
        qci.setCost(0d);
        qci.setCostItem(ci);
        qci.setEnabled(false);
        qci.setFeeCategory(FeeCategoryCO.IMPL);
        qcis.add(qci);
        
        qci = new QuoteCostItem();
        qci.setCost(0d);
        qci.setCostItem(ci);
        qci.setEnabled(false);
        qci.setFeeCategory(FeeCategoryCO.MONTHLY);
        qcis.add(qci);
        
        // Tours for Multi Load Planning - IMPL
        qci = new QuoteCostItem();
        ci = new CostItem();
        ci.setId(5l);
        qci.setCost(333d);
        qci.setCostItem(ci);
        qci.setEnabled(true);
        qci.setFeeCategory(FeeCategoryCO.IMPL);
        qcis.add(qci);
        // Tours for Multi Load Planning - MONTHLY        
        qci = new QuoteCostItem();
        qci.setCost(3d);
        qci.setCostItem(ci);
        qci.setEnabled(true);
        qci.setFeeCategory(FeeCategoryCO.MONTHLY);
        qcis.add(qci);
       
        service.saveCostItemsForQuote(q, qcis);
        
    }*/
    
   /* public void testUpdateCostItemsForQuote() {
        System.out.println("***Running testUpdateCostItemsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(15l); // 2013-12-11 11:05:24.0
        List<QuoteCostItem> qcis = new ArrayList<QuoteCostItem>();
        
        // Tours for Multi Load Planning - IMPL, changing enabled to false
        QuoteCostItem qci = new QuoteCostItem();
        CostItem ci = new CostItem();
        ci.setId(5l);
        qci.setId(5l);
        qci.setCost(333d);
        qci.setCostItem(ci);
        qci.setEnabled(false);
        qci.setFeeCategory(FeeCategoryCO.IMPL);
        qcis.add(qci);
        // Tours for Multi Load Planning - MONTHLY        
        qci = new QuoteCostItem();
        qci.setId(6l);
        qci.setCost(3d);
        qci.setCostItem(ci);
        qci.setEnabled(false);
        qci.setFeeCategory(FeeCategoryCO.MONTHLY);
        qcis.add(qci);
       
        service.saveCostItemsForQuote(q, qcis);
    
    }*/
    
    public void testGetAllCountries() {
        System.out.println("***Running testGetAllCountries()");
        SquotemServiceBean service = new SquotemServiceBean();
        List<Country> entries = service.getCountries();
        assertNotNull("Countries not found", entries);
        for (Country e : entries) {
            System.out.println("***Country: " + e.getCountryCode() + " , " + e.getCountryName());
        }        
    }
    
    public void testGetAllCountryStates() {
        System.out.println("***Running testGetAllCountryStates()");
        SquotemServiceBean service = new SquotemServiceBean();
        Country country = new Country();
        country.setCountryCode("US");
        List<State> entries = service.getCountryStates(country);
        assertNotNull("Country states not found", entries);
        for (State e : entries) {
            System.out.println("***Country: " + e.getCountryCode() + " , " + e.getStateShortName() + " , " + e.getStateName());
        }        
    }

    public void testGetAllBusinessSectors() {
        System.out.println("***Running testGetAllBusinessSectors()");
        SquotemServiceBean service = new SquotemServiceBean();
        List<BusinessSector> entries = service.getBusinessSectors();
        assertNotNull("Business sectors not found", entries);
        for (BusinessSector e : entries) {
            System.out.println("***Business sector: " + e.getId() + " , " + e.getDescription());
        }        
    }
    
    public void testGetMTSMatrix() {
        System.out.println("***Running testGetMTSMatrix()");
        SquotemServiceBean service = new SquotemServiceBean();
        Long mtsMatrixId = new Long(1);
        MTSMatrix mtsMatrix = service.getMTSMatrix(mtsMatrixId);
        assertNotNull("MTS Matrix not found",mtsMatrix);
        System.out.println("MTS Matrix, name: " + mtsMatrix.getName() 
                + " , description: " + mtsMatrix.getDescription()
                + " , baselineWeeklyLoadCount: " + mtsMatrix.getBaselineWeeklyLoadCount());
        
        List<MTSMatrixScopeQuestion> entries = mtsMatrix.getScopeQuestions();
        assertNotNull("Matrix Scope Question items not found", entries);
        for (MTSMatrixScopeQuestion e : entries) {
            System.out.println("***MTSMatrixScopeQuestion: category "  + e.getCategory().getDescription() 
                    + " , question: " + e.getQuestion() 
                    + " , baselineScopeImpact: " + e.getBaselineScopeImpact()
                    + " , headcountImpact: " + e.getHeadcountImpact());
        }                                
        
        List<MTSMatrixRoleCost> roleCostEntries = mtsMatrix.getRoleCosts();
        assertNotNull("Matrix Role Costs not found", roleCostEntries);
        for (MTSMatrixRoleCost e : roleCostEntries) {
            System.out.println("***MTSMatrixRoleCost: role "  + e.getMtsRole().getDescription() 
                    + " , costPer: " + e.getCostPer()
                    + " , margin: " + e.getMargin());
        }                                
        
        List<MTSMatrixRoleRelation> roleRelationEntries = mtsMatrix.getRoleRelations();
        assertNotNull("Matrix Role Relations not found", roleRelationEntries);
        for (MTSMatrixRoleRelation e : roleRelationEntries) {
            System.out.println("***MTSMatrixRoleRelation: role "  + e.getMtsRole().getDescription() 
                    + " , rangeStart: " + e.getRangeStart()
                    + " , rangeEnd: " + e.getRangeEnd()
                    + " , roleCount: " + e.getRoleCount());
        }                                
        
        
    }
        
    public void testGetMTSScopeAnswersForQuote() {
        System.out.println("***Running testGetMTSScopeAnswersForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(89l);
        Matrix m = new Matrix();
        m.setMtsMatrixId(1l);
        q.setMatrix(m);
        
        List<QuoteMTSScopeAnswer> entries = service.getMTSScopeAnswersForQuote(q);
        assertNotNull("Quote scope answer for quote not found", entries);
        for (QuoteMTSScopeAnswer e : entries) {
            System.out.println("***Quote scope answer: " + e.getMtsScopeQuestion().getQuestion() + " , answer: " + e.getAnswer());
        }                                
    }
    
    public void testGetMTSRoleCostsForQuote() {
        System.out.println("***Running testGetMTSRoleCostsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(89l);
        Matrix m = new Matrix();
        m.setMtsMatrixId(1l);
        q.setMatrix(m);
        
        List<QuoteMTSRoleCost> entries = service.getMTSRoleCostsForQuote(q);
        assertNotNull("Quote MTS role costs not found", entries);
        for (QuoteMTSRoleCost e : entries) {
            System.out.println("***Quote mts role cost, role: " + e.getMtsRole().getDescription() 
                    + " , cost per: " + e.getMtsRoleCost().getCostPer() 
                    + " , additional: " + e.getRoleCountAdditional());
        }                                
    }
    
    /* public void testSaveNoteForQuote() {
        System.out.println("***Running testSaveNoteForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        User u = new User();
        u.setId(2l); // carias
        try {
            service.saveNoteForQuote(u, 89l, "This comment was create at " + System.currentTimeMillis() );
        }
        catch (QuoteEditException e) {
            e.printStackTrace();
        }
    }  */
    
    public void testGetNotesForQuote() {
        System.out.println("***Running testGetNotesForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(89l);
        List<QuoteNote> entries = service.getNotesForQuote(q);
        assertNotNull("Notess for quote not found", entries);
        for (QuoteNote e : entries) {
            System.out.println("***Quote Note: " + e.getComment() + ", by: " + e.getUser().getFirstName() + " " + e.getUser().getLastName());
        }                                
    }
    
    public void testGetRiskAnalysisItemsForQuote() {
        System.out.println("***Running testGetRiskAnalysisItemsForQuote()");
        SquotemServiceBean service = new SquotemServiceBean();
        Quote q = new Quote();
        q.setId(1l);
        List<QuoteRiskAnalysis> entries = service.getRiskAnalysisItemsForQuote(q);
        assertNotNull("Risk analysis items for quote not found", entries);
        for (QuoteRiskAnalysis e : entries) {
            System.out.println("***Quote Risk Analysys: " + e.getRiskAnalysis().getDescription() + " , risk level: " + e.getRiskLevel() 
                    + ", enabled: " + e.getEnabled());
        }                        
    }
    
    public void testGetMatrixRiskAnalysisItems() {
        System.out.println("***Running testGetMatrixRiskAnalysisItems()");
        SquotemServiceBean service = new SquotemServiceBean();
        Matrix m = new Matrix();
        m.setId(1l); // 2013 Matrix...        
        List<MatrixRiskAnalysis> entries = service.getMatrixRiskAnalysisItems(m);
        assertNotNull("Matrix Risk Analysis Items not found", entries);
        for (MatrixRiskAnalysis e : entries) {
            System.out.println("***MatrixRiskAnalysis: category "  + e.getRiskAnalysis().getRiskCategory().getDescription() 
                    + " , description: " + e.getRiskAnalysis().getDescription() 
                    );            
        }                        
    }           
            
}
