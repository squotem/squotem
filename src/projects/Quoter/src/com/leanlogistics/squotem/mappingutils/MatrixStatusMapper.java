package com.leanlogistics.squotem.mappingutils;

import com.leanlogistics.squotem.co.MatrixStatusCO;

public class MatrixStatusMapper extends PersistentEnumUserType<MatrixStatusCO> {

    @Override
    public Class<MatrixStatusCO> returnedClass() {
        return MatrixStatusCO.class;
    }
}
