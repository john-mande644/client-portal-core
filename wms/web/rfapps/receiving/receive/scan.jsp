<html><%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Enabled">
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
<font color='blue' size="+2">
<b>${outcome}</b>
</font>
<HR>
</c:if>
<span class="label">Select what you intend to do</span>

    <table lass="fullWidthTable">

<html:form action="receiveLoadAsn" >
<html:hidden property="rcvId"/>
<tr><td><html:submit value="Continue Receive"/></td></tr>
</html:form>
</table>

 <br><br><br>
<br>
    <html:form action="startreceive">
        <html:submit value="Mark Receive In Building"/>
    </html:form>

</div>
</body>
</html>