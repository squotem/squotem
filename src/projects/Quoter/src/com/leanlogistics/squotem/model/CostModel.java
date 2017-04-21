package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.model.costmodel.CostModelRules;

public class CostModel {
    
    private Long id;
    private String name;
    private String xmlContent;
    private CostModelRules costModelObject;
    // Stored in case there's an error in XML content
    private String errorMessage;
    
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
    public String getXmlContent() {
        return xmlContent;
    }
    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }
    public CostModelRules getCostModelObject() {
        return costModelObject;
    }
    public void setCostModelObject(CostModelRules costModelObject) {
        this.costModelObject = costModelObject;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
