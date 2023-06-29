<%@ page import="com.owd.core.managers.ConnectionManager,
                 com.owd.hibernate.HibernateSession" %>
<%
    java.sql.ResultSet rs = null;
    String reportAction = (String) request.getAttribute("searchSKU");

%>
<!doctype html public "-//w3c/dtd HTML 4.0//en">

<html>
<head>
    <link REL="stylesheet" HREF="/internal/owd.css" TYPE="text/css">

    <title>Warehouse Location Manager</title>

</head>

<BODY BGCOLOR="white" LEFTMARGIN="15" TOPMARGIN="15" MARGINHEIGHT="15" MARGINWIDTH="15" ALINK="blue" VLINK="blue"
      LINK="blue" TEXT="black">
<H2>Warehouse Location Search</H2>

<FORM METHOD=POST ACTION=index.jsp>
    Type in SKU or part of SKU: <INPUT TYPE=text name=searchSKU value="">&nbsp;<INPUT TYPE=SUBMIT NAME=ACTION
                                                                                      VALUE="Find SKU Location History">
</FORM>
<HR>


<%


    String sku = request.getParameter("searchSKU");
    if (sku == null) sku = "";
    if (sku.length() > 0) {
%>
<table border=1 cellpadding=3 cellspacing=0>
    <tr><td colspan=5 align=center>Search Results for SKU <B><%= sku %></B></TD></TR>

    <TR><TH align=center>Client</TH><TH align=center>SKU</TH><TH align=center>Date Assigned</TH><TH align=center>
        Assigned By</TH><TH align=center>Location</TH></TR>

    <%
        boolean foundSKU = false;

        try {

            String sql = "select company_name,inventory_num,assign_date,assigned_by, location from owd_inventory join owd_inventory_locations "
                    + " on inventory_id=inventory_fkey join owd_client on client_id=client_fkey where inventory_num = \'" + sku + "\' order by company_name asc, inventory_num asc, assign_date desc";

            rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sql);


            String oldClient = "";
            String oldSKU = "";

            while (rs.next())

            {


    %>

    <tr><%= oldClient.equals(rs.getString("company_name")) ? "<TD></TD>" : "<TD>" + rs.getString("company_name") + "</TD>" %>
        <TD><%= rs.getString("inventory_num") %></TD><td nowrap><font size=-1>
        &nbsp;<%= rs.getString("assign_date") %></TD><TD><font size=-1>&nbsp;<%= rs.getString("assigned_by") %></TD><TD>
        <font size=-1>
        <%= rs.getString("location") %></td></tr>

    <%
            oldClient = rs.getString("company_name");
            oldSKU = rs.getString("inventory_num");
            foundSKU = true;

        }

        HibernateSession.closeSession();

        if (!foundSKU) {
            sql = "select company_name,client_id, inventory_num,assign_date,assigned_by, location from owd_inventory join owd_inventory_locations "
                    + " on inventory_id=inventory_fkey join owd_client on client_id=client_fkey  where inventory_num like \'%" + sku + "%\' order by company_name asc, inventory_num asc, assign_date desc";

            rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sql);
            oldClient = "";
            oldSKU = "";
            while (rs.next())

            {

    %>

    <tr><%= oldClient.equals(rs.getString("company_name")) ? "<TD></TD>" : "<TD>" + rs.getString("company_name") + "</TD>" %>
        <TD><%= rs.getString("inventory_num") %></TD><td nowrap><font size=-1>
        &nbsp;<%= rs.getString("assign_date") %></TD><TD><font size=-1>&nbsp;<%= rs.getString("assigned_by") %></TD><TD>
        <font size=-1>
        <%= rs.getString("location") %></td></tr>

    <%
            oldClient = rs.getString("company_name");
            oldSKU = rs.getString("inventory_num");
        }
    %></table>
<%

    }


} catch (Throwable th)

{

    th.printStackTrace();

%>

<tr><td colspan=6>Error: <%= th.getMessage() %></td></tr>

<%

        } finally

        {

            HibernateSession.closeSession();

        }
    }
%>


</body>

</html>