package com.owd.jobs.jobobjects;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.transform.ResultTransformer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 10/16/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderedResultTransformer  implements ResultTransformer, Serializable {
private final static Logger log =  LogManager.getLogger();

    public static final OrderedResultTransformer INSTANCE = new OrderedResultTransformer();

    /**
     * Instantiate AliasToEntityMapResultTransformer.
     * <p/>
     * todo : make private, see deprecation...
     *
     */
    public OrderedResultTransformer() {
    }


    public List transformList(List collection)
    {
        return collection;
    }


    /**
     * {@inheritDoc}
     *   var result = new Dictionary<string, object>();
     for (int i = 0; i < aliases.Length; i++)
     {
     result[aliases[i]] = tuple[i];
     }
     return result;
     *
     */
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map result = new LinkedHashMap(aliases.length);
        for ( int i=0; i<aliases.length; i++ ) {
                result.put( aliases[i], tuple[i] );
        }
        return result;
    }



    /**
     * Serialization hook for ensuring singleton uniqueing.
     *
     * @return The singleton instance : {@link #INSTANCE}
     */
    private Object readResolve() {
        return INSTANCE;
    }


    // all AliasToEntityMapResultTransformer are considered equal ~~~~~~~~~~~~~

    /**
     * All AliasToEntityMapResultTransformer are considered equal
     *
     * @param other The other instance to check for equality
     * @return True if (non-null) other is a instance of AliasToEntityMapResultTransformer.
     */
    public boolean equals(Object other) {
        // todo : we can remove this once the deprecated ctor can be made private...
        return other != null && OrderedResultTransformer.class.isInstance( other );
    }

    /**
     * All AliasToEntityMapResultTransformer are considered equal
     *
     * @return We simply return the hashCode of the AliasToEntityMapResultTransformer class name string.
     */
    public int hashCode() {
        // todo : we can remove this once the deprecated ctor can be made private...
        return getClass().getName().hashCode();
    }
}
