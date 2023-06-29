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
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kUPSAreaName, "Upload Invoice", request);

%>


<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/><font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? request.getAttribute("errormessage") : "") %></B></font><BR>
    <font color=red>
        <B><%= (request.getSession(true).getAttribute("intravexebill_errormessage") != null ? request.getSession(true).getAttribute("intravexebill_errormessage") : "") %></B>
    </font>

<P>

<FORM METHOD="POST" ENCTYPE="multipart/form-data" ACTION="/internal/intravexbills/intravexbillservlet">
    Click the Browse button to select the Intravex invoice file from your local drive:<HR>

    <INPUT TYPE="FILE" size="60" NAME="UploadFile"><BR>

    <P><HR>

    <INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Load New Intravex Invoice File"></FORM>
<HR>

    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>


