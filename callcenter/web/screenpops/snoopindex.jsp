<%@ page import="com.owd.hibernate.HibernateSession" %>
<% boolean campaignNotFound = false;


    java.sql.Connection cxn = null;
    java.sql.PreparedStatement stmt = null;
    java.sql.ResultSet rs = null;

    //Parameters passed in by Microlog
    String handle = request.getParameter("callId");
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

    javax.servlet.http.Cookie callid = new Cookie("OWDCALLID", handle);

    callid.setDomain(".owd.com");
    response.addCookie(callid);

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
    int do_orderref_entry = 0;
    String url_quick_ref = "";
    int do_script = 0;

    //campaign = "ACTIVE_VIDEO";

    try {

        String jdbcDriver = "net.sourceforge.jtds.jdbc.Driver";

        //Class.forName(jdbcDriver).newInstance();

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
            do_orderref_entry = rs.getInt("do_orderref_entry");
            url_quick_ref = rs.getString("url_quick_ref");
            do_script = rs.getInt("script");


            while (announce_script.indexOf("<AGENT_NAME>") >= 0) {
                announce_script = announce_script.substring(0, announce_script.indexOf("<AGENT_NAME>")) + agentfname + announce_script.substring(announce_script.indexOf("<AGENT_NAME>") + new String("<AGENT_NAME>").length() + 1);
            }
            //      System.out.println(announce_script);
            announce_script = announce_script.replaceAll("<CALL_ID>", handle);

        } else {
            campaignNotFound = true;

        }

        cxn.rollback();

    } catch (Throwable th) {
        th.printStackTrace();
    } finally {
        try {
            rs.close();
        } catch (Exception ex) {
        }
        try {
            stmt.close();
        } catch (Exception ex) {
        }
        try {
            //cxn.close();
        } catch (Exception ex) {
        }

    }

    if (campaignNotFound) {
%>

<html>
<title>One World Intranet</title>
<B>No valid campaign found for value <%=campaign%></B>
</html>
<% }
else{
%>
<html>
<title>One World Intranet</title>
   <frameset cols="160,*">
       <frame name="worklist" src="list.jsp?campaign=<%=campaign%>&callId=<%=handle%>&do_script=<%=do_script%>" scrolling="auto" frameborder="0" border=0 noresize>
          <FRAMESET  rows="90,*" >
    		<frame name="navigation" src="snoop2.jsp?action=contactreceived&handle=<%=handle%>&campaign=<%=campaign%>&agentid=<%=agentid%>&mtype=<%=mtype%>&agentfname=<%=agentfname%>&statID=<%=statID%>&direction=<%=direction%>&customerID=<%=customerID%>&customerType=<%=customerType%>&destination=<%=destination%>&origin=<%=origin%>" >
      		<frame name="content" src="script.jsp?announce_script=<%= java.net.URLEncoder.encode(announce_script) %>&do_orderref_entry=<%=do_orderref_entry%>&client_fkey=<%=client_fkey%>&handle=<%=handle%>" >
		</frameset>
    </frameset>
</html>
<% } %>
