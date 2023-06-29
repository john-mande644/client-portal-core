<%@ page import="com.owd.hibernate.HibernateSession" %>
<html>
<!--
  Copyright (c) 1999 The Apache Software Foundation.  All rights 
  reserved.
-->
<% boolean campaignNotFound = false; %>

<body bgcolor="white">

<!--
 		top.main.location.href = "http://172.16.2.111:8080/examples/jsp/snp/snoop.jsp?action=contactreceived&handle="+arguments[2]+"&campaign="+arguments[5]+"&agentid="+arguments[1]+"&mtype="+arguments[6]+"&agentfname="+agentFname+"&statID="+arguments[3]+"&direction="+arguments[4]+"&customerID="+arguments[7]+"&customerType="+arguments[8]+"&destination="+arguments[9]+"&origin="+arguments[10];

-->

<%

    java.sql.Connection cxn = null;
    java.sql.PreparedStatement stmt = null;
    java.sql.ResultSet rs = null;

    //Parameters passed in by Microlog
    String handle = request.getParameter("handle");
    String campaign = request.getParameter("campaign");
    String agentid = request.getParameter("agentid");
    String mtype = request.getParameter("mtype");
    String agentfname = request.getParameter("agentfname");
    String statID = request.getParameter("statID");
    String direction = request.getParameter("direction");
    String customerID = request.getParameter("customerID");
    String customerType = request.getParameter("customerType");
    String destination = request.getParameter("destination");
    String origin = request.getParameter("origin");

    //client_callcenter column values
    String announce_script = "";
    int do_entry = 0;
    int do_status = 0;
    int do_service = 1;
    int client_fkey = 0;
    String url_entry = "";
    String url_status = "";
    String url_service = "";
    String Mlog_campaign_name = "";
    String url_kb_base = "";

    campaign = "ACTIVE_VIDEO";

    try {

        String jdbcDriver = "net.sourceforge.jtds.jdbc.Driver";

        Class.forName(jdbcDriver).newInstance();

        //cxn =  java.sql.DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.254/OWD","sa","wahoo");
        cxn = HibernateSession.currentSession().connection();
        cxn.setAutoCommit(false);

        stmt = cxn.prepareStatement("select * from owd_client_callcenter where Mlog_campaign_name=?");
        stmt.setString(1, campaign);

        stmt.executeQuery();

        rs = stmt.getResultSet();
        int index = 0;

        if (rs.next()) {
            announce_script = rs.getString("announce_script");
            do_entry = rs.getInt("do_entry");
            do_status = rs.getInt("do_status");
            do_service = rs.getInt("do_service");
            client_fkey = rs.getInt("client_fkey");
            url_entry = rs.getString("url_entry");
            url_status = rs.getString("url_status");
            url_service = rs.getString("url_service");
            Mlog_campaign_name = rs.getString("Mlog_campaign_name");
            url_kb_base = rs.getString("url_kb_base");


            while (announce_script.indexOf("<AGENT_NAME>") >= 0) {
                announce_script = announce_script.substring(0, announce_script.indexOf("<AGENT_NAME>")) + agentfname + announce_script.substring(announce_script.indexOf("<AGENT_NAME>") + new String("<AGENT_NAME>").length() + 1);
            }


        } else {
            campaignNotFound = true;

        }

        cxn.rollback();

    } catch (Throwable th) {
        th.printStackTrace();
%>ERROR <%= th.getMessage() %><%
		}finally
		{
			try{rs.close();}catch(Exception ex){}
			try{stmt.close();}catch(Exception ex){}
			//try{cxn.close();}catch(Exception ex){}
		
		}

	if(campaignNotFound)
	{
%>
<B>No valid campaign found for value <%=campaign%></B>
<HR><HR>
<h1> Request Information </h1>

<% }
else{
%>
<TABLE>
<TR>
<TH><A HREF="<%= url_entry %>">New Order/Catalog</A></TH>
<TH><A HREF="<%= url_status %>">Order Status</A></TH>
<TH><A HREF="<%= url_service %>">Customer Service</A></TH>
<TH><A HREF="<%= url_kb_base %>">KBase</A></TH>
</TR>
<TR>
<TD COLSPAN=4>
<HR><P><BR><P><CENTER><FONT SIZE=+2>
<%= announce_script %></FONT></CENTER>
</TD>
</TR>
</TABLE>

<% } %>
</body>
</html>
