<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.owd.dc.inventory.multiSkuForm,
                 java.util.HashMap,
                 java.util.Iterator,
                 java.util.ArrayList"%>
<% HashMap info = (HashMap) request.getSession(true).getAttribute("skus");
  %>
<html>
      <head>
<META HTTP-Equiv="Scanner" content="disabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/buttonmeta.jsp" />
<script language="JavaScript" type="text/javascript">
<!--
function submitMulti ( id )
{
  document.multi.sku.value = id ;
  document.multi.submit() ;
}
-->
</script>
<title>Return Select Sku</title>

<link href="<html:rewrite page="/rfapps/handhelds.css" />" rel="stylesheet" type="text/css">
</head>
<body link="#85147C" vlink="#85147C">

<TABLE><TR><td><center>Select or Scan Sku</center></td></TR>
 <html:form action="recv-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out value='${loginName}'/></html:form>
 <br>
 <c:if test='${error != null}'>
<font color=red><B><c:out value='${error}'/></B></font>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
<hr>
</c:if>
 <html:form action="returnSelectSku" focus="sku">
Scan Sku or select from list
<br>
<html:text property="sku" value=""/>
<html:submit value="Submit"/>
 </html:form>
</TABLE>
 <form name="multi" action="returnSelectSku" method="post">
 <html:hidden property="sku" value=""/>
 <table class="selectsku">
 <%  for(int i =1; i <= info.size(); i++ ){
     multiSkuForm skuInfo = (multiSkuForm) info.get(""+i);

     %>

     <tr class="selectsku"><td class="selectsku"><font color="#01D709"><%=skuInfo.getInventoryId()%></font></td>
    <td class="selectsku"><a href="javascript:submitMulti('<%=skuInfo.getInventoryId()%>')"><%=skuInfo.getInventoryNum()%></a></td> </tr>
    <tr class="selectsku"><td class="selectskub" colspan="2"><%=skuInfo.getDescription()%></td></tr>

     <%
}
     %>
 </table>
  </form>
 </body>

</html>