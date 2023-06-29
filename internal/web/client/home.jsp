<%@ page import="com.owd.hibernate.HibernateSession,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 java.util.Calendar,
                 com.owd.web.internal.navigation.*,
                 com.owd.web.internal.client.ClientHome,
                 java.sql.ResultSet,
                 org.apache.commons.beanutils.RowSetDynaClass" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Calendar today = Calendar.getInstance();
    String todayDateString = df.format(today.getTime());
    today.add(Calendar.DATE, -7);
    String sevenDayDateString = df.format(today.getTime());
    today.add(Calendar.DATE, -23);
    String thirtyDayDateString = df.format(today.getTime());


%><%
    UIUtilities.setRequestNavigationElements(UIUtilities.kClientAreaName, "Client Status Summary for " + todayDateString, request);

%>
<html>
<head>
    <title><%= request.getAttribute("lf_CURRMANAGER") %></title>
    <jsp:include page="/includes/owdtop.jsp"/>
    <%
        try {

            int activeClientCount = 0;

            ResultSet rsc = HibernateSession.getResultSet("select count(*) from owd_client where is_active=1 ");

            if (rsc.next()) {
                activeClientCount = rsc.getInt(1);
            }

            HibernateSession.closeSession();

            ResultSet rs = HibernateSession.getResultSet("select top 20 company_name as 'Client',convert(varchar,created_date,101) as 'Created',am_email as 'Account Manager' from owd_client where is_active=1 order by client_id desc ");
            RowSetDynaClass displayrs = new RowSetDynaClass(rs, false);
            System.out.println("got dynaset");
            request.setAttribute("reportrs", displayrs);

            HibernateSession.closeSession();
            /*
              ResultSet rsa = HibernateSession.getResultSet("select top 10 company_name as 'Client', count(*) as 'Orders Released' \n" +
"from owd_client \n" +
"join owd_order on client_fkey=client_id and is_void=0 \n" +
"and post_date >= dateadd(day,-7,getdate())\n" +
"where is_active=1 \n" +
"group by company_name\n" +
"order by count(*) desc");
                        RowSetDynaClass displayrsa = new RowSetDynaClass(rsa, false);
                            System.out.println("got dynaset");
                 request.setAttribute("reportrsa",displayrsa);

               HibernateSession.closeSession();
            */
            ResultSet rssh = HibernateSession.getResultSet("select company_name as 'Client' from owd_client where is_on_shipping_hold=1");
            RowSetDynaClass displayrssh = new RowSetDynaClass(rssh, false);
            System.out.println("got dynaset");
            request.setAttribute("reportrssh", displayrssh);

            HibernateSession.closeSession();


            ResultSet rsam = HibernateSession.getResultSet("select convert(varchar,count(*))+' -- '+am_email as 'AM Client Loads'" +
                    "from owd_client \n" +
                    "where is_active=1 \n" +
                    "group by am_email\n" +
                    "order by count(*) desc");
            RowSetDynaClass displayrsam = new RowSetDynaClass(rsam, false);
            System.out.println("got dynaset");
            request.setAttribute("reportrsam", displayrsam);

    %>
    <div class="Left"><H2>Active Clients: <%= activeClientCount %></H2>
        <display:table cellspacing="0" name="requestScope.reportrsam.rows" class="xxx" pagesize="10">
            <display:setProperty name="paging.banner.all_items_found" value=""/><display:setProperty
                name="paging.banner.onepage" value=""/>
        </display:table>
        <H2>On Shipping Hold</H2>
        <display:table cellspacing="0" name="requestScope.reportrssh.rows" class="xxx" pagesize="10">
            <display:setProperty name="paging.banner.all_items_found" value=""/><display:setProperty
                name="paging.banner.onepage" value=""/>
        </display:table>
    </div>


    <% if (request.getAttribute("reportrs") != null) {
    %> <div class="Right"><H2>10 Most Recent Clients</H2>
    <display:table cellspacing="0" name="requestScope.reportrs.rows" class="its" pagesize="10">
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.onepage" value=""/>
    </display:table>

    <%
        }
        if (request.getAttribute("reportrsa") != null) {
    %>
    <H2>10 Most Active Clients (Previous 7 Days)</H2>
    <display:table cellspacing="0" name="requestScope.reportrsa.rows" class="its" pagesize="20">
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.onepage" value=""/></display:table></div>
    <%
            }
        } finally {
            HibernateSession.closeSession();
        }
    %>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY>
</HTML>
