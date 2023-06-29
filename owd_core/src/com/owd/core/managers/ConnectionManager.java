package com.owd.core.managers;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ConnectionManager {
private final static Logger log =  LogManager.getLogger();

    private static ConnectionManager selfRef = null;

    //uncomment these and comment next group to use logging driver. Requires logdriver.jar
             //  public static String owdDriverClass = "net.rkbloom.logdriver.LogDriver";
            //   public static String owdLoggingDriver = "log:net.sourceforge.jtds.jdbc.Driver:";

       //uncomment these and comment previous group to use normal jtds driver
    public static String owdDriverClass = "net.sourceforge.jtds.jdbc.Driver";
    public static String owdLoggingDriver = "";

    private ConnectionManager() {
        //PreferencesManager.getPreferencesManager().getPrefs(
    }

    public static ConnectionManager getConnectionManager() {

        if (selfRef == null)
            selfRef = new ConnectionManager();

        return selfRef;

    }

    public static Connection getConnection() throws Exception {


        return getTestConnection();


    }


    protected static DataSource pooled = null;
    protected static Map poolMap = new TreeMap();

    public static String databaseName = "OWD";

    public static Connection getTestConnection() throws Exception {

        DataSourceManager dataSourceManager = DataSourceManager.getDataSource("OWD");


//        Connection cxn = getSpecifiedConnection(owdDriverClass, "jdbc:"+ owdLoggingDriver+"jtds:sqlserver://172.16.2.254/"+databaseName,
//                "sa",
//                "wahoo", "generic", "select count(*) from "+databaseName+".dbo.pooltester");
        log.debug("getting connection for "+dataSourceManager.getUrl());
        Connection cxn = getSpecifiedConnection( dataSourceManager.getDriver(), dataSourceManager.getUrl(),
                dataSourceManager.getUserId(), dataSourceManager.getUserPassword(), dataSourceManager.getDescription(), dataSourceManager.getTestSQL());

        cxn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        return cxn;



    }

    /**
     *
     * @param dataSourceName
     * @return
     * @throws Exception
     */
    public static Connection getSpecifiedConnection(String dataSourceName) throws Exception {

        DataSourceManager dataSourceManager = DataSourceManager.getDataSource(dataSourceName);

        return getSpecifiedConnection(dataSourceManager.getDriver(),
                dataSourceManager.getUrl(),
                dataSourceManager.getUserId(),
                dataSourceManager.getUserPassword(),
                dataSourceManager.getDescription(),
                dataSourceManager.getTestSQL());
    }

    public static Connection getSpecifiedConnection(String driver, String dbURL, String user, String pwd, String requestor, String testSQL) throws Exception {


//        String driver, String dbURL, String user, String pwd, String requestor, String testSQL
        String poolName = requestor + ":" + driver + dbURL + user + pwd;

        synchronized (poolMap) {
            if (poolMap.containsKey(poolName)) {
                //  log.debug("Getting cxn for: " + poolName);
            } else {
                Class.forName(driver).newInstance();

              //  PoolConfig pc = new PoolConfig();

                ComboPooledDataSource cpds = new  ComboPooledDataSource();
                cpds.setDriverClass(driver); //loads the jdbc driver
                cpds.setJdbcUrl(dbURL);
                cpds.setUser(user);
                cpds.setPassword(pwd);
                cpds.setBreakAfterAcquireFailure(false);
// the settings below are optional -- c3p0 can work with defaults
                cpds.setAcquireIncrement(1);
                cpds.setMaxStatements(0);  //turn off Statement pooling
                cpds.setPreferredTestQuery(testSQL);
                cpds.setInitialPoolSize(1);
                cpds.setMinPoolSize(0);
                cpds.setMaxPoolSize(100);
                cpds.setMaxIdleTime(120);
                cpds.setCheckoutTimeout(30000);
                cpds.setAcquireRetryAttempts(30);
                cpds.setTestConnectionOnCheckin(true);
                cpds.setTestConnectionOnCheckout(false);
                cpds.setUnreturnedConnectionTimeout(3600);
                cpds.setDebugUnreturnedConnectionStackTraces(false);


                poolMap.put(poolName, cpds);
                log.debug("Creating pool for: " + poolName);
            }
        }
        Connection con = ((DataSource) poolMap.get(poolName)).getConnection();

        // make sure it's a c3p0 PooledDataSource
        if ((poolMap.get(poolName)) instanceof PooledDataSource) {
            PooledDataSource pds = (PooledDataSource) poolMap.get(poolName);
        }
        con.setAutoCommit(false);

        try {
            setQuotedIdentifier(con, false);
        }catch(Exception ex){}

        log.debug("Connections:"+((ComboPooledDataSource)poolMap.get(poolName)).getNumConnectionsAllUsers() );
        con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        return con;
    }

    static public void setQuotedIdentifier(Connection cxn, boolean on) throws Exception {
        String rString = null;

        Statement stmt = cxn.createStatement();

        String esql = "SET QUOTED_IDENTIFIER OFF";
        if (on == true)
            esql = "SET QUOTED_IDENTIFIER ON";

        boolean hasResultSet = stmt.execute(esql);
        stmt.close();
    }

    public static void freeConnection(Connection con)  {
         try{ con.rollback(); }catch(Exception ex){}
       try{ con.close(); }catch(Exception ex){}
        log.debug("freeconnection");

    }



      static public int getCanReleaseBackorder(String orderNum) throws Exception {
        int rInt = 0;
        ResultSet rs = null;
        Statement stmt = null;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();

            stmt = cxn.createStatement();
            String esql = "exec getCanReleaseBackorder \'" + orderNum + "\'";


            rs = stmt.executeQuery(esql);

            while (rs.next()) {
                rInt = rs.getInt(1);
            }


        } catch (Exception ex) {


        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }


            try {
                stmt.close();
            } catch (Exception exc) {
            }

            freeConnection(cxn);

        }
        return rInt;
    }


    static public String getOrderKeyID(String orderNum) throws Exception {
        String rString = null;
        ResultSet rs = null;
        Statement stmt = null;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();

            stmt = cxn.createStatement();
            String esql = "exec get_order_key \'" + orderNum + "\'";


            rs = stmt.executeQuery(esql);

            while (rs.next()) {
                rString = rs.getString(1);
            }


        } catch (Exception ex) {


        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }


            try {
                stmt.close();
            } catch (Exception exc) {
            }

            freeConnection(cxn);

        }
        return rString;
    }

    static public String getOrderKey(String orderNum, String ckey) throws Exception {
        String rString = null;
        ResultSet rs = null;
        Statement stmt = null;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();

            stmt = cxn.createStatement();
            String esql = "select order_id from owd_order where client_fkey = " + ckey + " and order_num =  \'" + orderNum + "\'";


            rs = stmt.executeQuery(esql);

            boolean gotOrder = false;
            while (rs.next()) {
                if(gotOrder)
                {
                   throw new Exception("Non-unique OWD order reference "+orderNum+" used to find order key.");
                }
                gotOrder = true;
                rString = rs.getString(1);
            }


        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                stmt.close();
            } catch (Exception exc) {
            }

            freeConnection(cxn);
        }
        return rString;

    }

      static public List getOrderKeyForClientIDAndShippedDate(String ckey, String shippedDate) throws Exception
    {
        ResultSet rs=null;
        Statement stmt = null;
        Connection cxn = null;
        List resultList = new ArrayList();


        try{
        cxn = ConnectionManager.getConnection();

        stmt = cxn.createStatement();
        String esql = "select order_id from owd_order where client_fkey = "+ckey+" and convert(datetime,convert(varchar,shipped_on,101)) =  \'"+shippedDate+"\'";
        esql=esql+" order by order_id asc";





        rs = stmt.executeQuery(esql);


      while (rs.next())
            {
                resultList.add(rs.getString(1));
            }


        }catch(Exception ex)
        {


        }finally{
             try{                rs.close();}catch(Exception x)
        {}

           try{      stmt.close();  }catch(Exception xx)
        {}
           try{      freeConnection(cxn); }catch(Exception xx)
        {}

        }
        return resultList;


    }

      static public List getOrderKeyForClientIDAndCreatedDate(String ckey, String createdDate) throws Exception
    {
        ResultSet rs=null;
        Statement stmt = null;
        Connection cxn = null;
        List resultList = new ArrayList();


        try{
        cxn = ConnectionManager.getConnection();

        stmt = cxn.createStatement();
        String esql = "select order_id from owd_order where client_fkey = "+ckey+" and convert(datetime,convert(varchar,created_date,101)) =  \'"+createdDate+"\'";
        esql=esql+" order by order_id asc";





        rs = stmt.executeQuery(esql);

           
      while (rs.next())
            {
                resultList.add(rs.getString(1));
            }


        }catch(Exception ex)
        {


        }finally{
             try{                rs.close();}catch(Exception x)
        {}

           try{      stmt.close();  }catch(Exception xx)
        {}
           try{      freeConnection(cxn); }catch(Exception xx)
        {}

        }
        return resultList;


    }

    static public List getActiveOrderKeyForClientID(String orderNum, String ckey, String prefixSearch) throws Exception
    {
        String rString = null;
        ResultSet rs=null;
        Statement stmt = null;
        Connection cxn = null;
        List resultList = new ArrayList();

        if (prefixSearch == null) prefixSearch = "FALSE";

        try{
            cxn = ConnectionManager.getConnection();

            stmt = cxn.createStatement();
            String esql = "select order_id from owd_order where client_fkey = "+ckey+" and order_refnum =  \'"+orderNum+"\' and is_void=0";
            esql=esql+" order by order_id asc";


            if (prefixSearch.equalsIgnoreCase("TRUE"))
            {
                esql = "select order_id from owd_order where client_fkey = "+ckey+" and is_void = 0 and order_refnum like  \'"+orderNum+"%\'  and is_void=0";
                esql=esql+" order by order_refnum asc";
            }


            boolean hasResultSet = stmt.execute(esql);

            rs = stmt.getResultSet();


            while (rs.next())
            {
                resultList.add(rs.getString(1));
            }



        }catch(Exception ex)
        {


        }finally{
            try{         rs.close();    }catch(Exception xx){}

            try{      stmt.close();          }catch(Exception xx){}
            try{      freeConnection(cxn);          }catch(Exception xx){}

        }
        return resultList;


    }

    static public List getOrderKeyForClientID(String orderNum, String ckey, String prefixSearch) throws Exception
    {
        String rString = null;
        ResultSet rs=null;
        Statement stmt = null;
        Connection cxn = null;
        List resultList = new ArrayList();

        if (prefixSearch == null) prefixSearch = "FALSE";

        try{
        cxn = ConnectionManager.getConnection();

        stmt = cxn.createStatement();
        String esql = "select order_id from owd_order where client_fkey = "+ckey+" and order_refnum =  \'"+orderNum+"\'";
        esql=esql+" order by order_id asc";


        if (prefixSearch.equalsIgnoreCase("TRUE"))
        {
            esql = "select order_id from owd_order where client_fkey = "+ckey+" and is_void = 0 and order_refnum like  \'"+orderNum+"%\'";
        esql=esql+" order by order_refnum asc";
        }


        boolean hasResultSet = stmt.execute(esql);

             rs = stmt.getResultSet();


            while (rs.next())
            {
                resultList.add(rs.getString(1));
            }



        }catch(Exception ex)
        {


        }finally{
           try{         rs.close();    }catch(Exception xx){}

           try{      stmt.close();          }catch(Exception xx){}
           try{      freeConnection(cxn);          }catch(Exception xx){}

        }
        return resultList;


    }

    static public String getOrderKeyID(Connection cxn, String orderNum) throws Exception {
        String rString = null;

        // Connection cxn = ConnectionManager.getConnectionManager().getConnection();

        // OWDUtilities.debugApp("getting id for "+orderNum);
        Statement stmt = cxn.createStatement();
        String esql = "exec get_order_key \'" + orderNum + "\'";
       // OWDUtilities.debugApp(esql);

        ResultSet rs = stmt.executeQuery(esql);

        if (rs.next()) {
            rString = rs.getString(1);
        }

        rs.close();

        stmt.close();

        //  freeConnection(cxn);

        return rString;

    }


    static public boolean InventoryExists(String part, String clientID) throws Exception {
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean orderExists = false;

        if ((part == null) || ("".equals(part))) {
            throw new Exception("Part # cannot be left blank");
        }

        try {
            cxn = getConnection();
            String esql = "select count(inventory_id) from  owd_inventory where  inventory_num = \'" + part + "\' and client_fkey=" + clientID;
            stmt = cxn.createStatement();
            rs = stmt.executeQuery(esql);

            if (rs != null) {


                if (rs.next()) {
                    orderExists = (0 < rs.getInt(1));
                }
            }

        } catch (Exception ex) {
            throw ex;

        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
            return orderExists;
        }

    }


    static public Connection getRawConnection() throws Exception {
        String jdbcDriver = owdDriverClass;

        Class.forName(jdbcDriver).newInstance();
        //log.debug("got test cxn");
        Connection cxn = java.sql.DriverManager.getConnection("jdbc:"+ owdLoggingDriver+"jtds:sqlserver://172.16.2.254/OWD", "sa", "wahoo");
        cxn.setAutoCommit(false);

        return cxn;
    }

    static public String getNextID(String module) throws Exception {
        String rString = null;

        Connection cxn = ConnectionManager.getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            //log.debug("getting id for " + module);
            stmt = cxn.createStatement();
            String esql = "exec get_next_id \'" + module + "\'";

            rs = stmt.executeQuery(esql);

            if (rs.next()) {
                //log.debug("gotnext");
                rString = rs.getString(1);
            }

            cxn.commit();
        } catch (Exception ex) {
            throw ex;
        } finally {


            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }

        }
        if (rString != null) {
            if (rString.length() < 7) {
                for (int i = 0; i <= (7 - rString.length()); i++)
                    rString = "0" + rString;
            }

        }
        return rString;

    }

    static public void main(String[] args) {
        ConnectionManager cm = ConnectionManager.getConnectionManager();

        // OWDUtilities.debugApp("getting list");
        try {


            //  owdChoiceList bob = cm.getChoiceList("Service");
            // //log.debug(bob);

            //log.debug("Next order id = " + getNextID("Order"));


        } catch (Exception ex) {
            //log.debug(ex);
            ex.printStackTrace();
        }
    }


    static public int getOrdersCount(int clientId, String shippedDate, String createdDate, String orderRefNum, String prefixSearch) throws Exception   {
        int count = 0;
        ResultSet rs=null;
        Statement stmt = null;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();
            stmt = cxn.createStatement();
            String esql = "select count(*) from owd_order where client_fkey = "+clientId;

            if (orderRefNum!= null && orderRefNum != "") {
                if (prefixSearch != null && prefixSearch.equalsIgnoreCase("TRUE")) {
                    esql += " and order_refnum like  \'"+orderRefNum+"%\'  and is_void=0";
                } else  {
                    esql += " and order_refnum =  \'"+orderRefNum+"\' and is_void=0";
                }
            }

            if (shippedDate!=null && shippedDate!= "19000101") {
                esql +=  " and convert(datetime,convert(varchar,shipped_on,101)) =  \'"+shippedDate+"\'";
            }

            if (createdDate!=null && createdDate!= "19000101") {
                esql +=  " and convert(datetime,convert(varchar,created_date,101)) =  \'"+createdDate+"\'";
            }

            rs =  stmt.executeQuery(esql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception ex)  {

        } finally {
            try { rs.close();  }catch(Exception xx){}
            try { stmt.close();  }catch(Exception xx){}
            try { freeConnection(cxn); }catch(Exception xx){}
        }


        return  count;
    }

    public static List getPagedOrderKeysForClientID(int clientId, String shippedDate, String createdDate, String orderRefNum, String prefixSearch, int pageIndex, int pageSize) {

        List resultList = new ArrayList();
        ResultSet rs=null;
        Statement stmt = null;
        Connection cxn = null;

        try{
            cxn = ConnectionManager.getConnection();
            stmt = cxn.createStatement();
            String esql = "select order_id from owd_order where client_fkey = "+clientId;

            if (orderRefNum!= null && orderRefNum != "") {
                if (prefixSearch != null && prefixSearch.equalsIgnoreCase("TRUE")) {
                    esql += " and order_refnum like  \'"+orderRefNum+"%\'  and is_void=0";
                } else  {
                    esql += " and order_refnum =  \'"+orderRefNum+"\' and is_void=0";
                }
            }

            if  (shippedDate!=null && shippedDate!= "19000101") {
                esql +=  " and convert(datetime,convert(varchar,shipped_on,101)) =  \'"+shippedDate+"\'";
            }

            if (createdDate!=null && createdDate!= "19000101") {
                esql +=  " and convert(datetime,convert(varchar,created_date,101)) =  \'"+createdDate+"\'";
            }

            esql += " order by order_id";

            if (pageSize > 0 && pageIndex >= 0) {
                int rowsOffset = pageIndex * pageSize;

                if(pageIndex >= 0 && pageSize >= 0) {
                    esql += " OFFSET " + rowsOffset + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY ";
                }
            }

            rs =  stmt.executeQuery(esql);
            while (rs.next()) {
                resultList.add(rs.getString(1));
            }
        } catch (Exception ex) {

        } finally {
            try { rs.close();  }catch(Exception xx){}
            try { stmt.close();  }catch(Exception xx){}
            try { freeConnection(cxn); }catch(Exception xx){}
        }

        return  resultList;
    }
}
