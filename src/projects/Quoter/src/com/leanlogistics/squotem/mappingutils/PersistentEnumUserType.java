package com.leanlogistics.squotem.mappingutils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.leanlogistics.squotem.co.PersistentEnum;

/**
 * Base class to map enum fields
 * See http://www.gabiaxel.com/2011/01/better-enum-mapping-with-hibernate.html
 */
public abstract class PersistentEnumUserType<T extends PersistentEnum> implements UserType{

    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return  x == null ? 0 : x.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names,
            SessionImplementor sessionImpl, Object owner) throws HibernateException,
            SQLException {
        int id = rs.getInt(names[0]);
        if(rs.wasNull()) {
            return null;
        }
        
        for(Object ovalue : returnedClass().getEnumConstants()) {
            PersistentEnum value = (PersistentEnum) ovalue;
            if(id == value.getCode()) {
                return value;
            }
        }
        
        throw new IllegalStateException("Unknown " + returnedClass().getSimpleName() + " id");
    }
    
    

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
            SessionImplementor sessionImpl) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.INTEGER);            
        }
        else {
            st.setInt(index,  ((PersistentEnum)value).getCode());
        }
        
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

    @Override
    public abstract Class<T> returnedClass();

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.INTEGER};
    }
    
}
