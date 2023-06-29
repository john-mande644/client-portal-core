<html>
<head>
<title>Owd Casetracker Problem Form Search</title>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
   <script type="text/javascript" src="<html:rewrite page="/js/owdcal.js" module=""/>"></script>
    </head>
<link href="<html:rewrite page="/css/callcenter.css" module=""/>" rel="stylesheet" type="text/css">
<body>

<div id="getCase">
    ${error}
    <html:form action="caseSearch">
     <table class="search">
     <tr class="search">
         <td class="search,right">
             <span class="label">Search casetracker subject lines for: </span>
         </td>
         <td class="search,left"><html:text property="searchText"/></td>
         <td class="search,left">
        <html:submit property="submit">
            <bean:message key="button.caseSearch"/>
        </html:submit>
        </td>
         </tr>
     </table>
           <table class="search">
           <tr class="search">
         <c:if test="${caseSearchForm.clients!=null}">

               <td class="search,right"><span class="label">Client: </span></td>
               <td class="search,left"><html:select name="caseSearchForm" property="client">
                    <html:optionsCollection name="caseSearchForm" property="clients" value="action" label="display"/>
        </html:select></td>
          </c:if>




            </tr>
       </table>
    </html:form>
    <hr>
</div>
<!-------------------Search Area Begin----------------------------------->

<div id="results">
    <hr>
    <!----------------------------disoplay-->

    <c:if test="${columnlist!=null}">
        <display:table id="table1"  name="sessionScope.casesearch.rows" sort="list" pagesize="100"
                       export="false" cellspacing="0" requestURI="" class="cases">

            <c:forEach var="item" items="${sessionScope.columnlist}">
                <c:choose>
                   <c:when test='${item.action == "link"}'>
                        <display:column property="${item.action}"
                                        title="Click to View" sortable="true" headerClass="sortable"
                                >
                        </display:column>
                    </c:when>
                    <c:when test='${item.action == "dtOpened"}'>
                        <display:column property="${item.action}"
                                        title="Created" sortable="true" headerClass="sortable"
                                decorator="com.owd.callcenter.displaytag.StandardDateTimeDecorator"
                                >
                        </display:column>
                    </c:when>
                    <c:when test='${item.action=="scustomeremail"}'>
                        <display:column property="${item.action}" sortable="true"  headerClass="sortable" title="Created By"
                                        decorator="com.owd.callcenter.displaytag.StandardDateTimeDecorator"/>
                    </c:when>
                    <c:otherwise>
                        <display:column property="${item.action}" title="${item.action}" sortable="true" headerClass="sortable"/>
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
</body>

</html>