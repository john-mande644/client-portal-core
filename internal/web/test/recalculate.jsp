<%@ page import="com.owd.web.internal.warehouse.Unshipper"%>
<%@ page import="java.sql.Statement" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="com.owd.hibernate.HibUtils" %>
<html>
<h2>Bob's Tools</h2>
<hr>
<%
    String results = "Please enter a valid date to recalculate";

    try {
        if (request.getParameter("workdate") != null) {
            if (request.getParameter("workdate").length() > 5) {
                PreparedStatement stmt = HibernateSession.getPreparedStatement("exec OWD_CLIENT_REPORTS.dbo.sp_update_productivity_date ?");
                stmt.setString(1, request.getParameter("workdate"));
                stmt.executeUpdate();
                HibUtils.commit(HibernateSession.currentSession());
                results = "Successfully updated data for "+request.getParameter("workdate");
            }
        }
    } catch (Exception ex) {
        results = results + ":" + ex.getMessage();
    }
%>
<FORM METHOD=POST ACTION="recalculate.jsp">
    Enter a date (in the format YYYY-MM-DD 00:00:00.0) to recalculate productivity data for that day:<hr>
    Work Date: <INPUT type=text name="workdate" value="YYYY-MM-DD 00:00:00.0"><br>
    <INPUT type=submit name="Recalculate Date" value="Recalculate Date">
</FORM>
<font color=red><b><%= results %></b></font>
</html>