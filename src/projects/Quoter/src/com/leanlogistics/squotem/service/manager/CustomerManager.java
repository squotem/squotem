package com.leanlogistics.squotem.service.manager;

import com.leanlogistics.squotem.model.Customer;
import com.leanlogistics.squotem.service.dao.CustomerDao;

import java.util.List;

public class CustomerManager extends BaseManager {
    
    private CustomerDao dao;

    public CustomerManager() {
        dao = new CustomerDao();
    }
    
    public List<Customer> getCustomers() {
        return dao.getCustomers(hibernateSession);
    }
    
    public void createCustomer(Customer c) {
        dao.createCustomer(hibernateSession, c);
    }

}
