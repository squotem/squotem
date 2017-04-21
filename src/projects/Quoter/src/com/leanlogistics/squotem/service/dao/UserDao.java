package com.leanlogistics.squotem.service.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.leanlogistics.squotem.co.QuoteActionLevelCO;
import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.model.User;

public class UserDao {
    
    public User getUser(Session ses, String userName, String password) {
        Query q =  ses.createQuery("from User u where u.userName = :userName and u.password = :password")
                .setParameter("userName", userName).setParameter("password", password);        
        User u = (User) q.uniqueResult();
        return u;                        
    }
    
    @SuppressWarnings("unchecked")
    public List<User> getUsers(Session ses) {
        return (List<User>) ses.createQuery("from User").list();
    }
    
    @SuppressWarnings("unchecked")
	public List<User> getNotificationList(Session ses, QuoteWorkflowStatusCO status)
    {
    	Query q = null;
    	if (status.equals(QuoteWorkflowStatusCO.SUBMITTED))
    	{
    		q = ses.createQuery("from User u where u.notificationType = :baseline or u.notificationType = :all")
    				.setParameter("baseline", QuoteActionLevelCO.BASELINE)
    				.setParameter("all", QuoteActionLevelCO.ALL);
    	}
    	else 
	    	if (status.equals(QuoteWorkflowStatusCO.APPROVED))
	    	{
	    		q = ses.createQuery("from User u where u.notificationType = :businessPlan or u.notificationType = :all")
	    				.setParameter("businessPlan", QuoteActionLevelCO.BUSINESS_PLAN)
	    				.setParameter("all", QuoteActionLevelCO.ALL);
	    	}
    	
    	return (q == null) ? null : (List<User>)q.list();
    }
}
