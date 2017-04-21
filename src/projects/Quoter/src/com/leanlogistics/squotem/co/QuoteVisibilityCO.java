package com.leanlogistics.squotem.co;

public enum QuoteVisibilityCO implements PersistentEnum {
	SALES_DIRECTOR_ONLY(0),
	ALL(1);
	
	private int code;

	private QuoteVisibilityCO(int code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return code;
	}
	
    public String getName() {
        return name();
    }

}
