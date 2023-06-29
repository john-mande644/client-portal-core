<%@ page import="com.owd.web.internal.navigation.UIUtilities" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, request.getSession(true).getAttribute("owd_current_location")+" Box Types", request);
%>
<html>
<head>
    <link href="<s:url value='/stylesheets/boxcodes.css'/>" rel="stylesheet" type="text/css"/>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>
<s:url id="url" action="crud!input" />
<div id="pagecommands" style="padding-top: 8px;">
    <h3>
        <a class="admin" href="<s:property value="#url"/>">Add New Box Type</a>

    </h3>
</div>
</head>
<body>
<s:set name="dt_boxcodelist" value="boxcodes" scope="request"/>
<div class="CenterTableTitle"><s:text name="boxcodes.list.title"/></div>
<display:table id="table1" cellspacing="0" name="requestScope.dt_boxcodelist" sort="list" class="its" pagesize="50"
   requestURI=""  decorator="com.owd.web.internal.displaytag.BoxCodesTableDecorator"          export="false" >

 <display:column property="name" title="Name" sortable="true"/>
 <display:column property="description" title="Description" sortable="true"/>
 <display:column property="clientAndSkuData" title="Client/SKU" sortable="true"/>
 <display:column property="cost" decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator" title="Cost" sortable="true"/>
 <display:column property="links" title="Actions" sortable="true"/>

    <display:setProperty name="basic.empty.showtable" value="true"/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.page.selected" value=""/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
</display:table>
<jsp:include page="/includes/owdbottom.jsp"/>
</body>
</html>