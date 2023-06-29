package com.owd.hibernate;

import org.apache.commons.lang.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by stewartbuskirk1 on 8/24/15.
 */
public class UtfVarcharType implements UserType {
private final static Logger log =  LogManager.getLogger();




    @Override
    public int[] sqlTypes() {
        return new int[] {
                Types.VARCHAR
        };
    }

    @Override
    public Class returnedClass() {
        return String.class;
    }



    @Override
    public Object nullSafeGet(
            ResultSet rs,
            String[] names,
            SessionImplementor session,
            Object owner)
            throws HibernateException, SQLException
    {
        assert names.length == 1;
        String value = "";
        log.debug("utf8 override start");
        try{
            value = new String(rs.getBytes(names[0]),"UTF-8");

            log.debug("utf8 override of ["+rs.getString(names[0])+"] with ["+value+"]");
        }  catch(Exception ex){
            log.debug("utf fail!!!");
            ex.printStackTrace();
            value = (String) StringType.INSTANCE.nullSafeGet(rs, names, session, owner);
        }

        return value;
    }


    @Override
    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SessionImplementor session)
            throws HibernateException, SQLException
    {
        log.debug("setting type of "+value.getClass().getCanonicalName());
        StringType.INSTANCE.nullSafeSet(st, value, index, session);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if ( value == null ) {
            return null;
        }
        return value;
    }



    public String objectToSQLString(Object value, Dialect dialect)
            throws Exception
    {
        if ( value == null ) {
            return null;
        }

        String sqlString = StringType.INSTANCE.objectToSQLString(value.toString(), dialect);

        return sqlString;
    }



    /* (non-Javadoc)
  * @see org.hibernate.usertype.UserType#isMutable()
  */
    public boolean isMutable() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
     */
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.equals(x, y);
    }

    /* (non-Javadoc)
     * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    public int hashCode(Object x) throws HibernateException {
        assert (x != null);
        return x.hashCode();
    }

    /* (non-Javadoc)
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
     */
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        // also safe for mutable objects
        return deepCopy(cached);
    }

    /**
     * Disassembles the object in preparation for serialization.
     * See {@link org.hibernate.usertype.UserType#disassemble(java.lang.Object)}.
     * <p>
     * Expects {@link #deepCopy(Object)} to return a {@code Serializable}.
     * <strong>Subtypes whose {@code deepCopy} implementation returns a
     * non-serializable object must override this method.</strong>
     */
    public Serializable disassemble(Object value) throws HibernateException {
        // also safe for mutable objects
        Object deepCopy = deepCopy(value);

        if (!(deepCopy instanceof Serializable)) {
            throw new SerializationException(
                    String.format("deepCopy of %s is not serializable", value), null);
        }

        return (Serializable) deepCopy;
    }

    /* (non-Javadoc)
     * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        // also safe for mutable objects
        return deepCopy(original);
    }

}
