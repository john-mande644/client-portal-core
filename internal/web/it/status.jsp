<%@ page import="com.owd.web.internal.navigation.UIUtilities" %>
<%@ page import="com.owd.web.internal.servlet.HomeServlet" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kITAreaName, "Home", request);

    String bgcolor = "#ffffff";
%>


<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <meta http-equiv="refresh" content="10">
    <jsp:include page="/includes/owdtop.jsp"/>

<div id="pagecommands" style="padding-top: 8px;">

</div>
<style>
    span.pagelinks {
        border: none;
    }
</style>
<font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
</font>
<CENTER><H2>OWD IT Dept Current Status</H2><B><script> document.write(new Date().toString()); </script></B>
</CENTER>
<HR>
<TABLE width="100%"><tr><td width="50%" valign="top"><s:set name="dt_currentlist" value="currentCases" scope="request"/>
<div class="CenterTableTitle"><s:text name="itcurrent.list.title"/></div>
<display:table id="table2" cellspacing="0"  name="requestScope.dt_currentlist" sort="list" class="its" pagesize="50"
   requestURI=""           export="false" >
    <display:column property="person" title="Person" sortable="true"/>
     <display:column property="assignment" title="Case" sortable="true"/>
     <display:column property="started" title="Started" sortable="true"/>

    <display:setProperty name="basic.empty.showtable" value="false"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
</display:table>
<s:set name="dt_caselist" value="cases" scope="request"/>
<div class="CenterTableTitle"><s:text name="itcases.list.title"/></div>
<display:table id="table1"
               decorator="com.owd.web.internal.displaytag.ITStatusTableDecorator" cellspacing="0" name="requestScope.dt_caselist" sort="list" class="its" pagesize="15"
   requestURI=""           export="false" >

     <display:column property="caseId" title="Case ID" sortable="true"/>
     <display:column property="title" title="Title" sortable="true"/>

    <display:setProperty name="basic.empty.showtable" value="true"/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
</display:table>
    <s:set name="dt_clientlist" value="clientcases" scope="request"/>
<div class="CenterTableTitle"><s:text name="itclients.list.title"/></div>
<display:table id="table3" cellspacing="0"
               decorator="com.owd.web.internal.displaytag.ITStatusTableDecorator" name="requestScope.dt_clientlist" sort="list" class="its" pagesize="50"
   requestURI=""           export="false" >

     <display:column property="client" title="Client" sortable="true" group="1" />
     <display:column property="caseId" title="Case ID" sortable="true"/>
     <display:column property="title" title="Title" sortable="true"/>
     <display:column property="scheduled" title="Scheduled" sortable="true"/>

    <display:setProperty name="basic.empty.showtable" value="true"/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
</display:table>     <hr>
    <s:set name="dt_closedlist" value="closedcases" scope="request"/>
    <div class="CenterTableTitle"><s:text name="itclosed.list.title"/></div>
<display:table id="table5" cellspacing="0"   defaultorder="descending" defaultsort="1"
               decorator="com.owd.web.internal.displaytag.ITStatusTableDecorator" name="requestScope.dt_closedlist" sort="list" class="its" pagesize="50"
   requestURI=""           export="false" >

     <display:column property="due" title="Resolved" sortable="true" group="1"  />
     <display:column property="client" title="Client" sortable="true" group="2" />
     <display:column property="caseId" title="Case ID" sortable="true"/>
     <display:column property="title" title="Title" sortable="true"/>

    <display:setProperty name="basic.empty.showtable" value="true"/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
</display:table>
</td><td valign="top"><s:set name="dt_nextcaselist" value="futurecases" scope="request"/>
    <div class="CenterTableTitle"><s:text name="itfuturecases.list.title"/></div>
    <display:table id="table4" cellspacing="0"
               decorator="com.owd.web.internal.displaytag.ITStatusTableDecorator" name="requestScope.dt_nextcaselist" sort="list" class="its" pagesize="100"
       requestURI=""           export="false" >
         <display:column property="scheduled" title="Scheduled" sortable="true" group="1" />
         <display:column property="caseId" title="Case ID" sortable="true"/>
         <display:column property="title" title="Title" sortable="true"/>


        <display:setProperty name="basic.empty.showtable" value="true"/>
        <display:setProperty name="paging.banner.placement" value="bottom"/>
        <display:setProperty name="paging.banner.page.selected" value=""/>
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.no_items_found" value=""/>
        <display:setProperty name="paging.banner.one_item_found" value=""/>
    </display:table>
 
    </td></tr></TABLE>
<HR>

<P></P>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>
<%
    HibernateSession.closeSession();

%>