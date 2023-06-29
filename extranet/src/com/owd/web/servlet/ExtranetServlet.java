package com.owd.web.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.user.UserFactory;
import com.owd.core.business.user.UserManager;
import com.owd.core.managers.ConnectionManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class ExtranetServlet extends HttpServlet {
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

    public static final String kBulkLoadSaveDir = "webapps/webapps/WEB-INF/uploads/";

    public static final String kExtranetJSPPath = "/WEB-INF/jsp/extranet/";
    public static final String kExtranetURLPath = "/webapps/webapps";
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
        log.debug("in extranetServlet");
        try {

            if (req.getRemoteUser() == null) {
                forceUserLogout(req, resp);
                return;
            }
            if ("logout".equals(getStringParam(req, "logout", ""))) {
                forceUserLogout(req, resp);
            } else {
                authenticateUser(req);
                if (getSessionFlag(req, kExtranetAuthenticatedFlag)) {
                    if (kParamChangeClientAction.equals(getStringParam(req, kParamAdminAction, ""))) {
                        if (getSessionFlag(req, kExtranetInternalFlag) || getSessionFlag(req, kExtranetCSRFlag)) {
                            setSessionString(req, kExtranetClientID, getStringParam(req, kParamChangeClientTo, ""));

                        }
                    }
                    if (kParamChangeMgrAction.equals(getStringParam(req, kParamAdminAction, ""))) {
                        if (getSessionFlag(req, kExtranetInternalFlag)) {
                            setSessionString(req, kExtranetCurrMgr, getStringParam(req, kParamChangeMgrTo, ""));
                        }
                    }
                    if (kParamDownloadAction.equals(getStringParam(req, InventoryManager.kParamInvMgrAction, ""))) {

                        new InventoryManager().download(req, resp);

                    } else {

                        (req.getRequestDispatcher(kExtranetJSPPath + "extranettop.jsp")).include(req, resp);
                        getManager(req, resp).getContent(req, resp);
                        (req.getRequestDispatcher(kExtranetJSPPath + "extranetbottom.jsp")).include(req, resp);

                    }
                } else {
                    //not authenticatable
                    req.getRequestDispatcher("/error.jsp").forward(req, resp);
                }
            }

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

       public static final String kExtranetAuthenticatedUserName = "extranet.authlogin";

    public static void authenticateUser(HttpServletRequest req) {

        if (!getSessionFlag(req,kExtranetAuthenticatedFlag) || (!(req.getUserPrincipal().getName().equals(getSessionString(req,kExtranetAuthenticatedUserName))))) {
            setSessionFlag(req, kExtranetAuthenticatedFlag, false);
                   setSessionFlag(req,kExtranetAdminFlag,false);

        //	setSessionFlag(req,kExtranetInternalFlag,rs.getString(1).equals("0")?true:false);

            setSessionString(req,kExtranetClientID,"");

            setSessionString(req,kExtranetCurrMgr,"Home");

            setSessionFlag(req,kExtranetAuthenticatedFlag,false);
            setSessionFlag(req,kExtranetCSRFlag,false);
            setSessionFlag(req,kExtranetInternalFlag,false);
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
    protected void doAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ServletContext sc = getServletConfig().getServletContext();
        (sc.getRequestDispatcher(kExtranetJSPPath + "extranettop.jsp")).include(req, resp);
        getManager(req, resp).getContent(req, resp);
        (sc.getRequestDispatcher(kExtranetJSPPath + "extranetbottom.jsp")).include(req, resp);

    }

    public static ExtranetManager getManager(HttpServletRequest req, HttpServletResponse resp) {
        if (ExtranetManager.kInvMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("invmgr");
            return new InventoryManager();

        } else if (ExtranetManager.kOrdersMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("ordersmgr");
            return new OrdersManager();

        } else if (ExtranetManager.kServicesMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("ordersmgr");
            return new ServiceManager();

        } else if (ExtranetManager.kCouponsMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("ordersmgr");
            return new CouponManager();

        } else if (ExtranetManager.kReportsMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("reportsmgr");
            return new ReportsManager();

        } else if (ExtranetManager.kSuppliersMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("supppliersmgr");
            return new SupplierManager();

        } else if (ExtranetManager.kAutoShipMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("autoshipmgr");
            return new AutoShipManager();

        } else if (ExtranetManager.kReceiveMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("rcvmgr");
            return new ReceivingManager();
        } else if (ExtranetManager.kSchedulesMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("usrmgr");
            if (ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetAdminFlag) && ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetInternalFlag)) {
                return new ExtranetManager();//SchedulesManager();
            } else {
                ExtranetServlet.setSessionString(req, ExtranetServlet.kExtranetCurrMgr, ExtranetManager.kManagers[0]);
                return getManager(req, resp);
            }
        } else if (ExtranetManager.kClientsMgrName.equals(getSessionString(req, ExtranetServlet.kExtranetCurrMgr))) {
            log.debug("clientmgr");

            if (ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetAdminFlag) && ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetInternalFlag)) {
                return new ClientManager();//ClientManager();
            } else {
                ExtranetServlet.setSessionString(req, ExtranetServlet.kExtranetCurrMgr, ExtranetManager.kManagers[0]);
                return getManager(req, resp);
            }

        } else {
            log.debug("unrecognized action - go home");
            ExtranetServlet.setSessionString(req, ExtranetServlet.kExtranetCurrMgr, ExtranetManager.kManagers[0]);
            return getManager(req, resp);
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


            return "<INPUT TYPE=HIDDEN NAME=\"" + com.owd.web.servlet.ExtranetServlet.kParamAdminAction
                    + "\"  VALUE=\"" + com.owd.web.servlet.ExtranetServlet.kParamChangeClientAction
                    + "\"><SELECT NAME=\"" + com.owd.web.servlet.ExtranetServlet.kParamChangeClientTo + "\" onChange=\"this.form.submit()\">"
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
