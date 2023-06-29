<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link href="<html:rewrite page="/warehouse/warehouse.css" />" rel="stylesheet" type="text/css" media="screen">
<!doctype html public "-//w3c/dtd HTML 4.0//en">

<html>
      <head>


<html:form action="loadCountItem">
<html:submit value="Cancel"/>
</html:form>
<div id="header">
<span class="label">Edit Quanity for ${countEditForm.invId} in ${countEditForm.location}</span>
</div>
<div class="content">


<c:if test='${error != null}'><B class="error">${error}</B>
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

${countFormEdit.invId}
<html:form action="countSaveEdit" focus="quanity">
Quanity: <html:text property="quanity" size="10"/>
<p>
<html:submit value="edit"/>
</html:form>
<br><br>

 </div>

    </body>

