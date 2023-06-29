<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.text.DecimalFormat,
                 java.util.*,
                 java.text.DateFormat,
                 com.owd.core.managers.ConnectionManager,
                 java.text.SimpleDateFormat,
                 java.sql.ResultSet,
                 com.owd.web.internal.navigation.*,
                 com.owd.hibernate.HibernateSession,
                 org.hibernate.Session,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 com.owd.core.OWDUtilities" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Inventory Counts", request);
    //request.getSession(true).setAttribute("ordershiphold.currshiphold",null);

    String currCountID = (String) request.getParameter("currCountID");
    String docountpost = (String) request.getParameter("docountpost");

    if (currCountID != null && docountpost != null) {

        //do post


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
    Count Request Status
    <HR>
    <%

        try {

            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select w_inv_request.id as 'Link',company_name as 'Client',w_inv_request.description as 'Notes',w_inv_request.date_created as 'Created',\n" +
                    "w_inv_request.done as 'Posted',(100*(sum(w_inv_locations.done)))/count(*) as 'Complete' from w_inv_request \n" +
                    "join owd_client on client_id=client_fkey join w_inv_locations on w_inv_request.id=inv_request_fkey\n" +
                    "where inventory_num is null\n" +
                    "group by company_name,w_inv_request.id,w_inv_request.description,w_inv_request.date_created,\n" +
                    "w_inv_request.done order by Link desc");
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
    <div class="CenterTableTitle">Inventory Counts</div>
    <display:table id="table1" cellspacing="0" name="requestScope.reportrs1c.rows"  class="its" pagesize="10"
                   export="false" decorator="com.owd.web.internal.displaytag.UnshippedOrdersTableDecorator">
        <%
            Iterator itcols = ((List) request.getAttribute("reportrsc-column-list1")).iterator();
            while (itcols.hasNext()) {
                String colName = (String) itcols.next();


                if (colName.equals("Link")) {
        %><display:column href="index.jsp" paramId="currCountID" property="<%= colName%>" title="<%= colName %>"
                          sortable="true"/><%
    } else {

    %><display:column property="<%= colName%>" title="<%= colName %>" sortable="true"/><%
            }
        }

    %>
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
</TD>
<TD width=99% valign=top align=center>
    Count Request Detail
    <HR>
    <% if (currCountID != null) {
    %>
    <%
        String CntClientName = "";
        String CntDescription = "";
        int CntPosted = 0;
        int CntComplete = 0;
        try {

            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),
                    "select company_name as 'Client',w_inv_request.description as 'Notes',\n" +
                            "w_inv_request.done as 'Posted',CASE WHEN sum(w_inv_locations.done) = count(*) THEN 1 ELSE 0 END as 'Complete' from w_inv_request \n" +
                            "join owd_client on client_id=client_fkey join w_inv_locations on w_inv_request.id=inv_request_fkey\n" +
                            "where inventory_num is null and w_inv_request.id=" + currCountID + "\n" +
                            "group by company_name,w_inv_request.id,w_inv_request.description,w_inv_request.date_created,\n" +
                            "w_inv_request.done");
            if (rs.next()) {
                CntClientName = rs.getString(1);
                CntDescription = rs.getString(2);
                CntPosted = rs.getInt(3);
                CntComplete = rs.getInt(4);


            }
            rs.close();

            String recountQuery="select stuff.*,ISNULL(l.location,'NO OLD') +':'+dbo.udf_getCurrentCountLocations(inventory_id,?) as 'Location',ISNULL(l.assign_date,'') as 'Last Assigned',ISNULL(assigned_by,'') as 'Location Source' from (\n" +
"(select inventory_id,inventory_num,description,[Current Adjustment Needed],unpicked,[on hand],counted from vw_wcountdata  where inv_request_fkey=?)  \n" +
"  union  \n" +
"  (\n" +
"select i.inventory_id,i.inventory_num,i.description,  -1*(ISNULL(postedunpicked,0)+qty_on_hand) as 'Current Adjustment Needed' ,\n" +
"ISNULL(postedunpicked,0) as Unpicked,  \n" +
"h.qty_on_hand as 'On Hand',  \n" +
"0 as Counted\n" +
"\n" +
"  from owd_inventory i (NOLOCK) join w_inv_request r (NOLOCK) on r.client_fkey=i.client_fkey and r.id=? and r.done=0 \n" +
"  INNER JOIN dbo.owd_inventory_oh h (NOLOCK) ON h.inventory_fkey = i.inventory_id    \n" +
"  LEFT OUTER JOIN  (SELECT  l.inventory_id AS 'iid', SUM(ISNULL(quantity_actual, 0)) AS postedunpicked, client_fkey    \n" +
"   FROM  \n" +
"  owd_line_item l (NOLOCK) JOIN  \n" +
"  owd_order (NOLOCK) ON order_id = l.order_fkey AND post_date >'2000-1-1' AND is_void = 0 AND   \n" +
"  ship_packs = 0 AND pick_status = 0   \n" +
"   GROUP BY l.inventory_id, client_fkey) unpicked ON i.inventory_id = unpicked.iid   \n" +
"  where i.inventory_id not in (select distinct c.inventory_id    \n" +
"  FROM w_inv_request r (NOLOCK)  \n" +
"   INNER JOIN dbo.w_inv_locations l (NOLOCK)  \n" +
"    INNER JOIN dbo.w_inv_counts c (NOLOCK)   \n" +
"  ON c.inv_loc_fkey = l.id   \n" +
"    on r.id=l.inv_request_fkey   \n" +
"  where r.done=0 and r.id=? and c.quanity>0) and qty_on_hand>0  and i.is_auto_inventory=0\n" +
"  )) as stuff left outer join owd_inventory_locations l on l.inventory_fkey=inventory_id and l.location <> 'UNKNOWN'\n" +
"where [Current Adjustment Needed]<>0 order by inventory_id, location";

            PreparedStatement ps4 = HibernateSession.getPreparedStatement(recountQuery);
                     ps4.setInt(1, Integer.parseInt(currCountID));
                     ps4.setInt(2, Integer.parseInt(currCountID));
                     ps4.setInt(3, Integer.parseInt(currCountID));
            ps4.setInt(4, Integer.parseInt(currCountID));
                     ResultSet rs4 = ps4.executeQuery();

                   //  rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select inventory_num as 'SKU',description as 'Description',counted as 'Counted'," +
                  //           "[On Hand],Unpicked,[Current Adjustment Needed] from vw_wcountdata where inv_request_fkey=" + currCountID);
                     int cols4 = rs4.getMetaData().getColumnCount();
                     List colList4 = new ArrayList();

                     for (int i = 1; i <= cols4; i++) {
                         colList4.add(rs4.getMetaData().getColumnName(i));
                     }

                     System.out.println("getting dynaset");
                     RowSetDynaClass displayrsc4 = new RowSetDynaClass(rs4, false);
                     System.out.println("got dynaset");
                     request.setAttribute("reportrs4c", displayrsc4);
                     request.setAttribute("reportrsc-column-list4", colList4);

             String checkQuery  =
                       "select * from (\n" +
                               "(select inventory_num,description,[Current Adjustment Needed],unpicked,[on hand],counted from vw_wcountdata  where inv_request_fkey=?)  \n" +
                               "  union  \n" +
                               "  (\n" +
                               "select i.inventory_num,i.description,  -1*(ISNULL(postedunpicked,0)+qty_on_hand) as 'Current Adjustment Needed' ,\n" +
                               "ISNULL(postedunpicked,0) as Unpicked,  \n" +
                               "h.qty_on_hand as 'On Hand',  \n" +
                               "0 as Counted\n" +
                               "\n" +
                               "  from owd_inventory i (NOLOCK) join w_inv_request r (NOLOCK) on r.client_fkey=i.client_fkey and r.id=?  and r.done=0\n" +
                               "  INNER JOIN dbo.owd_inventory_oh h (NOLOCK) ON h.inventory_fkey = i.inventory_id    \n" +
                               "  LEFT OUTER JOIN  (SELECT  l.inventory_id AS 'iid', SUM(ISNULL(quantity_actual, 0)) AS postedunpicked, client_fkey    \n" +
                               "   FROM  \n" +
                               "  owd_line_item l (NOLOCK) JOIN  \n" +
                               "  owd_order (NOLOCK) ON order_id = l.order_fkey AND post_date >'2000-1-1' AND is_void = 0 AND   \n" +
                               "  ship_packs = 0 AND pick_status = 0   \n" +
                               "   GROUP BY l.inventory_id, client_fkey) unpicked ON i.inventory_id = unpicked.iid   \n" +
                               "  where i.inventory_id not in (select distinct c.inventory_id    \n" +
                               "  FROM w_inv_request r (NOLOCK)  \n" +
                               "   INNER JOIN dbo.w_inv_locations l (NOLOCK)  \n" +
                               "    INNER JOIN dbo.w_inv_counts c (NOLOCK)   \n" +
                               "  ON c.inv_loc_fkey = l.id   \n" +
                               "    on r.id=l.inv_request_fkey and r.done=0   \n" +
                               "  where r.done=0 and r.id=? and c.quanity>0) and qty_on_hand>0 and i.is_auto_inventory=0 \n" +
                               "  )) as stuff where counted<unpicked";

            PreparedStatement ps3 = HibernateSession.getPreparedStatement(checkQuery);
            ps3.setInt(1, Integer.parseInt(currCountID));
            ps3.setInt(2, Integer.parseInt(currCountID));
            ps3.setInt(3, Integer.parseInt(currCountID));
            ResultSet rs3 = ps3.executeQuery();

          //  rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select inventory_num as 'SKU',description as 'Description',counted as 'Counted'," +
         //           "[On Hand],Unpicked,[Current Adjustment Needed] from vw_wcountdata where inv_request_fkey=" + currCountID);
            int cols3 = rs3.getMetaData().getColumnCount();
            List colList3 = new ArrayList();

            for (int i = 1; i <= cols3; i++) {
                colList3.add(rs3.getMetaData().getColumnName(i));
            }

            System.out.println("getting dynaset");
            RowSetDynaClass displayrsc3 = new RowSetDynaClass(rs3, false);
            System.out.println("got dynaset");
            request.setAttribute("reportrs3c", displayrsc3);
            request.setAttribute("reportrsc-column-list3", colList3);


            String countQuery = "(select inventory_num as 'SKU',description as 'Description',counted as 'Counted'," +
                    "[On Hand],Unpicked,[Current Adjustment Needed] from vw_wcountdata  where inv_request_fkey=?) \n" +
                    "union\n" +
                    "(select i.inventory_num as 'SKU',i.description as 'Description',0 as 'Counted'," +
                     "h.qty_on_hand as 'On Hand',\n" +
                     "ISNULL(postedunpicked,0) as Unpicked,\n" +
                    "-1*(ISNULL(postedunpicked,0)+qty_on_hand) as 'Current Adjustment Needed' \n" +
                    "from owd_inventory i (NOLOCK) join w_inv_request r (NOLOCK) on r.client_fkey=i.client_fkey and r.id=? and r.done=0\n" +
                    "INNER JOIN dbo.owd_inventory_oh h (NOLOCK) ON h.inventory_fkey = i.inventory_id  \n" +
                    "LEFT OUTER JOIN  (SELECT     l.inventory_id AS 'iid', SUM(ISNULL(quantity_actual, 0)) AS postedunpicked, client_fkey  \n" +
                    "                            FROM         \n" +
                    "                                                   owd_line_item l (NOLOCK) JOIN\n" +
                    "                                                   owd_order (NOLOCK) ON order_id = l.order_fkey AND post_date >'2000-1-1' AND is_void = 0 AND \n" +
                    "                                                   ship_packs = 0 AND pick_status = 0 \n" +
                    "                            GROUP BY l.inventory_id, client_fkey) unpicked ON i.inventory_id = unpicked.iid \n" +
                    "where i.inventory_id not in (select distinct c.inventory_id  \n" +
                    "FROM w_inv_request r (NOLOCK) \n" +
                    " INNER JOIN dbo.w_inv_locations l (NOLOCK) \n" +
                    " INNER JOIN    dbo.w_inv_counts c (NOLOCK) \n" +
                    " ON c.inv_loc_fkey = l.id \n" +
                    " on r.id=l.inv_request_fkey  \n" +
                    "where r.done=0 and r.id=? and c.quanity>0) and qty_on_hand>0 and is_auto_inventory=0 \n" +
                    ")";
            PreparedStatement ps = HibernateSession.getPreparedStatement(countQuery);
            ps.setInt(1, Integer.parseInt(currCountID));
            ps.setInt(2, Integer.parseInt(currCountID));
            ps.setInt(3, Integer.parseInt(currCountID));
            rs = ps.executeQuery();

          //  rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select inventory_num as 'SKU',description as 'Description',counted as 'Counted'," +
         //           "[On Hand],Unpicked,[Current Adjustment Needed] from vw_wcountdata where inv_request_fkey=" + currCountID);
            int cols = rs.getMetaData().getColumnCount();
            List colList = new ArrayList();

            for (int i = 1; i <= cols; i++) {
                colList.add(rs.getMetaData().getColumnName(i));
            }

            System.out.println("getting dynaset");
            RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
            System.out.println("got dynaset");
            request.setAttribute("reportrs2c", displayrsc);
            request.setAttribute("reportrsc-column-list2", colList);

            if (CntComplete == 1) {
                if (CntPosted == 0) {
    %>
    <FORM ACTION="index.jsp" METHOD=POST><INPUT TYPE=HIDDEN NAME="currCountID" VALUE="<%= currCountID %>"><INPUT
            TYPE=HIDDEN NAME="docountpost" VALUE="1"><INPUT TYPE=SUBMIT NAME="Post Adjustments"
                                                            VALUE="Post Adjustments"></FORM>
    <%
            }
        }%>
     <div class="CenterTableTitle">Recount Locations from Count <%= currCountID %> for <%= CntClientName %><BR><%= CntDescription %></div>
    <display:table id="table4" cellspacing="0" name="requestScope.reportrs4c.rows" class="its"
                   pagesize="10" export="true"
                   decorator="com.owd.web.internal.displaytag.UnshippedOrdersTableDecorator">
        <%
            Iterator itcols = ((List) request.getAttribute("reportrsc-column-list4")).iterator();
            while (itcols.hasNext()) {
                String colName = (String) itcols.next();
                if (colName.equals("Link")) {
        %><display:column href="index.jsp" paramId="currCountID" property="<%= colName%>" title="<%= colName %>"
                          sortable="true"/><%
    } else {

    %><display:column property="<%= colName%>" title="<%= colName %>" sortable="true"/><%
            }
        }

    %>
        <display:setProperty name="basic.empty.showtable" value="true"/>
        <display:setProperty name="paging.banner.placement" value="bottom"/>
        <display:setProperty name="paging.banner.page.selected" value=""/>
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.no_items_found" value=""/>
        <display:setProperty name="paging.banner.one_item_found" value=""/>
    </display:table><p> </p>
     <div class="CenterTableTitle">Unpickable Items from Count <%= currCountID %> for <%= CntClientName %><BR><%= CntDescription %></div>
    <display:table id="table3" cellspacing="0" name="requestScope.reportrs3c.rows" class="its"
                   pagesize="10" export="true"
                   decorator="com.owd.web.internal.displaytag.UnshippedOrdersTableDecorator">
        <%
            Iterator itcols = ((List) request.getAttribute("reportrsc-column-list3")).iterator();
            while (itcols.hasNext()) {
                String colName = (String) itcols.next();
                if (colName.equals("Link")) {
        %><display:column href="index.jsp" paramId="currCountID" property="<%= colName%>" title="<%= colName %>"
                          sortable="true"/><%
    } else {

    %><display:column property="<%= colName%>" title="<%= colName %>" sortable="true"/><%
            }
        }

    %>
        <display:setProperty name="basic.empty.showtable" value="true"/>
        <display:setProperty name="paging.banner.placement" value="bottom"/>
        <display:setProperty name="paging.banner.page.selected" value=""/>
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.no_items_found" value=""/>
        <display:setProperty name="paging.banner.one_item_found" value=""/>
    </display:table>
    <p> </p>
    <div class="CenterTableTitle">Count <%= currCountID %> for <%= CntClientName %><BR><%= CntDescription %></div>
    <display:table id="table2" cellspacing="0" name="requestScope.reportrs2c.rows" class="its"
                   pagesize="10" export="true"
                   decorator="com.owd.web.internal.displaytag.UnshippedOrdersTableDecorator">
        <%
            Iterator itcols = ((List) request.getAttribute("reportrsc-column-list2")).iterator();
            while (itcols.hasNext()) {
                String colName = (String) itcols.next();
                if (colName.equals("Link")) {
        %><display:column href="index.jsp" paramId="currCountID" property="<%= colName%>" title="<%= colName %>"
                          sortable="true"/><%
    } else {

    %><display:column property="<%= colName%>" title="<%= colName %>" sortable="true"/><%
            }
        }

    %>
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


    <% } %>
</TD></TR></TABLE>


<P>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>
