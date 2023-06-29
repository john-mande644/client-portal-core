<%@ page import="com.owd.extranet.servlet.ExtranetServlet"%>
<%@ page import="com.owd.web.warehouse.asn.ASNHome" %>
<%     ExtranetServlet.authenticateUser(request);

    request.setAttribute("clientselector","<FORM method=POST action=\"./edit\">"+ASNHome.getClientSelector(request)+"</FORM>");
    request.setAttribute("areatitle","ASN Manager");
    %>
    <jsp:include page="/extranettop.jsp" />

<TABLE border=0 WIDTH=100%><TR><TD ALIGN=left NOWRAP>
 <%
	 String error = (String) request.getAttribute("errormessage");
 %>
</td></tr></table>
<HR>
<TABLE border=0 WIDTH=100%><TR> <TD align=center nowrap>
<A HREF="./edit"><B>ASN Home</B></A>
</td>
<TD align=center nowrap>&nbsp;&nbsp;&nbsp;<B>|</B>&nbsp;&nbsp;&nbsp;
<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=asn-search"><B>Search ASNs</B></A>
</td>
<TD align=center nowrap>&nbsp;&nbsp;&nbsp;<B>|</B>&nbsp;&nbsp;&nbsp;
<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=create"><B>Create New ASN</B></A>
</td>
<%
    if(ASNHome.getSessionFlag(request,ASNHome.kExtranetInternalFlag))
    {
%>
<TD align=center nowrap>&nbsp;&nbsp;&nbsp;<B>|</B>&nbsp;&nbsp;&nbsp;
<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=create&blind=1"><B>Create New Blind ASN</B></A>
</td>
<% } %>

<TD align=center nowrap>
</td>
<TD align=center nowrap width=100%>

</td></tr></table>
<HR>
<%= (error!= null?"<font color=red><B>"+error+"</b></font><HR>":"")%>
