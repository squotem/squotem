package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum CustomerTypeCO implements PersistentEnum{
    SHIPPER(1), 
    TPL(2);
    
    private int code;

    private CustomerTypeCO(int code) {
        this.code = code;
    }
    
    @Override    
    public int getCode() {
        return this.code;
    }   
    
    public String getName() {
        return name();
    }
    
    private static Map<Integer, CustomerTypeCO> map;
    
    public static CustomerTypeCO getByCode(int code) {
        return map.get(code);
    }
    
    static {
        map = new HashMap<Integer, CustomerTypeCO>();
        for (CustomerTypeCO entry : CustomerTypeCO.values()) {
            map.put(entry.getCode(), entry);
        }
    }
    
}

