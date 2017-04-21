package com.leanlogistics.squotem.co;

// Used to allow hibernate mapping of Enum types
// http://www.gabiaxel.com/2011/01/better-enum-mapping-with-hibernate.html

public interface PersistentEnum {
    
    int getCode();

}
