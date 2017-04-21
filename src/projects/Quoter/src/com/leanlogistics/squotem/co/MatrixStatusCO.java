package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum MatrixStatusCO implements PersistentEnum {
    DISABLED(0), 
    ADMIN_ONLY(1),
    ALL_USERS(2);
    
    private int code;

    private MatrixStatusCO(int code) {
        this.code = code;
    }
    
    @Override    
    public int getCode() {
        return this.code;
    }   
    
    public String getName() {
        return name();
    }
    
    private static Map<Integer, MatrixStatusCO> map;
    
    public static MatrixStatusCO getByCode(int code) {
        return map.get(code);
    }
    
    static {
        map = new HashMap<Integer, MatrixStatusCO>();
        for (MatrixStatusCO entry : MatrixStatusCO.values()) {
            map.put(entry.getCode(), entry);
        }
    }    
}