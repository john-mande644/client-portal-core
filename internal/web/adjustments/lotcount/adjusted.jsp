<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus" %>
<%@ page import="com.owd.hibernate.generated.OwdFacility" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if(request.getParameter("location")!=null)
    {
//        request.getSession(true).setAttribute("owd_current_location",request.getParameter("location"));
        request.getSession(true).setAttribute("owd_current_adjustment_location", request.getParameter("location"));
    }
    String currentLocation = WarehouseStatus.getCurrentLocation(request);
    List<OwdFacility> facilities = WarehouseStatus.getFacilityList();
    StringBuffer fsb = new StringBuffer();
    String currFacility = "UNKNOWN ERROR";
    for(OwdFacility f: facilities)
    {
        if(currentLocation.equals(f.getFacilityCode()))
        {
            currFacility = f.getFacilityCode()+" - "+f.getCity()+", "+f.getState();
        } else
        {
            fsb.append("<li><a href=\"/internal/do/indexLotCount?location="+f.getFacilityCode()+"\">"+f.getFacilityCode()+" - "+f.getCity()+", "+f.getState()+"</a></li>");
        }
    }
%>
<html>
<head>
    <link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<html:rewrite page="/js/Ajax.js" module=""/>"></script>

    <link rel="Stylesheet" href="/internal/stylesheets/locationmenu.css" type="text/css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <title> OWD Lot Count!!!!!! </title>
</head>

<body onload="toggleVisibility('throb','hidden','hidden','hidden')">
<div id="header"><p class="header">OWD Lot Counts</p></div>

<div id="leftMenu">
    <jsp:include page="../adjust/menu.jsp"/>
</div>

    <div id="return">
        <div id="rightContent">
            <span id="errors"><html:errors/>${errors}</span>
            <span class="outcome" id="outcome">${outcome}</span>
       <span id="items">
                           <table><tr><td colspan="4">${lotCountForm.sku} - ${lotCountForm.description}<BR>
                               Updated Lot Counts for <%= currFacility %><hr></td></tr></table>
               <table class="returnitems">
                   <th class="returnxl">Lot Value</th>
                   <th class="returnsmall">Original Quantity</th>
                   <th class="returnsmall">Updated Quantity</th>
                   <th class="returnsmall">Change</th>
                   <!-- <th class="return">Return Reason</th>-->
                   <c:if test="${sessionScope.lotCountForm.inventoryId!=null}">
                       <c:forEach var="formItems" items="${sessionScope.lotCountForm.formItems}" varStatus="itemStatus">
                           <tr>
                              <td class="returnxl">${formItems.lotValue}</td>
                               <td class="returnxl">${formItems.lotQty}</td>
                               <td class="returnxl">${formItems.newQty}</td>
                           <td class="returnxl">${formItems.newQty-formItems.lotQty}</td>
                           </tr>
                       </c:forEach>
                   </c:if>
               </table>
           <P></P></span><BR>
           </span>
        </div>
    </div>
</body>
</html>