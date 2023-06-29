<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.text.DecimalFormat,
                 java.util.*,
                 java.text.DateFormat,
                 com.owd.core.managers.ConnectionManager,
                 java.text.SimpleDateFormat,
                 java.sql.ResultSet,
                 com.owd.web.internal.navigation.*,
                 com.owd.hibernate.HibernateSession,
                 org.hibernate.Session,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 com.owd.core.OWDUtilities" %>



<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<HEAD>

    <TITLE>${lf_CURRMANAGER}</TITLE>
    <script type="text/javascript" src="/internal/warehouse/admin/adjustments/title2note.js"></script>

    	<style type="text/css" media="screen">

    	@import "/internal/warehouse/admin/adjustments/title2note.css";
</style>
<jsp:include page="/includes/owdtop.jsp"/>
    <!-----------------Form Area Begin--------------------------------------------------------------------->
Search for Receives<br>
<form name="searchReceive" method="post" action="/internal/warehouse/admin/adjustments">
<select name="clientFkey" title="Select Client you want to Search for">
<c:forEach var="item" items="${clientList}">
<c:choose>
    <c:when test="${item.action == adbean.fkey}">
      <option value="${item.action}" selected="true">${item.display}</option>
    </c:when>
    <c:otherwise><option value="${item.action}">${item.display}</option></c:otherwise>
</c:choose>

</c:forEach>
</select>
<select name="field" title="Select Criteria to Search On">
 <c:forEach var="item" items="${adbean.fields}">
   <c:choose>
     <c:when test="${item == adbean.field}">
         <option value="${item}" selected="true">${item}</option>
     </c:when>
     <c:otherwise>
           <option value="${item}">${item}</option>
     </c:otherwise>
   </c:choose>
</c:forEach>
</select>

<select name="type">
 <c:forEach var="item" items="${adbean.types}">
   <c:choose>
     <c:when test="${item == adbean.type}">
         <option value="${item}" selected="true">${item}</option>
     </c:when>
     <c:otherwise>
           <option value="${item}">${item}</option>
     </c:otherwise>
   </c:choose>
</c:forEach>
</select>

<input type="text" name="data" size="25" value="${adbean.data}" title="Enter Search Value Here"/>
<br>
Restrict Dates:<input type="checkbox" name="dateRes" value="yes" onClick="dates()" title="Select If You Want To Search On Date Range">
From: <input type="text" name="fromDate" size="15" disabled="true" value="${adbean.fromDate}" title="Enter Start Date Here"/>
&nbsp To: <input type="text" name="toDate" size="15" disabled="true" value="${adbean.toDate}" title="Enter End Date Here"/>
<input type="hidden" name="action" value="search">
<input type="submit" value="Search">
</form>
<br>

  <!-----------------Form Area End--------------------------------------------------------------------->
  <!-----------------Receive lookup Area Begin--------------------------------------------------------------------->
<div style=" margin: 0;
        padding:0;
        text-align: left;
        width: 50%;
        float: left;">
<c:if test="${columlist!=null}">
<display:table id="table1" cellspacing="0" name="sessionScope.receiverp.rows" sort="list"  pagesize="15"
                   export="false" requestURI="" class="its" decorator="com.owd.web.internal.displaytag.SkuAdjustmentTableDecorator">

  <c:forEach var="item" items="${sessionScope.columlist}">
   <c:choose>
        <c:when test='${item.action == "receive_id"}'>

        </c:when>
         <c:when test='${item.action == "transaction_num"}'>
           <display:column href="/internal/warehouse/admin/adjustments?action=loadRec&" paramId="recId" paramProperty="receive_id"
                          title="Transaction" property="${item.action}" sortable="true"/>
        </c:when>
        <c:when test='${item.action=="receive_date"}'>
               <display:column  property="${item.action}" sortable="true" title="Receive Date"
                decorator="com.owd.web.internal.displaytag.StandardDateTimeDecorator"/>
        </c:when>
        <c:when test='${item.action=="receive_user"}'>
               <display:column  property="${item.action}" sortable="true" title="User"/>
        </c:when>
         <c:when test='${item.action=="void"}'>
               <display:column  property="voided" sortable="true" title="Void"/>

        </c:when>
        <c:otherwise>
           <display:column property="${item.action}" title="${item.action}" sortable="true"/>
        </c:otherwise>
    </c:choose>

</c:forEach>
    <display:setProperty name="basic.empty.showtable" value="true"/>
        <display:setProperty name="paging.banner.placement" value="bottom"/>
        <display:setProperty name="paging.banner.page.selected" value=""/>
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.no_items_found" value=""/>
        <display:setProperty name="paging.banner.one_item_found" value=""/>
    </display:table>
</c:if>

</div>
<!-----------------Receive lookup Area End--------------------------------------------------------------------->


<!-----------------Receive Information area Begin--------------------------------------------------------------------->

<div style=" margin: 0;
        padding: 0;
        text-align: left; /*position: absolute;*/
        width: 50%;
        float: left;">

<c:if test="${rec!=null}">
Ref No. ${rec.refNum}




<div class="CenterTableTitle">Receive Items For Receive ${rec.receiveId}</div>
<display:table id="table2" cellspacing="0" name="sessionScope.rec.owdReceiveItems" sort="list"  pagesize="15"
                   export="true" requestURI="" class="its">
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
Notes:    ${rec.notes}
<br>
<a href="/internal/warehouse/admin/adjustments?action=moreInfo&recId=${rec.receiveId}">More Info</a>
<c:if test="${!rec.isVoid}">

<a href="/internal/warehouse/admin/adjustments?action=Void&recId=${rec.receiveId}">Void</a>
</c:if>
</c:if>

</div>

<!-----------------Receive Information area End--------------------------------------------------------------------->
  <jsp:include page="/includes/owdbottom.jsp"/>



<SCRIPT LANGUAGE="JavaScript"><!--
function dates() {

if(document.searchReceive.dateRes.checked)
{
document.searchReceive.fromDate.disabled=false;
document.searchReceive.toDate.disabled=false;

}
if(!document.searchReceive.dateRes.checked)
{
document.searchReceive.fromDate.disabled=true;
document.searchReceive.toDate.disabled=true;

}

}

//-->
</SCRIPT>
</HTML>