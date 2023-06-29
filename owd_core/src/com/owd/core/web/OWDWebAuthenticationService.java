package com.owd.core.web;

import com.owd.core.AuthenticationException;
import com.owd.core.business.user.UserFactory;
import com.owd.core.business.user.UserManager;
import com.owd.core.managers.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

/**
 * For use with Struts2 Actions implementing SessionAware interface
 * Map object passed to authenticateUser represents map of session-scoped variables in Struts2 web application
 */
public class OWDWebAuthenticationService {
private final static Logger log =  LogManager.getLogger();

    public static final String kExtranetClientID = "extranet.clientid";
    public static final String kExtranetAdminFlag = "extranet.isAdmin";
    public static final String kExtranetInternalFlag = "extranet.isInternal";
    public static final String kExtranetAuthenticatedFlag = "extranet.auth";
    public static final String kExtranetCSRFlag = "extranet.callcenter";
    public static final String kExtranetAuthenticatedUserName = "extranet.authlogin";


    /**
     * Clears all session variables associated with authentication
     *
     * @param strutsSessionVarMap Struts-type Session variable Map - see Struts SessionAware interface for actions
     */
    public static void clearSessionAuthVariables(Map strutsSessionVarMap) {
        setSessionFlag(strutsSessionVarMap, kExtranetAuthenticatedFlag, false);
        setSessionString(strutsSessionVarMap, kExtranetClientID, null);
        setSessionFlag(strutsSessionVarMap, kExtranetAdminFlag, false);
        setSessionFlag(strutsSessionVarMap, kExtranetCSRFlag, false);
        setSessionFlag(strutsSessionVarMap, kExtranetInternalFlag, false);
        setSessionString(strutsSessionVarMap, kExtranetAuthenticatedUserName, null);
    }


    public static void authenticateUser(Map req, String userPrincipalName) throws AuthenticationException {
        if ("".equals(getSessionString(req, kExtranetClientID)) || !getSessionFlag(req, kExtranetAuthenticatedFlag) || (!(userPrincipalName.equals(getSessionString(req, kExtranetAuthenticatedUserName))))) {
            setSessionFlag(req, kExtranetAuthenticatedFlag, false);
            setSessionFlag(req, kExtranetAdminFlag, false);

            setSessionString(req, kExtranetClientID, "");

            setSessionFlag(req, kExtranetAuthenticatedFlag, false);
            setSessionFlag(req, kExtranetCSRFlag, false);
            setSessionFlag(req, kExtranetInternalFlag, false);
            //user = OWDUtilities.getCurrentUserName();
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                setSessionFlag(req, kExtranetInternalFlag, UserManager.isInternal(UserFactory.getOwdUserFromLogin(userPrincipalName)));


                conn = ConnectionManager.getConnection();

                stmt = conn.createStatement();

                String sqlQuery = "select client_fkey, is_admin "
                        + "from owd_users (NOLOCK) where login = \'" + userPrincipalName + "\' and (client_fkey = 0 OR client_fkey in (select client_id from owd_client))";

                rs = stmt.executeQuery(sqlQuery);

                if (rs.next()) {

                    setSessionFlag(req, kExtranetAdminFlag, (rs.getInt(2) == 1 && getSessionFlag(req, kExtranetInternalFlag)));
                    if (!getSessionFlag(req, kExtranetInternalFlag) || "".equals(getSessionString(req, kExtranetClientID))) {

                        setSessionString(req, kExtranetClientID, rs.getString(1));
                    }
                    setSessionFlag(req, kExtranetAuthenticatedFlag, true);
                    setSessionString(req, kExtranetAuthenticatedUserName, userPrincipalName);
                }
                log.debug("set user");
            } catch (Exception e) {

                e.printStackTrace();
                clearSessionAuthVariables(req);
                throw new AuthenticationException("Authentication error: " + e.getMessage());
            } finally {
                log.debug("finally user");
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
            //everything's copacetic!
            if (getSessionFlag(req, kExtranetInternalFlag) && "".equals(getSessionString(req, kExtranetClientID))) {
                setSessionString(req, kExtranetClientID, "55");
            }


        }

        if (!getSessionFlag(req, kExtranetAuthenticatedFlag) || (!(userPrincipalName.equals(getSessionString(req, kExtranetAuthenticatedUserName))))) {

            throw new AuthenticationException("User " + userPrincipalName + " not authenticated!");
        }

    }

    public static boolean getSessionFlag(Map req, String key) {
        String val = "";

        try {
            val = (String) req.get(key);
            if (val.equals("Y"))
                return true;
            else
                return false;
        } catch (Exception ex) {

            return false;
        }

    }

    public static void setSessionFlag(Map req, String key, boolean val) {
        try {
            if (val)
                req.put(key, "Y");
            else
                req.put(key, "N");
        } catch (Exception ex) {
        }
    }

    public static String getSessionString(Map req, String key) {
        String val = "";

        try {
            val = (String) req.get(key);
            if (val == null)
                val = "";
        } catch (Exception ex) {
        }

        return val.trim();
    }

    public static void setSessionString(Map req, String key, String val) {
        try {
            req.put(key, val);
        } catch (Exception ex) {
        }
    }
}
