package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum MetricDataTypeCO implements PersistentEnum {
    NUMERIC(1), 
    STRING(2),
    BOOLEAN(3),
    // Combo metrics: If condition = true then numeric/string value is captured
    BOOL_PLUS_NUMERIC(4),
    BOOL_PLUS_STRING(5);
    
    private int code;

    private MetricDataTypeCO(int code) {
        this.code = code;
    }
    
    @Override    
    public int getCode() {
        return this.code;
    }   
    
    public String getName() {
        return name();
    }
    
    private static Map<Integer, MetricDataTypeCO> map;
    
    public static MetricDataTypeCO getByCode(int code) {
        return map.get(code);
    }
    
    static {
        map = new HashMap<Integer, MetricDataTypeCO>();
        for (MetricDataTypeCO entry : MetricDataTypeCO.values()) {
            map.put(entry.getCode(), entry);
        }
    }
    

}
