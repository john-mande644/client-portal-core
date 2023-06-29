

<html>
<head>
  <%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<SCRIPT type="text/JavaScript"><!--
function dates() {

if(document.viewAdjustForm.dateRes.checked)
{
document.viewAdjustForm.fromDate.disabled=false;
document.viewAdjustForm.toDate.disabled=false;

}
if(!document.viewAdjustForm.dateRes.checked)
{
document.viewAdjustForm.fromDate.disabled=true;
document.viewAdjustForm.toDate.disabled=true;

}

}

//-->
</SCRIPT>
 <script type="text/javascript" src="<html:rewrite page="/js/owd.js" />"></script>
 <script type="text/javascript" src="<html:rewrite page="/js/owdcal.js" />"></script>
 <link href="<html:rewrite page="/css/geneva.css" />" rel="stylesheet" type="text/css">
    <link href="<html:rewrite page="/css/intranet.css" />" rel="stylesheet" type="text/css">
</head>

<body onload="toggleVisibility('throb','hidden','hidden','hidden')">
<jsp:include page="/geneva/gheader.jsp"/>


    <!-----------------Form Area Begin--------------------------------------------------------------------->
Search for Receives<br>
<html:form action="searchAdjust">




<html:select name="viewAdjustForm" property="field" title="Select Criteria to Search On">
    <html:optionsCollection name="viewAdjustForm" property="fields" value="action" label="display"/>
</html:select>

<html:select name="viewAdjustForm" property="type" >
    <html:optionsCollection name="viewAdjustForm" property="types" value="action" label="display"/>
</html:select>
<html:text property="data" size="25" title="Enter Search Value Here"/>

<br>

Restrict Dates:<html:checkbox property="dateRes" value="yes" onclick="dates()" title="Select If You Want To Search On Date Range"/>
   <table class=date><tr>
    <td>From:</td><td><script>DateInput('fromDate', true, 'YYYY-MM-DD'<c:if test="${viewAdjustForm.fromDate!=null&&viewAdjustForm.fromDate!=''}">,'${viewAdjustForm.fromDate}'</c:if>)</script></td>

<td>To:</td><td> <script>DateInput('toDate', true, 'YYYY-MM-DD'<c:if test="${viewAdjustForm.toDate!=null&&viewAdjustForm.toDate!=''}">,'${viewAdjustForm.toDate}'</c:if>)</script></td>
<td><html:submit value="Search"/> </td> 
    </tr>
</table>
</html:form>
<br>
<span class="errors">${errors}</span>
    <span class="outcome">${outcome}</span>
  <!-----------------Form Area End--------------------------------------------------------------------->
  <!-----------------Receive lookup Area Begin--------------------------------------------------------------------->
<div style=" margin: 0;
        padding:0;
        text-align: left;
        width: 50%;
        float: left;
        height:auto !important;
	height:1%;
     padding-bottom:26px !important;
	padding-bottom:0;">
<c:if test="${sessionScope.columlist!=null}">
<display:table id="table1" cellspacing="0" name="sessionScope.receiverp.rows" sort="list"  pagesize="25"
                   export="false" requestURI="" class="its" decorator="com.owd.web.remotewarehouse.geneva.adjustments.AdjustmentTableDecorator">

  <c:forEach var="item" items="${sessionScope.columlist}">
   <c:choose>
        <c:when test='${item.action == "receive_id"}'>

        </c:when>
         <c:when test='${item.action == "transaction_num"}'>
           <display:column property="link" title="Transaction"  sortable="true"/>
        </c:when>
        <c:when test='${item.action=="receive_date"}'>
               <display:column  property="${item.action}" sortable="true" title="Receive Date"
                decorator="com.owd.web.remotewarehouse.geneva.adjustments.StandardDateTimeDecorator"/>
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
        padding-left: 15;
        text-align: left; /*position: absolute;*/
        width: 45%;
        float: right;">
    <span id="loadedadjust">
<c:if test="${sessionScope.rec!=null}">
    Transaction Num: ${rec.transactionNum}       <br>
    Ref No. ${rec.refNum}




<div class="CenterTableTitle">Receive Items For Receive ${rec.receiveId}</div>
<display:table id="table2" cellspacing="0" name="sessionScope.rec.owdReceiveItems" sort="list"
                   export="false" requestURI="" class="report">
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

<c:if test="${!rec.isVoid}">

<a href="/webapps/geneva/voidAdjust.do?recId=${rec.receiveId}" title="Click here to Void this Adjustment">Void</a>
</c:if>
<c:if test="${rec.isVoid}">
  <span class="errors">Adjust Voided</span>
</c:if>
</c:if>
 </span>
</div>

<!-----------------Receive Information area End--------------------------------------------------------------------->



 <jsp:include page="/geneva/gfooter.jsp"/>

</body>
</html>