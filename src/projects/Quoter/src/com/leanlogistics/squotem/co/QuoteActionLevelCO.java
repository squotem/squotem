package com.leanlogistics.squotem.co;

import java.util.HashMap;
import java.util.Map;

public enum QuoteActionLevelCO implements PersistentEnum {
	BASELINE(1),
	BUSINESS_PLAN(2),
	ALL(3);
	
	private int code;
	
	private QuoteActionLevelCO(int code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return code;
	}

    public String getName() {
        return name();
    }

	private static Map<Integer, QuoteActionLevelCO> map;
	
	public static QuoteActionLevelCO getByCode(int code) {
	    return map.get(code);
	}
	
	static {
	    map = new HashMap<Integer, QuoteActionLevelCO>();
	    for (QuoteActionLevelCO entry : QuoteActionLevelCO.values()) {
	        map.put(entry.getCode(), entry);
	    }
	}
	
}
