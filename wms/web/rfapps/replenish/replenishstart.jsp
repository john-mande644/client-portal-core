<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<html>
<head>

 <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>

<script language="JavaScript" type="text/javascript">
<!--
function submitMulti ( id )
{
  document.stockout.location.value = id ;
  document.stockout.submit() ;
}
-->
</script>
</head>
<body><div class="body">
<c:if test='${error != null}'>
<B class="error"><c:out value='${error}'/></B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
</c:if>
<c:if test='${outcome != null}'>
<b class="outcome"><c:out value='${outcome}'/></b>
<hr>
</c:if>
<span class="label"> Replenishing for</span><br>
<center>
<span class="inventoryId"><c:out value='${pickItem.barcode}'/></span>
<br>
<span class="inventoryNum"><c:out value='${pickItem.sku}'/></span>
<BR>${pickItem.description}
</center>
<p>
<span class="label">Locations:</span>
<hr>
<form name="stockout" action="stockout" method="post">
<html:hidden property="inventoryId" value="${pickItem.barcode}"/>
<html:hidden property="pickItemId" value="${pickItem.pickItemId}"/>
<html:hidden property="pickStatusId" value="${pickItem.pickStatusId}"/>
<html:hidden property="startTime" value="${startreplenishtime}"/>
<html:hidden property="location" value=""/>
<span class="location"><c:out value='${pickItem.locFirst}'/></span> <p class="button">
<c:forEach var="item" items="${pickItem.locList}">
 <span class="location"><c:out value='${item}'/></span>
 <p class="button">
</c:forEach>
</form>
<BR>
<html:form action="replenishEnd">
<html:hidden property="inventoryId" value="${pickItem.barcode}"/>
<html:hidden property="pickItemId" value="${pickItem.pickItemId}"/>
<html:hidden property="pickStatusId" value="${pickItem.pickStatusId}"/>
<html:hidden property="startTime" value="${startreplenishtime}"/>
<html:submit value="DONE"/>
</html:form>
<br>




</div>
<script type="text/javascript">
    function tryCancel(){
        s = prompt('This will put the order on hold. To do so Enter 9137','Cancel Code');
        if (s == 9137){
            return true;
        }
        return false;
    }
</script>
</body>

</HTML>
