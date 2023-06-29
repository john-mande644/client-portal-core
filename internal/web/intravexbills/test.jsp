<%@ page import="java.text.DecimalFormat,
                 com.owd.hibernate.HibernateSession,
                 org.hibernate.Hibernate,
                 java.util.Iterator,
                 org.hibernate.Session,
                 com.owd.hibernate.generated.UpsEbill,
                 java.sql.ResultSet,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 java.util.List,
                 org.hibernate.Query,
                 java.util.ArrayList,
                 com.owd.web.internal.navigation.*,
                 com.owd.web.internal.intravexbills.InvoiceDataBean" %>
<%@ page import="org.apache.commons.beanutils.RowSetDynaClass"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kUPSAreaName, "Completed Imports", request);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html" %>
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<HEAD>

    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>

    <font color=red>
        <B><%= (request.getAttribute("errormessage") != null ? request.getAttribute("errormessage") + "<BR>" : "") %></B>
    </font>
    <font color=red>
        <B><%= (request.getSession(true).getAttribute("upsebill_errormessage") != null ? request.getSession(true).getAttribute("upsebill_errormessage") : "") %></B>
    </font>
 <tab:tabConfig />
    <%
        request.getSession(true).setAttribute("upsebill_errormessage", null);

        request.getSession(true).setAttribute("inprogress", null);
        DecimalFormat moneyFormatter = new DecimalFormat("$#,000.00");


        DateFormat dateReader = new SimpleDateFormat("MMddyyyy");
        DateFormat dateWriter = new SimpleDateFormat("MMMM d, yyyy");


        try {

            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select ups.invoiceNumber, count(*),"
                    + "min(ups.billDate),sum(convert(money,ups.netCharges)) ,sum(convert(money,ups.incentive)),"
                    + "max(ups.importUser),max(ups.importFileName),max(ups.importDate),sum(needs_review),sum(convert(money,ups.client_incentive)) from ups_ebill ups group by ups.invoiceNumber order by min(ups.billDate) desc");
            List list = new ArrayList();

            while (rs.next()) {
                InvoiceDataBean d = new InvoiceDataBean();
                d.setInvoiceNumber(rs.getString(1));
                d.setItems(rs.getInt(2));
                d.setBillDate(rs.getString(3));
                d.setCharges(rs.getFloat(4));
                d.setIncentive(rs.getFloat(5));
                d.setUser(rs.getString(6));
                d.setFileName(rs.getString(7));
                d.setImportDate(rs.getString(8));
                d.setUnresolvedCount(rs.getInt(9));
                d.setClientIncentive(rs.getFloat(10));
                list.add(d);
            }


            request.setAttribute("tabdata", list);


        } catch (Exception eee) {
            throw eee;
        } finally {
            HibernateSession.closeSession();
        }
    %>
 <tab:tabContainer id="foo-bar-container">
<tab:tabPane id="foo" tabTitle="UPS Invoices">
<div class="CenterTableTitle">Imported UPS Invoices</div>
    <display:table cellspacing="0" name="tabdata" sort="list" class="its" export="true" pagesize="10">

        <display:column property="invoiceNumber" sortable="true" title="Invoice (Click for details)"
                        href="billsummary.jsp" paramProperty="invoiceNumber" paramId="invoice"/>
        <display:column property="items" title="Items" sortable="true"/>
        <display:column property="unresolvedCount" title="Unresolved" sortable="true"/>
        <display:column property="billDate" sortable="true" title="Bill Date"
                decorator="com.owd.web.internal.displaytag.StandardDateTimeDecorator"  />
        <display:column property="grossCharge" sortable="true"
                        decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" title="Gross Charges"/>

        <display:column property="charges" sortable="true"
                        decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" title="OWD Charges"/>
        <display:column property="incentive" sortable="true"
                        decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" title="OWD Incentive"/>
        <display:column property="clientIncentive" sortable="true"
                        decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" title="Client Incentive"/>


        <display:setProperty name="paging.banner.all_items_found" value=""/><display:setProperty
            name="paging.banner.onepage" value=""/></display:table>
</tab:tabPane>
</tab:tabContainer>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>


