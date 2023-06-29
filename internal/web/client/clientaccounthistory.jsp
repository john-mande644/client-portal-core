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
        int pagesize = 10;

%>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kClientAreaName, "Account History for " + client.getCompanyName(), request);

    PreparedStatement stmt = HibernateSession.getPreparedStatement("select transaction_date as [Transaction Date],transaction_type as [Type],convert(varchar,intacct_id)+':'+intacct_ref as [OWD Ref],description as [Description],notes as [Notes],\n" +
            " case when (total_amount>0 and transaction_type ='Payment') or (transaction_type='Adjustment' and total_amount<0) or (transaction_type='Invoice' and total_amount<0) then case when transaction_type='Adjustment' or transaction_type='Invoice'  then (-1.00*total_amount) else total_amount end else 0.00 end as [Credit],  \n" +
            "case when (total_amount<0 and transaction_type='Payment') or (transaction_type='Invoice' and total_amount>0) or (transaction_type='Adjustment' and total_amount>0) then case when total_amount<0 then (-1.00*total_amount) else  total_amount end else 0.00 end as [Debit]\n" +
"from owd_client_acct_transactions where account_type='Shipping' and client_fkey="+client.getClientId()+" " +
"order by transaction_date asc");
    ResultSet rs = stmt.executeQuery();
    int cols = rs.getMetaData().getColumnCount();
    List colList = new ArrayList();

    for (int i = 1; i <= cols; i++) {
        colList.add(rs.getMetaData().getColumnName(i));
    }

    System.out.println("getting dynaset");
    RowSetDynaClass displayrs = new RowSetDynaClass(rs, false);
       pageContext.setAttribute("pagesize",(displayrs.getRows().size()>pagesize?pagesize:displayrs.getRows().size())+"");

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
    <display:table id="shipping" name="requestScope.reportrs.rows" sort="list" class="its" pagesize="<%= pagesize %>" export="true" decorator="com.owd.web.internal.displaytag.AccountingTableDecorator">
        <%
            Iterator itcols = ((List) request.getAttribute("reportrs-column-list")).iterator();
            while (itcols.hasNext()) {
              String colName = (String) itcols.next();

           if (colName.equals("Credit") || colName.equals("Debit")) {
%><display:column property="<%= colName%>" title="<%= colName %>"
                  decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" style="text-align:right" sortable="true"/><%
} else {
%><display:column property="<%= colName%>" title="<%= colName %>" sortable="true"/><%}
        }
    %>
        <display:setProperty name="export.excel.filename" value="report.xls"/>
        <display:setProperty name="export.csv.filename" value="report.csv"/>
        <display:setProperty name="export.excel.include_header" value="true"/>
        <display:setProperty name="export.csv.include_header" value="true"/>
    </display:table>

       <hr>Billing<hr>

   <%
    stmt = HibernateSession.getPreparedStatement("select transaction_date as [Transaction Date],transaction_type as [Type],convert(varchar,intacct_id)+':'+intacct_ref as [OWD Ref],description as [Description],notes as [Notes],\n" +
            " case when (total_amount>0 and transaction_type ='Payment') or (transaction_type='Adjustment' and total_amount<0) or (transaction_type='Invoice' and total_amount<0) then case when transaction_type='Adjustment' or transaction_type='Invoice'  then (-1.00*total_amount) else total_amount end else 0.00 end as [Credit],  \n" +
            "case when (total_amount<0 and transaction_type='Payment') or (transaction_type='Invoice' and total_amount>0) or (transaction_type='Adjustment' and total_amount>0) then case when total_amount<0 then (-1.00*total_amount) else  total_amount end else 0.00 end as [Debit]\n" +
"from owd_client_acct_transactions where account_type='Activity' and client_fkey="+client.getClientId()+" " +
"order by transaction_date asc");
    ResultSet rs2 = stmt.executeQuery();
    int cols2 = rs2.getMetaData().getColumnCount();
    List colList2 = new ArrayList();

    for (int i = 1; i <= cols2; i++) {
        colList2.add(rs2.getMetaData().getColumnName(i));
    }

    System.out.println("getting dynaset");
    RowSetDynaClass displayrs2 = new RowSetDynaClass(rs2, false);
    System.out.println("got dynaset");
       pageContext.setAttribute("pagesize",(displayrs2.getRows().size()>pagesize?pagesize:displayrs2.getRows().size())+"");
    request.setAttribute("reportrs2", displayrs2);
    request.setAttribute("reportrs2-column-list", colList2);

%>

    <display:table id="billing" name="requestScope.reportrs2.rows" sort="list" class="its" pagesize="<%= pagesize %>" export="true" decorator="com.owd.web.internal.displaytag.AccountingTableDecorator">
        <%
            Iterator itcols = ((List) request.getAttribute("reportrs2-column-list")).iterator();
            while (itcols.hasNext()) {
              String colName = (String) itcols.next();

           if (colName.equals("Credit") || colName.equals("Debit")) {
%><display:column property="<%= colName%>" title="<%= colName %>"
                  decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" style="text-align:right" sortable="true"/><%
} else {
%><display:column property="<%= colName%>" title="<%= colName %>" sortable="true"/><%}
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
