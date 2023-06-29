package com.owd.web.internal.user;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.user.UserManager;
import com.owd.core.business.user.UserFactory;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.generated.OwdUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 14, 2004
 * Time: 11:10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserHome {
private final static Logger log =  LogManager.getLogger();

    public static boolean getSessionFlag(HttpServletRequest req, String key) {
        String val = "";

        try {
            val = (String) req.getSession(true).getAttribute(key);
            if (val.equals("Y"))
                return true;
            else
                return false;
        } catch (Exception ex) {

            return false;
        }

    }


    static public String getClientSelector(String id, String zeroName, String zeroValue) {

        String currClient = id;


        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer selector = new StringBuffer();
        boolean selected = false;
        try {


            conn = com.owd.core.managers.ConnectionManager.getConnection();

            stmt = conn.createStatement();

            String sqlQuery = "select client_id, company_name " + "from owd_client (NOLOCK) where is_active = 1 or client_id="+id+" order by company_name";

            rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                if (!(rs.getString(1).equals(""))) {
                    selector.append("<OPTION VALUE=\"" + rs.getString(1) + "\"");

                    if (currClient != null && (rs.getString(1).equals(currClient))) {
                        selected = true;
                        selector.append(" SELECTED");
                    }

                    selector.append(">" + rs.getString(2));
                }
            }

        } catch (Exception e) {
            selector.append("<OPTION>SQLException");
        } finally {

            try {
                rs.close();
            } catch (Exception e) {
            }
            try {

                stmt.close();
            } catch (Exception e) {
            }
            try {

                conn.close();
            } catch (Exception e) {
            }


        }
        if (!selected && zeroValue != null && zeroName != null) {
            return "<OPTION VALUE=\"" + zeroValue + "\" SELECTED>" + zeroName + selector.toString();
        } else if (selected && zeroValue != null && zeroName != null) {
            return "<OPTION VALUE=\"" + zeroValue + "\">" + zeroName + selector.toString();
        } else {
            return selector.toString();
        }

    }


    public static final String kParamAdminAction = "act";


    public static void setSessionFlag(HttpServletRequest req, String key, boolean val) {
        try {
            if (val)
                req.getSession(true).setAttribute(key, "Y");
            else
                req.getSession(true).setAttribute(key, "N");
        } catch (Exception ex) {
        }
    }

    public static String getSessionString(HttpServletRequest req, String key) {
        String val = "";

        try {
            val = (String) req.getSession(true).getAttribute(key);
            if (val == null)
                val = "";
        } catch (Exception ex) {
        }

        return val.trim();
    }

    public static void setSessionString(HttpServletRequest req, String key, String val) {
        try {
            req.getSession(true).setAttribute(key, val);
        } catch (Exception ex) {
        }
    }

    public static String getStringParam(HttpServletRequest req, String paramName, String defaultValue) {
        String param = req.getParameter(paramName);

        if (param == null)
            param = defaultValue;

        return param;

    }

    public static Vector getStringVector(HttpServletRequest req, String paramName, Vector defaultValue) {
        String[] param_ay = req.getParameterValues(paramName);
        Vector param = new Vector();

        if (param_ay == null)
            param = defaultValue;
        else {
            if (param_ay.length > 0) {
                for (int i = 0; i < param_ay.length; i++) {
                    param.addElement(param_ay[i]);
                }
            }
        }

        return param;

    }

    public static int getIntegerParam(HttpServletRequest req, String paramName, int defaultValue) {
        int param = 0;

        try {
            param = new Integer(req.getParameter(paramName)).intValue();
        } catch (Exception ex) {
            param = defaultValue;
        }

        return param;

    }

    public static final String kUserSearchStatus = "user.search.status";
    public static final String kUserSearchType = "user.search.type";
    public static final String kUserSearchString = "user.search.string";
    public static final String kUserSearchCompare = "user.search.compare";


    public static final void setSearchSessionAttributes(HttpServletRequest req) {
        HttpSession wsess = req.getSession(true);
        if (null == wsess.getAttribute(kUserSearchStatus)) {
            wsess.setAttribute(kUserSearchStatus, "-1");
        }
        if (null == wsess.getAttribute(kUserSearchType)) {
            wsess.setAttribute(kUserSearchType, "");
        }
        if (null == wsess.getAttribute(kUserSearchString)) {
            wsess.setAttribute(kUserSearchString, "");
        }
        if (null == wsess.getAttribute(kUserSearchCompare)) {
            wsess.setAttribute(kUserSearchCompare, "0");
        }


    }

    public static final String kExtranetCurrMgr = "extranet.currmgr";
    public static final String kExtranetClientID = "extranet.clientid";
    public static final String kExtranetAdminFlag = "extranet.isAdmin";
    public static final String kExtranetInternalFlag = "extranet.isInternal";
    public static final String kExtranetAuthenticatedFlag = "extranet.auth";
    public static final String kExtranetCSRFlag = "extranet.callcenter";

    public static void authenticateUser(HttpServletRequest req) {

        boolean needsAuth = (!(getSessionFlag(req, kExtranetAuthenticatedFlag))) ;
        try
        {
            int test = Integer.decode(UserHome.getSessionString(req, UserHome.kExtranetClientID)).intValue();

        }   catch(Exception ex)
        {
             needsAuth = true;
        }
        if (needsAuth) {
            setSessionFlag(req, kExtranetAuthenticatedFlag, false);
            //user = OWDUtilities.getCurrentUserName();
            String user = req.getUserPrincipal().getName();
            //log.debug("RemotePrincipal=" + req.getUserPrincipal());
            //log.debug("RemotePrincipal name=" + req.getUserPrincipal().getName());
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
              OwdUser currUser = UserFactory.getOwdUserFromLogin(user);
                if (currUser != null) {
                    setSessionFlag(req, kExtranetCSRFlag, UserManager.listAllGroupsForUser(currUser).contains("callcenter"));
                    setSessionFlag(req, kExtranetInternalFlag, UserManager.listAllGroupsForUser(currUser).contains("internal"));
                } else {
                    setSessionFlag(req, kExtranetCSRFlag, false);
                    setSessionFlag(req, kExtranetInternalFlag, false);
                }

                conn = ConnectionManager.getConnection();

                stmt = conn.createStatement();

                String sqlQuery = "select client_fkey, is_admin "
                        + "from owd_users (NOLOCK) where login = \'" + user + "\' and (client_fkey = 0 OR client_fkey in (select client_id from owd_client))";
                //log.debug(sqlQuery);
                rs = stmt.executeQuery(sqlQuery);

                if (rs.next()) {
                    //log.debug("found user");
                    setSessionFlag(req, kExtranetAdminFlag, (rs.getInt(2) == 1));
                    setSessionString(req, kExtranetClientID, rs.getString(1));
                    setSessionString(req, kExtranetCurrMgr, "Home");
                    setSessionFlag(req, kExtranetAuthenticatedFlag, true);
                }

            } catch (Exception e) {

                e.printStackTrace();
                setSessionFlag(req, kExtranetAuthenticatedFlag, false);
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
}
