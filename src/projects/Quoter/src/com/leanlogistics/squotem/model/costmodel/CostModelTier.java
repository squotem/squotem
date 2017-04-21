package com.leanlogistics.squotem.model.costmodel;

import javax.xml.bind.annotation.XmlAttribute;

public class CostModelTier {
    private String from;
    private String to;
    private String expression;
    
    public String getFrom() {
        return from;
    }
    @XmlAttribute
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    @XmlAttribute
    public void setTo(String to) {
        this.to = to;
    }
    public String getExpression() {
        return expression;
    }
    @XmlAttribute(name="expr")
    public void setExpression(String expression) {
        this.expression = expression;
    }

}
