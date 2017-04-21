package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum QuoteWorkflowStatusCO implements PersistentEnum {
    DRAFT(1), 
    SUBMITTED(2),
    APPROVED(3);
    
    private int code;

    private QuoteWorkflowStatusCO(int code) {
        this.code = code;
    }
    
    @Override
    public int getCode() {
        return this.code;
    }   
    
    public String getName() {
        return name();
    }
    
    private static Map<Integer, QuoteWorkflowStatusCO> map;
    
    public static QuoteWorkflowStatusCO getByCode(int code) {
        return map.get(code);
    }
    
    static {
        map = new HashMap<Integer, QuoteWorkflowStatusCO>();
        for (QuoteWorkflowStatusCO entry : QuoteWorkflowStatusCO.values()) {
            map.put(entry.getCode(), entry);
        }
    }

}
