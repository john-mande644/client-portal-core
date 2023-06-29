<%@ page import="java.util.Enumeration" %>
<%--
  Created by IntelliJ IDEA.
  User: Stewart Buskirk
  Date: Sep 29, 2009
  Time: 10:08:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Simple jsp page</title></head>
  <body>
  <%
      Enumeration names = request.getSession(true).getAttributeNames();
      while (names.hasMoreElements())
      {
          Object currName = names.nextElement();
          %><%= currName %> : <%= request.getSession(true).getAttribute(currName+"")%><BR><%
      }
  %>
  </P></body>
</html>