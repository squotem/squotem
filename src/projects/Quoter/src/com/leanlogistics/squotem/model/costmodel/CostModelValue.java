package com.leanlogistics.squotem.model.costmodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class CostModelValue {
    
    private String expression;
    private String operator;
    private String evalExpression;
    private String conditionExpresion;
    private List<CostModelTier> tiers;
    
    public String getExpression() {
        return expression;
    }
    
    @XmlAttribute(name="expr")
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public String getOperator() {
        return operator;
    }
    @XmlAttribute
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getEvalExpression() {
        return evalExpression;
    }
    @XmlAttribute(name="eval")
    public void setEvalExpression(String evalExpression) {
        this.evalExpression = evalExpression;
    }
    public List<CostModelTier> getTiers() {
        return tiers;
    }
    @XmlElement(name = "tier")
    public void setTiers(List<CostModelTier> tiers) {
        this.tiers = tiers;
    }
    public String getConditionExpresion() {
        return conditionExpresion;
    }
    @XmlAttribute(name = "if")
    public void setConditionExpresion(String conditionExpresion) {
        this.conditionExpresion = conditionExpresion;
    }
}
