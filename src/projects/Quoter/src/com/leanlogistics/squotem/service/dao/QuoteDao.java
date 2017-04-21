package com.leanlogistics.squotem.service.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.leanlogistics.squotem.co.QuoteVisibilityCO;
import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.co.UserTypeCO;
import com.leanlogistics.squotem.model.Customer;
import com.leanlogistics.squotem.model.Matrix;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.QuoteCostAdjustment;
import com.leanlogistics.squotem.model.QuoteCostItem;
import com.leanlogistics.squotem.model.QuoteMTSRoleCost;
import com.leanlogistics.squotem.model.QuoteMTSScopeAnswer;
import com.leanlogistics.squotem.model.QuoteMetric;
import com.leanlogistics.squotem.model.QuoteNote;
import com.leanlogistics.squotem.model.QuoteProduct;
import com.leanlogistics.squotem.model.QuoteQuery;
import com.leanlogistics.squotem.model.QuoteRiskAnalysis;
import com.leanlogistics.squotem.model.User;

public class QuoteDao {
    
    // Quote status to exclude when querying someone else's quotes for a regular user
    public final static List<QuoteWorkflowStatusCO> EXCLUDE_STATUSES = Arrays.asList(QuoteWorkflowStatusCO.DRAFT, 
                                                                                   QuoteWorkflowStatusCO.SUBMITTED);
    
    public void createQuote(Session ses, Quote q) {
        ses.save(q);        
    }
    
    public void updateQuote(Session ses, Quote q) {
        Query hq = ses.createQuery("update Quote set salesDirector = :salesDirector, businessConsultant = :businessConsultant, "
                                 + "partnerReferenced = :partnerReferenced, customer = :customer, "
                                 + "effectiveDate = :effectiveDate, expireDate = :expireDate, hasMts = :hasMts, modifiedDate = :modifiedDate, "
                                 + "modifiedBy = :modifiedBy "
                                 //+ ", budgetaryImplCost = :budgetaryImplCost, budgetaryMonthlyCost = :budgetaryMonthlyCost "
                                 + "where id = :id");
        hq.setParameter("salesDirector", q.getSalesDirector());       
        hq.setParameter("businessConsultant", q.getBusinessConsultant());
        hq.setParameter("partnerReferenced", q.getPartnerReferenced());
        hq.setParameter("customer", q.getCustomer());
        hq.setParameter("effectiveDate", q.getEffectiveDate());
        hq.setParameter("expireDate", q.getExpireDate());
        hq.setParameter("hasMts", q.getHasMts());
        hq.setParameter("modifiedDate",  q.getModifiedDate());
        hq.setParameter("modifiedBy", q.getModifiedBy());
        // this fields are updated with updateQuoteMarketCost function
        //hq.setParameter("budgetaryImplCost", q.getBudgetaryImplCost());
        //hq.setParameter("budgetaryMonthlyCost", q.getBudgetaryMonthlyCost());
        hq.setParameter("id", q.getId());        
        hq.executeUpdate();               
    }
    
    public void updateBudgetaryCost(Session ses, Quote q) {
        Query hq = ses.createQuery("update Quote set budgetaryImplCost = :budgetaryImplCost, budgetaryMonthlyCost = :budgetaryMonthlyCost where id = :id");
        hq.setParameter("budgetaryImplCost", q.getBudgetaryImplCost());
        hq.setParameter("budgetaryMonthlyCost", q.getBudgetaryMonthlyCost());
        hq.setParameter("id", q.getId());
        hq.executeUpdate();        
    }

    public void updateMarketCost(Session ses, Quote q) {
        Query hq = ses.createQuery("update Quote set marketImplCost = :marketImplCost, marketMonthlyCost = :marketMonthlyCost where id = :id");
        hq.setParameter("marketImplCost", q.getMarketImplCost());
        hq.setParameter("marketMonthlyCost", q.getMarketMonthlyCost());
        hq.setParameter("id", q.getId());
        hq.executeUpdate();        
    }

    public void updateQuoteTerms(Session ses, Quote q) {
        Query hq = ses.createQuery("update Quote set terms = :terms where id = :id");
        hq.setParameter("terms", q.getTerms());
        hq.setParameter("id", q.getId());
        hq.executeUpdate();        
    }
    
    public void updateQuoteStatus(Session ses, Quote q, QuoteWorkflowStatusCO newStatus) {
        Query hq = ses.createQuery("update Quote set status = :status, modifiedDate = :modifiedDate where id = :id");
        hq.setParameter("status", newStatus);
        hq.setParameter("modifiedDate", new Date());
        hq.setParameter("id", q.getId());
        hq.executeUpdate();
    }
    
    public void updateQuoteModifiedDate(Session ses, Quote q) {
        Query hq = ses.createQuery("update Quote set modifiedDate = :modifiedDate where id = :id");
        hq.setParameter("modifiedDate", q.getModifiedDate());
        hq.setParameter("id",q.getId());
        hq.executeUpdate();
    }
    
    public void updateQuoteMatrix(Session ses, Quote q) {
        Query hq = ses.createQuery("update Quote set matrix = :matrix "
                + "where id = :id");
		hq.setParameter("matrix", q.getMatrix());
		hq.setParameter("id", q.getId());        
		hq.executeUpdate();               
    }
    
    public void updateQuoteMTSFields(Session ses, Quote q) {
        Query hq = ses.createQuery("update Quote set mtsAnnualLoadCount = :annualLoadCount, "
                + "mtsScopedWeeklyLoadCount = :scopedWeeklyLoadCount, mtsAnnualPrice = :annualPrice "
                + "where id = :id");
        hq.setParameter("annualLoadCount", q.getMtsAnnualLoadCount());
        hq.setParameter("scopedWeeklyLoadCount", q.getMtsScopedWeeklyLoadCount());
        hq.setParameter("annualPrice", q.getMtsAnnualPrice());
        hq.setParameter("id", q.getId());        
        hq.executeUpdate();                       
    }
    
    @SuppressWarnings("unchecked")
    public List<Quote> getDraftQuotesForUser(Session ses, User u) {
    	StringBuilder sb = new StringBuilder();
    	boolean addSalesDirector = false;
    	sb.append("from Quote q "); //where q.activeRevision = :activeRevision ");

        // Sales Director
        // verify visibility
        Long visibility = u.getQuoteVisibility();
        if (visibility == null || visibility.equals((long) QuoteVisibilityCO.SALES_DIRECTOR_ONLY.getCode()))
        {
        	sb.append(" where salesDirector = :salesDirector ");
        	addSalesDirector = true;
        }

        sb.append("order by q.createDate desc");
        Query q =  ses.createQuery(sb.toString());
        		//q.modifiedBy = :modifiedBy and q.status = :status and 
                //.setParameter("modifiedBy", u).setParameter("status", QuoteWorkflowStatusCO.DRAFT)
                //.setParameter("activeRevision", Boolean.TRUE); 
        if (addSalesDirector)
        	q.setParameter("salesDirector", u);

        return (List<Quote>) q.list();
    }
    
    public void saveQuoteMetrics(Session ses, List<QuoteMetric> quoteMetrics) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteMetric qm : quoteMetrics) {
            ses.saveOrUpdate(qm);
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
        }        
    }
    
    
    @SuppressWarnings("unchecked")
    public List<QuoteMetric> getMetricsForQuote(Session ses, Quote quote) {
        Query q = ses.createQuery("from QuoteMetric qm where qm.quote = :quote")
                .setParameter("quote", quote);
        return (List<QuoteMetric>) q.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<QuoteCostItem> getCostItemsForQuote(Session ses, Quote quote) {
        Query q = ses.createQuery("select qci " +
                "from QuoteCostItem qci " +
                "     left join fetch qci.costItem as ci " +
                "     left outer join fetch qci.costModel as cm " +
                "where qci.quote = :q and ci.productCategory.active = :active")
                .setParameter("q", quote).setParameter("active", Boolean.TRUE); 
        return (List<QuoteCostItem>) q.list();
    }
        
    public Quote getQuote(Session ses, User usr, long id) {
        StringBuilder sbQuery = new StringBuilder("from Quote q where q.id = :id");
        StringBuilder sbAccessRestriction = new StringBuilder();                
        Map<String,Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id",id);        
        applyAccessRestrictionForUser(sbAccessRestriction, queryParams, "q", usr);
        if (sbAccessRestriction.length() > 0) {
            sbQuery.append(sbAccessRestriction);           
        }
        Query q =  ses.createQuery(sbQuery.toString());
        // Store all params
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return (Quote) q.uniqueResult();        
    }
    
    public void saveQuoteCostItems(Session ses, List<QuoteCostItem> quoteCostItems) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteCostItem qci : quoteCostItems) {
            ses.saveOrUpdate(qci);
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
        }        
    }    
    
    public void deleteQuoteCostItems(Session ses, List<QuoteCostItem> quoteCostItems) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteCostItem qci : quoteCostItems) {
            ses.delete(qci);;
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
        }                
    }
    
    public void nullifyQuoteCostItemsCostData(Session ses, Quote q) {
        Query hq = ses.createQuery("update QuoteCostItem set cost = NULL, costModel = NULL, required = NULL "
                + "where quote = :quote");
        hq.setParameter("quote", q);        
        hq.executeUpdate();                       
    }
    
    // Remove elements in QuoteCostItem that are not part of the (new) Matrix 
    public void deleteQuoteCostItemsNotInMatrix(Session ses, Quote quote, Matrix m) {
        Query q = ses.createSQLQuery("delete from Quote_Cost_Item " +
                "where quote_id = :q " +
                "and (cost_item_id, fee_catg) not in ( select cost_item_id, fee_catg from Matrix_Cost_Item where matrix_id = :m)")
                .setParameter("q", quote.getId())
                .setParameter("m", m.getId()); 
        q.executeUpdate();
    }
    
    public void deleteQuoteMetricsNotInMatrix(Session ses, Quote quote, Matrix m) {
        Query q = ses.createSQLQuery("delete from Quote_Metric " +
                "where quote_id = :q " +
                "and metric_id not in (select metric_id from Matrix_Metric where matrix_id = :m)")
                .setParameter("q", quote.getId())
                .setParameter("m", m.getId()); 
        q.executeUpdate();
    }
    
    public void deleteQuoteCostAdjNotInMatrix(Session ses, Quote quote, Matrix m) {
        Query q = ses.createSQLQuery("delete from Quote_Cost_Adj " +
                "where quote_id = :q " +
                "and cost_adjustment_id not in (select cost_adjustment_id from Matrix_Cost_Adj where matrix_id = :m)")
                .setParameter("q", quote.getId())
                .setParameter("m", m.getId()); 
        q.executeUpdate();
    }
    
    public void deleteQuoteCostItemsInProductCategory(Session ses, Quote quote, ProductCategory p) {
    	//select * from Quote_Cost_Item where quote_id = 54 and cost_item_id in (select cost_item_id from Cost_Item where product_catg_id = 1);
    	
        Query q = ses.createSQLQuery("delete from Quote_Cost_Item " +
                "where quote_id = :q " +
                "and cost_item_id not in ( select cost_item_id from Cost_Item where product_catg_id = :p)")
                .setParameter("q", quote.getId())
                .setParameter("p", p.getId()); 
        q.executeUpdate();
    
    }
    
    public void deleteQuoteCostAdjInProductCategory(Session ses, Quote quote, ProductCategory p) {
        Query q = ses.createSQLQuery("delete from Quote_Cost_Adj " +
                "where quote_id = :q " +
                "and cost_adjustment_id in (select cost_adjustment_id from Cost_Adjustment where product_catg_id = :p)")
                .setParameter("q", quote.getId())
                .setParameter("p", p.getId()); 
        q.executeUpdate();
    }
    
    public void deleteQuoteProductCategory(Session ses, Quote quote, ProductCategory p) {
        Query q = ses.createQuery("delete from QuoteProduct " +
                "where quoteId = :q " +
                "and productId = :p")
                .setParameter("q", quote.getId())
                .setParameter("p", p.getId()); 
        q.executeUpdate();
    }
    
    public void deleteQuoteMetrics(Session ses, List<QuoteMetric> quoteMetrics) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteMetric qm : quoteMetrics) {
            ses.delete(qm);;
            if (++i % 20 == 0) {
              ses.flush();
              ses.clear();              
            }
        }                
    }
    
    
    @SuppressWarnings("unchecked")
    public List<QuoteCostAdjustment> getCostAdjustmentsForQuote(Session ses, Quote quote) {
        Query q = ses.createQuery("select qca " +
                "from QuoteCostAdjustment qca " +
                "     left join fetch qca.costAdjustment as ca " +
                "where qca.quote = :q and ca.productCategory.active = :active")
                .setParameter("q", quote).setParameter("active", Boolean.TRUE); 
        return (List<QuoteCostAdjustment>) q.list();
    }
    
    public void saveQuoteCostAdjustments(Session ses, List<QuoteCostAdjustment> quoteCostAdjustments) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteCostAdjustment qca : quoteCostAdjustments) {
            ses.saveOrUpdate(qca);
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
            i++;
        }                
    }
    
    public void deleteQuoteCostAdjustments(Session ses, List<QuoteCostAdjustment> quoteCostAdjustments) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteCostAdjustment qca : quoteCostAdjustments) {
            ses.delete(qca);;
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
        }                        
    }
    
    @SuppressWarnings("unchecked")
    public List<QuoteMTSScopeAnswer> getMTSScopeAnswersForQuote(Session ses, Quote quote) {
        Query q = ses.createQuery("select qsa " +
                "from QuoteMTSScopeAnswer qsa " +
                "     left join fetch qsa.mtsScopeQuestion as sq " +
                "     left join sq.mtsMatrix as mtsMat " +
                "where qsa.quote = :q  and mtsMat.id = :mstMatId")
                .setParameter("q", quote).setParameter("mstMatId", quote.getMatrix().getMtsMatrixId()); 
        return (List<QuoteMTSScopeAnswer>) q.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<QuoteMTSRoleCost> getMTSRoleCostsForQuote(Session ses, Quote quote) {
        Query q = ses.createQuery("select qrc " +
                "from QuoteMTSRoleCost qrc " +
                "     left join fetch qrc.mtsRoleCost as mrc " +
                "     left join fetch qrc.mtsRole as mr " +
                "     left join mrc.mtsMatrix as mtsMat " +
                "where qrc.quote = :q  and mtsMat.id = :mstMatId")
                .setParameter("q", quote).setParameter("mstMatId", quote.getMatrix().getMtsMatrixId()); 
        return (List<QuoteMTSRoleCost>) q.list();        
    }
    
    public void deleteQuoteMTSScopeAnswers(Session ses, List<QuoteMTSScopeAnswer> quoteScopeAnswers) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteMTSScopeAnswer qsa : quoteScopeAnswers) {
            ses.delete(qsa);;
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
        }                
    }
    
    public void deleteQuoteMTSScopeAnswers(Session ses, Quote quote) {
    	Query q = ses.createQuery("delete from QuoteMTSScopeAnswer " +
                "where quote = :q ")
                .setParameter("q", quote);
        q.executeUpdate();
    }

    public void deleteQuoteMTSRoleCosts(Session ses, Quote quote) {
    	Query q = ses.createQuery("delete from QuoteMTSRoleCost " +
                "where quote = :q ")
                .setParameter("q", quote);
        q.executeUpdate();
    }
    
    public void saveQuoteMTSScopeAnswers(Session ses, List<QuoteMTSScopeAnswer> quoteScopeAnswers) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteMTSScopeAnswer qsa : quoteScopeAnswers) {
            ses.saveOrUpdate(qsa);
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
        }        
    }    
    
    public void saveQuoteMTSRoleCosts(Session ses, List<QuoteMTSRoleCost> quoteMTSRoleCosts) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteMTSRoleCost qrc : quoteMTSRoleCosts) {
            ses.saveOrUpdate(qrc);
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
        }                
    }
    
    @SuppressWarnings("unchecked")
    public List<QuoteProduct> getProductsForQuote(Session ses, Quote quote) {
        Query q = ses.createQuery("from QuoteProduct qp where qp.quote = :quote and qp.productCategory.active = :active")
                .setParameter("quote", quote).setParameter("active", Boolean.TRUE);
        return (List<QuoteProduct>) q.list();
    }
    
    public void saveQuoteProducts(Session ses, List<QuoteProduct> quoteProducts) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteProduct qci : quoteProducts) {
            ses.saveOrUpdate(qci);
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
        }        
    }    
    
    public void updateQuoteMtsCustomerQuote(Session ses, Quote q) {
        Query hq = ses.createQuery("update Quote set mtsCustomerQuoteImpl = :mtsCustomerQuoteImpl, "
                + "mtsCustomerQuoteMonthly = :mtsCustomerQuoteMonthly "
                + "where id = :id");
        hq.setParameter("mtsCustomerQuoteImpl", q.getMtsCustomerQuoteImpl());
        hq.setParameter("mtsCustomerQuoteMonthly", q.getMtsCustomerQuoteMonthly());
        hq.setParameter("id", q.getId());        
        hq.executeUpdate();                       
    }
    
    @SuppressWarnings("unchecked")
    public List<Quote> queryQuotes(Session ses, QuoteQuery query) {
        StringBuilder sbWhere = new StringBuilder();
        Map<String,Object> queryParams = new HashMap<String, Object>();
        String quoteEntity = "q";
        
        // Query by Quote Number?
        if (query.getQuoteNumber() != null) {
            long quoteNum = 0;
            long quoteRev = 0;
            int dotPosition = query.getQuoteNumber().indexOf(".");
            try {
            	if (dotPosition == -1)
            		quoteNum = Long.valueOf(query.getQuoteNumber());
            	else
            	{
            		quoteNum = Long.valueOf(query.getQuoteNumber().substring(0, dotPosition));
            		quoteRev = Long.valueOf(query.getQuoteNumber().substring(dotPosition + 1));
            	}
            		
            }
            catch (NumberFormatException e) {};
            if (quoteNum > 0) {
               appendWhereCriteria(sbWhere, queryParams, quoteEntity, "quoteNumber", quoteNum);
               
               if (quoteRev > 0) {
            	   appendWhereCriteria(sbWhere, queryParams, quoteEntity, "revisionNumber", quoteRev);
               }
            }
        }
        // Query by status?
        if ( (query.getStatus() != null) && (! query.getStatus().isEmpty() )) {
            QuoteWorkflowStatusCO status = QuoteWorkflowStatusCO.valueOf(query.getStatus());
            appendWhereCriteria(sbWhere, queryParams, quoteEntity , "status", status);
        }
        // Query By Customer?
        if ( ( query.getCustomerId() != null) && (query.getCustomerId() > 0) ) {
            Customer c = new Customer();
            c.setId(query.getCustomerId());
            appendWhereCriteria(sbWhere, queryParams,  quoteEntity, "customer", c);
        }
        // Regular product categories
        if ( (query.getProductCategories() != null) && (! query.getProductCategories().isEmpty()) ) {
            int i = 0;
            for (Iterator<Long> it = query.getProductCategories().iterator(); it.hasNext(); i++) {
                ProductCategory pc = new ProductCategory();
                pc.setId(it.next());
                appendMemberOfWhereCriteria(sbWhere, queryParams, quoteEntity , "productCategories", "pcat" + i, pc);
            }
        }
        
        // Special category: MTS
        if ( (query.getHasMts() != null) && ( query.getHasMts() == true) ) {
            appendWhereCriteria(sbWhere, queryParams, quoteEntity , "hasMts", Boolean.TRUE);
        }
        
        // From and To Date
        if ( (query.getFrom() != null) || (query.getTo() != null) ) {
            appendDateRangeCriteria(sbWhere, queryParams, quoteEntity , "modifiedDate", query.getFrom(), query.getTo());
        }
        
        // Modified By
        if ( ( query.getOwnerId() != null) && (query.getOwnerId() > 0) ) {
            User u = new User();
            u.setId(query.getOwnerId());
            appendWhereCriteria(sbWhere, queryParams, quoteEntity, "modifiedBy", u);                            
            if ( (! UserTypeCO.ADMINISTRATIVE.equals(query.getQueryUser().getUserType())) && (! query.getOwnerId().equals(query.getQueryUser().getId())) ) {
                // This is a regular user querying other user's quotes: exclude some statuses
                for (QuoteWorkflowStatusCO status : EXCLUDE_STATUSES) {
                    appendWhereCriteria(sbWhere, queryParams, quoteEntity, "status", "status" + status.getName(), status, "<>");                    
                }
            }            
        }
        else {
            // No explicit author selected, apply default access restrictions
            applyAccessRestrictionForUser(sbWhere, queryParams, quoteEntity, query.getQueryUser());
        }
        
        // Only active revision? GET ALL OF THEM FOR NOW
        /*if ( (query.getOnlyActive() != null) && (query.getOnlyActive() == true)) {
            appendWhereCriteria(sbWhere, queryParams, quoteEntity , "activeRevision", Boolean.TRUE);
        }*/

        // Sales Director
        // verify visibility
        Long visibility = query.getQueryUser().getQuoteVisibility();
        if (visibility != null && visibility.equals((long) QuoteVisibilityCO.ALL.getCode()))
        {
        	if ( ( query.getSalesDirector() != null) && (query.getSalesDirector().longValue() > 0) ) {
	            User u = new User();
	            u.setId(query.getSalesDirector());
	            appendWhereCriteria(sbWhere, queryParams, quoteEntity, "salesDirector", u);                            
	        }
        }
        else 
            appendWhereCriteria(sbWhere, queryParams, quoteEntity, "salesDirector", query.getQueryUser());                            

        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("from Quote ").append(quoteEntity);
        if (sbWhere.length() > 0) {
            sbQuery.append(" where ").append(sbWhere);
        }
        sbQuery.append(" order by ").append(quoteEntity).append(".createDate desc");
        
        String queryString = sbQuery.toString();
        System.out.println("*QUERY QUOTES, queryString: " + queryString);
        System.out.println("*QUERY QUOTES, params: " + queryParams);
        
        Query q =  ses.createQuery(queryString);
        // Store all params
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return (List<Quote>) q.list();
    }
    
    // Used to make sure a user can not see someone else's quote when doing a query
    private void applyAccessRestrictionForUser(StringBuilder sbQuery, Map<String, Object> params, String quoteEntity, User u) {
        return;
        /*
    	if (UserTypeCO.ADMINISTRATIVE.equals(u.getUserType())) {
            return;
        }
        // Regular user, do the access restriction
        if (! params.isEmpty()) {
            sbQuery.append(" and ");
        }
        sbQuery.append("( (");
        appendCriteria(sbQuery, params, quoteEntity, "author", "author", u, "=");
        sbQuery.append(") or (");
        int i = 0;
        for (QuoteWorkflowStatusCO status : EXCLUDE_STATUSES) {
            if (i > 0) {
                sbQuery.append(" and ");
            }
            appendCriteria(sbQuery, params, quoteEntity, "status", "status" + status.getName(), status, "<>");
            i++;
        }
        sbQuery.append(")) ");
        */
    }
    
    /*private void appendMaxWhereCriteria(StringBuilder sb, Map<String, Object> params, String externalEntity, String internalEntity, String maxField, String compareField) {
        if (! params.isEmpty()) {
            sb.append(" and ");
        }
        sb.append(externalEntity).append(".").append(maxField).append(" = (select max (").append(internalEntity).append(".").append(maxField);
        sb.append(") from Quote ").append(internalEntity).append(" where ").append(internalEntity).append(".").append(compareField).append(" = ");
        sb.append(externalEntity).append(".").append(compareField).append(" ) ");
    }*/
    
    private void appendWhereCriteria(StringBuilder sb, Map<String, Object> params, String entity, String field, Object fieldValue) {
        appendWhereCriteria(sb, params, entity, field, field, fieldValue, "=");        
    }
    
    private void appendWhereCriteria(StringBuilder sb, Map<String, Object> params, String entity, String field, String fieldParamName, Object fieldValue, String operator) {
        if (! params.isEmpty()) {
            sb.append(" and ");
        }
        appendCriteria(sb, params, entity, field, fieldParamName, fieldValue, operator);
    }
    
    // Append criteria without adding any and / or condition
    private void appendCriteria(StringBuilder sb, Map<String, Object> params, String entity, String field, String fieldParamName, Object fieldValue, String operator) {
        sb.append(entity).append(".").append(field).append(" ").append(operator).append(" :").append(fieldParamName);
        params.put(fieldParamName, fieldValue);                        
    }
    
    private void appendMemberOfWhereCriteria(StringBuilder sb, Map<String, Object> params, String entity, String collection, String fieldName, Object fieldValue) {
        if (! params.isEmpty()) {
            sb.append(" and ");
        }
        sb.append(":").append(fieldName).append(" member of ").append(entity).append(".").append(collection);
        params.put(fieldName, fieldValue);
    }
    
    private void appendDateRangeCriteria(StringBuilder sb, Map<String, Object> params, String entity, String field, Date dateFrom, Date dateTo) {
        if (! params.isEmpty()) {
            sb.append(" and ");
        }
        if ( (dateFrom != null) && (dateTo != null) ) {
            sb.append("q").append(".").append(field).append(" between :").append(field).append("from and :").append(field).append("to");
            params.put(field + "from", dateFrom);
            params.put(field + "to", dateTo);
        }
        else if (dateFrom != null) {
            sb.append("q").append(".").append(field).append(" >= :").append(field);
            params.put(field, dateFrom);
        }
        else if (dateTo != null) {
            sb.append("q").append(".").append(field).append(" <= :").append(field);
            params.put(field, dateTo);            
        }        
    }
    
    public void deactivateQuoteRevision(Session ses, Quote q) {
        Query hq = ses.createQuery("update Quote set activeRevision = :activeRevision "
        						 + "where id = :id");
        hq.setParameter("activeRevision", Boolean.FALSE);
        hq.setParameter("id", q.getId());        
        hq.executeUpdate();               
    }
    
    public void saveQuoteNote(Session ses, QuoteNote quoteNote) {
        ses.saveOrUpdate(quoteNote);
    }
    
    @SuppressWarnings("unchecked")
    public List<QuoteNote> getNotesForQuote(Session ses, Quote quote) {
        // not sure if should be better to create a no procedure for this
    	/*Query q = ses.createQuery("from QuoteNote qn where qn.quote = :quote order by qn.createDate desc")
                .setParameter("quote", quote);
        return (List<QuoteNote>) q.list();*/
        Query q = ses.createQuery("from QuoteNote qn" +
                "     join fetch qn.quote as q " +
                "where q.quoteNumber = :quoteNumber and q.revisionNumber <= :revisionNumber order by qn.createDate desc")
                .setParameter("quoteNumber", quote.getQuoteNumber())
                .setParameter("revisionNumber", quote.getRevisionNumber()); 
        return (List<QuoteNote>) q.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<QuoteRiskAnalysis> getRiskAnalysisItemsForQuote(Session ses, Quote quote) {
        Query q = ses.createQuery("from QuoteRiskAnalysis qra where qra.quote = :quote")
                .setParameter("quote", quote);
        return (List<QuoteRiskAnalysis>) q.list();
    }
    
    public void deleteQuoteRiskAnalysisItems(Session ses, List<QuoteRiskAnalysis> quoteRiskAnalysisItems) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteRiskAnalysis qra : quoteRiskAnalysisItems) {
            ses.delete(qra);;
            if (++i % 20 == 0) {
              ses.flush();
              ses.clear();              
            }
        }                
    }
    
    public void saveQuoteRiskAnalysisItems(Session ses, List<QuoteRiskAnalysis> quoteRiskAnalysisItems) {
        // Use an index in order to control the size of Hibernate's first level cache, 
        //  same as the JDBC batch size
        int i = 0;
        for (QuoteRiskAnalysis qra : quoteRiskAnalysisItems) {
            ses.saveOrUpdate(qra);
            if (++i % 20 == 0) {
              // flush a batch of inserts and release memory
              ses.flush();
              ses.clear();              
            }
        }        
    }
    
    
    
}
