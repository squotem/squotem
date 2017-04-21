package com.leanlogistics.squotem.service.dao;

import java.util.List;

import org.hibernate.Session;

import com.leanlogistics.squotem.model.BusinessSector;

public class BusinessSectorDao {
    
    @SuppressWarnings("unchecked")
    public List<BusinessSector> betBusinessSectors(Session ses){
        return (List<BusinessSector>) ses.createQuery("from BusinessSector bs order by bs.description").list();
        
    }

}
