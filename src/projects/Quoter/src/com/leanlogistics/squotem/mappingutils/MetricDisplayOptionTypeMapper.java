package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.MetricDisplayOptionTypeCO;

public class MetricDisplayOptionTypeMapper extends PersistentEnumUserType<MetricDisplayOptionTypeCO> {

    @Override
    public Class<MetricDisplayOptionTypeCO> returnedClass() {
        return MetricDisplayOptionTypeCO.class;
    }

}
