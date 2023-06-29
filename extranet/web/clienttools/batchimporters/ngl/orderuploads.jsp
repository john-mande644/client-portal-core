<%@ page import="com.owd.core.business.Client,
				 com.owd.core.xml.OrderXMLDoc,
				 java.util.Iterator,
				 java.util.List"%>

<%

	String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";
     System.out.println(user_client_id);

    String internal_current_client_id = (String) request.getSession(true).getAttribute("internal_current_client_id");
	if(internal_current_client_id == null) internal_current_client_id = "0";
	if(internal_current_client_id.equals("")) internal_current_client_id = "0";
    System.out.println(internal_current_client_id);

%>

<HTML>  <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
<TITLE>NGL Batch Order Upload Page</TITLE>
<BODY>
<P><B>NGL Order Batch Importer</B>
<P>
<FORM METHOD="POST" ENCTYPE="multipart/form-data" ACTION="/webapps/clienttools/batchimporters/nglorderuploadservlet">


    <INPUT TYPE=HIDDEN VALUE="362" NAME="client_id">


<BR>
    <input type="hidden" name="backorder_rule" value="<%= OrderXMLDoc.kBackOrderAll %>">

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
	%></TABLE><HR><%
}
    request.getSession(true).setAttribute("resultList",null);
	%>

	</BODY></HTML>

