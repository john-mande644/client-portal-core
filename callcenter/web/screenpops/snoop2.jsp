<%@ page import="com.owd.hibernate.HibernateSession" %>
<html>
<head>
    <title>Report List</title>

    <STYLE TYPE="text/css">
        .header {
            font-size: 12px;
            font-family: arial;
            letter-spacing: 0pt;
            font-weight: bold;
            color: #ffffff;
        }

        .text {
            font-size: 12px;
            font-family: arial;
            letter-spacing: 0pt;
            font-weight: normal;
            color: #000000;
        }

        .menuhide {
            display: none;
            border-left: 2 solid white;
            border-top: 2 solid white;
            border-right: 2 solid black;
            border-bottom: 2 solid black;
            background-color: #d0d0c9;
            position: absolute;
        }

        .menu {
            display: '';
            border-left: 2 solid white;
            border-top: 2 solid white;
            border-right: 2 solid black;
            border-bottom: 2 solid black;
            background-color: #d0d0c9;
            position: absolute;
        }

        .org {
            color: #000000;
            text-decoration: none;
        }

        .dec {
            color: #0000ff;
            text-decoration: underline;
        }

        .cont {
            text-decoration: none;
            font-size: 12px;
            color: #000000;
        }

        .contdec {
            text-decoration: none;
            font-size: 12px;
            color: #0000ff;
        }
    </STYLE>

</head>

<BODY BGCOLOR="#FFFFFF" TOPMARGIN=8 LEFTMARGIN=6 MARGINWIDTH=11 MARGINHEIGHT=8>

<div id=fsdfd class=text>
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
    String skill = request.getParameter("skill");

    //client_callcenter column values
    String announce_script = "";
    int do_entry = 0;
    int do_status=0;
    int do_service = 1;
    int client_fkey = 0;
    String url_entry = "";
    String url_status = "";
    String url_service = "";
    String Mlog_campaign_name = "";
    String url_kb_base = "";
    int kb_pop=0;
    int entry_pop=0;
    int status_pop=0;
    int service_pop=0;
    int do_orderref_entry=0;
    String url_quick_ref="";


        try
        {

String jdbcDriver = "net.sourceforge.jtds.jdbc.Driver";

            Class.forName(jdbcDriver).newInstance();

            //cxn =  java.sql.DriverManager.getConnection("jdbc:jtds:sqlserver://172.16.2.254/OWD","sa","wahoo");
            cxn = HibernateSession.currentSession().connection();
            cxn.setAutoCommit(false);

            stmt = cxn.prepareStatement("select * from owd_client_callcenter where Mlog_campaign_name=?");
            stmt.setString(1,campaign);

            stmt.executeQuery();

            rs = stmt.getResultSet();
            int index=0;

            if(rs.next())
            {
    announce_script = rs.getString("announce_script");
    do_entry = rs.getInt("do_entry");
    do_status= rs.getInt("do_status");
    do_service = rs.getInt("do_service");
    client_fkey = rs.getInt("client_fkey");
    url_entry = rs.getString("url_entry");
    url_status = rs.getString("url_status");
    url_service = rs.getString("url_service");
    Mlog_campaign_name = rs.getString("Mlog_campaign_name");
    url_kb_base = rs.getString("url_kb_base");
    do_orderref_entry= rs.getInt("do_orderref_entry");
    url_quick_ref= rs.getString("url_quick_ref");

    kb_pop = rs.getInt("url_kb_base_pop");
    entry_pop = rs.getInt("url_entry_pop");
    status_pop = rs.getInt("url_status_pop");
    service_pop = rs.getInt("url_service_pop");

    if(announce_script != null)
    {
    while(announce_script.indexOf("<AGENT_NAME>")>=0)
    {
        announce_script = announce_script.substring(0,announce_script.indexOf("<AGENT_NAME>"))+agentfname+announce_script.substring(announce_script.indexOf("<AGENT_NAME>")+new String("<AGENT_NAME>").length());
    }
    }

    if(url_entry != null)
    {

        while(url_entry.indexOf("<AGENT_ID>")>=0)
    {
        url_entry = url_entry.substring(0,url_entry.indexOf("<AGENT_ID>"))+(agentid.length()>10?agentid.substring(0,10):agentid)+url_entry.substring(url_entry.indexOf("<AGENT_ID>")+new String("<AGENT_ID>").length());
    }

    if(handle==null) handle = "contactID";
    if("null".equals(handle)) handle="contactID";

        while(url_entry.indexOf("<CALL_ID>")>=0)
    {
        url_entry = url_entry.substring(0,url_entry.indexOf("<CALL_ID>"))+handle+url_entry.substring(url_entry.indexOf("<CALL_ID>")+new String("<CALL_ID>").length());
    }
        }

        if(url_status != null)
    {
    while(url_status.indexOf("<AGENT_ID>")>=0)
    {
        url_status = url_status.substring(0,url_status.indexOf("<AGENT_ID>"))+(agentid.length()>10?agentid.substring(0,10):agentid)+url_status.substring(url_status.indexOf("<AGENT_ID>")+new String("<AGENT_ID>").length());
    }
        }

        if(url_service != null)
    {
    while(url_service.indexOf("<AGENT_ID>")>=0)
    {
        url_service = url_service.substring(0,url_service.indexOf("<AGENT_ID>"))+(agentid.length()>10?agentid.substring(0,10):agentid)+url_service.substring(url_service.indexOf("<AGENT_ID>")+new String("<AGENT_ID>").length());
    }
    }
            }else
            {
                campaignNotFound = true;

            }

            cxn.rollback();

        }catch(Throwable th)
        {
            th.printStackTrace();

%>ERROR <%= th.getMessage() %><%

        }finally
        {
            try{rs.close();}catch(Exception ex){}
            try{stmt.close();}catch(Exception ex){}
          //  try{cxn.close();}catch(Exception ex){}

        }

    if(campaignNotFound)
    {

%>
<B>No valid campaign found for value <%=campaign%>
</B>

<%
}
else{

%>
<hr align="center" SIZE=10 NOSHADE=FALSE color=black>
<TABLE width=100%>
    <TR>
        <% if (do_entry == 1) { %>
        <TH><A HREF="<%= url_entry %>" target="<%= entry_pop==1?"_blank":"content" %>">Place Order</A></TH>
        <% }
            if (do_status == 1) { %>
        <TH><A HREF="<%= url_status %>" target="<%= status_pop==1?"_blank":"content" %>">Order Status</A></TH>
        <% }
            if (do_service == 1) { %>
        <TH><A HREF="<%= url_service %>" target="<%= service_pop==1?"_blank":"content" %>">Customer Service</A></TH>
        <% } %>
        <TH><A HREF="<%= url_kb_base %>" target="<%= kb_pop==1?"_blank":"content" %>">KBase</A></TH>
        <% if (url_quick_ref != null) {
            if (!url_quick_ref.equals("")) {%>
        <TH><A HREF="<%= url_quick_ref %>" target="_blank">Quick Ref</a></th>
        <% }
        } %>
    </TR>

</TABLE>
<hr align="center" SIZE=10 NOSHADE=FALSE color=black>
<% } %>
</div>

</body>
</html>
