<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ page import="com.owd.core.business.Client,
				 com.owd.hibernate.HibernateSession,
				 com.owd.hibernate.generated.OwdClient,
				 org.apache.commons.beanutils.RowSetDynaClass,
				 java.sql.ResultSet,
				 java.text.DateFormat,
				 java.text.DecimalFormat,
				 java.text.SimpleDateFormat,
                 java.util.Calendar,
                 java.util.Date,
                 java.util.Locale"%>
<%



      String cid = null;

                          String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
     	if(user_client_id == null) user_client_id = "-1";
     	if(user_client_id.equals("")) user_client_id = "-1";

    cid=user_client_id;

                          if(!(request.getParameter("cid")==null))
                          {
                            if("0".equals(user_client_id))
                            {
                                cid=request.getParameter("cid");
                            }
                          }
                     String errorNote = (String) request.getAttribute("errorNote");


      request.setAttribute("cid",cid);
    
     DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date yesterday = cal.getTime();
    %><HTML><HEAD><TITLE>OWD Client Report Downloads</TITLE></HEAD><script language="JavaScript">
// The following script is used to hide the calendar whenever you click the document.
document.onmousedown=function(e){
var n=!e?self.event.srcElement.name:e.target.name;
if (document.layers) {
with (gfPop) var l=pageX, t=pageY, r=l+clip.width,b=t+clip.height;
if (n!="popcal"&&(e.pageX>r||e.pageX<l||e.pageY>b||e.pageY<t)) gfPop.fHideCal();
return routeEvent(e); // must use return here.
} else if (n!="popcal") gfPop.fHideCal();
       }
if (document.layers) document.captureEvents(Event.MOUSEDOWN);
</script>
	   <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
<body leftmargin="15" marginheight="0" marginwidth="15" topmargin="0" style="font-size:11px;" >
<B>Inventory Movement Statement</B>
 <%
    if(errorNote != null)
    {

    %><p><B><font color=red size=+1 ><%= errorNote %></font></b><P><%
    }
	%> <br><B>[<A HREF="/webapps/clienttools/index.jsp">Return to Client Tools page</A>]</B><P>&nbsp;</P><P>This report provides details on the physical count of inventory in OWD's control during the period requested. Please note that kit or virtual items are not included in this report as such items have no physical inventory.
   Most OWD systems and reports report inventory stock levels as an available quantity - that is, the quantity not committed to pending orders. This report, in contrast, reflects the quantity physically present at OWD. For example, an order released to day and shipped tomorrow would show a changed stock level today in most reports, but would not report the change until tomorrow for this report, because the item will not physically leave our facilities until then.
</P>

    <hr>
    <form method=POST name="dateform" ACTION="index.jsp">
Statement for: <% if (user_client_id.equals("0"))
{
    %><b><SELECT NAME="cid"><%= com.owd.web.servlet.ExtranetServlet.getClientSelector(request,"cid",cid==null?user_client_id:cid) %></SELECT></b>
    <% }else{

    Client client = Client.getClientForUser(request.getUserPrincipal().getName());
    %><B><%= client.company_name %></B>
   <% } %>
    <BR>
Start Date:&nbsp;<input name="sd" value="<%= request.getParameter("sd")==null?df.format(yesterday):request.getParameter("sd") %>" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.sd);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
&nbsp;&nbsp;End Date:&nbsp;<input name="ed" value="<%= request.getParameter("ed")==null?df.format(yesterday):request.getParameter("ed") %>" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.ed);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
&nbsp;&nbsp;<INPUT TYPE=SUBMIT NAME="View Movement Summary" VALUE="View Movement Summary" ><hr>
    </form>
<%
    if("View Movement Summary".equals(request.getParameter("View Movement Summary")))
    {
        %><style>



</style>
<%
    try
    {
        //    String cid = request.getParameter("cid");

    String startDate = request.getParameter("sd");
    String endDate = request.getParameter("ed");



        if(cid==null)
        {
            cid = (String) request.getAttribute("cid");

        }
       ResultSet summaryrs = null;



        DateFormat dff = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        startDate = dff.format(df.parse(startDate));
        endDate = dff.format(df.parse(endDate));
    summaryrs = HibernateSession.getResultSet("exec sp_getinventorymovementsummary_test '"+startDate+"','"+endDate+"',"+cid);

        RowSetDynaClass resultSet = new RowSetDynaClass(summaryrs, false);
        int summaryrows = resultSet.getRows().size();
        System.out.println(summaryrows +" This is how many rows we have");
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(cid);
        request.setAttribute("results1", resultSet);

    OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,new Integer(cid));
    DecimalFormat moneyFormat = new DecimalFormat("$#,###,##0.00");

             //   DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

%>
<hr>
<table cellpadding="0" border="0" width="100%">
        <tr>
            <td colspan=3 align=center><font size="+2"><strong>Inventory Movement Statement</strong><br></font><font size=+1><strong><%= startDate%> to <%= endDate%></strong></font>
        <tr>
            <td width="60%">
            <td width="7%">
            <td align="left" width="33%">

        </table>
    <hr>


    <p><br clear="all">


        <font size="+1"><strong>Inventory Movement Summary</strong></font>
          <br />
        <display:table  name="requestScope.results1.rows" export="true" pagesize="10" id="mvsummary"  >
        <display:setProperty name="export.csv.filename" value="inventorysummary.csv" />
        <display:setProperty name="export.amount" value="list" />
              <display:column property="inventory_num" title="SKU" />
        <display:column property="description" title="Description" />
  <display:column property="startqty" title="Starting Qty"/>
  <display:column property="received" title="Received"/>
  <display:column property="shipped" title="Shipped" />
  <display:column property="returned" title="Returned"/>
  <display:column property="adjusted" title="Adjusted"/>
  <display:column property="damaged" title="Damaged"/>
  <display:column property="endqty" title="Ending Qty"/>
            </display:table>


    <p><br clear="all">
    <br clear="all">
     <% summaryrs.close();


    summaryrs = HibernateSession.getResultSet("exec sp_getinventorymovementdetail_test '"+startDate+"','"+endDate+"',"+cid);

 RowSetDynaClass resultSet2 = new RowSetDynaClass(summaryrs, false);
         summaryrows = resultSet2.getRows().size();
        request.setAttribute("results2", resultSet2);



    %>
    <font size="+1"><strong>Inventory Movement Detail</strong></font>
    <br />
    <display:table  name="requestScope.results2.rows" export="true" pagesize="20" id="mvdetails" >
        <display:setProperty name="export.csv.filename" value="inventorydetail.csv" />
        <display:setProperty name="export.amount" value="list" />
        <display:column property="inventory_num" title="SKU" group="1"/>
        <display:column property="Description" title="Description"/>
  <display:column property="TransactionDate" title="Date" group="2"/>
  <display:column property="ClientReference" title="Reference"/>
  <display:column property="TransactionType" title="Type" />
  <display:column property="QuantityChange" title="Quantity"/>
  <display:column property="Notes" title="Notes"/>
            </display:table>

    <p><br clear="all">
    <br clear="all">

    <% summaryrs.close();

    %>

</center>

<p class="text2" align=left>This statement was automatically generated - if you have questions about it, please contact your Account Manager
        at 605-845-7172.<p class="text2" align=left>To contact your Account Manager via email, simply reply to this message or send a message to <A href="mailto:<%= client.getAmEmail()%>"><%= client.getAmEmail()%></a>.
    <p class="text2" align=center>Thank you for working with OWD!
  </p>
<hr>
<p class=trademarkText>One World Direct - 605-845-7172 - 10 1st Ave East - Mobridge - South Dakota - 57601</p>
</body>
<%
    }catch(Exception ex)
    {
        throw ex;
    }            finally
    {
        HibernateSession.closeSession();
    }
%><%
    }
%>


 <!--  PopCalendar(tag name and id must match) Tags should sit at the page bottom -->
     <iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js" src="/webapps/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
     <LAYER name="gToday:outlook:agenda.js" src="/webapps/popcal/npopeng.htm">     </LAYER>
     </iframe>
     </FORM><!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;
    <script src="//rum-static.pingdom.net/pa-5b369fc56a549f00160000bc.js" async></script>
    </BODY>
</HTML>
