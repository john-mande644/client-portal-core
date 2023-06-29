<%@ page import="com.owd.web.internal.client.ClientHome" %>
<link REL="stylesheet" HREF="../owd.css" TYPE="text/css">
<TABLE border=0 WIDTH=100%><TR><TD align=center width=100%>
    <B><font size=+1>OWD Client Manager</B></font></TD><TD ALIGN=RIGHT NOWRAP>
    <%
        String error = (String) request.getAttribute("errormessage");
    %>


</td></tr>
</table>
<HR>
<TABLE border=0 WIDTH=100%><TR><TD align=center nowrap>
    <A HREF="./edit"><B>Home</B></A>
</td>
    <TD align=center nowrap>&nbsp;&nbsp;&nbsp;<B>|</B>&nbsp;&nbsp;&nbsp;
        <A HREF="./edit?<%= ClientHome.kParamAdminAction%>=client-search"><B>Search Existing Clients</B></A>
    </td>
    <TD align=center nowrap>&nbsp;&nbsp;&nbsp;<B>|</B>&nbsp;&nbsp;&nbsp;
        <A HREF="./edit?<%= ClientHome.kParamAdminAction%>=create"><B>Create New Client</B></A>
    </td>

    <TD align=center nowrap>
    </td>
    <TD align=center nowrap width=100%>

    </td></tr></table>
<HR>
<%= (error != null ? "<font color=red><B>" + error + "</b></font><HR>" : "")%>
