<%@ page import="com.owd.extranet.servlet.ExtranetServlet"%>
<%@ page import="com.owd.web.autoship.AutoShipHome" %>

<%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM method=POST action=\"./edit\">"+ AutoShipHome.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Subscriptions");
    String error = (String) request.getAttribute("errormessage");

%>
<jsp:include page="/extranettop.jsp" />

<TABLE border=0 WIDTH=100%><TR> <TD align=center nowrap>
<A HREF="./edit"><B>Home</B></A>
</td>
<TD align=center nowrap>&nbsp;&nbsp;&nbsp;<B>|</B>&nbsp;&nbsp;&nbsp;
<A HREF="./edit?<%= AutoShipHome.kParamAdminAction%>=auto-search"><B>Search Existing Subscriptions</B></A>
</td>
<TD align=center nowrap>&nbsp;&nbsp;&nbsp;<B>|</B>&nbsp;&nbsp;&nbsp;
<A HREF="./edit?<%= AutoShipHome.kParamAdminAction%>=create"><B>Create New Subscription Order</B></A>
</td>

<TD align=center nowrap>
</td>
<TD align=center nowrap width=100%>

</td></tr></table>
<HR>
<%= (error!= null?"<font color=red><B>"+error+"</b></font><HR>":"")%>
