package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.UserTypeCO;


public class UserTypeMapper extends PersistentEnumUserType<UserTypeCO> {

    @Override
    public Class<UserTypeCO> returnedClass() {
        return UserTypeCO.class;
    }

}
