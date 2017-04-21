package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.MetricDataTypeCO;

public class MetricDataTypeMapper extends PersistentEnumUserType<MetricDataTypeCO> {

    @Override
    public Class<MetricDataTypeCO> returnedClass() {
        return MetricDataTypeCO.class;
    }

}
