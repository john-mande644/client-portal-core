<%@ page import="com.owd.hibernate.HibernateSession,
                 org.hibernate.Session,
                 java.util.List,
                 org.hibernate.Criteria,
                 org.hibernate.criterion.Expression,
                 com.owd.hibernate.generated.OwdClient,
                 com.owd.web.internal.navigation.*,
                 com.owd.web.internal.client.ClientHome" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Warehouse Locations", request);

%><html>
<head>
    <title><%= request.getAttribute("lf_CURRMANAGER") %></title>
    <jsp:include page="/includes/owdtop.jsp"/>

<P>
    <iframe
            name="mainpage"
            src="main.jsp"
            width="100%"
            height="500"
            frameborder="0">
    </iframe>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY>
</HTML>


