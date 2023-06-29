<%@ page import="com.owd.core.OWDUtilities,
                 com.owd.core.business.user.UserManager,
                 com.owd.core.managers.ConnectionManager"%><%@ page import="java.util.Vector" %><%

    java.sql.Connection cxn = null;
    java.sql.PreparedStatement stmt = null;
    java.sql.ResultSet rs = null;

    String clientKey = request.getParameter("client_fkey");
    String activityID = request.getParameter("activity_id");
    String activityDesc = request.getParameter("activity_desc");
    String category = "";
    if (activityDesc.indexOf(":") >= 0) {
        category = activityDesc.substring(0, activityDesc.indexOf(":"));
        activityDesc = activityDesc.substring(activityDesc.indexOf(":") + 1);
    } else {
        category = "";
    }
    String elapsedSecs = request.getParameter("elapsed_seconds");
    String notes = request.getParameter("notes");
    String noCharge = request.getParameter("no_charge");
    String rate = request.getParameter("rate");
    String createdBy = request.getParameter("created_by");


    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try {

        if (UserManager.isValidInternalUser(username, password)) {
            cxn = ConnectionManager.getConnection();
            cxn.setAutoCommit(false);

            final String recentActivitiesPrefkey = "com.owd.prefs.timer.last_activities";
            final String recentClientsPrefkey = "com.owd.prefs.timer.last_clients";

            int clientRecordID = 0;
            int activityRecordID = 0;

            String prefsSql = "select ISNULL(pref_value,\'\'),ISNULL(id,0) from owd_timer_prefs where user_name = \'"
                    + username + "\' and pref_name = \'" + recentActivitiesPrefkey + "\'";
            Vector recentActivities = new Vector();
            System.out.println("rs:" + recentActivities);

            System.out.println(prefsSql);
            stmt = cxn.prepareStatement(prefsSql);

            stmt.executeQuery();

            rs = stmt.getResultSet();
            if (rs.next()) {
                recentActivities = OWDUtilities.parseableStringToVector(rs.getString(1));
                System.out.println("rs2:" + recentActivities);

                activityRecordID = rs.getInt(2);
            }
            rs.close();
            stmt.close();
            prefsSql = "select ISNULL(pref_value,\'\'),ISNULL(id,0) from owd_timer_prefs where user_name = \'"
                    + username + "\' and pref_name = \'" + recentClientsPrefkey + "\'";
            Vector recentClients = new Vector();

            System.out.println(prefsSql);
            stmt = cxn.prepareStatement(prefsSql);

            stmt.executeQuery();

            rs = stmt.getResultSet();
            if (rs.next()) {
                recentClients = OWDUtilities.parseableStringToVector(rs.getString(1));
                clientRecordID = rs.getInt(2);
            }
            rs.close();
            stmt.close();
            System.out.println("rs3:" + recentActivities);
            System.out.println("activityID=" + activityID);
            if (!(recentActivities.contains(activityID))) {
                System.out.println("rs4:" + recentActivities);
                recentActivities.add(activityID);
                System.out.println("rs5:" + recentActivities);
            }

            if (!(recentClients.contains(clientKey))) {
                recentClients.add(clientKey);
            }
            System.out.println("recentClientsCk:" + recentClients);
            System.out.println("recentActivitiesCk:" + recentActivities);
            while (recentClients.size() > 10) {
                recentClients.removeElementAt(0);
            }
            while (recentActivities.size() > 10) {
                recentActivities.removeElementAt(0);
            }

            String insertSql = "insert into owd_timer_prefs (user_name,pref_name,pref_value) VALUES(?,?,?)";
            String updateSql = "update owd_timer_prefs set user_name = ?,pref_name=?,pref_value=? where id = ?";

            System.out.println(insertSql);
            if (activityRecordID < 1) {
                stmt = cxn.prepareStatement(insertSql);
                stmt.setString(1, username);
                stmt.setString(2, recentActivitiesPrefkey);
                stmt.setString(3, OWDUtilities.vectorToParseableString(recentActivities));
            } else {
                stmt = cxn.prepareStatement(updateSql);
                stmt.setString(1, username);
                stmt.setString(2, recentActivitiesPrefkey);
                stmt.setString(3, OWDUtilities.vectorToParseableString(recentActivities));
                stmt.setInt(4, activityRecordID);
            }
            stmt.executeUpdate();
            if (clientRecordID < 1) {
                stmt = cxn.prepareStatement(insertSql);
                stmt.setString(1, username);
                stmt.setString(2, recentClientsPrefkey);
                stmt.setString(3, OWDUtilities.vectorToParseableString(recentClients));
            } else {
                stmt = cxn.prepareStatement(updateSql);
                stmt.setString(1, username);
                stmt.setString(2, recentClientsPrefkey);
                stmt.setString(3, OWDUtilities.vectorToParseableString(recentClients));
                stmt.setInt(4, clientRecordID);
            }
            stmt.executeUpdate();
            cxn.commit();
            stmt.close();


            String sql = "insert into owd_timers (client_fkey,activity_desc,elapsed_seconds,notes,rate,no_charge,created_by,category) VALUES (?,?,?,?,?,?,?,?)";

            //cxn.setQuotedIdentifier(cxn,false);

            stmt = cxn.prepareStatement(sql);
            stmt.setInt(1, new Integer(clientKey).intValue());
            stmt.setString(2, activityDesc);
            stmt.setInt(3, new Integer(elapsedSecs).intValue());
            stmt.setString(4, notes);
            stmt.setInt(6, new Integer(noCharge).intValue());
            stmt.setFloat(5, 0.00f);
            stmt.setString(7, createdBy);
            stmt.setString(8, category);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
%><%= "SUCCESS\r\n" %><%
			}else{
			%><%= "ERROR" %><%
			}
			
			cxn.commit();
			
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