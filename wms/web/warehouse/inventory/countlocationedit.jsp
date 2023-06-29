<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<link href="<html:rewrite page="/warehouse/warehouse.css" />" rel="stylesheet" type="text/css" media="screen">



<html:link action="loadCountLocations?id=${countEditForm.id}&${sessionScope.locPage}=${sessionScope.locPageNum}" >Location List</html:link>
<div id="header"> <span class="label">
Sku list for Location ${countEditForm.location}
</span>
</div>
<div class="leftmenu">
Enter sku to search for
<html:form action="loadEditSkuLocation" focus="invId">
<html:text property="invId" value=""/>
<html:submit value="Search"/>
</html:form>
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

        <c:when test='${item.name == "inventory_id"}'>
             <c:if test="${countEditForm.posted==0}">
           <display:column href="loadEditSkuLocation" paramId="invId" property="${item.name}" title="Sku"
                          sortable="true"  />
            </c:if>  
            <c:if test="${countEditForm.posted==1}">
            <display:column property="${item.name}" title="sku" sortable="true"/>
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