package com.owd.dc.actions;

import com.owd.WMSUtils;
import com.owd.dc.loginAround;
import com.owd.hibernate.HibernateTimeForceSession;
import com.owd.dc.loginDTO;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.loginForm;
import com.owd.dc.setup.buttons;
import org.hibernate.Session;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 11, 2005
 * Time: 3:10:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class loginAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        try {
            loginForm loginForm = (loginForm) form;
            loginDTO loginDTO = new loginDTO();
            BeanUtils.copyProperties(loginDTO, loginForm);
             String username = loginDTO.getusername();
            if (username.startsWith("0")) {
               username = username.replaceFirst("0","");
            }
            request.setAttribute("tc_id", username);
            System.out.println("Username" + username);
            String authName = "";
        request.setAttribute("loginName", null);


        String remoteReq = request.getHeader("x-forwarded-for");
        if (remoteReq == null) remoteReq = "";
        System.out.println("remoteaddr:"+request.getRemoteAddr());
         System.out.println("remoteReq:"+remoteReq);
        //OK if local, no proxy, or proxying from local...
        if (1==1) {




                try {
                  //  Session sess = HibernateTimeForceSession.currentSession();
                System.out.println("1");
                    String tcID = request.getAttribute("tc_id").toString();
                    if (tcID == null) throw new Exception("Login ID was not supplied!");

                    try {
                        int testid = Integer.decode(tcID).intValue();
                    } catch (Exception edd) {
                        throw new Exception("Login must be a number!");
                    }
                  //  ResultSet rs = HibernateTimeForceSession.getResultSet(sess, "select firstname,lastname from empMain (NOLOCK) where cardnumber=" + tcID);
                    String name = loginAround.checkLogin(tcID,request);

                    if (name.length()>0) {
                        String facility = WMSUtils.getFacilityFromLoginInDatbase(tcID,request);
                        request.getSession(true).setAttribute("owd_current_location",facility);
                        Cookie facCook = new Cookie("wmsFacility",facility);
                        facCook.setPath("/wms");
                        response.addCookie(facCook);
                        request.setAttribute("loginName", name);
                        System.out.println("2");
                        // Cookie cookie = new Cookie("tcid", tcID);
                        //  cookie.setDomain(".owd.com");

                        Cookie cookie2 = new Cookie("tcname", name);
                         cookie2.setPath("/wms");
                        //  resp.addCookie(cookie);
                        response.addCookie(cookie2);
                         Cookie cookie4 = new Cookie("tcid",request.getAttribute("tc_id").toString());
                         cookie4.setPath("/wms");
                        response.addCookie(cookie4);
                        System.out.println("AUTH:   "+authName);
                        System.out.println(name) ;
                     /*  buttons buttns = HandheldUtilities.getMetaButtons(tcID,name);
                       Cookie cookie3 = new Cookie("hhMetaButtons",buttns.getT5()+"&"+buttns.getT6()+"&"+buttns.getT7()+"&"
                               +buttns.getT8()+"&"+buttns.getRed()+"&"+buttns.getGreen());
                       response.addCookie(cookie3);*/

                    } else {
                        throw new Exception("Login ID " + tcID + " not recognized");
                    }
                } catch (Exception ex) {
                    throw new Exception("Unable to authorize user: " + ex.getMessage());
                } finally {
                   // HibernateTimeForceSession.closeSession();
                }


            if (null == request.getAttribute("loginName")) {
                throw new Exception("No login name found!");
            }
        } else {
            throw new Exception("External access not permitted!");
        }

           // checkAuthentication.check(request, response);

            return (mapping.findForward("success"));


        } catch (Exception ex){
            request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("login"));
        }
        finally {


          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
        }
    }

}
