<%@ page import="com.owd.core.business.Client,
				 com.owd.extranet.servlet.ExtranetServlet,
				 java.text.DateFormat,
				 java.text.SimpleDateFormat,
				 java.util.Calendar,
				 java.util.Date,
				 java.util.Locale"%>
<%



      String cid = null;

                          String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
     	if(user_client_id == null) user_client_id = "-1";
     	if(user_client_id.equals("")) user_client_id = "-1";

    cid=user_client_id;

                          if(!(request.getParameter("cid")==null))
                          {
                            if("0".equals(user_client_id))
                            {
                                cid=request.getParameter("cid");
                            }
                          }
                     String errorNote = (String) request.getAttribute("errorNote");


      request.setAttribute("cid",cid);
    
     DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date yesterday = cal.getTime();
    %><%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Shipping Account");
%>
<jsp:include page="/extranettop.jsp" /><script language="JavaScript">
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

<B>Shipping Account Statement</B>
 <%
    if(errorNote != null)
    {

    %><p><B><font color=red size=+1 ><%= errorNote %></font></b><P><%
    }
	%> <br><B>[<A HREF="/webapps/clienttools/index.jsp">Return to Client Tools page</A>]</B>

    <hr>
    <form method=POST name="dateform" ACTION="index.jsp">
Statement for: <% if (user_client_id.equals("0"))
{
    %><b><SELECT NAME="cid"><%= com.owd.web.servlet.ExtranetServlet.getClientSelector(request,"cid",cid==null?user_client_id:cid) %></SELECT></b>
    <% }else{

    Client client = Client.getClientForUser(request.getUserPrincipal().getName());
    %><B><%= client.company_name %></B>
   <% } %>
    <BR>
Start Date:&nbsp;<input name="sd" value="<%= request.getParameter("sd")==null?df.format(yesterday):request.getParameter("sd") %>" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.sd);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
&nbsp;&nbsp;End Date:&nbsp;<input name="ed" value="<%= request.getParameter("ed")==null?df.format(yesterday):request.getParameter("ed") %>" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.ed);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
&nbsp;&nbsp;<INPUT TYPE=SUBMIT NAME="View Statement" VALUE="View Statement" ><hr>
    </form>
<%
    if("View Statement".equals(request.getParameter("View Statement")))
    {
        %><jsp:include page="shipstatement.jsp"/><%
    }
%>


 <!--  PopCalendar(tag name and id must match) Tags should sit at the page bottom -->
     <iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js" src="/webapps/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
     <LAYER name="gToday:outlook:agenda.js" src="/webapps/popcal/npopeng.htm">     </LAYER>
     </iframe>
     </FORM></HTML>
