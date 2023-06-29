package com.owd.web.autoship;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.user.UserFactory;
import com.owd.core.business.user.UserManager;
import com.owd.core.managers.ConnectionManager;

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
public class AutoShipHome {
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


    public static final String kParamChangeClientTo = "cid";
    public static final String kParamChangeClientAction = "cngCid";

    public static final String kParamAdminAction = "act";

    public static String getClientSelector(HttpServletRequest req) {

        StringBuffer sb = new StringBuffer();

        if (getSessionFlag(req, kExtranetInternalFlag)) {


            return "<INPUT TYPE=HIDDEN NAME=\"" + kParamAdminAction
                    + "\"  VALUE=\"" + kParamChangeClientAction
                    + "\"><SELECT NAME=\"" + kParamChangeClientTo + "\" onChange=\"this.form.submit()\">"
                    + getClientSelector(req, "changeClient", getSessionString(req, kExtranetClientID), "All Clients", "0")
                    + "</SELECT>";
        } else {
            Connection con = null;

            try {

                con = ConnectionManager.getConnection();

                Client client = Client.getClientForID(con, getSessionString(req, kExtranetClientID));
                sb.append("<B>" + client.company_name + "</B>");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (Exception ex) {
                }
            }

        }

        return sb.toString();


    }


    static public String getClientSelector(HttpServletRequest req, String name, String id) {

        return getClientSelector(req, name, id, null, null);
    }

    static public String getClientSelector(HttpServletRequest req, String name, String id, String zeroName, String zeroValue) {

        String currClient = id;


        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer selector = new StringBuffer();
        boolean selected = false;
        try {


            conn = ConnectionManager.getConnection();

            stmt = conn.createStatement();

            String sqlQuery = "select client_id, company_name " + "from owd_client (NOLOCK) where is_active = 1 order by company_name";

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

    public static final String kAutoSearchStatus = "autoship.search.status";
    public static final String kAutoSearchType = "autoship.search.type";
    public static final String kAutoSearchString = "autoship.search.string";
    public static final String kAutoSearchCompare = "autoship.search.compare";


    public static final void setSearchSessionAttributes(HttpServletRequest req) {
        HttpSession wsess = req.getSession(true);
        if (null == wsess.getAttribute(kAutoSearchStatus)) {
            wsess.setAttribute(kAutoSearchStatus, "-1");
        }
        if (null == wsess.getAttribute(kAutoSearchType)) {
            wsess.setAttribute(kAutoSearchType, "");
        }
        if (null == wsess.getAttribute(kAutoSearchString)) {
            wsess.setAttribute(kAutoSearchString, "");
        }
        if (null == wsess.getAttribute(kAutoSearchCompare)) {
            wsess.setAttribute(kAutoSearchCompare, "0");
        }


    }

    public static final String kExtranetCurrMgr = "extranet.currmgr";
    public static final String kExtranetClientID = "extranet.clientid";
    public static final String kExtranetAdminFlag = "extranet.isAdmin";
    public static final String kExtranetInternalFlag = "extranet.isInternal";
    public static final String kExtranetAuthenticatedFlag = "extranet.auth";
    public static final String kExtranetCSRFlag = "extranet.callcenter";
    public static final String kExtranetAuthenticatedUserName = "extranet.authlogin";

    public static void authenticateUser(HttpServletRequest req) {
        if (!getSessionFlag(req,kExtranetAuthenticatedFlag) || (!(req.getUserPrincipal().getName().equals(getSessionString(req,kExtranetAuthenticatedUserName))))) {
            setSessionFlag(req, kExtranetAuthenticatedFlag, false);
            //user = OWDUtilities.getCurrentUserName();
            String user = req.getUserPrincipal().getName();
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                setSessionFlag(req, kExtranetInternalFlag, UserManager.isInternal(UserFactory.getOwdUserFromLogin(user)));


                conn = ConnectionManager.getConnection();

                stmt = conn.createStatement();

                String sqlQuery = "select client_fkey, is_admin "
                        + "from owd_users (NOLOCK) where login = \'" + user + "\' and (client_fkey = 0 OR client_fkey in (select client_id from owd_client))";
                log.debug(sqlQuery);
                rs = stmt.executeQuery(sqlQuery);

                if (rs.next()) {
                    log.debug("found user");
                    setSessionFlag(req, kExtranetAdminFlag, (rs.getInt(2) == 1 && getSessionFlag(req, kExtranetInternalFlag)));
                    if(false==getSessionFlag(req, kExtranetInternalFlag) || "".equals(getSessionString(req, kExtranetClientID)))
                    {
                        setSessionString(req, kExtranetClientID, rs.getString(1));
                    }
                    setSessionFlag(req, kExtranetAuthenticatedFlag, true);
                setSessionString(req, kExtranetAuthenticatedUserName, user);
                }

            } catch (Exception e) {

                e.printStackTrace();
                setSessionFlag(req, kExtranetAuthenticatedFlag, false);
                setSessionString(req, kExtranetAuthenticatedUserName, "");
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
