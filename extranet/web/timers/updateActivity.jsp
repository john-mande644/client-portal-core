<%@ page import="com.owd.core.business.user.UserManager"%><%@ page import="com.owd.core.managers.ConnectionManager" %><%

    java.sql.Connection cxn = null;
    java.sql.PreparedStatement stmt = null;
    java.sql.ResultSet rs = null;

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String aname = request.getParameter("aname");
    String acategory = request.getParameter("acategory");
    String aid = request.getParameter("aid");

    try {
        if (UserManager.isValidInternalUser(username, password)) {
            String insertSql = "insert into owd_activities(activity_name,activity_category) VALUES (?,?)";
            String updateSql = "update owd_activities set activity_name = ?, activity_category = ? where activity_id=?";


            cxn = ConnectionManager.getConnection();

            cxn.setAutoCommit(false);
            //cxn.setQuotedIdentifier(cxn,false);

            if (new Integer(aid).intValue() == 0) {
                stmt = cxn.prepareStatement(insertSql);
                stmt.setString(1, aname);
                stmt.setString(2, acategory);

            } else {
                stmt = cxn.prepareStatement(updateSql);
                stmt.setString(1, aname);
                stmt.setString(2, acategory);
                stmt.setInt(3, new Integer(aid).intValue());
            }


            int rows = stmt.executeUpdate();

            if (rows > 0) {
%><%= "SUCCESS\r\n" %><%
		}else
		{
%><%= "ERROR" %><%
		}

		cxn.commit();
	}

}catch(Exception th)
{
	th.printStackTrace();
	%>
	<%= "ERROR\r\nServer:"+th.getMessage() %>
	<%
}finally
{
	try{rs.close();}catch(Exception ex){}
	try{stmt.close();}catch(Exception ex){}
	try{cxn.close();}catch(Exception ex){}

}
%>