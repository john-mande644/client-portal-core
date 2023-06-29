package com.owd.jobs.jobobjects;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 10/16/13
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class SqlUtils {
private final static Logger log =  LogManager.getLogger();

    public static List<Map<String,Object>> getResultSetMap(Session session, String aquery)
    {
        
    Query query=session.createSQLQuery(aquery);
    query.setResultTransformer(new OrderedResultTransformer());
    List<Map<String,Object>> aliasToValueMapList=query.list();
        return aliasToValueMapList;
    }
}
