<%@ page import="java.sql.ResultSet,
                 com.owd.hibernate.HibernateSession,
                 java.util.List,
                 java.util.ArrayList,
                 java.net.URLEncoder,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 java.util.Iterator,
                 com.owd.web.internal.servlet.HomeServlet" %>
<%

    try {


        System.out.println(">>>>>>>>>>>>>>>>>>>>got cid=" + request.getParameter("q"));

        HomeServlet.setSessionString(request, HomeServlet.kExtranetClientID, request.getParameter("q"));

    } catch (Exception e) {
        e.printStackTrace();
        throw e;
    } finally {
    }
%>