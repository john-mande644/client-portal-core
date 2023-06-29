package com.owd.web.internal.client;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.user.UserManager;
import com.owd.core.business.user.UserFactory;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.generated.OwdUser;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdOrder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 14, 2004
 * Time: 11:10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientHome {
private final static Logger log =  LogManager.getLogger();


      public static void main (String[] args)
    {

        try
        {
                Session sess = HibernateSession.currentSession();

    //if internal user and all clients id is current, return all - else return just for active client id

    //  List autoshipl = sess.find("from OwdOrderAuto "+((ClientHome.getSessionFlag(request,ClientHome.kExtranetInternalFlag) && ClientHome.getSessionString(request,ClientHome.kExtranetClientID).equals("0"))?"":(" where client_fkey="+ClientHome.getSessionString(request,ClientHome.kExtranetClientID))));
    //   //log.debug("found autoship list of:"+autoshipl.size());

    Criteria crit = sess.createCriteria(OwdClient.class);
    crit.setResultTransformer(Criteria.ROOT_ENTITY);
  //   crit.setFetchMode("client", FetchMode.EAGER);
    //   crit.add(Expression.eq("status", request.getParameter("asnstatus")));

    OwdClient client = (OwdClient) sess.load(OwdClient.class,new Integer(117));
            //log.debug(client.getCompanyName());
            OwdOrder order = (OwdOrder) sess.load(OwdOrder.class,new Integer(2342174))
                    ;

            //log.debug(""+order.getLineitems().size());


    int statusSrch = new Integer((String) "-1").intValue();
    int compareSrch = new Integer((String) "1").intValue();
    String stringSrch = ((String) "");
    String typeSrch = (String) "";

    if (!(statusSrch == -1)) {
        crit.add(Restrictions.eq("isActive", new Boolean(statusSrch == 1)));
    }

    if (stringSrch.trim().length() > 0 && typeSrch.trim().length() > 1) {
        if (compareSrch == 0)//is
        {

            crit.add(Restrictions.eq(typeSrch, stringSrch));


        } else {  //contains

            crit.add(Restrictions.like(typeSrch, "%" + stringSrch + "%"));

        }
    }

  
    List clientlist = null;

    //log.debug("trying list");
    clientlist = crit.list();

    //log.debug("found client list of:" + clientlist.size());

        }catch(Exception ex)
        {
              ex.printStackTrace();
        }   finally
        {
            HibernateSession.closeSession();
        }
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

    public static final String kClientSearchStatus = "client.search.status";
    public static final String kClientSearchType = "client.search.type";
    public static final String kClientSearchString = "client.search.string";
    public static final String kClientSearchCompare = "client.search.compare";


    public static final void setSearchSessionAttributes(HttpServletRequest req) {
        HttpSession wsess = req.getSession(true);
        if (null == wsess.getAttribute(kClientSearchStatus)) {
            wsess.setAttribute(kClientSearchStatus, "-1");
        }
        if (null == wsess.getAttribute(kClientSearchType)) {
            wsess.setAttribute(kClientSearchType, "");
        }
        if (null == wsess.getAttribute(kClientSearchString)) {
            wsess.setAttribute(kClientSearchString, "");
        }
        if (null == wsess.getAttribute(kClientSearchCompare)) {
            wsess.setAttribute(kClientSearchCompare, "0");
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
            int test = Integer.decode(getSessionString(req, kExtranetClientID)).intValue();

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
