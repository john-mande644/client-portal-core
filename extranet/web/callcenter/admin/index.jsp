<%@ page import="com.owd.web.callcenter.admin.CallCenterStatus,
                 com.owd.web.servlet.ExtranetServlet"%>
<!doctype html public "-//w3c/dtd HTML 4.0//en">
<%
     ExtranetServlet.authenticateUser(request);
     CallCenterStatus status = null;


      synchronized(CallCenterStatus.lockObject)
    {
        //System.out.println("CCS: in sync block for "+CallCenterStatus.getCreationTime()+":"+Calendar.getInstance().getTime().getTime());

       // System.out.println("1 minute mark passed...");
        if(CallCenterStatus.getOldStatus()!= null)
        {
            CallCenterStatus.getOldStatus().setOldChart(null);
        }
        CallCenterStatus.setOldStatus(null);
       // System.out.println("Creating new CC Status");
	    status = new CallCenterStatus();


    }

     response.sendRedirect("/webapps/callcenter/admin/index.jsp");
    }else
    {


    System.out.println("cking Call Center Status data...");
    synchronized(CallCenterStatus.lockObject)
    {
   //     System.out.println("CCS: in sync block for "+CallCenterStatus.getCreationTime()+":"+Calendar.getInstance().getTime().getTime());
	if(CallCenterStatus.getCreationTime() < (Calendar.getInstance().getTime().getTime()-((1*1000*60)/6))) //1 minute interval
	{
        //System.out.println("1 minute mark passed...");
        if(CallCenterStatus.getOldStatus()!= null)
        {
            CallCenterStatus.getOldStatus().setOldChart(null);
        }
        CallCenterStatus.setOldStatus(null);
        //System.out.println("Creating new CC Status");
	    status = new CallCenterStatus();

	}else{
		status = CallCenterStatus.getOldStatus();

	}
    }
   String bgcolor="#ffffff";
	DecimalFormat twoPlaceNumFormatter = new DecimalFormat("00");

%>
<html>

<head>
<link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
<title>OWD Call Center Current Status</title>
<meta http-equiv="refresh" content="10">
</head>


<body>
<CENTER><H2>OWD Call Center Current Status</H2><BR><B><script> document.write( new Date().toString() ); </script></B></CENTER>
<HR>
<center><IMG SRC="callHistoryChart.jsp">
</center>


<TABLE cellspacing=0 cellpadding=3 width="100%">
<TR><TH ALIGN=LEFT>Agent Status</TH><TH ALIGN=LEFT>Contact</TH></TR>
<%
         java.sql.Connection cxn = null;
                java.sql.PreparedStatement stmt = null;
                java.sql.ResultSet rs = null;


                try {


                    cxn = ConnectionManager.getConnection();
                    cxn.setAutoCommit(true);
                    cxn.setTransactionIsolation(java.sql.Connection.TRANSACTION_READ_UNCOMMITTED);
                    stmt = cxn.prepareStatement("select uqst.ext+' - '+uqpro.name+' - <B>'+uqst.status+'</B></TD><TD>'+ ISNULL(uqq.mediatype,'')+' - '+\n" +
                            "ISNULL(cl.company_name,'')+(CASE WHEN max(ISNULL(receivedtime,0))>0 THEN  ' - Time Elapsed: <B>'\n" +
                            "+LEFT((convert(varchar,getdate()-max(ISNULL(receivedtime,0)),114)),8)+\n" +
                            "'</B>' ELSE '' END)+\n" +
                            "'</TD></TR>'\n" +
                            " from  MQMAIN..dba.uqagentstatus uqst join MQMAIN..dba.uqagentprofile uqpro on uqst.username=uqpro.username\n" +
                            " left outer join MQMAIN..dba.uqqueue uqq \n" +
                            "join MQMAIN..dba.uqcontact uqc on\n" +
                            "uqq.contacthandle = uqc.handle\n" +
                            "left outer join owd_client_callcenter join owd_client cl on client_id=client_fkey on mlog_campaign_name=uqq.campaign\n" +
                            "on uqst.username=agenthandle\n" +
                            " left outer join MQSTATS..dba.uqcontactstatistics uqh\n" +
                            "on uqh.contacthandle=uqc.handle \n" +
                            "group by uqst.ext,uqpro.name,uqst.status,uqq.mediatype,cl.company_name\n" +
                            "order by ext");
                    stmt.executeQuery();
                    rs = stmt.getResultSet();




%>
 <% while(rs.next())
 {
	 if(bgcolor.equals("#ffffff"))
	 {
		 bgcolor = "#dddddd";
	 }else
	 {
		 bgcolor="#ffffff";
	 }
%>
<TR bgcolor="<%= bgcolor %>">
<TD><%= rs.getString(1) %>
<%      }
             rs.close();
             stmt.close();




bgcolor="#ffffff";	 %>
	 </TABLE>

<HR>
<%

                    stmt = cxn.prepareStatement("select company_name+' ('+uqq.CAMPAIGN+')',mediatype,\n" +
"convert(varchar,datepart(hour,getdate()-(ISNULL(timeentered,0)))+(24*(datepart(DAY,getdate()-(ISNULL(timeentered,0)))-1)))+' h, '+convert(varchar,datepart(minute,getdate()-(ISNULL(timeentered,0))))+' m',\n" +
"RET_PATH as 'From',SUBJECT as 'Subject',BODY_URL,CONTACT_H from MQMAIN..dba.uqqueue uqq \n" +
"left outer join owd_client_callcenter join owd_client cl on client_id=client_fkey on mlog_campaign_name=uqq.campaign\n" +
"left outer join MQMAIN..dba.uqemail em on em.contact_h=uqq.contacthandle, MQMAIN..dba.uqcontact  \n" +
"where contacthandle = MQMAIN.dba.uqcontact.handle and contacthandle not in\n" +
"( select uqq.contacthandle\n" +
"             from  MQMAIN..dba.uqagentstatus uqst \n" +
"             left outer join MQMAIN..dba.uqqueue uqq on uqst.username=agenthandle, MQMAIN..dba.uqcontact\n" +
"             where status in ('Contact', 'WrapupStart', 'StatusingEnd') and uqq.contacthandle = MQMAIN.dba.uqcontact.handle ) \n" +
"order by mediatype desc, timeentered asc");
                    stmt.executeQuery();
                    rs = stmt.getResultSet();

%>
<H2><B>Queue Status</B></H2>
<TABLE cellspacing=5 cellpadding=0 width="100%" bgcolor="#E4ECF4">
<TR><TH COLSPAN=3>Contact Queue</TH></TR>
<TR><TD><TABLE cellspacing=4 cellpadding=8 width="100%" bgcolor="#ffffff">
<TR><TH ALIGN=LEFT>Type</TH><TH ALIGN=LEFT>Client</TH><TH ALIGN=LEFT>Age</TH><TH ALIGN=LEFT>Email Info</TH></TR>
 <%
	 while(rs.next())
 {

	 if(bgcolor.equals("#ffffff"))
	 {
		 bgcolor = "#dddddd";
	 }else
	 {
		 bgcolor="#ffffff";
	 }
%>
<TR bgcolor="<%= bgcolor %>"><TD ALIGN=LEFT NOWRAP><%= rs.getString(2)%></TD>
                 <TD ALIGN=LEFT NOWRAP><%= rs.getString(1)%></TD><TD ALIGN=LEFT NOWRAP><%= rs.getString(3)%></TD>
                 <TD><%= rs.getString(2).equals("EMAIL")?rs.getString(4)+"   "+(true==ExtranetServlet.getSessionFlag(request,ExtranetServlet.kExtranetAdminFlag)?"<A HREF=\"/webapps/callcenter/admin/index.jsp?delete=1&id="+rs.getString("CONTACT_H")+"\">DELETE</A>":"")+"<BR><A target=\"newbodywin\" HREF=\""+rs.getString("BODY_URL")+"body.txt\" >[["+rs.getString("SUBJECT")+"]]</A>":""%></TD>
</TR>
<%
 }
bgcolor="#ffffff";	 %>
</TABLE></TD></TR></TABLE>




</body></html>
<%    } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }

            try {
                cxn.close();
            } catch (Exception e) {
            }
        }
    }
%>
