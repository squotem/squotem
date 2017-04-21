package com.leanlogistics.squotem.model.costmodel;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java Object representation of the rules included as the XML content of a Cost Model  
 *
 */

@XmlRootElement(name = "cost-model")
public class CostModelRules {
    
    
    private List<CostModelValue> values;

    public List<CostModelValue> getValues() {
        return values;
    }

    @XmlElement(name = "value")
    public void setValues(List<CostModelValue> values) {
        this.values = values;
    }
    
}
