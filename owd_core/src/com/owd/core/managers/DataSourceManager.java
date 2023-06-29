package com.owd.core.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 9/18/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataSourceManager {
private final static Logger log =  LogManager.getLogger();

    private String name = null;
    private String driver = null;
    private String environment = null;
    private String description = null;
    private String url = null;
    private String userId = null;
    private String userPassword = null;
    private String testSQL = null;
    private String dialect = null;

    private static String masterDriver = null;
    private static String masterUrl = null;
    private static String masterUserId = null;
    private static String masterUserPasssword = null;
    private static String masterRequester = null;
    private static String masterTestSql = null;
    private static String masterDialect = null;

    private static String owdDriverClass = "net.sourceforge.jtds.jdbc.Driver";

    public static String getMasterEnvironment() {

        return masterEnvironment;
    }

    /**
     * Set the master environment.
     * @param masterEnvironment
     */
    public static void setMasterEnvironment(String masterEnvironment) {
        DataSourceManager.masterEnvironment = masterEnvironment;
    }

    private static String masterEnvironment = "test";

    private static DataSourceManager instance = null;

    private static ArrayList<DataSourceManager>dataSourceManagerList = new ArrayList<DataSourceManager>();


    /**
     * Get named data source.
     * @param name
     * @return
     */
    public static DataSourceManager getDataSource(String name) {
        Iterator<DataSourceManager> dataSourceManagerIterator = dataSourceManagerList.iterator();

        while(dataSourceManagerIterator.hasNext()) {
            DataSourceManager dataSourceManager = dataSourceManagerIterator.next();
            if (dataSourceManager.getName().equals(name)) {
                return dataSourceManager;
            }
        }
        DataSourceManager dataSourceManager = new DataSourceManager(name, locateEnvironment());

        dataSourceManagerList.add(dataSourceManager);
        return dataSourceManager;
    }

    /**
     * Loads environment info from environment.properties file.
     *
     * @return
     */
    private static String locateEnvironment() {

        String environment = null;
        Properties environmentProperties = new Properties();

        //master environment set through environment variable "com.owd.environment'

        Properties env = System.getProperties();
        for (Object envName : env.keySet()) {
            if(envName.toString().contains("com.owd"))
            {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
            }
        }

        try {
            //load a properties file
//            environmentProperties.load(new FileInputStream("properties/environment.properties"));
            environment = System.getProperty("com.owd.environment");
            if(environment==null)
            {
                environment="production";
            }
            environmentProperties.load(DataSourceManager.class.getClassLoader().getResourceAsStream("com/owd/properties/"+environment + ".properties"));

            //get the property value and print it out
           // environment = environmentProperties.getProperty("environment");
            masterDriver = environmentProperties.getProperty("masterDriver");
            masterUrl = environmentProperties.getProperty("masterUrl");
            masterUserId = environmentProperties.getProperty("masterUserId");
            masterUserPasssword = environmentProperties.getProperty("masterUserPassword");
            masterRequester = environmentProperties.getProperty("masterRequester");
            masterTestSql = environmentProperties.getProperty("masterTestSql");
            masterDialect = environmentProperties.getProperty("masterDialect");

            log.debug("******************\r\n\r\nCURRENT DATABASE ENVIRONMENT: " + environment.toUpperCase()+"\r\n\r\n******************\r\n");

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return environment;
    }

    /**
     * Constructor. Environment string is used to load connection data from default data source.
     *
      * @param environment
     */
    public DataSourceManager(String environment, String dataSourceName) {
        loadConnectData(environment, dataSourceName);
    }

    private void setEnvironment(String environment) {
        this.environment = environment;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    private void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getDescription() {
        return description;
    }

    public String getDialect() {
        return dialect;
    }

    private void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getUrl() {
        return url;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Retrieves logon information from connection table in default database.
     * @param environment
     * @throws RuntimeException
     */
    public void loadConnectData(String name, String environment) throws RuntimeException {

        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection cxn = null;

        try {
            cxn = getMasterConnection();

            String esql = "select description, url, user_id, user_password, driver, test_sql, dialect " +
                    "from OWD_data_source (NOLOCK) " +
                    "where environment = ? " +
                    "and name = ?";

            stmt = cxn.prepareStatement(esql);
            stmt.setString(1, environment);
            stmt.setString(2, name);

            rs = stmt.executeQuery();

            if (rs.next()) {
                setName(name);
                setEnvironment(environment);

                setDescription(rs.getString(1));
                setUrl(rs.getString(2));
                setUserId(rs.getString(3));
                setUserPassword(rs.getString(4));
                setDriver(rs.getString(5));
                setTestSQL(rs.getString(6));
                setDialect(rs.getString(7));
            } else {
                throw new Exception("Environment: " + environment + " not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
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
                ConnectionManager.freeConnection(cxn);
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Get master conection to obtain login information.
     *
     * @return
     * @throws Exception
     */
    private static Connection getMasterConnection() throws Exception {

        Connection cxn = ConnectionManager.getSpecifiedConnection(masterDriver, masterUrl, masterUserId, masterUserPasssword,
                masterRequester, masterTestSql);

        cxn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        return cxn;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getDriver() {
        return driver;
    }

    private void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTestSQL() {
        return testSQL;
    }

    private void setTestSQL(String testSQL) {
        this.testSQL = testSQL;
    }

    public static String getOwdDriverClass() {
        return owdDriverClass;
    }

    private static void setOwdDriverClass(String owdDriverClass) {
        DataSourceManager.owdDriverClass = owdDriverClass;
    }
}
