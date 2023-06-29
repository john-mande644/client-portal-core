<%@ page import="com.owd.core.CSVReader,
                 java.util.List,
                 java.util.Iterator,
                 com.owd.core.xml.OrderXMLDoc,
                 java.sql.Connection,
                 java.sql.PreparedStatement,
                 java.sql.ResultSet,
                 com.owd.core.managers.ConnectionManager,
                 java.text.DecimalFormat,
                 com.owd.core.OWDUtilities,
                 com.owd.hibernate.HibernateSession,
                 com.owd.core.managers.IntravexEbillManager,
                 com.owd.hibernate.generated.UpsEbill,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList,
                 com.owd.core.business.Client,
                 com.owd.web.internal.navigation.*,
                 com.owd.core.business.client.DefaultClientPolicy"
        %>
<%@ page import="com.owd.web.internal.intravexbills.InvoiceDataGroupTypeSummary" %>
<%@ page import="com.owd.hibernate.generated.IntravexEbill" %>
<%@ page import="com.owd.core.managers.IntravexEbillManager" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kUPSAreaName, "Invoice " + request.getParameter("invoice") + " Detail", request);

%>


<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>

<P><font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? request.getAttribute("errormessage") : "") %></B></font><BR>
    <font color=red>
        <B><%= (request.getSession(true).getAttribute("upsebill_errormessage") != null ? request.getSession(true).getAttribute("upsebill_errormessage") : "") %></B>
    </font>

<P>
    UPS Invoice Summary

<P>
    <%
         ResultSet rs = null;
    DecimalFormat moneyFormatter = new DecimalFormat("$#,##0.00");
             System.out.println("executing summarypage");
    

    String tab = (String) request.getParameter("tab");


	%>
<HR>
<B><A HREF="billsummary.jsp?tab=summary&invoice=<%= request.getParameter("invoice") %>">Summary</A>&nbsp;|&nbsp;
<A HREF="billsummary.jsp?tab=byclient&invoice=<%= request.getParameter("invoice") %>">View by Client</A>
<HR>
<%
     if ("byclient".equals(tab))
    {
         %>
<TABLE cellspacing=4>
    <TR><TH>Client</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;Items</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;Billed to OWD</TH><TH
            ALIGN=RIGHT>&nbsp;&nbsp;Incentive</TH><TH
            ALIGN=RIGHT>&nbsp;&nbsp;Intravex&nbsp;Fee</TH></TR>
    <%


        DateFormat dateReader = new SimpleDateFormat("MMddyyyy");
        DateFormat dateWriter = new SimpleDateFormat("MMMM d, yyyy");
        try {


            rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select company_name, count(*),"
                    + "sum(convert(money,ups.totalcharge)) ,sum(convert(money,ups.discount)),sum(fee) "
                    + " from intravexebills ups  left outer join  owd_client on client_id=client_fkey where invoicenumber=\'" + request.getParameter("invoice") + "\' group by company_name order by company_name asc");


            while (rs.next()) {
    %><TR><TD NOWRAP><%= rs.getString(1) %></TD>
    <TD NOWRAP ALIGN=RIGHT><%= rs.getString(2) %></TD>
    <TD NOWRAP ALIGN=RIGHT><%= moneyFormatter.format(rs.getFloat(3)) %></TD>
    <TD NOWRAP ALIGN=RIGHT><%= moneyFormatter.format(rs.getFloat(4)) %></TD>
    <TD NOWRAP ALIGN=RIGHT><%= moneyFormatter.format(rs.getFloat(5)) %></TD>
</TR>
    <%
            }
        } catch (Exception e) {
            throw e;
        } finally {
            HibernateSession.closeSession();
        }
    %>
</TABLE>
<%
    }   else if ("bytype".equals(tab))
    {
         %>

<%


        DateFormat dateReader = new SimpleDateFormat("MMddyyyy");
        DateFormat dateWriter = new SimpleDateFormat("MMMM d, yyyy");
	try
    {


             rs = HibernateSession.getResultSet(HibernateSession.currentSession(),"select sourceType+':'+serviceType, count(*),"
+"sum(ups.totalcharge) ,sum(ups.discount), sum(ups.totalcharge+ups.discount),sum(item_value),sum(fee) "
+" from intravexebills ups  left outer join  owd_client on client_id=client_fkey where invoicenumber=\'"+request.getParameter("invoice")+"\' group by sourceType order by sourceType asc");




             List list = new ArrayList();

        while(rs.next())
        {
            InvoiceDataGroupTypeSummary d = new InvoiceDataGroupTypeSummary();
            d.setItemsInGroup(rs.getInt(2));
            d.setTransactionCode(rs.getString(1));
            d.setSumNetCharges(rs.getFloat(3));
            d.setSumIncentives(rs.getFloat(4));
            d.setSumTotalBill(rs.getFloat(5));
            d.setSumRatedCost(rs.getFloat(6));





            list.add(d);
        }


            request.setAttribute("tabdata",list);


    }  catch (Exception e) {
            throw e;
        } finally {
            HibernateSession.closeSession();
        }
	%>
<display:table cellspacing="0" name="tabdata" sort="list" class="its" export="true" pagesize="30">

    <display:column property="transactionCode" sortable="true" title="Invoice Ref" href="billsummary.jsp"
                    paramProperty="transactionCode" paramId="invoice"/>
    <display:column property="itemsInGroup" title="Items" sortable="true"/>
    <display:column property="sumNetCharges" sortable="true"
                    decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" title="OWD Billed"/>
    <display:column property="sumIncentives" sortable="true"
                    decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" title="Incentive"/>
    <display:column property="sumTotalBill" sortable="true"
                    decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" title="UPS Charges"/>
    <display:column property="sumRatedCost" sortable="true" title="Predicted Charges"
                    decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator"/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.onepage" value=""/>
</display:table>
<%
    }   else
    {
        //default to summary
	    try
        {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select invoicenumber as 'invoice', count(*) as \'Invoice Items\',"
            +"min(shipdate),sum(convert(money,totalcharge)),sum(convert(money,discount)),"
            +"max(importuser),max(importfilename),max(importdate) from intravexebills where invoicenumber = ? group by invoicenumber ");
             ps.setString(1,request.getParameter("invoice"));
            rs = ps.executeQuery();
            if(rs.next())
            {
         %>Invoice: <%= rs.getString(1) %><BR>
Items: <%= rs.getString(2) %><BR>
Invoice Date: <%= rs.getString(3) %><BR>
Invoice Total: <%= rs.getString(4) %><BR>
Incentive: <%= rs.getString(5) %>
(<%= OWDUtilities.roundFloat(100.0f*(new Float(rs.getString(5)).floatValue()/(new Float(rs.getString(4)).floatValue()+new Float(rs.getString(5)).floatValue()))) %>%)
<BR>
Imported By: <%= rs.getString(6) %><BR>
Imported From: <%= rs.getString(7) %><BR>
Imported On: <%= rs.getString(8) %><BR>
<%
            }

        }   catch(Exception ex)
        {ex.printStackTrace();
        }   finally
        {HibernateSession.closeSession();
        }

    %>
<P>
    Unresolved Items:

<P>
<TABLE cellspacing=8 width=100%>
    <TR><TH ALIGN=LEFT COLSPAN=2>Item Reference</TD><TH ALIGN=LEFT>Client</TD><TH ALIGN=LEFT>Order Reference</TH><TH
            ALIGN=LEFT>Type</TH>
    </TR>
    <%
        try {
            Iterator it = IntravexEbillManager.getUnresolvedBillingItemsForInvoice(request.getParameter("invoice"));

            while (it.hasNext()) {
                IntravexEbill item = (IntravexEbill) it.next();
                if (item.getParentUpsKey() == null || item.getParentUpsKey().intValue() < 1) {

    %> <TR>
    <TD ALIGN=LEFT NOWRAP>&nbsp;<%= item.getNeedsReview().intValue() == 1 ? "UNRESOLVED" : "CONFIRMED"%>&nbsp;</TD>
    <TD ALIGN=LEFT NOWRAP><A
            HREF="bill_detail.jsp?ups_bill_id=<%= item.getId() %>"><%= item.getInvoiceNumber() + "-" + item.getId() %></A>
    </TD>

    <TD ALIGN=LEFT
        NOWRAP><%= item.getOwdClient() == null ? "No matching client" : item.getOwdClient().getCompanyName() %></TD>
    <TD ALIGN=LEFT

        NOWRAP><%= item.getOwdOrder() == null ? "No matching order" : item.getOwdOrder().getOrderNum() + " (" + item.getOwdOrder().getOrderRefnum() + ")" %></TD>
   <TD ALIGN=LEFT NOWRAP><%= item.getSourceType()+":"+item.getServiceType() %></TD>
</TR>
    <%
                }
            }
        } catch (Exception ex) {
            System.out.println("billsummary exception");
            ex.printStackTrace();
        }
    %>
</TABLE>
<% } %>
<HR>


    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>



