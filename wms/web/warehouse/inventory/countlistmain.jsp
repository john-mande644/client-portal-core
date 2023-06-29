<%@ page import="org.displaytag.tags.TableTagParameters,
                 org.displaytag.util.ParamEncoder" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%
    String pageId = new ParamEncoder("table2").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
    String num = request.getParameter(pageId);
%>
<link href="<html:rewrite page="/warehouse/warehouse.css" />" rel="stylesheet" type="text/css" media="screen">


<html:link href="loadCounts?${sessionScope.mainPage}=${sessionScope.mainPageNum}">Main Count List</html:link>

<div id="header">
<span class="label">
Veiwing sku's in request number ${countEditForm.id}
 </span>
</div>


<div class="leftmenu">

    Enter sku to load
    <html:form action="loadCountItem" focus="invId">
        <html:text property="invId" size="20" value=""/>
        <html:hidden property="action" value="search"/>
        <center><html:submit value="Search"/></center>
        <br>

    </html:form>
</div>


<div class="content">
    <c:if test='${error != null}'><B class="error">${error}</B>
        <script>
            var gen = new ActiveXObject("SymbolBrowser.Generic");
            gen.PlayWave("\\err.wav", 0);
        </script>
        <br>
    </c:if>
    <display:table id="table2" cellspacing="0" name="sessionScope.reportrs1c.rows" sort="list" pagesize="50"
                   export="false" requestURI="" decorator="com.owd.dc.inventorycount.inventoryCountDecorator"
                   class="invCount">

        <c:forEach var="item" items="${sessionScope.collist}">
            <c:choose>
                <c:when test='${item.name == "inventory_id"}'>
                    <display:column property="inventoryLink" title="Inventory Id" sortable="true"/>
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
