<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/includes/androidStuff.jsp"/>
<body>
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
<c:if test='${outcome != null}'>
<font color='blue' size="+2">
<b>${outcome}</b>
</font>
<hr>
</c:if>
Fill out the Form to Create new Inventory Request.




<table>
<html:form action="createInventoryRequest">
<tr><td class="right">Client:</td><td>
<html:select name="inventoryRequestForm" property="clientFkey">
        <html:options collection="clientList" property="action" labelProperty="display"/>
    </html:select>
</td></tr>
<tr><td class="right">Description:</td><td>
<html:text property="reference" size="40"/>
</td></tr>
<tr><td class="right">ASN ID</td><td>
<html:text property="asnId" size="20"/>
</td></tr>
<tr><td class="right">Location to Assign:</td><td>
<html:text property="locationToAssign" size="20"/>
</td></tr></table>
<html:submit value="Create"/>
</html:form>
<br>
<br>
<html:form action="doinventory">
<html:submit value="Done"/>
</html:form>
<br><br>
<html:form action="viewRequestStart">
<html:submit value="View Request"/>
</html:form>
</body>

