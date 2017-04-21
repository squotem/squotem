package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.MTSScopeAnswerCO;

public class MTSScopeAnswerMapper extends PersistentEnumUserType<MTSScopeAnswerCO> {

    @Override
    public Class<MTSScopeAnswerCO> returnedClass() {
        return MTSScopeAnswerCO.class;
    }

}
