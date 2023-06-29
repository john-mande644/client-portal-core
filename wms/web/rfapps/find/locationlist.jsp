<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="com.owd.hibernate.HibernateSession,
                 java.util.List,
                 com.owd.dc.locations.LocationBarcodeUtilities,
                 com.owd.hibernate.generated.OwdInventory,
                 java.util.Iterator,
                 com.owd.dc.inventory.beans.skuDTO,
                 org.hibernate.ObjectNotFoundException,
                 java.util.ArrayList"%>

<!doctype html public "-//w3c/dtd HTML 4.0//en">

<html>
      <head>

 <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
          <meta name="HandheldFriendly" content="true" />
<meta name="viewport" content="width=device-width, height=device-height, user-scalable=no" />
<jsp:include page="/rfapps/buttonmeta.jsp" />
<title>Warehouse Location Manager</title>
          <meta name="viewport" content="width=device-width, height=device-height, user-scalable=no target-densitydpi=high-dpi" />
          <jsp:include page="/rfapps/includes/androidStuff.jsp"/>

      </head>
<div class="body">
<body>
<TABLE><TR><TH ALIGN=LEFT>
<html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit>${loginName}</html:form> </TH><TH ALIGN=RIGHT>
 </TH></TR></TABLE>
<HR>
<% int colmn = 1; %>
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
<B>Sku's in Location</B><BR>

<html:form action="findSku" focus="sku">
<html:text property="sku" value="" styleId="autoSubmit"/>
<br>
<html:submit value="FIND"/>
</html:form>
<hr>
${fn:length(skusinlocation)} Items found in ${loc}
<table class="find">
<tr>

<c:forEach var="location" items="${skusinlocation}">
<td class="find">
        <span class="inventoryid">${location[0]}</span>
        <br>
        <span class="inventorynum">${location[1]}</span>
        </td>
 <%colmn+=1;
if(colmn == 3){
    colmn=1;
 %>
     </tr><tr>
   <% }
   %>
   </c:forEach>
 </table>
 </div>
 </body>
</html>
