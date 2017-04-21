package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.MetricGroupCO;

public class MetricGroupMapper extends PersistentEnumUserType<MetricGroupCO> {

    @Override
    public Class<MetricGroupCO> returnedClass() {
        return MetricGroupCO.class;
    }

}