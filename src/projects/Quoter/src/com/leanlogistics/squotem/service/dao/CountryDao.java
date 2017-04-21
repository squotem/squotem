package com.leanlogistics.squotem.service.dao;

import java.util.List;
import org.hibernate.Session;
import com.leanlogistics.squotem.model.Country;

public class CountryDao {

    @SuppressWarnings("unchecked")
    public List<Country> getCountries(Session ses) { 
        return (List<Country>) ses.createQuery("from Country").list();
    }


}
