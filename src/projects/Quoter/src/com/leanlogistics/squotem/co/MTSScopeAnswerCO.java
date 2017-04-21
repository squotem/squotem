package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum MTSScopeAnswerCO implements PersistentEnum{
    YES(1), 
    NO(0);
    
    private int code;

    private MTSScopeAnswerCO(int code) {
        this.code = code;
    }
    
    @Override    
    public int getCode() {
        return this.code;
    }   
    
    public String getName() {
        return name();
    }
    
    private static Map<Integer, MTSScopeAnswerCO> map;
    
    public static MTSScopeAnswerCO getByCode(int code) {
        return map.get(code);
    }
    
    static {
        map = new HashMap<Integer, MTSScopeAnswerCO>();
        for (MTSScopeAnswerCO entry : MTSScopeAnswerCO.values()) {
            map.put(entry.getCode(), entry);
        }
    }
    
}