package com.owd.dc;

import com.owd.hibernate.HibernateTimeForceSession;
import com.owd.dc.setup.buttons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 10:07:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class checkAuthentication {
    protected final static Logger log = LogManager.getLogger();
    public static void check(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        
        String authName = "";
        req.setAttribute("loginName", null);


        String remoteReq = req.getHeader("x-forwarded-for");
        if (remoteReq == null) remoteReq = "";
        log.debug("remoteaddr:"+req.getRemoteAddr());
        // log.debug("remoteReq:"+remoteReq);
        //OK if local, no proxy, or proxying from local...
        if (1==1) {
             log.debug("lalalla");
            //todo check on use of cookies - may be persisting unwanted data across sessions
            Cookie[] cookies = req.getCookies();
            log.debug(cookies.length);
            for (int i = 0; i < cookies.length; i++) {
                log.debug(cookies[i].getName());
                if ("tcname".equals(cookies[i].getName())) {
                     log.debug("intcname");
                    authName = cookies[i].getValue();
                    authName = authName.replace('\"', ' ').trim();

                    if (authName == null) authName = "";
                    if (authName.equals("null")) authName = "";
                    if (authName.trim().length() < 1) authName = "";
                    log.debug("Done System Name");
                }
                if ("remfkey".equals(cookies[i].getName())){
                    String fkey = cookies[i].getValue();
                    fkey = fkey.trim();
                    if (fkey == null) fkey = "";
                    if (fkey.equals("null")) fkey = "";
                    if (!fkey.equals("")){
                    log.debug("Setting remember") ;
                    req.setAttribute("remember",fkey);
                    }
                }
                if ("remname".equals(cookies[i].getName())) {
                    String name = cookies[i].getValue();
                    name = name.replace('\"', ' ').trim();
                    if (name == null) name = "";
                    if (name.equals("null")) name = "";
                    if(!name.equals("")){

                    req.setAttribute("rememberclientname",name);
                    }
                }
                 if ("returncid".equals(cookies[i].getName())) {
                    String name = cookies[i].getValue();
                    name = name.replace('\"', ' ').trim();
                    if (name == null) name = "0";
                    if (name.equals("null")) name = "0";
                    req.setAttribute("returncid",name);

                }
               /* if("hhMetaButtons".equals((cookies[i].getName()))){
                    
                  String value = cookies[i].getValue();
                  log.debug("yadayasdsa");
                    log.debug(cookies[i].getValue());
                   // value = value.replace('\"', ' ').trim();
                    log.debug(value);
                    String[] btn = value.split("&");
                    buttons info = new buttons();
                    info.setT5(btn[0]);
                    info.setT6(btn[1]);
                    info.setT7(btn[2]);
                    info.setT8(btn[3]);
                    info.setRed(btn[4]);
                    info.setGreen(btn[5]);

                   req.setAttribute("hhMetaButtonsAction",info);
                }*/
            }
            if (authName.equals("")) {
 try {

                    String tcID = req.getAttribute("tc_id").toString();
     log.debug(tcID)
     ;
                    if (tcID == null) throw new Exception("Login ID was not supplied!");

                    try {
                        int testid = Integer.decode(tcID).intValue();
                    } catch (Exception edd) {
                        throw new Exception("Login must be a number!");
                    }
                     String name = loginAround.checkLogin(tcID,req);

                    if (name.length() > 0) {

                        req.setAttribute("loginName", name);

                        // Cookie cookie = new Cookie("tcid", tcID);
                        //  cookie.setDomain(".owd.com");

                        Cookie cookie2 = new Cookie("tcname", name);
                        cookie2.setPath("/wms");
                        //  resp.addCookie(cookie);
                        resp.addCookie(cookie2);
                         Cookie cookie3 = new Cookie("tcid",req.getAttribute("tc_id").toString());
                          cookie3.setPath("/wms");
                        resp.addCookie(cookie3);
                    } else {
                        throw new Exception("Login ID " + tcID + " not recognized");
                    }
                } catch (Exception ex) {
                    throw new Exception("Unable to authorize user: " + ex.getMessage());
                } finally {
                   // HibernateTimeForceSession.closeSession();
                }
               /* try {
                    Session sess = HibernateTimeForceSession.currentSession();

                    String tcID = req.getAttribute("tc_id").toString();
                    if (tcID == null) throw new Exception("Login ID was not supplied!");

                    try {
                        int testid = Integer.decode(tcID).intValue();
                    } catch (Exception edd) {
                        throw new Exception("Login must be a number!");
                    }
                    ResultSet rs = HibernateTimeForceSession.getResultSet(sess, "select firstname,lastname from empMain  (NOLOCK) where cardnumber=" + tcID);
                    if (rs.next()) {
                        authName = rs.getString(1) + " " + rs.getString(2);
                        req.setAttribute("loginName", authName);

                        // Cookie cookie = new Cookie("tcid", tcID);
                        //  cookie.setDomain(".owd.com");

                        Cookie cookie2 = new Cookie("tcname", authName);
                        //  resp.addCookie(cookie);
                        resp.addCookie(cookie2);
                         Cookie cookie3 = new Cookie("tcid",req.getAttribute("tc_id").toString());
                        resp.addCookie(cookie3);
                    } else {
                        throw new Exception("Login ID " + tcID + " not recognized");
                    }
                } catch (Exception ex) {
                    throw new Exception("Unable to authorize user: " + ex.getMessage());
                } finally {
                   // HibernateTimeForceSession.closeSession();
                }*/
            } else {
                req.setAttribute("loginName", authName);
            }

            if (null == req.getAttribute("loginName")) {
                throw new Exception("No login name found!");
            }
        } else {
            throw new Exception("External access not permitted!");
        }

        if (req.getAttribute("loginName") != null) {
            /*
            log.debug("cking for old order status:" + req.getAttribute("loginName"));
            String user = (String) req.getAttribute("loginName");
            Session sess = HibernateSession.currentSession();

            Criteria crit = sess.createCriteria(OrderPickStatus.class);
            crit.setMaxResults(1000);
            crit.add(Expression.eq("pickerName", user));

            List orderList = crit.list();

            log.debug("got " + orderList.size() + " orders");

            if (orderList.size() > 0) {
                for (int i = (orderList.size() - 1); i > 0; i--) {

                    log.debug("deleting old order status:" + req.getAttribute("loginName"));
                    PickUtilities.clearCurrentPickStatus((OrderPickStatus) orderList.get(i), req);
                }
                sess.flush();
                com.owd.hibernate.HibUtils.commit(sess);
                OrderPickStatus pickstatus = (OrderPickStatus) orderList.get(0);
                //todo check on storing only pickorderstatus id instead of full object
               // sess.refresh(pickstatus);
                req.setAttribute("oldpickorder", (OrderPickStatus) pickstatus);
                //req.setAttribute("oldpickorder", (OrderPickStatus) orderList.get(0));

                log.debug("got old order status:" + req.getAttribute("loginName"));

               // log.debug(pickstatus.getCurrentItemIndex());

            } else {
               // req.setAttribute("oldpickorder", null);
                log.debug("no  old order status:" + req.getAttribute("loginName"));

            }
            */


        }
    }
}
