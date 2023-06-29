<%@ page import="com.owd.core.OWDUtilities"%>
<%@ page import="com.owd.core.business.user.UserFactory" %>
<%@ page import="com.owd.hibernate.generated.OwdUser" %>
<HTML>
<%
    String user = request.getUserPrincipal().getName();
    OwdUser userObj = UserFactory.getOwdUserFromLogin(user);

%>
<A HREF="http://172.16.2.254/adhocreportbuilder/login.aspx?test=<%= OWDUtilities.getLogiXMLAuthString(userObj.getName(),userObj.getPassword(),""+userObj.getClientFkey()) %>" >Reports</A>
<A HREF="" >link as admin</A>
<A HREF="" >link as new user</A>
<A HREF="" >link as new password</A>
</HTML>