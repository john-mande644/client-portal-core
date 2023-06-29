package com.owd.hibernate;

import com.owd.core.managers.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.Configurable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by stewartbuskirk1 on 3/8/15.
 */
public class MyConnectionProvider implements ConnectionProvider, Configurable {
private final static Logger log =  LogManager.getLogger();

    String connPoolName = "OWD";

    Map<String, Connection>  cxnMapX = new HashMap<String, Connection>();
    ThreadLocal   cxnMap = new ThreadLocal();


    public MyConnectionProvider() {

    }

    public Map<String,Connection> getCxnMap()
    {
        if(cxnMap.get()==null)
        {
            cxnMap.set(new HashMap<String,Connection>());
        }
        return (Map<String,Connection>)cxnMap.get();
    }
    public void configure(Properties props) throws HibernateException {
        log.debug("PPPPPPPPPPPPP");
     //   log.debug("PROP:"+props.getProperty("hibernate.owd.dbname"));
    }

    @Override
    public void configure(Map map) {
        log.debug("CONFIGURE:"+map);
        connPoolName=(""+map.get("hibernate.owd.dbname"));
    }

    public Connection getConnection() throws SQLException {
        //log.debug("getting connection for "+connPoolName);

        try {
            if(!(getCxnMap().containsKey(connPoolName)))
            {
               // log.debug("getting new connection...");
                getCxnMap().put(connPoolName, ConnectionManager.getSpecifiedConnection(connPoolName));
            } else {
                //log.debug("found existing connection...");

            }
            //log.debug("cxnmap:"+getCxnMap());
            return getCxnMap().get(connPoolName);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw new SQLException(ex.getMessage());
        }
    }
    public void closeConnection(Connection conn) throws SQLException {
        //log.debug("FREEEEEE");
        if(conn.equals(getCxnMap().get(connPoolName)))
        {
            ConnectionManager.freeConnection(conn);
            getCxnMap().remove(connPoolName);

        }   else
        {
           // log.debug(conn+"::"+getCxnMap().get(connPoolName));

           throw new SQLException("Attempt to free connection that doesn't belong to the connectionProvider");
        }
    }

    public void close() {
        //ConnectionManager.freeConnection(conn);
        if(getCxnMap().containsKey(connPoolName))
        {
            ConnectionManager.freeConnection(getCxnMap().get(connPoolName));
            getCxnMap().remove(connPoolName);

        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
