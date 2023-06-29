<%
	 if(request.getSession(true).getAttribute("inprogress") == null)
	 {
	 %><jsp:forward page="orderuploads.jsp" /><%
	 }else{
 %>
<HTML>
<meta http-equiv="refresh" content="2">
<link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
<TITLE>OWD Order Batch Upload In Progress Page</TITLE>
<BODY>
<P><B>Your file is being processed. This page will reload every 2 seconds until your import is complete. Please do not use your browser while this page is displayed or you may not be able to view the results of your import.</B>
<P><%= request.getSession(true).getAttribute("processed") %> of <%= request.getSession(true).getAttribute("toprocess") %> orders processed so far!
</BODY></HTML>
 <% } %>