package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;

public class QuoteWorkflowStatusMapper  extends PersistentEnumUserType<QuoteWorkflowStatusCO> {
    

    @Override
    public Class<QuoteWorkflowStatusCO> returnedClass() {
        return QuoteWorkflowStatusCO.class;
    }    

}
