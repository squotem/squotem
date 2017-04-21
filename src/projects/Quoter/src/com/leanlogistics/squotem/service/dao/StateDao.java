package com.leanlogistics.squotem.service.dao;

import java.util.List;

import org.hibernate.Session;

import com.leanlogistics.squotem.model.State;

public class StateDao {
    @SuppressWarnings("unchecked")
    public List<State> getStates(Session ses) { 
        return (List<State>) ses.createQuery("from State").list();
    }

    @SuppressWarnings("unchecked")
    public List<State> getCountryStates(Session ses, String countryCode) { 
        return (List<State>) ses.createQuery("from State s where s.countryCode = :countryCode")
        		                           .setParameter("countryCode", countryCode)
        		                           .list();
    }


}
