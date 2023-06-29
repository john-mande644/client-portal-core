<%@ page import="java.util.Map,
                 java.util.List,
                 com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.util.Iterator" %>
<%@ page import="java.net.URLEncoder" %>
<%


    String bgcolor = "odd";
    List clientStatus = (List) request.getAttribute("clientStatus");
    Iterator clientIT = clientStatus.iterator();
    WarehouseStatus status = (WarehouseStatus) request.getAttribute("whStatus");
    ;
    int detailTypeNum = 0;
    boolean showDetail = false;

    String isDetailRequested = request.getParameter("detailRequest");
    String detailType = request.getParameter("detailTypeNum");
    String detailValue = request.getParameter("detailValue");
    if ("1".equals(isDetailRequested)) {
        showDetail = true;
        detailTypeNum = new Integer(detailType).intValue();
    }
%><HR><CENTER><H2>Current Shipping Order Status (By Client)</H2></CENTER>
<TABLE cellspacing=0 cellpadding=3 width="100%" class="its">
    <thead><TR><TH ALIGN=LEFT>Client</TH><TH ALIGN=LEFT>Posted</TH><TH ALIGN=LEFT>Picked</TH><TH ALIGN=LEFT>Packed</TH><TH ALIGN=LEFT>Shipped</TH>
        <TH ALIGN=LEFT>Waiting to Ship</TH><TH ALIGN=LEFT>Units</TH></TR>
    </thead><tbody><% while (clientIT.hasNext()) {
    Map info = (Map) clientIT.next();
    if (bgcolor.equals("odd")) {
        bgcolor = "even";
    } else {
        bgcolor = "odd";
    }
%>
    <TR class="<%= bgcolor %>">
        <%
            List orderListx = status.getOrderInfoList(WarehouseStatus.kClientOrderList, (String) info.get("CLIENT"));
            if (orderListx.size() > 0) {
                Iterator orderIT = orderListx.iterator();

                if (orderIT.hasNext()) {

        %><TD NOWRAP><A
            HREF="detail.jsp?type=Client&detailRequest=1&detailTypeNum=<%= WarehouseStatus.kClientOrderList%>&detailValue=<%= URLEncoder.encode(""+info.get("CLIENT")) %>"><%= info.get("CLIENT") %></A>
    </TD>
        <%

                }
            }
        %>
        <TD><%= info.get("POST") %></TD>
        <TD><%= info.get("PICKEDUNPACKED") %></TD>
        <TD><%= info.get("PACKEDUNSHIPPED") %></TD>
        <TD><%= info.get("SHIP") %></TD>
        <TD><%= info.get("UNSHIP") %></TD>
        <TD><%= info.get("UNITS") %></TD>
    </TR>
    <%
        if (showDetail && (info.get("CLIENT").equals(detailValue))) {
            List orderList = status.getOrderInfoList(detailTypeNum, detailValue);
            if (orderList.size() > 0) {
                Iterator orderIT = orderList.iterator();
                String subcolor = "odd";
    %>
    <TR><TD></TD><TD COLSPAN=4>
        <TABLE WIDTH=100% border=0 class="its">
            <thead><TR><TH>OWD Ref</TH><TH>Client</TH><TH>Country</TH><TH>Posted</TH><TH>Customer</TH><TH>Method</TH>
                <TH>Status</TH><TH>SLA</TH><TH>On Hold</TH><TH>Lines</TH><TH>Units</TH></TR>
            </thead><tbody><%
			 while (orderIT.hasNext())
			 {
				 Map orderMap = (Map) orderIT.next();
                 if(subcolor.equals("odd"))
                 {
                     subcolor="even";
                 }                   else
                 {
                     subcolor="odd";
                 }
		 %>
            <TR class="<%= subcolor %>"><TD><%=orderMap.get("order_num")%></TD>
                <TD><%=orderMap.get("client")%></TD>
                <TD><%=orderMap.get("ship_country")%></TD>
                <TD><%=orderMap.get("post_date")%></TD>
                <TD><%=orderMap.get("name")%></TD>
                <TD><%=orderMap.get("carr_service")%></TD>
            <TH>
        <%=orderMap.get("shipped").equals("1") ? "<font color=green>SHIPPED</font>" : orderMap.get("packed").equals("1") ? "<font color=blue>PACKED</font>" : orderMap.get("picked").equals("1")?"<font color=purple>PICKED</font>":"<font color=red>NOT PICKED</font>"

        %></TD>

        <TD><%=orderMap.get("sla")%></TD>

        <TD><%=orderMap.get("on_hold")%></TD>
        <TD><%=orderMap.get("lines")%></TD>
        <TD><%=orderMap.get("units")%></TD>
    </TR>
    <%

        }
    %>
</tbody></TABLE>
</TD></TR>
<%
            }
        }
    }
    bgcolor = "odd";     %>
</tbody></TABLE>