package com.owd.core.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.owd.core.AuthenticationException;
import com.owd.core.web.ClientSecurityContext;
import com.owd.core.web.ClientSecurityContextAware;
import com.owd.core.web.OWDWebAuthenticationService;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 23, 2008
 * Time: 12:20:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class OWDWebAuthenticationInterceptor extends AbstractInterceptor {
private final static Logger log =  LogManager.getLogger();


    public String intercept(ActionInvocation actionInvocation) throws Exception {

        HttpServletRequest request = (HttpServletRequest) actionInvocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
        log.debug("in csc interceptor");

        try {

            String userLogin = "";
            try {
                userLogin = request.getUserPrincipal().getName();

                if (userLogin == null) {
                    throw new AuthenticationException("No user credentials found");
                }

                if ("logout".equals(request.getParameter("logout"))) {
                    throw new AuthenticationException("Logout request received");
                }


            } catch (NullPointerException exn) {

                exn.printStackTrace();

            }
         //   log.debug("Map before authentication:"+actionInvocation.getInvocationContext().getSession());
            OWDWebAuthenticationService.authenticateUser(actionInvocation.getInvocationContext().getSession(), userLogin);
         //   log.debug("Map after authentication:"+actionInvocation.getInvocationContext().getSession());
            Object action = actionInvocation.getAction();
            if (action instanceof ClientSecurityContextAware) {
                ClientSecurityContext csc = new ClientSecurityContext();

                if ("0".equals(OWDWebAuthenticationService.getSessionString(actionInvocation.getInvocationContext().getSession(), OWDWebAuthenticationService.kExtranetClientID))) {
                    //is an internal user who has not chosen a client to masquerade as. Set the client articifically to "55", which is the OWD internal client.
                    OWDWebAuthenticationService.setSessionString(actionInvocation.getInvocationContext().getSession(), OWDWebAuthenticationService.kExtranetClientID, "55");
                }
                csc.setCurrentClient(
                        (OwdClient) HibernateSession.currentSession().load(
                                OwdClient.class,
                                new Integer(OWDWebAuthenticationService.getSessionString(
                                        actionInvocation.getInvocationContext().getSession(),
                                        OWDWebAuthenticationService.kExtranetClientID))));


                if (OWDWebAuthenticationService.getSessionFlag(actionInvocation.getInvocationContext().getSession(), OWDWebAuthenticationService.kExtranetInternalFlag)) {
                    csc.setInternalUser(true);
                }

                ((ClientSecurityContextAware) action).setClientSecurityContext(csc);
            }

        } catch (AuthenticationException ex) {
          //  log.debug("OWD Auth Exception: " + ex.getMessage());
            OWDWebAuthenticationService.clearSessionAuthVariables(actionInvocation.getInvocationContext().getSession());
            request.getSession(true).invalidate();
            try {
                actionInvocation.getInvocationContext().getSession().clear();
                Cookie jossoCookie = new Cookie("JOSSO_SESSIONID", null);
                jossoCookie.setMaxAge(0);
                jossoCookie.setPath("/");
                jossoCookie.setDomain("owd.com");

                ((HttpServletResponse) actionInvocation.getInvocationContext().getContextMap().get(ServletActionContext.HTTP_RESPONSE)).addCookie(jossoCookie);
                ex.printStackTrace();
                actionInvocation.getInvocationContext().getSession().put("owd.login.last.error", ex.getMessage());
            } catch (Exception exx) {
            }
            return "owd.global.login";

        }

        return actionInvocation.invoke();

    }
}

