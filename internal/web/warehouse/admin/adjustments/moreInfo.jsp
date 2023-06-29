


<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<HEAD>

    <TITLE>${lf_CURRMANAGER}</TITLE>
    <script type="text/javascript" src="/internal/warehouse/admin/adjustments/title2note.js"></script>

    	<style type="text/css" media="screen">

    	@import "/internal/warehouse/admin/adjustments/title2note.css";
</style>
<jsp:include page="/includes/owdtop.jsp"/>

 Receive for ${rec.owdClient.companyName}
 <c:if test="${!rec.isVoid}">
<a href="/internal/warehouse/admin/adjustments?action=Void&recId=${rec.receiveId}">Void</a>
</c:if>
<table class="receive">
<c:if test="${rec.isVoid}">
<tr><td colspan="4" bgcolor="red"><span class="b">VOIDED</span></td></tr>
</c:if>
<tr>
<td><span class="b">Receive Id:</span> ${rec.receiveId}</td>
<td><span class="b">Time In:</span> <fmt:formatDate value="${rec.timeIn}" type="time" timeStyle="short" /> </td>
<td><span class="b">Recv. Date: </span><fmt:formatDate value="${rec.receiveDate}" type="date" dateStyle="full" /> </td>
<td><span class="b">User Id:</span> ${rec.receiveUser}</td>
</tr>
<tr>
<td colspan="2"><span class="b">No. Empl:</span>  ${rec.numEmployees}</td>
<td><span class="b">Time Out:</span> <fmt:formatDate value="${rec.timeOut}" type="time"  timeStyle="short" /></td>
<td><span class="b">Total Time:</span> ${rec.totalTime}</td>
</tr>
<td colspan="2"><span class="b">Service:</span> ${rec.carrier}</td>
<td><span class="b">B/L No:</span> ${rec.blNum}</td>
<td><span class="b">Driver:</span> ${rec.driver}</td>
</tr>
<tr>
<td colspan="4"><span class="b">Ref Number:</span> ${rec.refNum}</td></tr>
</table>

<div class="CenterTableTitle">Receive Items For Receive ${rec.receiveId}</div>
<display:table id="table2" cellspacing="0" name="sessionScope.rec.owdReceiveItems" sort="list"  pagesize="15"
                   export="false" requestURI="" class="its">
        <display:column property="owdInventory.inventoryId" title="Inventory Id" sortable="true"/>
        <display:column property="inventoryNum" title="Inventory Num" sortable="true"/>
         <display:column property="description" title="Description" sortable="true"/>
          <display:column property="quantity" title="Quantity" sortable="true"/>
           <display:column property="itemStatus" title="Status" sortable="true"/>

    <display:setProperty name="basic.empty.showtable" value="true"/>
        <display:setProperty name="paging.banner.placement" value="bottom"/>
        <display:setProperty name="paging.banner.page.selected" value=""/>
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.no_items_found" value=""/>
        <display:setProperty name="paging.banner.one_item_found" value=""/>
    </display:table>


<br>
<span class="b">Notes:</span>    ${rec.notes}

  <jsp:include page="/includes/owdbottom.jsp"/>




</BODY></HTML>