<%@ page import="com.owd.core.OWDUtilities,
                 com.owd.core.managers.ConnectionManager,
                 com.owd.hibernate.HibernateFogbugzSession,
                 com.owd.hibernate.HibernateSession,
                 com.owd.web.internal.navigation.UIUtilities,
                 com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 org.hibernate.Session,
                 java.sql.ResultSet,
                 java.text.DateFormat" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.*" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%@ page import="com.owd.core.ConnectshipManifest" %>
<%@ page import="com.owd.core.ConnectshipTransmissionFile" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>
<%
    String currentLocation = WarehouseStatus.getCurrentLocation(request);
    request.setAttribute("currentLocation",currentLocation);



    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, currentLocation + " Home/Summary", request);
    request.getSession(true).setAttribute("ordershiphold.currshiphold", null);
%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<HEAD>
<tab:tabConfig />
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>

<div id="pagecommands" style="padding-top: 8px;">
    <h3>
        <a class="/internal/warehouse/admin/" href="index.jsp">Summary</a>
        &nbsp;|&nbsp;<a class="admin" href="/internal/warehouse/admin/detail.jsp?type=Client">Order Detail (By
        Client)</a>
        &nbsp;|&nbsp;<a class="admin" href="/internal/warehouse/admin/detail.jsp?type=Method">Order Detail (By
        Method)</a>
        &nbsp;|&nbsp;<a class="admin" href="/internal/warehouse/admin/detail.jsp?type=SLA">Order Detail (By
        Deadline)</a>

    </h3>
</div>
<style>
    span.pagelinks {
        border: none;
    }
</style>
<font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
</font>
<%

    try {
        WarehouseStatus status = null;
        Session sess = HibernateSession.currentSession();


        synchronized (ConnectionManager.getConnectionManager()) {
            if (WarehouseStatus.getCreationTime(currentLocation) < (Calendar.getInstance().getTime().getTime() - (.5 * 1000 * 60))) //5 minute interval
            {

                if (WarehouseStatus.getOldStatus(currentLocation) != null) {
                    WarehouseStatus.getOldStatus(currentLocation).setOldChart(null);
                    WarehouseStatus.getOldStatus(currentLocation).setOldChart2(null);
                }
                WarehouseStatus.setOldStatus(currentLocation,null);
                status = new WarehouseStatus(currentLocation);

            } else {
                status = WarehouseStatus.getOldStatus(currentLocation);

            }
        }

        List clientStatus = status.getClientStatusList();
        List methodStatus = status.getMethodStatusList();
        List slaStatus = status.getSlaStatusList();
        Map ordersWaitingToPrint = status.getOrdersWaitingToPrint();
        int ordersPosted = status.getPostedOrders();
        int ordersShipped = status.getShippedOrders();
        Date currentDate = status.getReferenceDate();
        Iterator clientIT = clientStatus.iterator();
        Iterator methodIT = methodStatus.iterator();
        Iterator methodSLA = slaStatus.iterator();
        String bgcolor = "#ffffff";
        DecimalFormat twoPlaceNumFormatter = new DecimalFormat("00");

        int detailTypeNum = 0;
        boolean showDetail = false;

        String isDetailRequested = request.getParameter("detailRequest");
        String detailType = request.getParameter("detailTypeNum");
        String detailValue = request.getParameter("detailValue");
        if ("1".equals(isDetailRequested)) {
            showDetail = true;
            detailTypeNum = new Integer(detailType).intValue();
        }

        String updateTime = DateFormat.getDateTimeInstance().format(new Date(WarehouseStatus.getCreationTime(currentLocation)));

        int shippedPct = (int) (100.0 * (((float) ordersShipped) / ((float) ordersPosted)));
        int packedPct = (int) (100.0 * (((float) status.getPackedUnshippedOrders()) / ((float) ordersPosted)));
        int pickedPct = (int) (100.0 * (((float) status.getPickedUnpackedOrders()) / ((float) ordersPosted)));
        int unshippedPct = (int) (100.0 * (((float) ordersPosted - ordersShipped) / ((float) ordersPosted)));
%>
<CENTER><B>Last Updated: <%= updateTime %></B></CENTER>
<TABLE border=0><TR><TD width="200" valign="top">
    <HR>

    <FORM method="POST" action="/internal/warehouse/admin/holds/sethold.jsp"><CENTER><H2>Report Problem Order</H2><BR>
        <B>OWD&nbsp;Reference:&nbsp;</B><INPUT TYPE=TEXT NAME=owdref VALUE="" size=15>

        <P><CENTER><INPUT TYPE=SUBMIT NAME="GO" VALUE="GO"></CENTER>

        <P></FORM>
    <HR>

    <div class="latemilestones" style="display:block;">
        <h1>Shipping Holds</h1>
        <ul>

            <%
                System.out.println("HELLOOOOOO");
                ResultSet rssh = HibernateSession.getResultSet("select company_name as 'Client' from owd_client (NOLOCK) where is_on_shipping_hold=1 and is_active=1 and default_facility_code='"+currentLocation+"'");
                   System.out.println("hi") ;

                while (rssh.next()) {

            %> <li><%= rssh.getString(1) %></li>
            <%
                   System.out.println("Heeee");
                }
                HibernateSession.closeSession();
            %>
        </ul>
    </div>
    <TABLE WIDTH=100%><TR><TD ALIGN=CENTER COLSPAN=2 VALIGN=TOP NOWRAP>
        <B>Orders in Print Queue:</B></TD></TR><tr><TD ALIGN=RIGHT VALIGN=TOP COLSPAN=2 NOWRAP><%

        Iterator it = ordersWaitingToPrint.keySet().iterator();
        System.out.println("hohoho");
        while(it.hasNext())
        {
            String printerStr= (String)it.next();
            System.out.println(printerStr);

            %><b><%= printerStr.replaceAll(" ","&nbsp;") %>&nbsp;-&nbsp;<%= ordersWaitingToPrint.get(printerStr) %></b><br><%
        }System.out.println("not it") ;
    %></TD></tr>
        <TR>
            <TD ALIGN=LEFT VALIGN=TOP NOWRAP></TD><TD ALIGN=RIGHT><B>(Orders/Lines/Units)</B></TD></TR><TR>
        <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Posted to Ship:</B></TD><TD ALIGN=RIGHT><%= ordersPosted %>
        /<%= status.getPostedOrdersLines() %>/<%= status.getPostedOrdersUnits() %></TD></TR><TR>
        <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Unshipped:</B></TD><TD ALIGN=RIGHT><%= ordersPosted - ordersShipped %>
        /<%= status.getPostedOrdersLines() - status.getShippedOrdersLines() %>
        /<%= status.getPostedOrdersUnits() - status.getShippedOrdersUnits() %></TD></TR><TR>
        <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Picked(Unpacked):</B></TD><TD
            ALIGN=RIGHT><%= status.getPickedUnpackedOrders() %>
        /<%= status.getPickedOrdersLines() - status.getPackedOrdersLines()%>
        /<%= status.getPickedOrdersUnits() - status.getPackedOrdersUnits() %></TD></TR><TR><TR>
        <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Packed(Unshipped):</B></TD><TD
            ALIGN=RIGHT><%= status.getPackedUnshippedOrders() %>
        /<%= status.getPackedOrdersLines() - status.getShippedOrdersLines()%>
        /<%= status.getPackedOrdersUnits() - status.getShippedOrdersUnits() %></TD></TR><TR>
        <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Shipped:</B></TD><TD ALIGN=RIGHT><%= ordersShipped %>
        /<%= status.getShippedOrdersLines() %>/<%= status.getShippedOrdersUnits() %>

    </TD></TR></TABLE>

    <div>
        <hr>
        <br>
        <b>Manifest info
        <a href="/internal/warehouse/admin/manifestInfo.jsp?location=<%= currentLocation%>" target="blank" >View Open and Un-Transmitted</a>:</b>
        <br/>
        <br/>
        <hr>
    </div>


    <A HREF="/internal/warehouse/admin/charts.jsp" target="_chartwindow"><IMG
            SRC="/internal/warehouse/admin/orderHistoryChart.jsp" width="200" height="125" border=0><BR>
        <IMG SRC="/internal/warehouse/admin/orderPickHistoryChart.jsp" width="200" height="125"border=0><BR>
        <IMG SRC="/internal/warehouse/admin/orderShipHistoryChart.jsp" width="200" height="125"border=0>
    </A>
</TD>
<TD width=99% valign=top align=center>


<%

    try {

        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select distinct [OWD Ref],[Client],[Ship Method],[Posted],[Picked],[SLA Deadline],[Packed] from vw_unshipped_late_orders where is_on_shipping_hold=0 and [On Hold?]='' and is_shipping = 0 " +
                " and ISNULL(location,'DC1')='"+currentLocation+"'");

        RowSetDynaClass displayrs1 = new RowSetDynaClass(rs, false);
        request.setAttribute("reportrs1", displayrs1);


         rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select distinct [OWD Ref],[Client],[Ship Method],[Posted],[Picked],[SLA Deadline],[Packed] from vw_unshipped_late_orders_business where is_on_shipping_hold=0 and [On Hold?]='' and is_shipping = 0 " +
                " and ISNULL(location,'DC1')='"+currentLocation+"'");

        RowSetDynaClass displayrsBusiness = new RowSetDynaClass(rs, false);
        request.setAttribute("reportrsBusiness", displayrsBusiness);

        rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select order_num as [OWD Ref],Case \n" +
                "    when len(group_name) > 0\n" +
                "    then\n" +
                "    company_name +'-'+group_name\n" +
                "    else\n" +
                "    company_name\n" +
                "    end as [Client],carr_service as [Ship Method],post_date as [Posted],convert(varchar(255),wh_hold_notes) as [Notes], \n" +
                " '<B>'+wh_hold_reason+'</B><P><FORM METHOD=POST ACTION=/internal/warehouse/admin/holds/resolvehold.jsp><INPUT TYPE=HIDDEN NAME=owdref VALUE='+order_num+'><INPUT TYPE=SUBMIT NAME=\"Resolve Hold\" VALUE=\"Resolve Hold\"></FORM>' \n" +
                "  as 'Hold Status', order_status as 'Order Status', license_plate \n" +
                "from owd_order o (NOLOCK)\n" +
                " join owd_client c (NOLOCK) on c.client_id=client_fkey and c.is_active=1 join owd_order_ship_info s (NOLOCK) \n" +
                "on s.order_fkey=order_id join owd_order_ship_holds h (NOLOCK) on h.order_fkey=order_id and is_on_wh_hold=1 and needs_review=0 and ISNULL(o.facility_code,'DC1')='"+currentLocation+"'");


        RowSetDynaClass displayrs2 = new RowSetDynaClass(rs, false);
        request.setAttribute("reportrs2", displayrs2);

        rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select order_num as [OWD Ref],Case \n" +
                "    when len(group_name) > 0\n" +
                "    then\n" +
                "    company_name +'-'+group_name\n" +
                "    else\n" +
                "    company_name\n" +
                "    end as [Client],carr_service as [Ship Method],post_date as [Posted], \n" +
                "'<B>'+wh_hold_reason+'</B><P><FORM METHOD=POST ACTION=/internal/warehouse/admin/holds/resolvehold.jsp><INPUT TYPE=HIDDEN NAME=owdref VALUE='+order_num+'><INPUT TYPE=SUBMIT NAME=\"Resolve Hold\" VALUE=\"Resolve Hold\"></FORM>' \n" +
                "  as 'Hold Status',license_plate \n" +
                "from owd_order o (NOLOCK)\n" +
                " join owd_client c (NOLOCK) on c.client_id=client_fkey and c.is_active=1 join owd_order_ship_info s (NOLOCK) \n" +
                "on s.order_fkey=order_id join owd_order_ship_holds h (NOLOCK) on h.order_fkey=order_id and is_on_wh_hold=1 and needs_review=1 and ISNULL(o.facility_code,'DC1')='"+currentLocation+"'");


        RowSetDynaClass displayrs2p = new RowSetDynaClass(rs, false);
        request.setAttribute("reportrs2p", displayrs2p);

        rs = HibernateSession.getResultSet("select picker_name as 'Picker',max(ops.order_num) as 'Order',max(company_name) as 'Client',convert(varchar,max(current_item_index)+1)+' of '+convert(varchar,count(*)) as 'Status' from orderpickstatus ops (NOLOCK) \n" +
                "join order_pick_item (NOLOCK) on order_pick_fkey=ops.id\n" +
                "join owd_order o (NOLOCK) join owd_client c (NOLOCK) on client_id=client_fkey and c.is_active=1 on ops.order_id = o.order_id\n" +
                " where ISNULL(o.facility_code,'DC1')='"+currentLocation+"' group by picker_name\n" +
                "order by picker_name");


        RowSetDynaClass displayrs3 = new RowSetDynaClass(rs, false);
        request.setAttribute("reportrs3", displayrs3);

        rs = HibernateSession.getResultSet("select ph.pick_by as 'Picker', oc.company_name as 'Client' , count(distinct(oo.order_id)) as 'Orders', count(*) as 'Lines',sum(units_picked) as 'Units' from order_pick_history ph (NOLOCK) , \n" +
                "                            owd_order oo (NOLOCK), owd_client oc (NOLOCK), order_pick_item_history pi (NOLOCK)  where pi.pick_history_id=ph.id and ph.order_id = oo.order_id and oo.client_fkey = oc.client_id and oc.is_active=1 and \n" +
                "            ph.start_pick_time > '" + OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, -1, false) + " 20:00:00' and ISNULL(oo.facility_code,'DC1')='"+currentLocation+"'" +
                " group by ph.pick_by, oc.company_name order by ph.pick_by");


        RowSetDynaClass displayrs4 = new RowSetDynaClass(rs, false);
        request.setAttribute("reportrs4", displayrs4);

        String casetrackerSql = "select  case when sCustomerEmail<>'' then sCustomerEmail else (select sFullName from person where ixPerson=ixPersonOpenedBy) end as 'Opened By'\n" +
                "                                ,(SELECT SWITCHOFFSET(TODATETIMEOFFSET(dtdue, '+00:00'),DATEpart(TZOFFSET , SYSDATETIMEOFFSET())+("+ FacilitiesManager.getTimezoneHoursOffsetForFacilityCode(currentLocation)+"*60))  ) as 'Opened', \n" +
                "                (SELECT SWITCHOFFSET(TODATETIMEOFFSET(dtdue, '+00:00'),DATEpart(TZOFFSET , SYSDATETIMEOFFSET())+("+ FacilitiesManager.getTimezoneHoursOffsetForFacilityCode(currentLocation)+"*60))  ) as Due, \n" +
                "                stitle as 'Title',\n" +
                "                                                        p.sFullName as 'Assigned To',ISNULL(sName,'Not Set') as Client,\n" +
                "                                                        datediff(day,dtOpened,getdate()) as 'Days Open' \n" +
                "                                                        ,hrsOrigEst as 'Estimated Hours',\n" +
                "                                                        hrsElapsed as 'Elapsed Hours',\n" +
                "                                                ixBug  from bug b\n" +
                "                                                join category c on c.ixCategory=b.ixCategory \n" +
                "                                                join person p on p.ixPerson=b.ixPersonAssignedTo \n" +
                "                                                join status s on s.ixStatus=b.ixStatus \n" +
                "                                                join area a on a.ixArea=b.ixArea \n" +
                "                                                join project pro\n" +
                "                                                    left join acl  join PermissionGroup pg  on pg.ixPermissionGroup=acl.ixPermissionGroup\n" +
                "                                                    on acl.ixProject=pro.ixProject and acl.ixpermissiongroup>0\n" +
                "                                                on pro.ixProject=b.ixProject \n" +
                "                                                where fopen=1  and sStatus='Active'";


              casetrackerSql = casetrackerSql+" and p.sFullName like '%"+currentLocation+" Work Orders%' ";

        casetrackerSql = casetrackerSql+"order by dtDue asc  " ;
        System.out.println(casetrackerSql);
        rs = HibernateSession.getResultSet(HibernateFogbugzSession.currentSession(), casetrackerSql);

        RowSetDynaClass displayrsworkorders = new RowSetDynaClass(rs, false);
        request.setAttribute("displayrsworkorders", displayrsworkorders);

    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
    }
%>
<tab:tabContainer id="foo-bar-container">
<tab:tabPane id="LateUnshipped" tabTitle="Late Unshipped">
<div class="CenterTableTitle">Late Unshipped Orders</div>
<display:table id="table1" cellspacing="0" name="requestScope.reportrs1.rows" sort="list" class="its" pagesize="30"
               export="false" decorator="com.owd.web.internal.displaytag.UnshippedOrdersTableDecorator">
  <display:column property="printStatusLink" title="OWD Ref" sortable="true"/>
 <display:column property="Client" title="Client" sortable="true"/>
<display:column property="Ship Method" title="Ship Method" sortable="true"/>

<display:column property="Posted" title="Posted"
                  decorator="com.owd.web.internal.displaytag.StandardDateTimeDecorator" sortable="true"/>
                  <display:column property="Picked" title="Picked" sortable="true"/>
                  <display:column property="Packed" title="Packed" sortable="true"/>
<display:column property="SLA Deadline" title="SLA Deadline"
                  decorator="com.owd.web.internal.displaytag.StandardDateTimeDecorator" sortable="true"/>

    <display:setProperty name="basic.empty.showtable" value="true"/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
</display:table>
</tab:tabPane>

    <tab:tabPane id="LateUnshippedBusiness" tabTitle="Late Unshipped Business">
        <div class="CenterTableTitle">Late Unshipped Orders</div>
        <display:table id="table1" cellspacing="0" name="requestScope.reportrsBusiness.rows" sort="list" class="its" pagesize="30"
                       export="false" decorator="com.owd.web.internal.displaytag.UnshippedOrdersTableDecorator">
            <display:column property="printStatusLink" title="OWD Ref" sortable="true"/>
            <display:column property="Client" title="Client" sortable="true"/>
            <display:column property="Ship Method" title="Ship Method" sortable="true"/>

            <display:column property="Posted" title="Posted"
                            decorator="com.owd.web.internal.displaytag.StandardDateTimeDecorator" sortable="true"/>
            <display:column property="Picked" title="Picked" sortable="true"/>
            <display:column property="Packed" title="Packed" sortable="true"/>
            <display:column property="SLA Deadline" title="SLA Deadline"
                            decorator="com.owd.web.internal.displaytag.StandardDateTimeDecorator" sortable="true"/>

            <display:setProperty name="basic.empty.showtable" value="true"/>
            <display:setProperty name="paging.banner.placement" value="bottom"/>
            <display:setProperty name="paging.banner.page.selected" value=""/>
            <display:setProperty name="paging.banner.all_items_found" value=""/>
            <display:setProperty name="paging.banner.no_items_found" value=""/>
            <display:setProperty name="paging.banner.one_item_found" value=""/>
        </display:table>


 </tab:tabPane><tab:tabPane id="DCReviewHolds" tabTitle="Pending Holds"><div class="CenterTableTitle">DC Holds Pending Review</div>
<display:table id="table2p" cellspacing="0" name="requestScope.reportrs2p.rows" sort="list" class="its" pagesize="30"
               export="false" decorator="com.owd.web.internal.displaytag.UnshippedOrdersTableDecorator">

    <display:column property="orderLink" title="Link"/>

    <display:column property="OWD Ref" title="OWD Ref" sortable="true"/>

 <display:column property="Client" title="Client" sortable="true"/>
<display:column property="Ship Method" title="Ship Method" sortable="true"/>

<display:column property="Posted" title="Posted"
                  decorator="com.owd.web.internal.displaytag.StandardDateTimeDecorator" sortable="true"/>

<display:column property="Hold Status" title="Hold Status" sortable="true"/>
    <display:column property="license_plate" title="T" sortable="true"/>



    <display:setProperty name="basic.empty.showtable" value="true"/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
</display:table>
 </tab:tabPane><tab:tabPane id="DCHolds" tabTitle="DC Holds"><div class="CenterTableTitle">DC Holds</div>
<display:table id="table2" cellspacing="0" name="requestScope.reportrs2.rows" sort="list" class="its" pagesize="30"
               export="false" decorator="com.owd.web.internal.displaytag.UnshippedOrdersTableDecorator">

    <display:column property="orderLink" title="Link"/>

    <display:column property="OWD Ref" title="OWD Ref" sortable="true"/>

 <display:column property="Client" title="Client" sortable="true"/>
<display:column property="Ship Method" title="Ship Method" sortable="true"/>

<display:column property="Posted" title="Posted"
                  decorator="com.owd.web.internal.displaytag.StandardDateTimeDecorator" sortable="true"/>

<display:column property="Hold Status" title="Hold Status" sortable="true"/>
    <display:column property="Order Status" title="Order Status" sortable="true"/>
    <display:column property="Notes" title="Notes" sortable="false"/>
    <display:column property="license_plate" title="T" sortable="true"/>



    <display:setProperty name="basic.empty.showtable" value="true"/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
</display:table>
 </tab:tabPane><tab:tabPane id="CurrentPicking" tabTitle="Current Picking"><div class="CenterTableTitle">Current Picking Activities</div>
<display:table id="table3" cellspacing="0" name="requestScope.reportrs3.rows" sort="list" class="its" pagesize="30"
               export="false">

 <display:column property="Picker" title="Picker" sortable="true"/>

 <display:column property="Order" title="Order" sortable="true"/>

 <display:column property="Client" title="Client" sortable="true"/>

 <display:column property="Status" title="Status" sortable="true"/>

<display:setProperty name="basic.empty.showtable" value="true"/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.some_items_found" value=""/>
</display:table>

 </tab:tabPane><tab:tabPane id="PickingHistory" tabTitle="Picking History">
<div class="CenterTableTitle">Current Picker History (since 8 PM previous day)</div>

<display:table id="table4" cellspacing="0" name="requestScope.reportrs4.rows" sort="list" class="its" defaultsort="1"
               pagesize="30" export="false">

    <display:column property="Picker" title="Picker" group="1" sortable="true"/>

    <display:column property="Client" title="Client" sortable="true"/>
    
    <display:column property="Orders" title="Orders" sortable="true"/>
    <display:column property="Lines" title="Lines" sortable="true"/>
    <display:column property="Units" title="Units" sortable="true"/>

                <display:setProperty name="basic.empty.showtable" value="true"/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.some_items_found" value=""/>
</display:table>
</tab:tabPane>
<tab:tabPane id="WorkOrderStatus" tabTitle="Work Order Status">
<div class="CenterTableTitle">Pending Work Orders</div>

<display:table id="table5" cellspacing="0" name="requestScope.displayrsworkorders.rows" sort="list" class="its" pagesize="50"
               decorator="com.owd.web.internal.displaytag.WorkOrderTableDecorator">

    <display:column property="Due" title="Due" sortable="true"/>
    <display:column property="Client" sortable="true" title="Client"/>
    <display:column property="Title" sortable="true" title="Title"/>
    <display:column property="Assigned To" sortable="true" title="Assigned To"/>
    <display:column property="Days Open" sortable="true" title="Days Old"/>
    <display:column property="Estimated Hours" sortable="true" title="Estimate"/>
    <display:column property="Opened By" title="From" sortable="true"/>
    <display:column property="Opened" title="Opened" sortable="true"/>
    <display:column property="Elapsed Hours" sortable="true" title="Elapsed"/>
        <display:column property="bugLink" title="Details"/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.onepage" value=""/>
</display:table>
</tab:tabPane>
    <tab:tabPane id="PickPackStatus" tabTitle="Productivity Today">
        <div>
        <a href="/internal/warehouse/admin/productivityInfo.jsp?location=<c:out value="${currentLocation}"></c:out> " target="blank" >View Productivity info</a>
    </div>
    </tab:tabPane>
</tab:tabContainer>
</TD></TR></TABLE>


<P>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>
