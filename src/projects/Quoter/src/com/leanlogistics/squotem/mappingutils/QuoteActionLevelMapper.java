package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.QuoteActionLevelCO;

public class QuoteActionLevelMapper extends PersistentEnumUserType<QuoteActionLevelCO> {

	@Override
    public Class<QuoteActionLevelCO> returnedClass() {
        return QuoteActionLevelCO.class;
    }

}
