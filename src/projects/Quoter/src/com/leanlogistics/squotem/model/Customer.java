package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.CustomerTypeCO;

public class Customer {
    
    private Long id = -1l;
    private String name;
    private String city;
    private Country country;
    private State state;
	private String projectSponsor;
    private String sponsorPhone;
    private BusinessSector businessSector;
    private CustomerTypeCO customerType;
    
    public Customer() {        
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }    
    public BusinessSector getBusinessSector() {
        return businessSector;
    }
    public void setBusinessSector(BusinessSector businessSector) {
        this.businessSector = businessSector;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getProjectSponsor() {
        return projectSponsor;
    }
    public void setProjectSponsor(String projectSponsor) {
        this.projectSponsor = projectSponsor;
    }
    public String getSponsorPhone() {
        return sponsorPhone;
    }
    public void setSponsorPhone(String sponsorPhone) {
        this.sponsorPhone = sponsorPhone;
    }

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

    public CustomerTypeCO getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerTypeCO customerType) {
        this.customerType = customerType;
    }
	
}
