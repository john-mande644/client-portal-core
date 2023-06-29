<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<link href="<html:rewrite page="/warehouse/warehouse.css" />" rel="stylesheet" type="text/css" media="screen">
<div id="header">
 <span class="label">OWD Inventory Location History <c:if test="${skuForm.display != null}"> for ${skuForm.display}</c:if> </span>
</div>
<c:if test='${error != null}'><B class="error">${error}</B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
<br>
</c:if>
<div class="leftmenu">
Enter Sku to view history for
<html:form action="viewLocationHistory" focus="sku">
<html:text property="sku" size="8" value=""/>
<html:submit value="View"/>
</html:form>
</div>
<div class="content">
 
<display:table id="table1" cellspacing="0" name="sessionScope.list" defaultsort="2"
                   export="false" requestURI="" decorator="com.owd.dc.inventorycount.inventoryCountDecorator" class="invCount">


       <display:column property="location" title="Location" sortable="false"/>
    <display:column property="readableLocation" title="Readable Location" sortable="false"/>

       <display:column property="assignDate" title="Date Assigned" sortable="true"/>
       <display:column property="removedate" title="Date Removed" sortable="false"/>
       <display:column property="user" title="Assign User" sortable="false"/>
       <display:column property="historyPic" title="Still Active" sortable="false"/>

        <display:setProperty name="basic.empty.showtable" value="true"/>
        <display:setProperty name="paging.banner.placement" value="bottom"/>
        <display:setProperty name="paging.banner.page.selected" value=""/>
        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.no_items_found" value=""/>
        <display:setProperty name="paging.banner.one_item_found" value=""/>
    </display:table>
   <img src='/wms/images/greencheck20.png'> = Location Active

       <img src='/wms/images/redx20.png'> = Location Deleted
</div>