package com.owd.web.autoship;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.user.UserFactory;
import com.owd.core.business.user.UserManager;
import com.owd.core.managers.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jan 25, 2008
 * Time: 3:04:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class autoUtil {
private final static Logger log =  LogManager.getLogger();

     public static final String kExtranetCurrMgr = "extranet.currmgr";
    public static final String kExtranetClientID = "extranet.clientid";
    public static final String kExtranetAdminFlag = "extranet.isAdmin";
    public static final String kExtranetInternalFlag = "extranet.isInternal";
    public static final String kExtranetAuthenticatedFlag = "extranet.auth";
    public static final String kExtranetCSRFlag = "extranet.callcenter";
    public static final String kExtranetAuthenticatedUserName = "extranet.authlogin";

    public static void authenticateUser(Map session,String user) {
        if (!getSessionFlag(session,kExtranetAuthenticatedFlag) || (!(user.equals(getSessionString(session,kExtranetAuthenticatedUserName))))) {
            setSessionFlag(session, kExtranetAuthenticatedFlag, false);
            //user = OWDUtilities.getCurrentUserName();

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                setSessionFlag(session, kExtranetInternalFlag, UserManager.isInternal(UserFactory.getOwdUserFromLogin(user)));


                conn = ConnectionManager.getConnection();

                stmt = conn.createStatement();

                String sqlQuery = "select client_fkey, is_admin "
                        + "from owd_users (NOLOCK) where login = \'" + user + "\' and (client_fkey = 0 OR client_fkey in (select client_id from owd_client))";
                log.debug(sqlQuery);
                rs = stmt.executeQuery(sqlQuery);

                if (rs.next()) {
                    log.debug("found user");
                    setSessionFlag(session, kExtranetAdminFlag, (rs.getInt(2) == 1 && getSessionFlag(session, kExtranetInternalFlag)));
                    if(false==getSessionFlag(session, kExtranetInternalFlag) )
                    {
                        setSessionString(session, kExtranetClientID, rs.getString(1));
                    }else{
                    setSessionString(session, kExtranetClientID, "0");
                }
                    setSessionFlag(session, kExtranetAuthenticatedFlag, true);
                setSessionString(session, kExtranetAuthenticatedUserName, user);
                }

            } catch (Exception e) {

                e.printStackTrace();
                setSessionFlag(session, kExtranetAuthenticatedFlag, false);
                setSessionString(session, kExtranetAuthenticatedUserName, "");
            } finally {

                try {
                    if (rs != null)
                        rs.close();
                    if (stmt != null)
                        stmt.close();
                    conn.close();
                } catch (Exception e) {

                }

            }
        } else {

            //get user info from database
        }
    }

    public static String getSessionString(Map session, String key) {
        String val = "";

        try {
            val = (String) session.get(key);
            if (val == null)
                val = "";
        } catch (Exception ex) {
        }

        return val.trim();
    }

    public static void setSessionFlag(Map session, String key, boolean val) {
            try {
                if (val)
                    session.put(key, "Y");
                else
                    session.put(key, "N");
            } catch (Exception ex) {
            }
        }
 public static void setSessionString(Map session, String key, String val) {
        try {
            session.put(key, val);
        } catch (Exception ex) {
        }
    }
    public static boolean getSessionFlag(Map session, String key) {
        String val = "";

        try {
            val = (String) session.get(key);
            if (val.equals("Y"))
                return true;
            else
                return false;
        } catch (Exception ex) {

            return false;
        }

    }
}
