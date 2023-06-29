<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.text.DecimalFormat,
                 java.util.*,
                 java.text.DateFormat,
                 com.owd.core.managers.ConnectionManager,
                 java.text.SimpleDateFormat,
                 java.sql.ResultSet,
                 com.owd.web.internal.navigation.*,
                 com.owd.hibernate.HibernateSession,
                 org.hibernate.Session,
                 org.apache.commons.beanutils.RowSetDynaClass" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Standard Deviants Kitting Forms", request);
    request.getSession(true).setAttribute("ordershiphold.currshiphold", null);
    System.out.println("Stream:"+getClass().getClassLoader().getResourceAsStream("KittingSheet.jasper"));
    try
    {
%>


<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>

<div id="pagecommands" style="padding-top: 8px;">
    <h3>
        <a class="admin" href="/internal/warehouse/admin">Cancel and go back</a>

    </h3>
</div>
<style>
    span.pagelinks {
        border: none;
    }
</style>
<font color=red>
    <B><%= (request.getAttribute("sdkit-error") != null ? "<div class=\"AlertBad\">" + request.getAttribute("sdkit-error") + "</div>" : "") %></B>
</font>

<FORM action="/internal/warehouse/admin/sdkitmaker" method=POST>
    <H1>Download SD Kitting Form</H1><HR>

    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Enter SKU</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">Enter the complete SD kit SKU (the item to be built) and click the Download button to get a printable PDF file for that kit.</p>
                </td>
            </tr>
            <tr>

                <td>
SD Kit SKU: <INPUT TYPE=TEXT NAME=sdkitsku VALUE="">

                </td>

            </tr>
        </table>
    </div>


    <div style="background: #ffffff; border-top: 1px solid #ccc; padding: 10px 15px;
                     margin-top: 25px;">
        <input type="submit" name="download" value="Download PDF">
    </div>
    </form>

<P>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>
