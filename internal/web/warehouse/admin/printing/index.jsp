<%@ page import="java.util.Vector" %>
<%
    Vector print_queue = (Vector) request.getAttribute("print_queue");
    Integer print_total = (Integer) request.getAttribute("print_total");
    Integer move_total = (Integer) request.getAttribute("move_total");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<HTML>

<HEAD>

    <TITLE>

        One World Print Queue List

    </TITLE>

</HEAD>

<BODY BGCOLOR="white" LEFTMARGIN="0" TOPMARGIN="0" MARGINHEIGHT="0" MARGINWIDTH="0" ALINK="blue" VLINK="blue"
      LINK="blue" TEXT="black">

<BASEFONT FACE="Geneva,Verdana,Helvetica">


<P>
    <FONT SIZE="-1"><B> Warehouse Print Queue List</B></FONT>
<HR>
<% if (move_total != null) {
%>
<font color=red>(Request Completed: <%= move_total %> orders were moved to the active printing queue. Check the
    printserver for activity.)</font>

<P>
    <A HREF="/internal/warehouse/admin/printing/printcontroller">Return to list</A>
    <%}else{%>
    <B>Total pending: <%= print_total %></B>

<P>
<table border=1>

    <%
        if (print_queue != null && print_queue.size() > 0) {
    %>
    <TR><TH>SKU</TH>
        <TH>Quantity</TH>
        <TH>APO</TH>
        <TH>Ship Method</TH>
        <TH>Count</TH><TH>Print Commands</TH></TR>
    <%
        for (int i = 0; i < print_queue.size(); i++) {
            Vector data = (Vector) print_queue.elementAt(i);

    %>
    <TR><TD><%= data.elementAt(0) %><BR><%= data.elementAt(1) %></TD>

        <TD><%= data.elementAt(5) %></TD>
        <TD><%= data.elementAt(3) %></TD>
        <TD><%= data.elementAt(4) %></TD>
        <TD><%= data.elementAt(7) %></TD>
        <TD>
            <A HREF="/internal/warehouse/admin/printing/printcontroller?skuID=<%= data.elementAt(6) %>&sku=<%= data.elementAt(6) %>&shipMethod=<%=data.elementAt(4)%>&qty=<%= data.elementAt(5)%>&isAPO=<%= data.elementAt(3) %>">[Send&nbsp;To&nbsp;Printer]</A>
        </TD></TR>
    <%

        }
    } else {
    %>
    <LI> No print jobs!</LI>
    <%
        }
    %>
</table>
<%}%>

<!-- Begin Footer -->

<HR ALIGN="center" SIZE="5" NOSHADE>

<FONT SIZE="-2">

    &nbsp;&nbsp;Copyright 2002,

    <A HREF="http://www.owd.com/">

        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

</FONT>

</BODY>

</HTML>


