package com.leanlogistics.squotem.service.manager;

import org.hibernate.Session;

public class BaseManager {

    protected Session hibernateSession;
    
    public Session getHibernateSession() {
        return hibernateSession;
    }

    public void setHibernateSession(Session hibernateSession) {
        this.hibernateSession = hibernateSession;
    }
    
}
