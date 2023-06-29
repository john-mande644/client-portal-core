<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Disabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/includes/androidStuff.jsp"/>
<body>
<div class="receive">
<c:if test='${error != null}'><B class="error">${error}</B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
<br>
</c:if>
<c:if test='${outcome != null}'>
<font color='blue' size="+2"><b>${outcome}</b></font>
<hr size="3">
</c:if>
<span class="label">Please Enter Search Criteria<br></span>

<table>
<tr><td>
<html:form action="search">
<select name="fkey" title="Select Client you want to Search for">
<c:forEach var="item" items="${searchForm.clients}">
<c:choose>
    <c:when test="${item.action == searchForm.fkey}">
      <option value="${item.action}" selected="true">${item.display}</option>
    </c:when>
    <c:otherwise><option value="${item.action}">${item.display}</option></c:otherwise>
</c:choose>
</c:forEach>
</select></td><td>
<select name="status" title="ASN Status">
<c:forEach var="item" items="${searchForm.statuss}">
<c:choose>
    <c:when test="${item == searchForm.status}">
      <option value="${item}" selected="true">${item}</option>
    </c:when>
    <c:otherwise><option value="${item}">${item}</option></c:otherwise>
</c:choose>
</c:forEach>
</select>
</td></tr><tr><td>
<html:text property="search" size="10"/></td><td> </td></tr>

<tr><td><html:submit property="submit"><bean:message key="button.asnSearch"/></html:submit></td>
</tr>
</html:form>
</table>
<br>

</div>
</body>
