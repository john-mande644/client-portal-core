<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.owd.dc.inventory.multiSkuForm,
                 java.util.HashMap,
                 java.util.Iterator"%>
<% String url = (String) request.getAttribute("url");
   HashMap info = (HashMap) request.getAttribute("skus");
   String var = (String) request.getAttribute("var");

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
  document.multi.<%=var%>.value = id ;
  document.multi.submit() ;
}
-->
</script>
<title>Multi Sku Manager</title>

          <jsp:include page="/rfapps/includes/androidStuff.jsp"/>

</head>
<body link="#85147C" vlink="#85147C">

<TABLE><TR><td><center><bean:message key="label.multi1"/></center></td></TR>

</TABLE>
 <form name="multi" action="<%=url%>" method="post">
 <html:hidden property="<%=var%>" value=""/>
 <c:if test="${url=='pickitem'}">
 <html:hidden property="inventoryNum" value="skip"/>
 </c:if>
 <table class="multi">
 <% for (int i = 1; i <= info.size(); i++) {
     multiSkuForm skuInfo = (multiSkuForm) info.get("" + i);

 %>

     <tr class="multi"><td class="multi"><font color="#01D709"><%=skuInfo.getInventoryId()%></font></td>
    <td class="multi"><a href="javascript:submitMulti('<%=skuInfo.getInventoryId()%>')"><%=skuInfo.getInventoryNum()%></a></td> </tr>
     <tr><td class="multia" colspan="2"><font color="blue"><%=skuInfo.getClientName()%></font></td></tr>
     <tr class="multi"><td class="multib" colspan="2"><%=skuInfo.getDescription()%></td></tr>

     <%
}
     %>
 </table>
  </form>
 <br>

 <c:if test='${rememberclient != null && remember == null}'>
 <script language="JavaScript" type="text/javascript">
<!--
function submitMultirem ( id, rem, remname )
{
  document.multirem.<%=var%>.value = id ;
  document.multirem.remember.value = rem;
  document.multirem.rememberClientName.value=remname;
  document.multirem.submit() ;
}
-->
</script>
  <div class="center">Use these links to remember client.</div>
  <form name="multirem" action="<%=url%>" method="post">
 <html:hidden property="<%=var%>" value=""/>
  <html:hidden property="remember" value=""/>
   <html:hidden property="rememberClientName" value=""/>
   <table class="multirem">
  <%  for(int i =1; i <= info.size(); i++ ){
     multiSkuForm skuInfo = (multiSkuForm) info.get(""+i);
      String formName = "remmulti" + i;
      
     %>

     <tr class="multi"><td class="multi"><font color="#01D709"><%=skuInfo.getInventoryId()%></font></td>

     <td class="multi"><a href="javascript:submitMultirem('<%=skuInfo.getInventoryId()%>','<%=skuInfo.getClientFkey()%>','<%=skuInfo.getClientName()%>')"><%=skuInfo.getInventoryNum()%></a></td> </tr>
     <tr><td class="multia" colspan="2"><font color="blue"><%=skuInfo.getClientName()%></font></td></tr>
     <tr class="multi"><td class="multib" colspan="2"><%=skuInfo.getDescription()%></td></tr>

     <%
}
     %>
     </table>
     </form>
</c:if>
 <script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\Windows\\Alarm3.wav",0);
</script>
 </body>

</html>