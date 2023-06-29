<%@ page import="com.owd.web.internal.client.ClientHome,
                 com.owd.web.internal.user.UserHome" %>
<TABLE border=0 WIDTH=100%><TR><TD align=center width=100%>
    <B><font size=+1>OWD User Manager</B></font></TD><TD ALIGN=RIGHT NOWRAP>
    <%
        String error = (String) request.getAttribute("errormessage");
    %>


</td></tr>
</table>
<HR>
<TABLE border=0><TR><TD align=center nowrap>
    <A HREF="./edit"><B>Home</B></A>
</td>
    <TD align=center nowrap>&nbsp;&nbsp;&nbsp;<B>|</B>&nbsp;&nbsp;&nbsp;
        <A HREF="./edit?<%= UserHome.kParamAdminAction%>=user-search"><B>Search Existing Users</B></A>
    </td>
    <TD align=center nowrap>&nbsp;&nbsp;&nbsp;<B>|</B>&nbsp;&nbsp;&nbsp;
        <A HREF="./edit?<%= UserHome.kParamAdminAction%>=create"><B>Create New User</B></A>
    </td>

    <TD align=center nowrap>
    </td>
    <TD align=center nowrap>

    </td></tr></table>
<HR>
<%= (error != null ? "<font color=red><B>" + error + "</b></font><HR>" : "")%>
