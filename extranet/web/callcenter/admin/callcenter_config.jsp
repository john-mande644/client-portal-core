<%@ page import="com.owd.core.managers.ConnectionManager"%><!doctype html public "-//w3c/dtd HTML 4.0//en">

<html>

<head>

<title>OWD Call Center Screen Pop Configuration</title>

</head>
<BODY BGCOLOR="white" LEFTMARGIN="0" TOPMARGIN="0" MARGINHEIGHT="0" MARGINWIDTH="0" ALINK="blue" VLINK="blue" LINK="blue" TEXT="black" >

<BASEFONT >
OWD Call Center Screen Pop Configuration<P><FORM METHOD=POST ACTION=callcenter_edit.jsp>
<INPUT TYPE=HIDDEN NAME=id value=0>
<INPUT TYPE=SUBMIT NAME=ACTION VALUE="Create New Client Entry"></FORM><HR>

<table border=1 cellpadding=0 cellspacing=0>
<TR><TH>Client</TH><TH>MLog Campaign Name</TH><TH>Action</TH></TR>

<%

	

	java.sql.Connection cxn = null;

	java.sql.PreparedStatement stmt = null;

	java.sql.ResultSet rs = null;
	String reportAction = "";
	String actionRequest = request.getParameter("ACTION");
	if(actionRequest == null) actionRequest = "";

	try{

	String sql = "select company_name,* from owd_client_callcenter join owd_client on client_fkey=client_id order by company_name";

	cxn = ConnectionManager.getConnection();

			stmt = cxn.prepareStatement(sql);

		

			stmt.executeQuery();

			

			rs = stmt.getResultSet();

			

	while(rs.next())

	{

%>

<tr><td><font size=-1><FORM METHOD=POST ACTION=callcenter_edit.jsp>
<INPUT TYPE=HIDDEN NAME=id value=<%= rs.getString("id") %>>
&nbsp;<%= rs.getString("company_name") %></TD><TD><font size=-1>&nbsp;<%= rs.getString("Mlog_campaign_name") %></TD><TD><font size=-1>
<INPUT TYPE=SUBMIT NAME=ACTION VALUE="EDIT"><BR>
<B><A HREF="http://172.16.9.1/uqagentapp/snoopindex.jsp?agentfname=tester&agentid=testid&campaign=<%=rs.getString("Mlog_campaign_name")%>" target="_">Test Popup Screen</A></B>
</FORM></td></tr>

<%

	}





}catch(Throwable th)

{

	th.printStackTrace();

	%>

	<tr><td colspan=6>Error: <%= th.getMessage() %></td></tr>

	<%

}finally

{

	try{rs.close();}catch(Exception ex){}

	try{stmt.close();}catch(Exception ex){}

	try{cxn.close();}catch(Exception ex){}



}

%>

</table>

</body>

</html>