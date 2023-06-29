<%@ page import="com.owd.web.internal.warehouse.admin.servlet.ManualShipperServlet,
                 com.owd.core.business.order.OrderStatus,
                 com.owd.core.business.order.Package,
                 com.owd.hibernate.HibernateSession" %>
<%@ page import="com.owd.web.internal.navigation.UIUtilities" %>
<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus" %>
<%
    String shipmentType = (String) request.getAttribute("shipmentType");
    boolean isLTL = (shipmentType.indexOf("LTL") >= 0);

    boolean isPickup = (shipmentType.indexOf("Pick") >= 0);
    OrderStatus order = (OrderStatus) request.getAttribute("orderstatus");

    String error = (String) request.getAttribute("errormessage");

    String currentLocation = WarehouseStatus.getCurrentLocation(request);

    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, currentLocation+" Manual Shipment Entry for Order " + order.orderReference, request);
    try {


%>


<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>
    <style>
        span.pagelinks {
            border: none;
        }
    </style>

<div id="pagecommands" style="padding-top: 8px;">
    <h3>
        <a class="admin" href="/internal/warehouse/admin/shipments">Cancel and go back</a>

    </h3>
</div>

<font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
</font>
<%


%>
<P>
    Current Shipments:<BR>
    <TABLE cellpadding=3 border=1><TR><TH>Method</TH><TH>Shipment Reference</TH><TH>Weight</TH><TH>Cost</TH><TH>Ship
        Date</TH></TR>

        <%
            java.text.DecimalFormat decform = new java.text.DecimalFormat("$#,###,##0.00");

            for (int i = 0; i < order.packages.size(); i++) {
                Package pkg = (Package) order.packages.elementAt(i);
        %>
        <TR><TD><%=order.shipping.carr_service%></TD><TD><%=pkg.tracking_no %></TD><TD><%= pkg.weight%>&nbsp;lbs</TD>
            <TD><%=decform.format(pkg.total_billed)%></TD><TD><%= pkg.ship_date %></TD></TR>
        <%
            }
        %>
    </TABLE>
    If more than one package is in the shipment and you have information for all packages, <BR>enter each package
    individually. Otherwise, make one entry for the entire shipment.
    <HR>

    <FORM id="dateform" ACTION="manualship" METHOD=POST>
        <INPUT TYPE=HIDDEN NAME=orderid VALUE=<%= order.order_id%>>
        <TABLE cellpadding=3 border=0>
            <% if (!isPickup) { %>
            <TR>
                <TD ALIGN=RIGHT NOWRAP><B><%= isLTL ? "BOL Number:" : "Tracking/Confirmation Number:"%></B></TD>
                <TD ALIGN=LEFT><INPUT TYPE=TEXT SIZE=50 NAME="tracker">
                    <BR><FONT
                        size=-2><%= isLTL ? "Bill of Lading number or equivalent shipment identifier" : "Tracking or confirmation number, if available"%></font><br>
                    DO NOT INCLUDE ANY WORDS OR #. JUST THE PRO/LADING NUMBER.
                </TD>
            </TR>
            <% if (isLTL) {%>
            <TR>
                <TD ALIGN=RIGHT NOWRAP><B>Carrier:</B></TD>
                <TD ALIGN=LEFT><INPUT type="TEXT" size="50" NAME=LTLCarrier/>

                    <BR><FONT size=-2>Enter carrier responsible for shipment.</font></TD>
            </TR>
            <TR>
                <TD ALIGN=RIGHT NOWRAP><B>SCAC:</B></TD>
                <TD ALIGN=LEFT><INPUT type="TEXT" size="50" NAME=LTLSCAC/>

                    <BR><FONT size=-2>Enter carrier Alpha code for shipment. Should be 4 letters.</font></TD>
            </TR>
            <TR>
                <TD ALIGN=RIGHT NOWRAP><B>Pallets:</B></TD>
                <TD ALIGN=LEFT><INPUT type="TEXT" size="50" NAME=pallets/>

                    <BR><FONT size=-2>Enter number of pallets for shipment.</font></TD>
            </TR>
            <% }
            } %>
            <TR>
                <TD ALIGN=RIGHT NOWRAP><B>Weight:</B></TD>
                <TD ALIGN=LEFT><INPUT TYPE=TEXT SIZE=50 NAME="weight">
                    <BR><FONT size=-2>Weight of shipment in decimal pounds (e.g., "1.2")</font></TD>
            </TR>
            <TR>
                <TD ALIGN=RIGHT NOWRAP><B>Billed Cost:</B></TD>
                <TD ALIGN=LEFT><INPUT TYPE=TEXT SIZE=50 NAME="billed">
                    <BR><FONT size=-2>Cost of shipment billed to OWD client (leave as 0 if shipment not billed to
                    OWD)</font></TD>
            </TR>
            <TR>
                <TD ALIGN=RIGHT NOWRAP><B>Actual Cost:</B></TD>
                <TD ALIGN=LEFT><INPUT TYPE=TEXT SIZE=50 NAME="cost">
                    <BR><FONT size=-2>Cost of shipment as stated (leave as 0 if you don't know or if it is the same as
                    the Billed Cost)</font></TD>
            </TR>
            <TR>
                <TD ALIGN=RIGHT NOWRAP><B>Date of Shipment:</B></TD>
                <TD ALIGN=LEFT><input name="shipdate" value="" size="11" onfocus="this.blur()" readonly><a
                        href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.shipdate);return false;"
                        HIDEFOCUS><img name="popcal" align="absbottom" src="/internal/images/calbtn.gif" width="34"
                                       height="22" border="0" alt=""></A>
                    <BR><FONT size=-2>(Click on button on right to pick the date)</font></TD>
            </TR>
        </TABLE>
        <INPUT TYPE="HIDDEN" NAME="location" VALUE="<%= currentLocation%>">
        <INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Save Shipment Data">
    </FORM>


    <jsp:include page="/includes/owdbottom.jsp"/>

    <!--  PopCalendar(tag name and id must match) Tags should sit at the page bottom -->
    <iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js"
            src="/internal/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0"
            style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
        <LAYER name="gToday:outlook:agenda.js" src="/internal/popcal/npopeng.htm"></LAYER>
    </iframe>
</BODY></HTML>
<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>

