<%@ page import="com.owd.core.business.Client,
				 com.owd.extranet.servlet.ExtranetServlet,
				 com.owd.web.reports.ReportDefinition,
				 com.owd.web.reports.ReportsManager,
				 java.util.List"%>
<%

		
                     
                     String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";
     System.out.println("got cid:"+user_client_id+" for user "+request.getUserPrincipal().getName());

                     String errorNote = (String) request.getAttribute("errorNote");


	%><%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Custom Reports");
%>
<jsp:include page="/extranettop.jsp" />

<script language="JavaScript">
// The following script is used to hide the calendar whenever you click the document.
document.onmousedown=function(e){
var n=!e?self.event.srcElement.name:e.target.name;
if (document.layers) {
with (gfPop) var l=pageX, t=pageY, r=l+clip.width,b=t+clip.height;
if (n!="popcal"&&(e.pageX>r||e.pageX<l||e.pageY>b||e.pageY<t)) gfPop.fHideCal();
return routeEvent(e); // must use return here.
} else if (n!="popcal") gfPop.fHideCal();
       }
if (document.layers) document.captureEvents(Event.MOUSEDOWN);
</script>

<P>
Reports are delivered as a comma-delimited (.csv) file. They are suitable for opening directly in Microsoft Excel or import into any database or spreadsheet program.
<P>
<%
    if(errorNote != null)
    {

    %><B><font color=red><%= errorNote %></font></b><P><%
    }
	List pubReports = null;
    if("0".equals(user_client_id))
    {
       pubReports = ReportsManager.getAllReportList();
    }else
    {
         pubReports = ReportsManager.getPublicReportList();
    }
	       List clientReports = ReportsManager.getClientReportList(user_client_id);

	%>
<TABLE border=0 cellpadding=3 width=50%>
<TR><TD ALIGN=LEFT COLSPAN=2><B>Public Reports:</B>
 <UL>
<%
    for(int i=0;i<pubReports.size();i++)
    {
        %><P><LI><B><A HREF="reportServlet?action=start&id=<%= ((ReportDefinition) pubReports.get(i)).getId() %>" ><%= ((ReportDefinition) pubReports.get(i)).getName()+(((ReportDefinition) pubReports.get(i)).getClientID().equals("0")?"":"(Client: "+Client.getClientForID(((ReportDefinition) pubReports.get(i)).getClientID()).company_name+")") %></A></B><BR>&nbsp;&nbsp;&nbsp;<%=  ((ReportDefinition) pubReports.get(i)).getDescription() %><%
    }
%>
</UL></TD></TR>
<TR><TD ALIGN=LEFT COLSPAN=2><B>Custom Reports:</B>
 <UL>
<%
     if(!("0".equals(user_client_id)) )
    {
    for(int i=0;i<clientReports.size();i++)
    {
        %><P><LI><B><A HREF="reportServlet?action=start&id=<%= ((ReportDefinition) clientReports.get(i)).getId() %>" ><%= ((ReportDefinition) clientReports.get(i)).getName() %></A></B><BR>&nbsp;&nbsp;&nbsp;<%=  ((ReportDefinition) clientReports.get(i)).getDescription() %><%
    }
     }
%>
</UL></TD></TR>


<TR><TD COLSPAN=2></TD></TR>
 </TABLE>
<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

    </BODY>
    </HTML>
