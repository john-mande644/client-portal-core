<%@ page import="com.owd.core.business.Client,
                 com.owd.web.internal.navigation.*,
                 com.owd.core.OWDUtilities" %>
<%@ page import="com.owd.hibernate.generated.OwdUser" %>
<%@ page import="com.owd.core.business.user.UserFactory" %>
<%

    String user_client_id = Client.getClientIDForUser(request.getRemoteUser());
    if (user_client_id == null) user_client_id = "-1";
    if (user_client_id.equals("")) user_client_id = "-1";


%><%
    UIUtilities.setRequestNavigationElements(UIUtilities.kHomeAreaName, "Home", request);

%>
<html>
<head>
    <title><%= request.getAttribute("lf_CURRMANAGER") %></title>
    <jsp:include page="/includes/owdtop.jsp"/>


    <H2>One World Direct Client Tools</H2>

<P>
<HR>

The list below links to tools and information you may find helpful:
<P>
    <fontx size=+1><B>Tools and Reference Data</B>
        <UL>


            <% if (user_client_id.equals("0")) {
                String user = request.getRemoteUser();
                OwdUser userObj = UserFactory.getOwdUserFromLogin(user);

            %>

            <LI><A HREF="clients.jsp"><fontx size=+1><B>Clients</B></A> <BR><font size=-2>Official client names and
                internal ID numbers<P></font>

                <%       } else {     %>


                <% } %>

            </UL>

        <P>
            <fontx size=-1>For assistance, please send an email to One World technical support at <A
                    HREF=mailto:casetracker@owd.com>casetracker@owd.com</A> or call the One World office at 605-845-7172
                for help.
                <P>
                <HR>
                    <jsp:include page="/includes/owdbottom.jsp"/>
                </BODY>
</HTML>
