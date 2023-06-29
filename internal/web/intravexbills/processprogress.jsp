<%@ page import="com.owd.core.CSVReader,
                 java.util.List,
                 java.util.Iterator,
                 com.owd.web.internal.navigation.*,
                 com.owd.core.xml.OrderXMLDoc" %>
<%
    if (request.getSession(true).getAttribute("inprogress") == null) {
        response.sendRedirect("/internal/intravexbills/index.jsp");
    } else {
%>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kUPSAreaName, "Intravex Billing Invoice Upload In Progress Page", request);

%>
<html>
<head><meta http-equiv="refresh" content="2">

    <title><%= request.getAttribute("lf_CURRMANAGER") %></title>
    <jsp:include page="/includes/owdtop.jsp"/>

<P><B>Your file is being processed. This page will reload every 2 seconds until your import is complete. Please do not
    use your browser while this page is displayed or you may not be able to view the results of your import.</B>

<P><%= request.getSession(true).getAttribute("processed") %>
    of <%= request.getSession(true).getAttribute("toprocess") %> invoice items processed so far!

<P>
    <%= request.getSession(true).getAttribute("updateMessage") %>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY>
</HTML>

<% } %>