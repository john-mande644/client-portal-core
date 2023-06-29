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
                 java.io.InputStreamReader,
                 java.io.StringReader,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 com.owd.web.internal.servlet.HomeServlet" %>

<%@ page import="java.util.ArrayList,
                 com.owd.core.business.Client,
                 com.owd.web.internal.navigation.*,
                 com.owd.core.business.client.DefaultClientPolicy"
        %>
<%@ page import="com.owd.core.managers.IntravexEbillManager" %>
<%@ page import="com.owd.hibernate.generated.IntravexEbill" %>


<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kUPSAreaName, "Item Detail", request);

%>


<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>

    <P><font color=red>
        <B><%= (request.getAttribute("errormessage") != null ? request.getAttribute("errormessage") : "") %></B></font>
        <BR>
        <font color=red>
            <B><%= (request.getSession(true).getAttribute("upsebill_errormessage") != null ? request.getSession(true).getAttribute("upsebill_errormessage") : "") %></B>
        </font>

        <P>

<%
 try
     {
        DateFormat dateReader = new SimpleDateFormat("MMddyyyy");
        DateFormat dateWriter = new SimpleDateFormat("MMMM d, yyyy");
       IntravexEbill item = IntravexEbillManager.getBillingItemForItemID(request.getParameter("ups_bill_id"));
   //    System.setProperty("org.exolab.castor.indent","true") ;
     //  System.setProperty("org.exolab.castor.marshalling.validation","false") ;


%>

        <H2>UPS Invoice Item Detail for <%= item.getInvoiceNumber() + "-" + item.getId() %></H2><A
            HREF="intravexbillservlet?act=summary&invoice=<%= item.getInvoiceNumber() %>"><B>[Back to Invoice
        Summary]</B></A>

        <P>

            <FORM ACTION="intravexbillservlet?act=update" METHOD=POST>
                <INPUT TYPE=HIDDEN NAME=item_id VALUE="<%=item.getId() %>">
                <INPUT TYPE=HIDDEN NAME=intravex_bill_id VALUE="<%=item.getId() %>">
                <INPUT TYPE=HIDDEN NAME=invoice VALUE="<%=item.getInvoiceNumber() %>">

                <TABLE width=100%>
                    <TR><TD NOWRAP><H3>Billing Item Resolution</H3></TD><TD WIDTH=99% NOWRAP><HR></TD></TR>
                </TABLE>

                <TABLE width=100%>
                    <TR><TD colspan=2>
                        <INPUT TYPE=RADIO NAME=bill_to VALUE=OWD <%= item.getBillOwd().intValue()==0?"":"CHECKED" %>>
                        Apply to OWD&nbsp;&nbsp;
                        <INPUT TYPE=RADIO NAME=bill_to
                               VALUE=CLIENT <%= item.getBillClient().intValue()==0?"":"CHECKED" %>> Apply
                        to:&nbsp;<B><%= item.getOwdClient()!=null && item.getOwdOrder() != null ?item.getOwdClient().getCompanyName()+"<INPUT TYPE=HIDDEN NAME=client_id VALUE=\""+item.getOwdClient().getClientId()+"\" >":
                            "<SELECT NAME=\"client_id\" >"+HomeServlet.getClientSelector(request,"client_id",(item.getOwdClient()==null?"0":item.getOwdClient().getClientId()+""),"Unknown","0") +"</SELECT>" %></B>
                        Order Number:&nbsp;&nbsp;<INPUT TYPE=TEXT NAME=order_num value="">
                        Notes:&nbsp;&nbsp;<INPUT TYPE=TEXT NAME=resolve_notes value=<%= (item.getResolveNotes()==null?"":item.getResolveNotes()+"" ) %>>
                        <P>
                            <INPUT TYPE=SUBMIT NAME="Resolve this Item" VALUE="Resolve this Item">
                        </P>
                        </FORM>
        </TD></TR></TABLE>
<HR>
<TABLE><TR><TD VALIGN=TOP>
    <TABLE>
        <TR><TD ALIGN=RIGHT NOWRAP><B>UPS Item Type:</B></TD><TD><%= item.getSourceType()+":"+item.getServiceType() %></TD></TR>
        <TR><TD ALIGN=RIGHT NOWRAP><B>Picked Up:</B></TD><TD><%= dateWriter.format(item.getShipDate()) %></TD></TR>
        <TR><TD ALIGN=RIGHT NOWRAP><B>Delivery Date:</B></TD><TD><%= item.getDeliveryDate()==null?"":dateWriter.format(item.getDeliveryDate()) %></TD></TR>



        <TR><TD ALIGN=RIGHT NOWRAP><B>Net Charges:</B></TD><TD><%= item.getTotalCharge() %>
            &nbsp;<B>Incentive:</B>&nbsp;<%= item.getDiscount()%></TD></TR>
        <TR><TD colspan=2></TR>
        <TR><TD ALIGN=RIGHT NOWRAP VALIGN=TOP><B>Tracking:</B></TD><TD
                VALIGN=TOP><%= item.getTracking().length() < 6 ? item.getTracking() : "<A HREF=\"http://wwwapps.ups.com/etracking/tracking.cgi?HTMLVersion=4.0&TypeOfInquiryNumber=T&tracknums_displayed=1&InquiryNumber1=" + item.getTracking() + "\">" + item.getTracking() + "</A>" + "<BR>&nbsp;"  %></TD>
        </TR>
     

    </TABLE>
</TD><TD VALIGN=TOP>
    <TABLE>
        <TR><TD ALIGN=RIGHT NOWRAP><B>UPS Billed Weight:</B></TD>
            <TD><%=  item.getWeight().equals("LTR") ? "N/A" : ("" + new Float(item.getWeight()) + " lbs") %></TD>
        </TR>

        <TR><TD ALIGN=RIGHT NOWRAP><B>Ref 1:</B></TD><TD><%= item.getReference1() %></TD></TR>
        <TR><TD ALIGN=RIGHT NOWRAP><B>Ref 2:</B></TD><TD><%= item.getReference2() %></TD></TR>
        <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP><B>Sender:</B></TD><TD VALIGN=TOP>
            <%= (item.getShipName() + item.getShipCompany()).length() > 0 ? item.getShipName() + " " + item.getShipCompany() + "<BR>" : "" %>
            <%= item.getShipStreet().length() > 0 ? item.getShipStreet() + "<BR>" : "" %>
            <%= item.getShipCsz().length() > 0 ? item.getShipCsz() : "" %>
</TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP><B>Receiver:</B></TD><TD VALIGN=TOP>
    <%= (item.getShipToName() + item.getShipToCompany()).length() > 0 ? item.getShipToName() + " " + item.getShipToCompany() + "<BR>" : "" %>
    <%= item.getShipToAddress().length() > 0 ? item.getShipToAddress() + "<BR>" : "" %>
    <%= item.getShipToCsz().length() > 0 ? item.getShipToCsz() : "" %>
</TD></TR>
</TABLE>
</TD></TR>
</TABLE>


</FORM>
<%

    } catch (Exception ex) {
        System.out.println("billdetail exception");
        ex.printStackTrace();
    }
%>

<jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>

