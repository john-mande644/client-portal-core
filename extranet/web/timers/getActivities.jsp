<%@ page import="com.owd.core.OWDUtilities,
                 com.owd.core.business.user.UserManager,
                 com.owd.core.managers.ConnectionManager"%><%@ page import="java.util.Vector" %><%

    java.sql.Connection cxn = null;
    java.sql.PreparedStatement stmt = null;
    java.sql.ResultSet rs = null;

    String username = request.getParameter("username");
    String password = request.getParameter("password");


    try {
        if (UserManager.isValidInternalUser(username, password)) {

            final String recentActivitiesPrefkey = "com.owd.prefs.timer.last_activities";

            String prefsSql = "select ISNULL(pref_value,\'\') from owd_timer_prefs where user_name = \'"
                    + username + "\' and pref_name = \'" + recentActivitiesPrefkey + "\'";
            Vector recentActivities = new Vector();
            String sql = "select activity_id,activity_name, activity_category from owd_activities order by activity_category,activity_name asc ";

            cxn = ConnectionManager.getConnection();

            cxn.setAutoCommit(false);
            System.out.println(prefsSql);
            stmt = cxn.prepareStatement(prefsSql);

            stmt.executeQuery();

            rs = stmt.getResultSet();
            if (rs.next()) {
                System.out.println("got pref value:" + rs.getString(1));
                recentActivities = OWDUtilities.parseableStringToVector(rs.getString(1));
                System.out.println("got recentactivities:" + recentActivities);
            }
            rs.close();
            stmt.close();

            stmt = cxn.prepareStatement(sql);
            stmt.executeQuery();

            rs = stmt.getResultSet();
            boolean gotAPref = false;
            while (rs.next()) {

                //   System.out.println("Comparing recentactivities to:"+rs.getString(1));
                if (recentActivities.contains(rs.getString(1))) {
                    gotAPref = true;
%><%= rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\r" %><%
	}
    }
    rs.close();

			stmt.executeQuery();
			
			rs = stmt.getResultSet();
     if(gotAPref){      
	%><%= "0\t-\t-\r\n" %><%     }
	while(rs.next())
	{
%><%= rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\r" %><%
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