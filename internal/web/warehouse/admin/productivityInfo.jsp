<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: danny
  Date: 11/27/2018
  Time: 5:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>
<%
  String location = request.getParameter("location");

  List displayrsproductivity = WarehouseStatus.getProductivityData(location);
  request.setAttribute("displayrsproductivity", displayrsproductivity);
%>
<html>
<head>
    <title></title>
  <link rel="Stylesheet" href="/internal/stylesheets/AdHoc.css" type="text/css"/>
  <link rel="Stylesheet" href="/internal/stylesheets/screen.css" type="text/css"/>
  <link rel="Stylesheet" href="/internal/stylesheets/locationmenu.css" type="text/css"/>
</head>
<body>






<div class="CenterTableTitle">Pick/Pack Productivity Today</div>

<display:table id="table5" cellspacing="0" name="requestScope.displayrsproductivity" sort="list" class="its" pagesize="50">

  <display:column property="name" title="Name" sortable="true"/>
  <display:column property="packHours" sortable="true" title="Pack Hours" format="{0,number,#.##}" />
  <display:column property="pickHours" sortable="true" title="Pick Hours" format="{0,number,#.##}" />
  <display:column property="packUnits" sortable="true" title="Pack Units"/>
  <display:column property="pickUnits" sortable="true" title="Pick Lines"/>
  <display:column property="packMean" sortable="true" title="Pack Units/Hour" format="{0,number,#.##}" />
  <display:column property="pickMean" title="Pick Lines/Hour" sortable="true" format="{0,number,#.##}" />
  <display:setProperty name="paging.banner.all_items_found" value=""/>
  <display:setProperty name="paging.banner.onepage" value=""/>
</display:table>
</body>
</html>
