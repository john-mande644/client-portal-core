<%--
  Created by IntelliJ IDEA.
  User: stewartbuskirk
  Date: Jul 28, 2005
  Time: 10:29:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Map,
                 java.util.List,
                 com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.util.Iterator" %>
<%


    String bgcolor = "odd";
    List methodStatus = (List) request.getAttribute("methodStatus");
    Iterator methodIT = methodStatus.iterator();
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
%><HR><CENTER><H2>Current Shipping Order Status (By Method)</H2></CENTER>
    <TABLE cellspacing=0 cellpadding=3 width="100%" class="its">
        <thead><TR><TH ALIGN=LEFT>Method</TH><TH ALIGN=LEFT>Posted</TH><TH ALIGN=LEFT>Picked</TH><TH ALIGN=LEFT>Packed</TH><TH ALIGN=LEFT>
            Shipped</TH><TH ALIGN=LEFT>Waiting to Ship</TH></TR>
        </thead><tbody><% while (methodIT.hasNext()) {
        Map info = (Map) methodIT.next();
        if (bgcolor.equals("odd")) {
            bgcolor = "even";
        } else {
            bgcolor = "odd";
        }
    %>
        <TR class="<%= bgcolor %>">
            <%
                List orderListx = status.getOrderInfoList(WarehouseStatus.kMethodOrderList, (String) info.get("METHOD"));
                if (orderListx.size() > 0) {
                    Iterator orderIT = orderListx.iterator();


            %><TD NOWRAP><A
                HREF="detail.jsp?type=Method&detailRequest=1&detailTypeNum=<%= WarehouseStatus.kMethodOrderList%>&detailValue=<%= info.get("METHOD") %>"><%= info.get("METHOD") %></A>
        </TD>
            <%


                }
            %>
            <TD><%= info.get("POST") %></TD>
            <TD><%= info.get("PICKEDUNPACKED") %></TD>
            <TD><%= info.get("PACKEDUNSHIPPED") %></TD>
            <TD><%= info.get("SHIP") %></TD>
            <TD><%= info.get("UNSHIP") %></TD>
        </TR>
        <%
            if (showDetail && (info.get("METHOD").equals(detailValue))) {
                List orderList = status.getOrderInfoList(detailTypeNum, detailValue);
                if (orderList.size() > 0) {
                    Iterator orderITt = orderList.iterator();
                    String subcolor = "odd";
        %>
        <TR><TD></TD><TD COLSPAN=4>
            <TABLE WIDTH=100% border=0 class="its">
                <thead><TR><TH>OWD Ref</TH><TH>Client</TH><TH>Country</TH><TH>Posted</TH><TH>Customer</TH><TH>
                    Method</TH><TH>Status</TH><TH>SLA</TH><TH>On Hold</TH></TR>
                </thead><tbody><%
			 while (orderITt.hasNext())
			 {
				 Map orderMap = (Map) orderITt.next();
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
            <%=orderMap.get("shipped").equals("1") ? "<font color=green>SHIPPED</font>" :
                orderMap.get("packed").equals("1") ? "<font color=blue>PACKED</font>" :
                        orderMap.get("picked").equals("1")?"<font color=purple>PICKED</font>":
                                "<font color=red>NOT PICKED</font>"


            %></TD>

            <TD><%=orderMap.get("sla")%></TD>

            <TD><%=orderMap.get("on_hold")%></TD>
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
    bgcolor = "odd";

%>
</tbody></TABLE></html>