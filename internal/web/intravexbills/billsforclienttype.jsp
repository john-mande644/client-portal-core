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
                 com.owd.core.managers.UPSEbillManager,
                 com.owd.hibernate.generated.UpsEbill,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 com.owd.web.internal.intravexbills.UploadIntravexBillData" %>
<%@ page import="java.util.ArrayList,
                 com.owd.core.business.Client,
                 com.owd.core.business.client.DefaultClientPolicy"
        %>
<%@ page import="com.owd.core.managers.IntravexEbillManager" %>


<HTML><link REL="stylesheet" HREF="/internal/owd.css" TYPE="text/css">
<TITLE>UPS E-Billing Invoice Manager</TITLE>

<BODY>

<P><font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? request.getAttribute("errormessage") : "") %></B></font><BR>
    <font color=red>
        <B><%= (request.getSession(true).getAttribute("upsebill_errormessage") != null ? request.getSession(true).getAttribute("upsebill_errormessage") : "") %></B>
    </font>

<P>
    UPS Invoice Items - <%= request.getParameter("invoice") %><BR>
    Type:<%= UploadIntravexBillData.UPSCodeMap.get(request.getParameter("tcode")) %><P>
    <%
         ResultSet rs = null;
    DecimalFormat moneyFormatter = new DecimalFormat("$#,##0.00");

	try
    {
        PreparedStatement ps = HibernateSession.getPreparedStatement("select invoicenumber as 'invoice', count(*) as \'Invoice Items\',"
        +"min(billdate),sum(convert(money,netcharges)),sum(convert(money,incentive))"
        +" from ups_ebill where invoicenumber = ? and transactioncode = ? "+(request.getParameter("client_key").equals("0")?" and client_fkey is null":" and client_fkey=?")+"  group by invoicenumber ");
         ps.setString(1,request.getParameter("invoice"));
        ps.setString(2,request.getParameter("tcode"));
        if(!(request.getParameter("client_key").equals("0"))) ps.setString(3,request.getParameter("client_key"));
        rs = ps.executeQuery();
        if(rs.next())
        {
     %>

    Items: <%= rs.getString(2) %><BR>
    Invoice Date: <%= rs.getString(3) %><BR>
    Invoice Total: <%= rs.getString(4) %><BR>
    Net Incentive: <%= rs.getString(5) %>
    (<%= OWDUtilities.roundFloat(100.0f*(new Float(rs.getString(5)).floatValue()/(new Float(rs.getString(4)).floatValue()+new Float(rs.getString(5)).floatValue()))) %>%)
    <BR>

    <%
        }

    }   catch(Exception ex)
    {ex.printStackTrace();
    }   finally
    {HibernateSession.closeSession();
    }

    String tab = (String) request.getParameter("tab");


	%>

    <%
     if (true)
    {
         %>
<TABLE cellspacing=4>
    <TR><TH>Order</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;Activity&nbsp;Date</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;OWD Bill</TH><TH
            ALIGN=RIGHT>&nbsp;&nbsp;Incentive</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;Client Incentive</TH><TH ALIGN=RIGHT>
        &nbsp;&nbsp;Client Bill</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;OWD Profit</TH></TR>
    <%


        DateFormat dateReader = new SimpleDateFormat("MMddyyyy");
        DateFormat dateWriter = new SimpleDateFormat("MMMM d, yyyy");
        try {

            Iterator it = IntravexEbillManager.getInvoiceClientTypeItems(request.getParameter("invoice"), request.getParameter("tcode"), request.getParameter("client_key"));
            //       rs = HibernateSession.getResultSet(HibernateSession.currentSession(),"select company_name, count(*),"
//+"sum(convert(money,ups.netCharges)) ,sum(convert(money,ups.incentive)) "
//+" from ups_ebill ups  left outer join  owd_client on client_id=client_fkey where invoicenumber=\'"+request.getParameter("invoice")+"\'  and transactioncode =\'"+request.getParameter("tcode")+"\' "+(request.getParameter("client_key").equals("0")?" and client_fkey is null":" and client_fkey=\'"+request.getParameter("client_key")+"\'")+" group by company_name order by company_name asc");


            while (it.hasNext()) {
                UpsEbill item = (UpsEbill) it.next();

    %><TR><TD NOWRAP><A target="_"
                        HREF="bill_detail.jsp?ups_bill_id=<%= item.getId() %>"><%= item.getNeedsReview().intValue() == 1 ? "Unresolved Order" : item.getOwdOrder() == null ? "No&nbsp;Order&nbsp;Linked" : item.getOwdOrder().getOrderNum() %></A>
</TD>
    <TD NOWRAP ALIGN=RIGHT><%= item.getPickupDate() != null ? dateWriter.format(item.getPickupDate()) : "" %></TD>
    <TD NOWRAP ALIGN=RIGHT><%= moneyFormatter.format(item.getNetCharges().floatValue()) %></TD>
    <TD NOWRAP ALIGN=RIGHT><%= moneyFormatter.format(item.getIncentive().floatValue()) %></TD>
    <TD NOWRAP ALIGN=RIGHT><%= moneyFormatter.format(OWDUtilities.roundFloat(item.getClientIncentive().floatValue())) %>
        @ <%= OWDUtilities.roundFloat(item.getClientIncentivePct().floatValue() * 100.00f) %>%</TD>

    <TD NOWRAP
        ALIGN=RIGHT><%= moneyFormatter.format(item.getNetCharges().floatValue() + item.getIncentive().floatValue() - item.getClientIncentive().floatValue()) %></TD>
    <TD NOWRAP
        ALIGN=RIGHT><%= moneyFormatter.format(item.getIncentive().floatValue() - item.getClientIncentive().floatValue()) %></TD>
</TR>
    <%
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // HibernateSession.closeSession();
        }
    %>
</TABLE>
<%
    }
%>
<HR>


</BODY></HTML>

