package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.CustomerTypeCO;

public class CustomerTypeMapper extends PersistentEnumUserType<CustomerTypeCO> {

    @Override
    public Class<CustomerTypeCO> returnedClass() {
        return CustomerTypeCO.class;
    }
}
