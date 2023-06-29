<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/includes/androidStuff.jsp"/>

<body>
<div class="body">
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

<span class="label">Fill out the Form to Create new Inventory Request.</span>

<table>
<html:form action="wcreateInventoryRequest">
<tr><td class="right">Client:</td><td>
<html:select name="winventoryRequestForm" property="clientFkey">
        <html:options collection="clientList" property="action" labelProperty="display"/>
    </html:select>
</td></tr>

<tr><td>description</td><td><html:text property="description" value=""/></td></tr>
<tr><td><html:submit value="Create"/></td></tr>
</html:form>
</table>
<br>
<br>
<html:form action="dowinventory">
<html:submit value="Done"/>
</html:form>
</div>
</body>
</html>

