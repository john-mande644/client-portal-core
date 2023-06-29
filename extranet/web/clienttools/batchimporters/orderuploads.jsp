<%@ page import="com.owd.core.business.Client,
				 com.owd.core.xml.OrderXMLDoc,
				 com.owd.web.servlet.ExtranetServlet,
				 java.util.Iterator,
				 java.util.List"%>

<%

	String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";


    String internal_current_client_id = (String) request.getSession(true).getAttribute("internal_current_client_id");
	if(internal_current_client_id == null) internal_current_client_id = "0";
	if(internal_current_client_id.equals("")) internal_current_client_id = "0";


%>
<%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Batch Order Upload");
%>
<jsp:include page="/extranettop.jsp" />
<BODY>
<P><B>OWD Order Batch Import Instructions</B>
<P><font color=red><B><%= (request.getAttribute("errormessage")!=null?request.getAttribute("errormessage"):"") %></B></font>
<P>Please check that your document conforms to the rules described in the OWD Batch Import Format Description document
<A HREF="OWD%20Batch%20Order%20Importer.doc"><B>[MS Word format]</B></A> <A HREF="OWD%20Batch%20Order%20Importer.pdf"><B>[Adobe/Acrobat PDF format]</B></A>.<p>
<P>
<FORM METHOD="POST" ENCTYPE="multipart/form-data" ACTION="/webapps/clienttools/batchimporters/orderuploadservlet">
<% if (user_client_id.equals("0"))
{
    %>
     Choose a client:&nbsp;<SELECT NAME="client_id"><%= com.owd.web.servlet.ExtranetServlet.getClientSelector(request,"client_id",internal_current_client_id) %></SELECT>

    <% }else{

    Client client = Client.getClientForUser(request.getUserPrincipal().getName());
    %>

   For: <INPUT TYPE=HIDDEN VALUE="<%= user_client_id %>" NAME="client_id"><%= client.company_name %>

    <%
}   %>
<BR>
Choose an out-of-stock rule:&nbsp;<SELECT NAME="backorder_rule">
<OPTION VALUE="<%= OrderXMLDoc.kBackOrderAll %>" SELECTED>Backorder entire order
<OPTION VALUE="<%= OrderXMLDoc.kPartialShip %>">Ship in stock items now - backorder remainder
<OPTION VALUE="<%= OrderXMLDoc.kHoldBackOrder %>">Place the entire order on hold
<OPTION VALUE="<%= OrderXMLDoc.kRejectBackOrder %>">Reject entire order
</SELECT>
<P>

Click the Browse button to select the file from your local drive:<HR>

<INPUT TYPE="FILE" size="60" NAME="UploadFile"><BR><P><HR>

<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Load New Orders File"></FORM>
<HR>
<%
	List resultsList = (List) request.getSession(true).getAttribute("resultList");
	%>


<% if (resultsList != null)
{
	Iterator it = resultsList.iterator();
	%>Import Results<HR><TABLE><TR><TH>Order Reference</TH><TH>Results</TH></TR><%
	while(it.hasNext())
	{
		List result = (List) it.next();
		%><TR><TD><%= result.get(0) %></TD><TD><%= result.get(1) %></TR><%

	}
	%></TABLE><%
}
    request.getSession(true).setAttribute("resultList",null);
	%>

<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

</BODY>
</HTML>

