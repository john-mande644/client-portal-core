<%@ page import="com.owd.web.internal.navigation.UIUtilities" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kITAreaName, "Home", request);

    String bgcolor = "#ffffff";
%>


<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
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
<CENTER><H2>DC Order History</H2><B><script> document.write(new Date().toString()); </script></B>
</CENTER>
<HR>

<HR>
<TABLE width="100%"><tr><td width='100%' valign='top'>
<s:set name="dt_currentlist" value="pickhistory" scope="request"/>
<div class="CenterTableTitle"><s:text name="pickhistory.list.title"/></div>
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
    </td></tr></TABLE>

<HR>

<P></P>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>
<%
    HibernateSession.closeSession();

%>