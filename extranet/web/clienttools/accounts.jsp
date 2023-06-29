<%@ page import="com.owd.core.business.Client,
                 com.owd.core.business.client.ClientFactory,
                 com.owd.core.business.user.UserFactory,
                 com.owd.extranet.servlet.ExtranetServlet,
                 com.owd.hibernate.generated.OwdClient,
                 com.owd.hibernate.generated.OwdUser,
                 com.owd.web.warehouse.asn.ASNHome,
                 java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%
	String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
    boolean isInternal = false;
    String errormessage="";
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";
    int clientIDInt = new Integer(user_client_id).intValue();
    
    if(clientIDInt==0)
    {
        isInternal = true;
        //is internal
        if(request.getParameter("internalid") != null)
        {
            clientIDInt=new Integer(request.getParameter("internalid")).intValue();

        }else
        {
          if(request.getParameter("idc") != null)
        {
            clientIDInt=new Integer(request.getParameter("idc")).intValue();

        }
        }
    }

    if(clientIDInt==0) clientIDInt=55;


    if(request.getParameter("getstatement") != null)
        {
        if((clientIDInt+"").equals((String)request.getParameter("idc")))
        {

            try
            {


            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        }

    OwdClient client = ClientFactory.getClientFromClientID(new Integer(clientIDInt),clientIDInt,"0".equals(user_client_id));




    OwdUser ou = UserFactory.getOwdUserFromLogin(request.getUserPrincipal().getName());
    %>
<%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Shipping Account");
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
     	   <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
</head>


<P>
<HR>
<B>[<A HREF="index.jsp">Return to main client tools page</A>]</B>
<P><BR>&nbsp;
<P>
View your shipping account statement for any date range
<HR></P><BR><P>
<B>Get Shipping Account Statement for:
<%

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

     Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date billDate = cal.getTime();

    if (isInternal)
{
    %><FORM METHOD=POST ACTION="accounts.jsp"><SELECT NAME="internalid" onChange="this.form.submit()"><%= ASNHome.getClientSelector(request,"internalid",""+clientIDInt) %></SELECT></FORM><%
} else
{
  %><B><%= client.getCompanyName() %></B><%
}   %>
</B><FORM METHOD=POST  name=dateform id=dateform  ACTION="accounts.jsp"><INPUT TYPE=HIDDEN NAME=getstatement VALUE=1>
<INPUT TYPE=hidden name="idc" value="<%= clientIDInt %>">
  <P>&nbsp;<BR>
Choose a date range for the statement (earliest date is 3/1/2006):   <P>&nbsp;<BR>
<b>Start Date:&nbsp;</b><input name="sd" value="<%= df.format(billDate)%>" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.sd);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
                 <BR><font size=-1>(3/1/2006 or later)</font>
<p>&nbsp;<br></p>
<b>End Date:&nbsp;</b><input name="ed" value="<%= df.format(billDate)%>" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.ed);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
                 <BR><font size=-1>(same or later than start date)</font>



    <P>&nbsp;<BR></P>
<INPUT TYPE=SUBMIT NAME="Get Account Statement" VALUE="Get Account Statement">
                                                                   <P>&nbsp;<BR>

<P>
<fontx size=-1>For assistance, please send an email to One World technical support at <A HREF=mailto:casetracker@owd.com>casetracker@owd.com</A> or call the One World office at 605-845-7172 for help.
<P></P>
    <%

    %>
<HR>
Copyright 2000-2006, One World Direct All Rights Reserved.

  <!--  PopCalendar(tag name and id must match) Tags should sit at the page bottom -->
     <iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js" src="/webapps/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
     <LAYER name="gToday:outlook:agenda.js" src="/webapps/popcal/npopeng.htm"> </LAYER>
     </iframe>
     </FORM></body></HTML>
