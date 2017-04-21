package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum FeeCategoryCO implements PersistentEnum {
        IMPL(1), 
        MONTHLY(2);
        
        private int code;

        private FeeCategoryCO(int code) {
            this.code = code;
        }
        
        @Override    
        public int getCode() {
            return this.code;
        }   
        
        public String getName() {
            return name();
        }
        
        private static Map<Integer, FeeCategoryCO> map;
        
        public static FeeCategoryCO getByCode(int code) {
            return map.get(code);
        }
        
        static {
            map = new HashMap<Integer, FeeCategoryCO>();
            for (FeeCategoryCO entry : FeeCategoryCO.values()) {
                map.put(entry.getCode(), entry);
            }
        }
        
    }
