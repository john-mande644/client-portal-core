<%@ page import="com.owd.web.servlet.ExtranetServlet,
                 java.util.Map"%><%@ taglib uri="http://displaytag.sf.net" prefix="display" %><%

	com.owd.web.servlet.OrderFinder finder = (com.owd.web.servlet.OrderFinder)request.getSession(true).getAttribute(com.owd.web.servlet.OrdersManager.kCurrentFinder);

%>
<table width=100% cellpadding=0 cellspacing=0 border=0 bgcolor=ffffff>
<tr ><td valign=top bgcolor=f0f0f0 width=5%>
    <table cellpadding=0 cellspacing=0 border=0 width=100% >
    <tr><td bgcolor=dddddd ><b>&nbsp;Text Search</b>
    </td></tr>
    </table>
<img src="/webapps/images/bluepixel.gif" width=100% height=1 border=0 valign=top >
<BR><FORM METHOD=POST name="dateform" ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath %>">
<INPUT TYPE=HIDDEN NAME="<%=com.owd.web.servlet.OrdersManager.kParamOrderMgrAction%>" VALUE="<%=com.owd.web.servlet.OrdersManager.kParamOrderExploreAction%>">
<%


    Map fieldMap = finder.getTextSearchFieldMap();
    StringBuffer sb1 = new StringBuffer();

    sb1.append("<OPTION VALUE=\"\" >No text search selected");
    for(int i=0;i<fieldMap.size();i++)
    {

     sb1.append("<OPTION VALUE=\""+fieldMap.get(fieldMap.keySet().toArray()[i])+"\" "+(finder.getTextSearchField().equals((String)(fieldMap.get(fieldMap.keySet().toArray()[i])))?"SELECTED":"")+">"+fieldMap.keySet().toArray()[i]);

    }
    sb1.append("</SELECT>");


%> <CENTER>
<SELECT NAME="textSearchField" ><%= sb1.toString() %><BR>


 <SELECT NAME="textSearchType">
<OPTION VALUE=1 <%= finder.getTextSearchType()==1?"SELECTED":"" %>>is
<OPTION VALUE=2 <%= finder.getTextSearchType()==2?"SELECTED":"" %>>contains
</SELECT>
<BR>
<INPUT TYPE=TEXT NAME="textSearchValue" VALUE=<%= finder.getTextSearchValue() %> >
</CENTER>
<P>&nbsp;

    <table cellpadding=0 cellspacing=0 border=0 width=100% >
    <tr><td bgcolor=dddddd ><b>&nbsp;Status Search</b>
    </td></tr>
    </table>
<img src="/webapps/images/bluepixel.gif" width=100% height=1 border=0 valign=top >  <BR>
<CENTER>
&nbsp;Order&nbsp;Status&nbsp;<SELECT NAME="orderStatusType">
<OPTION VALUE=0 <%= finder.getOrderStatusCriterion()==0?"SELECTED":"" %>>All Unshipped
<OPTION VALUE=2 <%= finder.getOrderStatusCriterion()==2?"SELECTED":"" %>>Pending BO
<OPTION VALUE=3 <%= finder.getOrderStatusCriterion()==3?"SELECTED":"" %>>At Warehouse
<OPTION VALUE=1 <%= finder.getOrderStatusCriterion()==1?"SELECTED":"" %>>On Hold
<OPTION VALUE=4 <%= finder.getOrderStatusCriterion()==4?"SELECTED":"" %>>Shipped
<OPTION VALUE=5 <%= finder.getOrderStatusCriterion()==5?"SELECTED":"" %>>Cancelled

</SELECT>
 </CENTER> <P>&nbsp;

    <table cellpadding=0 cellspacing=0 border=0 width=100% >
    <tr><td bgcolor=dddddd ><b>&nbsp;Date Search</b>
    </td></tr>
    </table>
<img src="/webapps/images/bluepixel.gif" width=100% height=1 border=0 valign=top >  <BR>
<CENTER>  <P>
<SELECT NAME="dateType">
<OPTION VALUE=0 <%= (finder.getDateType()==0?"SELECTED":"") %>>Created
<OPTION VALUE=1 <%= (finder.getDateType()==1?"SELECTED":"") %>>Released
<OPTION VALUE=2 <%= (finder.getDateType()==2?"SELECTED":"") %>>Shipped

</SELECT>
<BR>
<SELECT NAME="dateContextType">
<OPTION VALUE=0  <%= finder.getDateContextLimit()==0?"SELECTED":"" %> >Today
<OPTION VALUE=1 <%= finder.getDateContextLimit()==1?"SELECTED":"" %>>In last week
<OPTION VALUE=2  <%= finder.getDateContextLimit()==2?"SELECTED":"" %>>In last month
<OPTION VALUE=3  <%= finder.getDateContextLimit()==3?"SELECTED":"" %>>In last year
<OPTION VALUE=4  <%= finder.getDateContextLimit()==4?"SELECTED":"" %>>At any time
<OPTION VALUE=4  <%= finder.getDateContextLimit()==5?"SELECTED":"" %>>On the date below:
</SELECT>
<BR>
<input name="xx" value="" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.xx);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>




<P>
<INPUT TYPE=SUBMIT NAME=<%= 1 %> VALUE="Find Orders">
</CENTER>
</FORM>
</td><TD width=1 bgcolor=000000></TD><TD width=90% NOWRAP valign=top>
<%= finder.showResults(request,response)  %>

<display:table name="sessionScope.orderfinderresults" sort="list" class="its" export="true" pagesize="30" decorator="com.owd.displaytag.OrderFinderTableDecorator">
<% if (ExtranetServlet.getSessionString(request, ExtranetServlet.kExtranetClientID).equals("0"))
{
      %><display:column property="clientName"  sortable="true"   title="Client"  /><%
}
    %>
<display:column property="editLink" title="ID/Details"  />
<display:column property="orderRefnum" sortable="true" title="Client Reference"/>
<display:column property="customer" sortable="true" title="Customer"/>
 <display:column property="orderStatus" sortable="true" title="Status"/>
 <display:column property="orderTotal" sortable="true"  decorator="com.owd.displaytag.StandardMoneyDecorator" title="Total"/>

</display:table>
</TD></TR></TABLE>
