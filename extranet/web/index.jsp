
<%@page contentType="text/html; charset=iso-8859-1" language="java" session="true" %>
<!--
Redirects the user to the propper login page.  Configured as the login url the web.xml for this application.
-->
<%
    response.sendRedirect(request.getContextPath() + "/clienttools/");
%>