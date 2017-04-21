package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.FeeCategoryCO;


public class FeeCategoryMapper extends PersistentEnumUserType<FeeCategoryCO> {

    @Override
    public Class<FeeCategoryCO> returnedClass() {
        return FeeCategoryCO.class;
    }

}
