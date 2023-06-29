<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<script language="JavaScript" type="text/javascript">
<!--
function removeLine (id, loc)
{
  var remove = confirm("Are you sure you want to remove "+loc)
  if (remove == true){
  location = "deleteCountItem?itemId="+id
                   }

}
-->
</script>



<link href="<html:rewrite page="/warehouse/warehouse.css" />" rel="stylesheet" type="text/css" media="screen">
<html:link href="loadCount?id=${countEditForm.id}&${sessionScope.skuPage}=${sessionScope.skuPageNum}" >Item List</html:link>
<div id="header">
<span class="label">Showing Locations for sku ${countEditForm.invId}</span>
</div>
<div class="content">
<c:if test='${error != null}'><B class="error">${error}</B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
<br>
</c:if>

<display:table id="table1" cellspacing="0" name="sessionScope.reportrs1c.rows" sort="list"  pagesize="50"
                   export="false" requestURI="" decorator="com.owd.dc.inventorycount.inventoryCountDecorator" class="invCount">

  <c:forEach var="item" items="${sessionScope.collist}">
   <c:choose>
        <c:when test='${item.name == "id"}'>
        <c:if test="${0==countEditForm.posted}">
           <display:column href="editCountItem" paramId="itemId" paramProperty="${item.name}"
                          sortable="false">edit</display:column>
           <display:column property="removelink"  title="" sortable="false"></display:column>

         </c:if>
         <c:if test="${1==countEditForm.posted}">

         </c:if>
        </c:when>
        <c:otherwise>
           <display:column property="${item.name}" title="${item.name}" sortable="true"/>
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

</div>