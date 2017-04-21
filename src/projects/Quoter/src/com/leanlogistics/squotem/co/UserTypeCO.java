package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum UserTypeCO implements PersistentEnum{
    BASIC(1), 
    ADMINISTRATIVE(2);
    
    private int code;

    private UserTypeCO(int code) {
        this.code = code;
    }
    
    @Override    
    public int getCode() {
        return this.code;
    }   
    
    public String getName() {
        return name();
    }
    
    private static Map<Integer, UserTypeCO> map;
    
    public static UserTypeCO getByCode(int code) {
        return map.get(code);
    }
    
    static {
        map = new HashMap<Integer, UserTypeCO>();
        for (UserTypeCO entry : UserTypeCO.values()) {
            map.put(entry.getCode(), entry);
        }
    }
    
}

