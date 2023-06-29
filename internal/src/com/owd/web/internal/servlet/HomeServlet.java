package com.owd.web.internal.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.user.UserManager;
import com.owd.core.business.user.UserFactory;
import com.owd.hibernate.generated.OwdUser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class HomeServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();

    public static final String kExtranetCurrMgr = "extranet.currmgr";
    public static final String kExtranetClientID = "extranet.clientid";
    public static final String kExtranetAdminFlag = "extranet.isAdmin";
    public static final String kExtranetInternalFlag = "extranet.isInternal";
    public static final String kExtranetAuthenticatedFlag = "extranet.auth";
    public static final String kExtranetCSRFlag = "extranet.callcenter";

    public static final String kParamAdminAction = "act";

    public static final String kParamChangeClientTo = "cid";
    public static final String kParamChangeClientAction = "cngCid";

    public static final String kParamChangeMgrTo = "mgr";
    public static final String kParamChangeMgrAction = "cngMgr";

    public static final String kParamDownloadAction = "download";

    public static final String kBulkLoadSaveDir = "uploads/";

    public static final String kExtranetJSPPath = "/WEB-INF/jsp/extranet/";
    public static final String kExtranetURLPath = "/webapps/extranet";
    public static final String kExtranetServletPath = "/extranet";

    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);

    }

    public void destroy() {
        super.destroy();

    }

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

    //GET requests not supported g
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        doPost(req, resp);
    }

    //all requests should be POST
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        //log.debug("in extranetServlet");
        try {


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void forceUserLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSessionFlag(req, kExtranetAuthenticatedFlag, false);
        setSessionString(req, kExtranetClientID, null);
        req.getSession(true).invalidate();
        resp.sendRedirect(kExtranetURLPath);
    }

    public static void authenticateUser(HttpServletRequest req) {
         boolean needsAuth = (!(getSessionFlag(req, kExtranetAuthenticatedFlag))) ;
        try
        {
            int test = Integer.decode(HomeServlet.getSessionString(req, HomeServlet.kExtranetClientID)).intValue();

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

                conn = com.owd.core.managers.ConnectionManager.getConnection();

                stmt = conn.createStatement();

                String sqlQuery = "select client_fkey, is_admin "
                        + "from owd_users (NOLOCK) where login = \'" + user + "\' and (client_fkey = 0 OR client_fkey in (select client_id from owd_client))";
                //log.debug(sqlQuery);
                rs = stmt.executeQuery(sqlQuery);

                if (rs.next()) {
                    //log.debug("found user");
                    setSessionFlag(req, kExtranetAdminFlag, (rs.getInt(2) == 1));
                    setSessionString(req, kExtranetClientID, rs.getString(1).equals("0") ? "55" : rs.getString(1));
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


    static final public String kServletName = "Extranet";

    public String getServletInfo() {
        return "One World Extranet v";
    }

    public static String getClientSelector(HttpServletRequest req) {

        StringBuffer sb = new StringBuffer();

        if (getSessionFlag(req, kExtranetInternalFlag)) {


            return "<INPUT TYPE=HIDDEN NAME=\"" + kParamAdminAction
                    + "\"  VALUE=\"" + kParamChangeClientAction
                    + "\"><SELECT NAME=\"" + kParamChangeClientTo + "\" onChange=\"this.form.submit()\">"
                    + getClientSelector(req, kParamChangeClientTo, getSessionString(req, kExtranetClientID), "All Clients", "0")
                    + "</SELECT>";
        } else {
            Connection con = null;

            try {

                con = com.owd.core.managers.ConnectionManager.getConnection();

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


            conn = com.owd.core.managers.ConnectionManager.getConnection();

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

}
