package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.SpecialQuoteCostItemCO;

public class SpecialQuoteCostItemMapper extends PersistentEnumUserType<SpecialQuoteCostItemCO> {

    @Override
    public Class<SpecialQuoteCostItemCO> returnedClass() {
        return SpecialQuoteCostItemCO.class;
    }

}
