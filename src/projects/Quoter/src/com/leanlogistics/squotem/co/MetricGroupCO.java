package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum MetricGroupCO implements PersistentEnum{
    QUALIFICATION(1), 
    BUSINESS_SCOPING(2);
    
    private int code;

    private MetricGroupCO(int code) {
        this.code = code;
    }
    
    @Override    
    public int getCode() {
        return this.code;
    }   
    
    public String getName() {
        return name();
    }
    
    private static Map<Integer, MetricGroupCO> map;
    
    public static MetricGroupCO getByCode(int code) {
        return map.get(code);
    }
    
    static {
        map = new HashMap<Integer, MetricGroupCO>();
        for (MetricGroupCO entry : MetricGroupCO.values()) {
            map.put(entry.getCode(), entry);
        }
    }
    
}

