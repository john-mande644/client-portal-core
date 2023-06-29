<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta name="viewport" content="width=device-width, height=device-height, user-scalable=no target-densitydpi=high-dpi" />
<jsp:include page="/rfapps/includes/androidStuff.jsp"/>

<body>
<html:form action="inv-menu"><html:submit value="MENU"/></html:form>
<html:form action="viewRequestStart">
<html:submit value="OK"/>
</html:form>
<c:if test='${error != null}'>
     <script>
         try{
             javautil.playErrorSound();
         }catch(err){

         }
     </script>
<B class="error">${error}</B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
<br>
</c:if>
<h1>Viewing receive for ID ${wi.id} </h1>
<c:if test='${wi.done==0}'>
<html:form action="enterReceive">
Received By<html:text property="receivedBy"/>  <br>
Minutes<html:text property="minutes"/>   <br>
Cartons<html:text property="carton"/>    <br>
pallet<html:text property="pallet"/>     <br>
Notes:<html:textarea property="notes"/> <br>
<html:hidden property="wiId" value="${wi.id}"/>
<html:submit value="Enter Receive"/>

</html:form>
</c:if>
<br>
<c:forEach var="item" items="${list}">
  ${item.inventoryId} ${item.inventoryNum} ${item.quanity}
  <br>
</c:forEach>
