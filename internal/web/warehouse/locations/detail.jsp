<%@ page import="java.util.Calendar,
                 org.hibernate.Session,
                 java.sql.ResultSet,
                 com.owd.web.internal.location.InventoryLocationDisplayData,
                 java.util.List,
                 java.util.ArrayList" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%

    String id = request.getParameter("location");
    StringBuffer locationPath = new StringBuffer();

    String sql = "select locname,description,is_mobile from w_location l join w_location_type lt on lt.ixLocType=l.ixLocType and ixnode=" + request.getParameter("location");

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
                "from w_location n, w_location_tree t, w_location p, w_location_type ty\n" +
                "where n.ixnode=" + id + "\n" +
                "\tand n.ixnode = t.ixnode\n" +
                "\tand t.ixparent = p.ixnode and p.ixLocType=ty.ixLocType order by ixLevel desc";

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
           sql = "(select c.locname as 'location',ty.description as 'Loc Description',qty,i.description,inventory_num,company_name\n" +
"from w_location n, w_location_tree t, w_location c, w_location_type ty, w_location_inventory il, owd_inventory i, owd_client cl\n" +
"where n.ixnode="+id+"\n" +
"\tand n.ixnode = t.ixparent\n" +
"\tand t.ixnode = c.ixnode\n" +
"        and c.ixnode=il.ixNode and qty>0 and ixinventory=inventory_id and cl.is_active=1 and client_fkey=client_id\n" +
"and ty.ixloctype=c.ixloctype) UNION "+"(select n.locname as 'location',ty.description as 'Loc Description',qty,i.description,inventory_num,company_name\n" +
"from w_location n,  w_location_type ty, w_location_inventory il, owd_inventory i, owd_client cl\n" +
"where n.ixnode="+id+"\n" +
"        and n.ixnode=il.ixNode and qty>0 and ixinventory=inventory_id and cl.is_active=1 and client_fkey=client_id\n" +
"and ty.ixloctype=n.ixloctype)"+" order by location";
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