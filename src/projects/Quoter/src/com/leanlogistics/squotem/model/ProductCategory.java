package com.leanlogistics.squotem.model;

public class ProductCategory {

    private Long id;
    
    private String description;
    private Boolean active;
    
    // Empty constructor    
    public ProductCategory() {}
        
    // Copy constructor
    public ProductCategory(ProductCategory source) {
        id = source.id;
        description = source.description;
    }    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    
}
