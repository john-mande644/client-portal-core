<%@ page import="java.text.DecimalFormat,
                 com.owd.hibernate.HibernateSession,
                 org.hibernate.Hibernate,
                 org.hibernate.Session,
                 com.owd.hibernate.generated.UpsEbill,
                 java.sql.ResultSet,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 org.hibernate.Query,
                 com.owd.hibernate.HibernateFogbugzSession,
                 java.util.*,
                 com.owd.web.internal.workorders.WorkOrder" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>


<HTML><link REL="stylesheet" HREF="/internal/owd.css" TYPE="text/css">
<TITLE>Work Orders Reporting</TITLE>

<BODY bgcolor=ffffff>
<TABLE border=2 cellspacing=3 width=100%><TR><TD NOWRAP>
    <P>&nbsp;

    <P>
        <B>Data</B><HR>
    <UL>
        <LI><A HREF="index.jsp?tab=OpenCases">Open Cases</A>
        <LI><A HREF="index.jsp?tab=ClosedCases">Closed in last week</A>
    </UL>
    <B>Graphs</B><HR>
    <UL>
        <LI><A HREF="index.jsp?tab=MultiClientByProject">Clients per Project</A>
        <LI><A HREF="index.jsp?tab=WOByPerson">WOs by Assignee</A>
        <LI><A HREF="index.jsp?tab=WOByProject">WOs by Project</A>
    </UL>
</TD><TD>
<P><font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? request.getAttribute("errormessage") : "") %></B></font><BR>
    <font color=red>
        <B><%= (request.getSession(true).getAttribute("workorder_errormessage") != null ? request.getSession(true).getAttribute("workorder_errormessage") : "") %></B>
    </font>

<P>
    <%
    String tab = request.getParameter("tab");
    if(tab==null)tab = "OpenCases";

    if(tab.equals("OpenCases"))
    {
        %>
    Open Cases:

<P>


<%
    request.getSession(true).setAttribute("workorder_errormessage",null);

    DecimalFormat moneyFormatter = new DecimalFormat("$#,000.00");


        DateFormat dateWriter = new SimpleDateFormat("MMMM d, yyyy");



	try
    {
        Map clientMap = new TreeMap();

          ResultSet rs = HibernateSession.getResultSet("select client_id,company_name from owd_client");
                while(rs.next())
        {
                clientMap.put(rs.getString(1),rs.getString(2));

                }
        clientMap.put("","Unknown");
        clientMap.put("0","Unknown");
        clientMap.put("","Unknown");

            HibernateSession.closeSession();

           rs = HibernateSession.getResultSet(HibernateFogbugzSession.currentSession(),"select dtopened as 'Opened',stitle as 'Title',"+
        "p.sFullName as 'Assigned To', sStatus as 'Status', sCategory as 'Category',b.sComputer as 'Client ID',datediff(day,dtOpened,getdate()) as 'Days Open' "+
        ",sProject  as 'Project',hrsOrigEst," +
"hrsCurrEst," +
"hrsElapsed,ixBug, sArea  from bug b       "+
"join category c on c.ixCategory=b.ixCategory "+
"join person p on p.ixPerson=b.ixPersonAssignedTo  "+
"join status s on s.ixStatus=b.ixStatus "+
"join area a on a.ixArea=b.ixArea "+
"join project pro on pro.ixProject=b.ixProject "+
"where fopen=1");
        List list = new ArrayList();

        while(rs.next())
        {
            WorkOrder d = new WorkOrder();
            d.setCreated(rs.getDate(1));
            d.setTitle(rs.getString(2));
            d.setAssignedTo(rs.getString(3));
            d.setStatus(rs.getString(4));
            d.setCategory(rs.getString(5));
            d.setClientName((String)clientMap.get(rs.getString(6)));
            d.setAgeInDays(rs.getInt(7));
            d.setProject(rs.getString(8));
            d.setEstimateHours(rs.getFloat(9));
            d.setElapsedHours(rs.getFloat(10));
            d.setBillableHours(rs.getFloat(11));
            d.setBugID(rs.getInt(12));
            d.setArea(rs.getString(13));
            list.add(d);
        }


            request.setAttribute("tabdata",list);

 %>

<display:table cellspacing="0" name="tabdata" sort="list" class="isis" pagesize="50"
               decorator="com.owd.web.internal.displaytag.WorkOrderTableDecorator">
    <display:column property="bugLink" title="Links"/>
    <display:column property="created" title="Created" sortable="true"/>
    <display:column property="ageInDays" title="Days Pending" sortable="true"/>
    <display:column property="title" title="Unresolved" sortable="title"/>
    <display:column property="assignedTo" sortable="true" title="Assigned To"/>
    <display:column property="status" sortable="true" title="Status"/>
    <display:column property="category" sortable="true" title="Category"/>
    <display:column property="project" sortable="true" title="Project"/>
    <display:column property="area" sortable="true" title="Area"/>

    <display:column property="clientName" sortable="true" title="Client"/>
    <display:column property="estimateHours" sortable="true" title="Estimate Hours"/>
    <display:column property="elapsedHours" sortable="true" title="Actual Hours"/>
    <display:column property="billableHours" sortable="true" title="Billable Hours"/>

    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.onepage" value=""/>
</display:table>
<%

    }  catch (Exception e) {
            throw e;
        } finally {
            HibernateFogbugzSession.closeSession();
        }

    }   else if(tab.equals("ClosedCases"))
    {
        %>Closed in last week
<%

	try
    {

          ResultSet rs = HibernateSession.getResultSet(HibernateFogbugzSession.currentSession(),"select dtopened as 'Opened',stitle as 'Title',"+
        "p.sFullName as 'Assigned To', sStatus as 'Status', sCategory as 'Category',b.sComputer as 'Client ID',datediff(day,dtOpened,dtClosed) as 'Days Open' "+
        ",sProject  as 'Project',hrsOrigEst," +
"hrsCurrEst," +
"hrsElapsed,ixBug,sArea  from bug b       "+
"join category c on c.ixCategory=b.ixCategory "+
"join person p on p.ixPerson=b.ixPersonAssignedTo  "+
"join status s on s.ixStatus=b.ixStatus "+
"join area a on a.ixArea=b.ixArea "+
"join project pro on pro.ixProject=b.ixProject "+
"where fopen=0 and dtClosed >= dateadd(day,-8,getdate())");
        List list = new ArrayList();

        while(rs.next())
        {
            WorkOrder d = new WorkOrder();
            d.setCreated(rs.getDate(1));
            d.setTitle(rs.getString(2));
            d.setAssignedTo(rs.getString(3));
            d.setStatus(rs.getString(4));
            d.setCategory(rs.getString(5));
            d.setClientName(rs.getString(6));
            d.setAgeInDays(rs.getInt(7));
            d.setProject(rs.getString(8));
            d.setEstimateHours(rs.getFloat(9));
            d.setElapsedHours(rs.getFloat(10));
            d.setBillableHours(rs.getFloat(11));
            d.setBugID(rs.getInt(12));
            d.setArea(rs.getString(13));
            list.add(d);
        }


            request.setAttribute("tabdata",list);

        //http://abweb2.internal.owd.com/casetracker/default.asp?pg=pgEditBug&command=view&ixBug=1552

 %>
<display:table cellspacing="0" name="tabdata" sort="list" class="isis" pagesize="50"
               decorator="com.owd.web.internal.displaytag.WorkOrderTableDecorator">
    <display:column property="bugLink" title="Links"/>
    <display:column property="created" title="Created" sortable="true"/>
    <display:column property="ageInDays" title="Days to Handle" sortable="true"/>
    <display:column property="title" title="Unresolved" sortable="title"/>
    <display:column property="assignedTo" sortable="true" title="Assigned To"/>
    <display:column property="status" sortable="true" title="Status"/>
    <display:column property="category" sortable="true" title="Category"/>
    <display:column property="project" sortable="true" title="Project"/>
    <display:column property="area" sortable="true" title="Area"/>
    <display:column property="clientName" sortable="true" title="Client"/>
    <display:column property="estimateHours" sortable="true" title="Estimate Hours"/>
    <display:column property="elapsedHours" sortable="true" title="Actual Hours"/>
    <display:column property="billableHours" sortable="true" title="Billable Hours"/>

    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.onepage" value=""/>
</display:table>
<%

    } catch (Exception e) {
        throw e;
    } finally {
        HibernateFogbugzSession.closeSession();
    }

} else if (tab.equals("MultiClientByProject")) {

%><center><IMG SRC="workOrdersByClientChart.jsp"></center><%
} else if (tab.equals("WOByPerson")) {
%><center><IMG SRC="workOrdersHistoryChart.jsp"></center><%
} else if (tab.equals("WOByProject")) {

%><center><IMG SRC="workOrdersByProjectChart.jsp"></center><%
    }

%>

</TD></TR></TABLE>
</BODY></HTML>


