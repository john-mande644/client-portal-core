<%@ page import="com.owd.hibernate.generated.OwdClient,
                 java.util.Iterator,
                 com.owd.web.internal.navigation.*,
                 com.owd.hibernate.HibernateSession,
                 java.sql.PreparedStatement,
                 java.sql.ResultSet,
                 java.util.ArrayList,
                 java.util.List,
                 org.apache.commons.beanutils.RowSetDynaClass" %>
<%
    try {
        OwdClient client = (OwdClient) request.getSession(true).getAttribute("client.currentclient");


%>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kClientAreaName, "Fulfillment SLA for " + client.getCompanyName(), request);

    PreparedStatement stmt = HibernateSession.getPreparedStatement("select * from vw_sla_summary where (type='Standard SLA' and [Ship Method] not in (select [Ship Method] from vw_sla_summary where type=?))\n" +
            "or type=? order by Type,[Ship method]");
    stmt.setString(1, client.getCompanyName());
    stmt.setString(2, client.getCompanyName());
    ResultSet rs = stmt.executeQuery();
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
<html>
<head>
    <title><%= request.getAttribute("lf_CURRMANAGER") %></title>
    <jsp:include page="/includes/owdtop.jsp"/>

<P>
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
    <%


    %>

    <table border="0" width=10%>


    </table>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY>
</HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>
