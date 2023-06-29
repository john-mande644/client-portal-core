<%@ page import="org.apache.struts.action.Action"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String step = (String) request.getAttribute("step");
   String label = "header.label."+step;
   String action = "header.action."+step;
   String button = "header.button."+step;
  %>
  <% org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)request.getAttribute("org.apache.struts.action.MESSAGE"); %>
  <% String key = action; %>
  <% String message = (String)myMessages.getMessage(key); %>

<html:form action="recv-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><%= " " + request.getAttribute("loginName")%></html:form>
<% if (step.equals("selectSku")){
String rcvid= (String) request.getAttribute("rcvid"); %>
<html:form action="cancelRcv">
<html:hidden property="asnid" value="<%=rcvid%>"/>
<html:submit><bean:message key="button.cancelrcv"/></html:submit>
</html:form>
<%}%>
<bean:message key="<%=label%>"/> <br>
<html:form action="<%=message%>" focus="sku">
<html:text property="sku" value=""/>  <br>
<html:submit><bean:message key="<%=button%>"/></html:submit></html:form>

