<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<link href="<html:rewrite page="/warehouse/warehouse.css" />" rel="stylesheet" type="text/css" media="screen">
<div id="header">
 <span class="label">OWD Inventory Count Editor</span>
</div>
<c:if test='${error != null}'><B class="error">${error}</B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
<br>
</c:if>

<c:if test="${'all' == countload}">
<html:link href="loadCounts?countload=30">Load Last 30 Days</html:link>
</c:if>
<c:if test="${countload == '30'}">
<html:link href="loadCounts?countload=all">Load All</html:link>
</c:if>
<div class="leftmenu">
Enter Count to load
<html:form action="loadCount" focus="id">
<html:text property="id" size="8" value=""/>
<html:submit value="Load"/>
</html:form>
</div>
<div class="content">
<display:table id="table1" cellspacing="0" name="sessionScope.reportrs1c.rows" sort="list"  pagesize="10"
                   export="false" requestURI="" decorator="com.owd.dc.inventorycount.inventoryCountDecorator" class="invCount">

  <c:forEach var="item" items="${sessionScope.collist}">
   <c:choose>
        <c:when test='${item.name == "Link"}'>
        <display:column property="${item.name}" title="Request Id" sortable="true"/>
           <display:column property="skuLink" title="" sortable="false"/>
            <display:column property="locationsLink" title="" sortable="false"/>

        </c:when>
        <c:when test='${item.name=="Posted"}'>
               <display:column  property="postedlink" sortable="true" title="Posted"/>
        </c:when>
        <c:otherwise>
           <display:column property="${item.name}" title="${item.name}" sortable="true"/>
        </c:otherwise>
    </c:choose>



</c:forEach>
     <display:column property="closelink" title="" sortable="false"/>

        <display:setProperty name="basic.empty.showtable" value="true"/>
        <display:setProperty name="paging.banner.placement" value="bottom"/>
        <display:setProperty name="paging.banner.page.selected" value=""/>
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.no_items_found" value=""/>
        <display:setProperty name="paging.banner.one_item_found" value=""/>
    </display:table>

</div>