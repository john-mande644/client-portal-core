<%@ page import="java.text.DecimalFormat,
                 com.owd.hibernate.HibernateSession,
                 org.hibernate.Hibernate,
                 java.util.Iterator,
                 org.hibernate.Session,
                 com.owd.hibernate.generated.UpsEbill,
                 java.sql.ResultSet,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 com.owd.web.internal.intravexbills.UploadIntravexBillData" %>


<HTML><link REL="stylesheet" HREF="/internal/owd.css" TYPE="text/css">
<TITLE>UPS E-Billing Invoice Upload Page</TITLE>

<BODY>

<P><font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? request.getAttribute("errormessage") : "") %></B></font><BR>
    <font color=red>
        <B><%= (request.getSession(true).getAttribute("upsebill_errormessage") != null ? request.getSession(true).getAttribute("upsebill_errormessage") : "") %></B>
    </font>

<P>

<P>
<TABLE cellspacing=4>
    <TR><TH>Client</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;Type</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;Items</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;Billed
        to OWD</TH><TH ALIGN=RIGHT>&nbsp;&nbsp;Incentive</TH></TR>
    <%
        request.getSession(true).setAttribute("upsebill_errormessage", null);

        request.getSession(true).setAttribute("inprogress", null);
        DecimalFormat moneyFormatter = new DecimalFormat("$#,##0.00");


        DateFormat dateReader = new SimpleDateFormat("MMddyyyy");
        DateFormat dateWriter = new SimpleDateFormat("MMMM d, yyyy");
        try {


            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select company_name, transactioncode, count(*),"
                    + "sum(convert(decimal,ups.netCharges)) ,sum(convert(decimal,ups.incentive)) "
                    + " from ups_ebill ups  left outer join  owd_client on client_id=client_fkey group by company_name,transactioncode order by company_name,transactioncode asc");

            while (rs.next()) {
    %><TR><TD NOWRAP><%= rs.getString(1) %></TD>
    <TD NOWRAP
        ALIGN=RIGHT><%= UploadIntravexBillData.UPSCodeMap.get(rs.getString(2)) == null ? "Unknown (" + rs.getString(2).trim() + ")</B>" : UploadIntravexBillData.UPSCodeMap.get(rs.getString(2)) %></TD>
    <TD NOWRAP ALIGN=RIGHT><%= rs.getString(3) %></TD>
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
<HR>
</BODY></HTML>

