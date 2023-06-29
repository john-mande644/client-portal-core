<html>
<head>
<title>Owd Recorded Call Search</title>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
   <script type="text/javascript" src="<html:rewrite page="/js/owdcal.js" module=""/>"></script>
    </head>
<link href="<html:rewrite page="/css/callcenter.css" module=""/>" rel="stylesheet" type="text/css">
<body>
<div id="getCall">
    ${error}
    <html:form action="callSearch">
     <table class="search">
     <tr class="search">
         <td class="search,right">
             <span class="label">Call Id: </span>
         </td>
         <td class="search,left"><html:text property="callId"/></td>
         <td class="search,left">
        <html:submit property="submit">
            <bean:message key="button.getCall"/>
        </html:submit>
        </td>
         </tr>
     </table>
    </html:form>
    <hr>
</div>
<!-------------------Search Area Begin----------------------------------->
<div id="callSearch">
    <html:form action="callSearch">
      <table class="search">
          <tr class="search">
              <td class="search,right">
                  <span class="label"> Enter Date To View: </span>
              </td>
              <td class="search,left">
                <script>DateInput('date', true, 'YYYY-MM-DD'<c:if test="${callSearchForm.date!=null&&callSearchForm.date!=''}">,'${callSearchForm.date}'</c:if>)</script>
              </td>
              <td class="search,left">
               <html:submit property="submit">
            <bean:message key="button.callSearch"/>
        </html:submit>
              </td>
          </tr>
      </table>


        <br>
          <table class="search">
           <tr class="search">
         <c:if test="${callSearchForm.campaigns!=null}">

               <td class="search,right"><span class="label">Campaign: </span></td>
               <td class="search,left"><html:select name="callSearchForm" property="campaign">
                    <html:optionsCollection name="callSearchForm" property="campaigns" value="action" label="display"/>
        </html:select></td>
          </c:if>



        <c:if test="${callSearchForm.agents!=null}">
            <td class="search,right"><span class="label">Agent: </span></td>
            <td class="search,left"><html:select name="callSearchForm" property="agent">
                    <html:optionsCollection name="callSearchForm" property="agents" value="action" label="display"/>
        </html:select></td>
            <td class="search,right"><span class="label">Notes:</span></td>
            <td class="search,left"><html:text property="notes"/></td>
         <td class="search,left"><html:submit property="submit">
            <bean:message key="button.callFilter"/>
        </html:submit></td>

        </c:if>
            </tr>
       </table>
    </html:form>
    </div>
<div id="results">
    <hr>
    <!----------------------------disoplay-->
  <c:if test="${columlist1==null}">
    <c:if test="${columlist!=null}">
        <display:table id="table1"  name="sessionScope.callsearch.rows" sort="list" pagesize="25"
                       export="false" cellspacing="0" requestURI="" class="calls">

            <c:forEach var="item" items="${sessionScope.columlist}">
                <c:choose>

                    <c:when test='${item.action == "contact_id"}'>
                        <display:column href="callSearch.do?submit=Get+Call" paramId="callId" property="${item.action}"
                                        title="Call Id" sortable="true" headerClass="sortable">
                        </display:column>
                    </c:when>
                    <c:when test='${item.action=="contact_initiated"}'>
                        <display:column property="${item.action}" sortable="true"  headerClass="sortable" title="Call Initiated Date"
                                        decorator="com.owd.callcenter.displaytag.StandardDateTimeDecorator"/>
                    </c:when>
                    <c:when test='${item.action=="agent_id"}'>
                        <display:column property="${item.action}" title="Agent Id" sortable="true" headerClass="sortable"/>
                    </c:when>
                    <c:when test='${item.action=="campaign"}'>
                        <display:column property="${item.action}" title="Campaign" sortable="true" headerClass="sortable"/>
                    </c:when>
                    <c:when test='${item.action=="contact_minutes"}'>
                        <display:column property="${item.action}" title="Call Minutes" sortable="true" headerClass="sortable"/>
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
 </c:if>

    <!-----Filtered list--------------------------------------->
  <c:if test="${columlist1!=null}">
   <display:table id="table2" cellspacing="0" name="sessionScope.callsearch1.rows" sort="list" pagesize="25"
                       export="false" requestURI="" class="calls">

            <c:forEach var="item" items="${sessionScope.columlist1}">
                <c:choose>

                    <c:when test='${item.action == "contact_id"}'>
                        <display:column href="callSearch.do?submit=Get+Call" paramId="callId" property="${item.action}"
                                        title="Call Id" sortable="true" headerClass="sortable">
                        </display:column>
                    </c:when>
                    <c:when test='${item.action=="contact_initiated"}'>
                        <display:column property="${item.action}" sortable="true"  headerClass="sortable"title="Call Initiated Date"
                                        decorator="com.owd.callcenter.displaytag.StandardDateTimeDecorator"/>
                    </c:when>
                    <c:when test='${item.action=="agent_id"}'>
                        <display:column property="${item.action}" title="Agent Id" sortable="true" headerClass="sortable"/>
                    </c:when>
                    <c:when test='${item.action=="campaign"}'>
                        <display:column property="${item.action}" title="Campaign" sortable="true" headerClass="sortable"/>
                    </c:when>
                    <c:when test='${item.action=="contact_minutes"}'>
                        <display:column property="${item.action}" title="Call Minutes" sortable="true" headerClass="sortable"/>
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