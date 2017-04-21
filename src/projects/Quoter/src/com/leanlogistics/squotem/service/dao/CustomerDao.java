package com.leanlogistics.squotem.service.dao;

import java.util.List;

import org.hibernate.Session;

import com.leanlogistics.squotem.model.Customer;

public class CustomerDao {
    
    /** Get all customers */
    @SuppressWarnings("unchecked")
    public List<Customer> getCustomers(Session ses) {
        return (List<Customer>) ses.createQuery("select c from Customer as c left join fetch c.country left join fetch c.state order by c.name").list();
    }
    
    public void createCustomer(Session ses, Customer c) {
        ses.save(c);
    }

}
