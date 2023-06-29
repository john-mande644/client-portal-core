<%@ page import="com.owd.core.business.Client"%>
<%@ page import="com.owd.core.managers.ConnectionManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%

	String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";

%><%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Shipping Methods");
%>
<jsp:include page="/extranettop.jsp" />
<P>
<font size=-1>For batch imports, clients should supply the ship method name shown below. For clients submitting orders through the OWD XML API, they must use the code value. Please contact technical support (link at bottom of page) if you have any questions.
</font><HR><table width=100% cellspacing=0 cellpadding=2 border=0>
<TR><TD colspan=2><HR></TD></TR>
<TR><TH align=left>Shipping Method Name</TH><TH align=left>Shipping Method Code</TH></TR>
<TR><TD colspan=2><HR></TD></TR>
<%



	java.sql.Connection cxn = null;

	java.sql.PreparedStatement stmt = null;

	java.sql.ResultSet rs = null;
	String reportAction = "";
	String actionRequest = request.getParameter("ACTION");
	if(actionRequest == null) actionRequest = "";

	try{

	String sql = "select list_value,reference_num from owd_lists where list_name = 'Service' and is_inactive = 0 order by list_value asc";

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


<TR bgcolor=<%= bgcolor %> ><TD><%= rs.getString(1) %></TD><TD><%= rs.getString(2) %></TD></TR>

<%

	}





}catch(Throwable th)

{

	th.printStackTrace();

	%>

	<tr><td colspan=2>Error: <%= th.getMessage() %></td></tr>

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
<fontx size=-2>Copyright 2000-2004, One World Direct All Rights Reserved.
</body>
</html>