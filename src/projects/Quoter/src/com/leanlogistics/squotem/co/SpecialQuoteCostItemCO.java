package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum SpecialQuoteCostItemCO implements PersistentEnum {
    YES(1);
    
    private int code;

    private SpecialQuoteCostItemCO(int code) {
        this.code = code;
    }
    
    @Override
    public int getCode() {
        return this.code;
    }   
    
    public String getName() {
        return name();
    }
    
    private static Map<Integer, SpecialQuoteCostItemCO> map;
    
    public static SpecialQuoteCostItemCO getByCode(int code) {
        return map.get(code);
    }
    
    static {
        map = new HashMap<Integer, SpecialQuoteCostItemCO>();
        for (SpecialQuoteCostItemCO entry : SpecialQuoteCostItemCO.values()) {
            map.put(entry.getCode(), entry);
        }
    }

}
