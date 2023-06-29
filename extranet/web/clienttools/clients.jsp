<%@ page import="com.owd.core.managers.ConnectionManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %><%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Current Clients");
%>
<jsp:include page="/extranettop.jsp" />
<P>
<table width=100% cellspacing=0 cellpadding=2 border=0>
<TR><TH align=left>Client Name</TH><TH align=left>AM Email</TH><TH align=left>Client ID</TH></TR>
<TR><TD colspan=3><HR></TD></TR>
<%



	java.sql.Connection cxn = null;

	java.sql.PreparedStatement stmt = null;

	java.sql.ResultSet rs = null;
	String reportAction = "";
	String actionRequest = request.getParameter("ACTION");
	if(actionRequest == null) actionRequest = "";

	try{

	String sql = "select company_name,am_email,client_id from owd_client where is_active=1 order by company_name asc";

	cxn = ConnectionManager.getConnection();

			stmt = cxn.prepareStatement(sql);



			stmt.executeQuery();



			rs = stmt.getResultSet();


   String bgcolor="dddddd";

	while(rs.next())

	{
		if("dddddd".equals(bgcolor))
		{
			bgcolor="ffffff";

		}   else
		{
			bgcolor= "dddddd";
		}

%>


<TR bgcolor=<%= bgcolor %> ><TD><%= rs.getString(1) %></TD><TD><%= rs.getString(2) %></TD><TD><%= rs.getString(3) %></TD></TR>

<%

	}





}catch(Throwable th)

{

	th.printStackTrace();

	%>

	<tr><td colspan=3>Error: <%= th.getMessage() %></td></tr>

	<%

}finally

{

	try{rs.close();}catch(Exception ex){}

	try{stmt.close();}catch(Exception ex){}

	try{cxn.close();}catch(Exception ex){}



}

%>

<TR><TD colspan=2><HR></TD></TR>
</table>
<P>
<fontx size=-1>For assistance, please send an email to One World technical support at <A HREF=mailto:casetracker@owd.com>casetracker@owd.com</A> or call the One World office at 605-845-7172 for help.
<P>
<HR>
<fontx size=-2>Copyright 2000-2003, One World Direct All Rights Reserved.
</body>
</html>
