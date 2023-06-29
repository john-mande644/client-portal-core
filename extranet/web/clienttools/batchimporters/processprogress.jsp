<%@ page import="com.owd.web.servlet.ExtranetServlet"%>
 <%
	 if(request.getSession(true).getAttribute("inprogress") == null)
	 {
	 %><jsp:forward page="orderuploads.jsp" /><%
	 }else{
 %>
<%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Batch Order Upload");
%>
<jsp:include page="/extranettop.jsp" />
<meta http-equiv="refresh" content="2">
<BODY>
<P><B>Your file is being processed. This page will reload every 2 seconds until your import is complete. Please do not use your browser while this page is displayed or you may not be able to view the results of your import.</B>
<P><%= request.getSession(true).getAttribute("processed") %> of <%= request.getSession(true).getAttribute("toprocess") %> orders processed so far!
    <!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

</BODY>
</HTML>
 <% } %>