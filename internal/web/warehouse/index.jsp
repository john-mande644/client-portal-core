<%@ page import="com.owd.hibernate.HibernateSession,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 com.owd.web.internal.client.ClientHome,
                 com.owd.web.internal.navigation.*,
                 java.util.*" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Home/Summary", request);

%>
<html>
<head>
    <title><%= request.getAttribute("lf_CURRMANAGER") %></title>
    <jsp:include page="/includes/owdtop.jsp"/>


    <jsp:include page="/warehouse/admin/index.jsp"/>

    <!--<p><span class="highlight"><strong>There haven't been any files uploaded to this project yet.</strong></span></p>
                                                                                           <p>Once files have been uploaded, you'll see a list of the files like the example below:</p>
                                                                                           <p style="margin-top: 25px;"><img src="/images/blankslate-ex_files.gif" alt="files example" width="715" height="205" /></p>  -->


    <jsp:include page="/includes/owdbottom.jsp"/>
</body>
</html>
