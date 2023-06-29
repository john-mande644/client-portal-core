<%@ page import="java.util.Map"%><%@ taglib uri="http://displaytag.sf.net" prefix="display" %><%

	com.owd.web.servlet.InventoryFinder finder = (com.owd.web.servlet.InventoryFinder)request.getSession(true).getAttribute(com.owd.web.servlet.InventoryManager.kCurrentFinder);

%>
<table width=100% cellpadding=0 cellspacing=0 border=0 bgcolor=ffffff>
<tr ><td valign=top bgcolor=f0f0f0 width=5%>
    <table cellpadding=0 cellspacing=0 border=0 width=100% >
    <tr><td bgcolor=dddddd ><b>&nbsp;Text Search</b>
    </td></tr>
    </table>
<img src="/webapps/images/bluepixel.gif" width=100% height=1 border=0 valign=top >
<BR><FORM METHOD=POST name="dateform" ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath %>">
<INPUT TYPE=HIDDEN NAME="<%=com.owd.web.servlet.InventoryManager.kParamInvMgrAction%>" VALUE="<%=com.owd.web.servlet.InventoryManager.kParamInvExploreAction%>">
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
<P>

<INPUT TYPE=SUBMIT NAME=<%= 1 %> VALUE="Find Items">
</CENTER>
</FORM>
</td><TD width=1 bgcolor=000000></TD><TD width=90% NOWRAP valign=top>
<%= finder.showResults(request,response)  %>

<display:table name="sessionScope.inventoryfinderresults" sort="list" class="its" export="true" pagesize="50" decorator="com.owd.displaytag.InventoryFinderTableDecorator">
<display:column property="editLink" title="ID/Details"  />
 <display:column property="description" sortable="false" title="Description"/>
 <display:column property="onHand" sortable="false" title="Available"/>
 <display:column property="backordered" sortable="false" title="Backordered"/>
 <display:column property="price" sortable="false"  decorator="com.owd.displaytag.StandardMoneyDecorator" title="Unit Price"/>
 <display:column property="mfrPartNum" sortable="false" title="Supplier"/>

</display:table>
</TD></TR></TABLE>
