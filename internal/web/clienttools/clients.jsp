<%@ page import="com.owd.core.managers.ConnectionManager,
                 com.owd.web.internal.navigation.*" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kHomeAreaName, "Tools", request);

%><html>
<head>
    <title><%= request.getAttribute("lf_CURRMANAGER") %></title>
    <jsp:include page="/includes/owdtop.jsp"/>


    <H2>One World Direct Internal Access Data</H2>

<P>
<HR>

<B>[<A HREF="index.jsp">Return to main page</A>]</B>

<P>
    <fontx size=+1><B>Current Client Names and IDs</B>
        <table width=100% cellspacing=0 cellpadding=2 border=0>
            <TR><TD colspan=2><HR></TD></TR>
            <TR><TH align=left>Client Name</TH><TH align=left>Client ID</TH></TR>
            <TR><TD colspan=2><HR></TD></TR>
            <%


                java.sql.Connection cxn = null;

                java.sql.PreparedStatement stmt = null;

                java.sql.ResultSet rs = null;
                String reportAction = "";
                String actionRequest = request.getParameter("ACTION");
                if (actionRequest == null) actionRequest = "";

                try {

                    String sql = "select company_name,client_id from owd_client where is_active=1 order by company_name asc";

                    cxn = ConnectionManager.getConnection();

                    stmt = cxn.prepareStatement(sql);


                    stmt.executeQuery();


                    rs = stmt.getResultSet();


                    String bgcolor = "dddddd";

                    while (rs.next())

                    {
                        if ("dddddd".equals(bgcolor)) {
                            bgcolor = "ffffff";

                        } else {
                            bgcolor = "dddddd";
                        }

            %>


            <TR bgcolor=<%= bgcolor %>><TD><%= rs.getString(1) %></TD><TD><%= rs.getString(2) %></TD></TR>

            <%

                }


            } catch (Throwable th)

            {

                th.printStackTrace();

            %>

            <tr><td colspan=2>Error: <%= th.getMessage() %></td></tr>

            <%

                } finally

                {

                    try {
                        rs.close();
                    } catch (Exception ex) {
                    }

                    try {
                        stmt.close();
                    } catch (Exception ex) {
                    }

                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }


                }

            %>

            <TR><TD colspan=2><HR></TD></TR>
        </table>
        <P>
            <fontx size=-1>For assistance, please send an email to One World technical support at <A
                    HREF=mailto:casetracker@owd.com>casetracker@owd.com</A> or call the One World office at 605-845-7172
                for help.
                <P>
                <HR>

                    <jsp:include page="/includes/owdbottom.jsp"/>
                </BODY>
</HTML>
