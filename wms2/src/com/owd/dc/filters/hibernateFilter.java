package com.owd.dc.filters;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Mar 3, 2009
 * Time: 4:00:03 PM
 * To change this template use File | Settings | File Templates.
 */
public final class hibernateFilter implements Filter {


    private FilterConfig config = null;


    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {


        config = null;
        HibernateSession.closeSession();
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            HibernateSession.currentSession();
            //   HibernateTimeForceSession.currentSession();
            System.out.println("HIbernate session filter for WMs2");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("hibernate in filter failed");
        }
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
        if (testIp.contains("192.168.100.") || testIp.contains("10.17.29")) {
            ((HttpServletRequest) request).getSession(true).setAttribute("owd_default_location", "DC5");
        } else {
            ((HttpServletRequest) request).getSession(true).setAttribute("owd_default_location", "DC1");
        }
        chain.doFilter(request, response);
        // System.out.println("Close hibernate filter aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        try {
            System.out.println("Rolling back all saved not commited lost");
            if (HibernateSession.currentSession().isDirty()) {
                System.out.println("Dirtyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");

            }
            HibUtils.rollback(HibernateSession.currentSession());
        } catch (Exception exx) {
            exx.printStackTrace();
            System.out.println("lllllllllllaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }
        try {       //HibUtils.rollback(HibernateTimeForceSession.currentSession());
        } catch (Exception exx) {
            exx.printStackTrace();
            System.out.println(":Llllllllllllllllllllllllllllllssssssss");
        }
        try {
            HibernateSession.closeSession();
            //  HibernateTimeForceSession.closeSession();

        } catch (Exception exx) {
            exx.printStackTrace();
            System.out.println("lululuuluulluuluulululllllllllul");
        }
    }
}