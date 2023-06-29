<%@ page import="java.util.Calendar,
                 org.hibernate.Session,
                 com.owd.hibernate.HibernateSession,
                 java.sql.ResultSet,
                 com.owd.web.internal.location.InventoryLocationDisplayData,
                 java.util.List,
                 java.util.ArrayList" %>
<%

    String id = request.getParameter("location");
    StringBuffer locationPath = new StringBuffer();

    String sql = "select locname,description,is_mobile from w_location l join w_location_type t on l.ixloctype=t.ixloctype and ixnode=" + request.getParameter("location");

    System.out.println("in detail.jsp, id=" + id);

    String locName = "";
    String locDescription = "";
    int locMobile = 0;
    List dataList = new ArrayList();

    try {
        Session sess = HibernateSession.currentSession();

        ResultSet rs = HibernateSession.getResultSet(sql);

        while (rs.next()) {
            locName = rs.getString(1);
            locDescription = rs.getString(2);
            locMobile = rs.getInt(3);
        }

        sql = "select p.ixnode,p.locname,ty.description\n" +
                "from Location n, LocationTree t, Location p, LocationType ty\n" +
                "where n.nodeid=" + id + "\n" +
                "\tand n.NodeId = t.NodeId\n" +
                "\tand t.parent_id = p.NodeId and p.type_fkey=ty.type_id order by loc_level desc";

        rs = HibernateSession.getResultSet(sql);
        while (rs.next()) {
            if (locationPath.length() > 0) {
                if (rs.getString(1).equals(id)) {
                    locationPath.append(">" + rs.getString(3) + ":" + rs.getString(2));

                } else {
                    locationPath.append("><A HREF=\"detail.jsp?location=" + rs.getString(1) + "\" >" + rs.getString(3) + ":" + rs.getString(2) + "</A>");

                }
            } else {
                if (rs.getString(1).equals(id)) {
                    locationPath.append(rs.getString(3) + ":" + rs.getString(2));

                } else {
                    locationPath.append("<A HREF=\"detail.jsp?location=" + rs.getString(1) + "\" >" + rs.getString(3) + ":" + rs.getString(2) + "</A>");
                }
            }
        }


%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<HTML><HEAD>
    <link type="text/css" rel="stylesheet" href="/internal/owd.css"/></HEAD>

<BODY>
<div class="mainArea">
    <CENTER><H1><%= locDescription.toUpperCase() %> : <%= locName %></H1></center>

    <P>
        <B><%= locationPath %></B><HR>
    Mobile? : <%= locMobile == 1 ? "YES" : "NO"%>
    <H3>Inventory within this location:

        <%
           sql = "select c.locname as 'location',ty.description as 'Loc Description',qty,i.description,inventory_num,company_name\n" +
"from Location n, LocationTree t, Location c, LocationType ty, InventoryLocation il, owd_inventory i, owd_client cl\n" +
"where n.nodeid="+id+"\n" +
"\tand n.NodeId = t.parent_id\n" +
"\tand t.NodeId = c.NodeId\n" +
"        and c.NodeID=il.loc_fkey and qty>0 and inventory_fkey=inventory_id and cl.is_active=1 and client_fkey=client_id\n" +
"and ty.type_id=c.type_fkey order by c.locname";
           rs = HibernateSession.getResultSet(sql);

           while(rs.next())
           {
dataList.add(new InventoryLocationDisplayData(rs.getString("location"),
        rs.getString("Loc Description"),rs.getString("inventory_num"),
        rs.getString("description"),rs.getString("company_name"),rs.getInt("qty")));          }

    }   catch(Exception ex)
              {
                        ex.printStackTrace();
              }   finally
              {
                 HibernateSession.closeSession();
              }

    request.setAttribute("dataList",dataList);
%><div class="bugList">
        <display:table cellspacing="0" name="dataList" sort="list" class="its" pagesize="30">
            <display:column property="location" sortable="true" title="Location"/>
            <display:column property="locDesc" sortable="true" title="Type"/>
            <display:column property="sku" sortable="true" title="SKU"/>
            <display:column property="skuDesc" sortable="true" title="Description"/>
            <display:column property="qty" sortable="true" title="Quantity"/>
            <display:column property="client" sortable="true" title="Client"/>

            <display:setProperty name="paging.banner.all_items_found" value=""/><display:setProperty
                name="paging.banner.onepage" value=""/></display:table>
    </div></div>

detail page <%= request.getParameter("location")%>             <BR>
At: <%= Calendar.getInstance().getTime().getTime() %>
</BODY>
</HTML>