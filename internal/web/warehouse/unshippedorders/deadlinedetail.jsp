<%@ page import="java.util.Calendar,
                 org.hibernate.Session,
                 com.owd.hibernate.HibernateSession,
                 java.sql.ResultSet,
                 com.owd.web.internal.location.InventoryLocationDisplayData,
                 java.util.List,
                 java.util.ArrayList,
                 com.owd.hibernate.HibernateSession,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 java.util.Iterator" %>
<%

    String id = request.getParameter("id");

    String sql = "select order_num as 'OWD Ref',order_refnum as 'Client Ref',v.post_date as 'Posted',[SLA Ship By] as 'Ship By' from owd_order o join vw_order_ship_sla v" +
            " on v.order_fkey=o.order_id where  is_void=0 and v.shipped_on is null and [SLA Ship By]='" + id + "' order by [SLA Ship By] asc, v.post_date asc";


    try {
        Session sess = HibernateSession.currentSession();
        ResultSet rs = HibernateSession.getResultSet(sql);
        int cols = rs.getMetaData().getColumnCount();
        List colList = new ArrayList();

        for (int i = 1; i <= cols; i++) {
            colList.add(rs.getMetaData().getColumnName(i));
        }

        System.out.println("getting dynaset");
        RowSetDynaClass displayrs = new RowSetDynaClass(rs, false);
        System.out.println("got dynaset");
        request.setAttribute("reportrs", displayrs);
        request.setAttribute("reportrs-column-list", colList);


%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<HTML><HEAD>
    <link type="text/css" rel="stylesheet" href="/internal/owd.css"/></HEAD>

<BODY>
<div class="mainArea">
    <CENTER><H1><%= request.getParameter("name") + " Unshipped Orders"%></H1></center>

    <P>
        <B>desc</B><HR>

    <%


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }

    %><div class="bugList">
    <display:table name="requestScope.reportrs.rows" sort="list" class="its" pagesize="50" export="true">
        <%
            Iterator itcols = ((List) request.getAttribute("reportrs-column-list")).iterator();
            while (itcols.hasNext()) {
                String colName = (String) itcols.next();
        %><display:column property="<%= colName%>" title="<%= colName %>" sortable="true"/><%
        }


    %>
        <display:setProperty name="export.excel.filename" value="report.xls"/>
        <display:setProperty name="export.csv.filename" value="report.csv"/>
        <display:setProperty name="export.excel.include_header" value="true"/>
        <display:setProperty name="export.csv.include_header" value="true"/>
    </display:table>
</div></div>

detail page <%= request.getParameter("location")%>             <BR>
At: <%= Calendar.getInstance().getTime().getTime() %>
</BODY>
</HTML>