<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.text.DecimalFormat,
                 java.util.*,
                 java.text.DateFormat,
                 com.owd.core.managers.ConnectionManager,
                 java.text.SimpleDateFormat,
                 java.sql.ResultSet,
                 com.owd.web.internal.navigation.*,
                 org.hibernate.Session,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 com.owd.core.OWDUtilities" %>
<%@ page import="com.owd.hibernate.*" %>
<%@ page import="com.owd.hibernate.generated.Receive" %>
<%@ page import="com.owd.hibernate.generated.ReceiveItem" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%

    String currentLocation = WarehouseStatus.getCurrentLocation(request);

    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, currentLocation+" Pending Receives", request);
    //request.getSession(true).setAttribute("ordershiphold.currshiphold",null);
    if (request.getParameter("deleteId") != null) {

        try {
            Receive receive = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(request.getParameter("deleteId")));
            if (receive.getCreatedBy().equals("PENDING")) {
                System.out.println(receive.getAsn().getId());
                for (ReceiveItem item : receive.getReceiveItems()) {
                    HibernateSession.currentSession().delete(item);

                }
                HibernateSession.currentSession().delete(receive);
                HibUtils.commit(HibernateSession.currentSession());
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

%>


<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>

<div id="pagecommands" style="padding-top: 8px;">
    <h3>

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

%>

<TABLE border=0><TR><TD width="33%" valign="top">
    <%

        try {

            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select r.id as 'id',a.id as 'ASN Id', company_name as 'Client',created_on as 'Created',client_ref+'/PO:'+client_po+'/'+shipper_name as 'ASN/PO/Shipper' \n" +
"from receive r join owd_client on client_id=client_fkey join asn a on a.id=asn_fkey\n" +
" where r.created_by='PENDING' and (owd_client.default_facility_code='"+currentLocation+"' or (owd_client.default_facility_code='ALL' and r.facility_code='"+currentLocation+"'))");

            int cols = rs.getMetaData().getColumnCount();
            List colList = new ArrayList();

            for (int i = 1; i <= cols; i++) {
                colList.add(rs.getMetaData().getColumnName(i));
            }

            System.out.println("getting dynaset");
            RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
            System.out.println("got dynaset");
            request.setAttribute("reportrs1c", displayrsc);
            request.setAttribute("reportrsc-column-list1", colList);


    %>
    <div class="CenterTableTitle">Pending Receives (Entered via HH)</div>
    <display:table id="table1" cellspacing="0" name="requestScope.reportrs1c.rows" decorator="com.owd.web.internal.displaytag.PendingReceiveTableDecorator" sort="list" class="its" pagesize="20"
                   export="true" >
        <%
            Iterator itcols = ((List) request.getAttribute("reportrsc-column-list1")).iterator();
            while (itcols.hasNext()) {
                String colName = (String) itcols.next();


                if (colName.equals("id")) {
        %><display:column href="http://service.owd.com/webapps/warehouse/asn/edit?act=receive-edit" paramId="pendingrcvid" property="<%= colName%>" title="Verify/Post"
                          sortable="true"/><%
    } else {

    %><display:column property="<%= colName%>" title="<%= colName %>" sortable="true"/><%
            }
        }


    %>

        <display:column property="scans" title="Scans" sortable="true"/>
        <display:column property="delete" title="Delete Pending Receive"/>
        <display:setProperty name="basic.empty.showtable" value="true"/>
        <display:setProperty name="paging.banner.placement" value="bottom"/>
        <display:setProperty name="paging.banner.page.selected" value=""/>
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.no_items_found" value=""/>
        <display:setProperty name="paging.banner.one_item_found" value=""/>
    </display:table>
    <%

        } catch (Exception e) {
            throw e;
        } finally {
            HibernateSession.closeSession();
        }
    %>
</TD></TR></TABLE>


<P>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>
