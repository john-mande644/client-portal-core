<%@ page import="java.sql.Statement" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="com.owd.hibernate.HibUtils" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.owd.core.OWDUtilities" %>
<html>
<h2>Job Errors</h2>
<hr>
<%
    String errorMsg = null;
    ResultSet resultSet = null;


    try {

    String action =  request.getParameter("act") ;
    String eid =  request.getParameter("id") ;
    if(action ==null) action="";
    if(action.equals("ack"))
    {
      PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_order_error set status=1 where order_error_id=?");
      ps.setInt(1,Integer.parseInt(eid));
      ps.executeUpdate();
      HibUtils.commit(HibernateSession.currentSession());
    }   else if (action.equals("ignore"))
    {
        PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_order_error set status=2 where order_error_id=?");
        ps.setInt(1,Integer.parseInt(eid));
        ps.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());
    }



    String sql = "select e.order_num, isnull(c.company_name,'None'), e.request_type, e.error_type, \n" +
            "e.error_message, e.error_count, e.first_recorded, e.last_recorded,e.order_error_id \n" +
            "from owd_order_error e left outer join owd_client c \n" +
            "on client_fkey=client_id where status=0 ";


          PreparedStatement stmt = HibernateSession.getPreparedStatement(sql);


                resultSet = stmt.executeQuery();

                HibUtils.rollback(HibernateSession.currentSession());



    } catch (Exception ex) {
        errorMsg = OWDUtilities.getStackTraceAsString(ex);
        ex.printStackTrace();
    }
%>

    <%
        if(errorMsg != null)
        {
          %><P><FONT COLOR="RED"><%= errorMsg %></FONT><P><HR><P><%
        }
    %>
    <TABLE BORDER="1">
        <TR>
            <TH>Order Number</TH>
            <TH>Company Name</TH>
            <TH>Request Type</TH>
            <TH>Error Type</TH>
            <TH>Error Message</TH>
            <TH>Error Count</TH>
            <TH>First Recorded</TH>
            <TH>Last Recorded</TH>
            <TH>Action</TH>
        </TR>
        <% if(resultSet != null ){
            while(resultSet.next()) { %>
    <TR>
        <TD><%= resultSet.getString(1)%></TD>
        <TD><%= resultSet.getString(2)%></TD>
        <TD><%= resultSet.getString(3)%></TD>
        <TD><%= resultSet.getString(4)%></TD>
        <TD><%= resultSet.getString(5)%></TD>
        <TD><%= resultSet.getString(6)%></TD>
        <TD><%= resultSet.getString(7)%></TD>
        <TD><%= resultSet.getString(8)%></TD>
        <TD NOWRAP><A HREF="./ordererrorlist.jsp?act=ack&id=<%= resultSet.getInt(9)%>">[Ack]</A>&nbsp;<A HREF="./ordererrorlist.jsp?act=ignore&id=<%= resultSet.getInt(9)%>">[Ignore]</A></TD>
    </TR>
    <% }
    }
     %>
    </TABLE>
</html>
