package com.owd.web;

import com.owd.hibernate.HibernateClientReportsSession;
import com.owd.hibernate.HibernateFogbugzSession;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 15, 2005
 * Time: 12:59:13 PM
 * To change this template use File | Settings | File Templates.
 */
public final class HibernateFilter implements Filter {
private final static Logger log =  LogManager.getLogger();


    private FilterConfig config = null;

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {


        config = null;
        // //log.debug("destroying intranet filter");
        HibernateSession.closeSession();
        HibernateFogbugzSession.closeSession();
        HibernateClientReportsSession.closeSession();
        //   HibernateBoxworksSession.closeSession();
        //     //log.debug("intranet filter session closed (destroy)");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            //   HibernateSession.currentSession();
            //       HibernateFogbugzSession.currentSession();
            // log.debug("Hibernate session in filter create");
            request.setCharacterEncoding("UTF-8");

            String remoteIp = request.getRemoteAddr();
            String forwardedIp = ((HttpServletRequest) request).getHeader("X-Forwarded-For");

            if (forwardedIp == null) {
                forwardedIp = "";
            }
            ((HttpServletRequest) request).getSession(true).setAttribute("owd_remote_ip", remoteIp);
            ((HttpServletRequest) request).getSession(true).setAttribute("owd_forwarded_ip", forwardedIp);
            String testIp = "";
            if ("".equals(forwardedIp)) {
                testIp = remoteIp;
            } else {
                testIp = forwardedIp;
            }
            if (testIp.contains("192.168.100.") || testIp.contains("10.17.29.") || testIp.contains("10.16.28.") || testIp.contains("192.168.106.")) {
                ((HttpServletRequest) request).getSession(true).setAttribute("owd_default_location", "DC6");
            } else if (testIp.contains("192.168.102.") ) {
                ((HttpServletRequest) request).getSession(true).setAttribute("owd_default_location", "DC7");
            } else{
                ((HttpServletRequest) request).getSession(true).setAttribute("owd_default_location", "DC1");
            }
            log.debug("hibfilter session:"+((HttpServletRequest) request).getSession(true));
            log.debug("CURRENT DEFAULT LOCATION SET : "+((HttpServletRequest) request).getSession(true).getAttribute("owd_default_location"));


            log.debug("filter chain proceeding in intranet");
            chain.doFilter(request, response);
            // log.debug("filter chain done in intranet");
            // //log.debug("Close hibernate filter aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
         /*   try {
                HibUtils.rollback(HibernateSession.currentSession());
            } catch (Exception e) {
                e.printStackTrace();
                //  //log.debug("hibernate in filter failed");
            }
            try {
                HibUtils.rollback(HibernateClientReportsSession.currentSession());
            } catch (Exception e) {
                e.printStackTrace();
                //  //log.debug("hibernate in filter failed");
            }
            try {
                HibUtils.rollback(HibernateFogbugzSession.currentSession());
            } catch (Exception e) {
                e.printStackTrace();
                //  //log.debug("hibernate in filter failed");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            //  //log.debug("hibernate in filter failed");
        } finally {
            HibernateSession.closeSession();
            HibernateFogbugzSession.closeSession();
            HibernateClientReportsSession.closeSession();
            //   HibernateBoxworksSession.closeSession();
        }
        //     //log.debug("session closed in intranet");
    }
}
