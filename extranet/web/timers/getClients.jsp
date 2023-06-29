<%@ page import="com.owd.core.OWDUtilities,
                 com.owd.core.business.user.UserManager,
                 com.owd.core.managers.ConnectionManager"%><%@ page import="java.util.Vector" %><%

    java.sql.Connection cxn = null;
    java.sql.PreparedStatement stmt = null;
    java.sql.ResultSet rs = null;

    //store client IDs
    final String recentClientsPrefkey = "com.owd.prefs.timer.last_clients";

    String username = request.getParameter("username");
    String password = request.getParameter("password");


    try {
        if (UserManager.isValidInternalUser(username, password)) {
            String prefsSql = "select ISNULL(pref_value,\'\') from owd_timer_prefs where user_name = \'"
                    + username + "\' and pref_name = \'" + recentClientsPrefkey + "\'";
            Vector recentClients = new Vector();
            String sql = "select company_name,client_id from owd_client where is_active = 1 order by company_name asc ";


            cxn = ConnectionManager.getConnection();
            cxn.setAutoCommit(false);
            //cxn.setQuotedIdentifier(cxn,false);
            System.out.println(prefsSql);
            stmt = cxn.prepareStatement(prefsSql);

            stmt.executeQuery();

            rs = stmt.getResultSet();
            if (rs.next()) {
                System.out.println("got pref value:" + rs.getString(1));
                recentClients = OWDUtilities.parseableStringToVector(rs.getString(1));
                System.out.println("got recentclients:" + recentClients);
            }
            rs.close();
            stmt.close();

            stmt = cxn.prepareStatement(sql);

            stmt.executeQuery();

            rs = stmt.getResultSet();
            boolean gotAPref = false;

            while (rs.next()) {

                //   System.out.println("Comparing recentclients to:"+rs.getString(2));
                if (recentClients.contains(rs.getString(2))) {
                    gotAPref = true;

%><%= rs.getString(1)+"\t"+rs.getString(2)+"\r" %><%
	}
    }
    rs.close();
    
    	stmt.executeQuery();
			
			rs = stmt.getResultSet();
 if(gotAPref){
%><%= "-\t0\r" %><%       }
	while(rs.next())
	{
%><%= rs.getString(1)+"\t"+rs.getString(2)+"\r" %><%
	}
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