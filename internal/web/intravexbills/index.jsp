<%@ page import="com.owd.hibernate.HibernateSession,
                 com.owd.web.internal.navigation.UIUtilities,
                 com.owd.web.internal.intravexbills.InvoiceDataBean,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 java.sql.ResultSet,
                 java.text.DateFormat,
                 java.text.DecimalFormat,
                 java.text.SimpleDateFormat,
                 java.util.ArrayList,
                 java.util.List" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kUPSAreaName, "Completed Imports", request);

%><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<HEAD>
<tab:tabConfig />
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>
    <font color=red>
        <B><%= (request.getAttribute("errormessage") != null ? request.getAttribute("errormessage") + "<BR>" : "") %></B>
    </font>
    <font color=red>
        <B><%= (request.getSession(true).getAttribute("upsebill_errormessage") != null ? request.getSession(true).getAttribute("upsebill_errormessage") : "") %></B>
    </font>
 <%

    try {

        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select ups.invoiceNumber, count(*),"
                    + "max(ups.shipDate),sum(convert(money,ups.totalcharge)) ,sum(convert(money,ups.discount)),"
                    + "max(ups.importUser),max(ups.importFileName),max(ups.importDate),sum(needs_review),sum(convert(money,ups.client_incentive)), ups.carriername from intravexEbills ups  group by ups.invoiceNumber, ups.carrierName order by min(importDate) desc");
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
                d.setCarrierName(rs.getString(11));
                list.add(d);
            }


            request.setAttribute("tabdata", list);





        } catch (Exception efx) {
        efx.printStackTrace();
            throw efx;
        } finally {
            HibernateSession.closeSession();
        }
    %>
 <P><tab:tabContainer id="foo-bar-container">
<tab:tabPane id="UPS" tabTitle="Carrier Invoices">
<div class="CenterTableTitle">Imported UPS Invoices</div>
    <display:table cellspacing="0" name="tabdata" sort="list" class="its" export="true" pagesize="30">

        <display:column property="invoiceNumber" sortable="true" title="Invoice (Click for details)"
                        href="billsummary.jsp" paramProperty="invoiceNumber" paramId="invoice"/>
        <display:column property="carrierName" title="Carrier" sortable="true"/>
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


