package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum MetricDisplayOptionTypeCO  implements PersistentEnum {
    NORMAL(1), 
    TOTAL(2),
    TOTAL_AND_PCT(3);

    private int code;

    private MetricDisplayOptionTypeCO(int code) {
        this.code = code;
    }
    
    @Override    
    public int getCode() {
        return this.code;
    }   
    
    public String getName() {
        return name();
    }
    
    private static Map<Integer, MetricDisplayOptionTypeCO> map;
    
    public static MetricDisplayOptionTypeCO getByCode(int code) {
        return map.get(code);
    }
    
    static {
        map = new HashMap<Integer, MetricDisplayOptionTypeCO>();
        for (MetricDisplayOptionTypeCO entry : MetricDisplayOptionTypeCO.values()) {
            map.put(entry.getCode(), entry);
        }
    }

}
