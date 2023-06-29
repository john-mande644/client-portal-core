<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<META HTTP-Equiv="OnKey0x0d" content="Javascript:onKey1()">
  <script type="text/javascript" >
    function onKey1(){
       document.forms[1].elements[1].click();

    }
</script>
<link href="<html:rewrite page="/rfapps/computer.css" />" rel="stylesheet" type="text/css" media="screen">
<link href="<html:rewrite page="/rfapps/handhelds.css" />" rel="stylesheet" type="text/css" media="handheld">
</head>
<body>
<div class="body">
<html:form action="menu"><html:submit value="CANCEL"/></html:form>  <P>

<c:if test='${error != null}'><B class="error">${error}</B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
<br>
</c:if>    
<c:if test='${outcome != null}'>
<b class="outcome"><c:out value='${outcome}'/></b>
</c:if>
<span class="label">Batch PackApp</span>

<c:if test="${batchPackForm.boxId == ''}">
    <html:form action="batchPack" >
<table>
<tr><td class="setup">Select Box To Use:</td></tr>
<tr><td class="right">
    <html:select name="batchPackForm" property="boxId" styleClass="select">
        <html:optionsCollection name="batchPackForm" property="boxes" value="action" label="display"/>

    </html:select>
    <hr>
</td></tr>
    <tr><td><html:submit property="submit"><bean:message key="button.selectBox"/></html:submit></td></tr>
    </table>
    </html:form>
 </c:if>

 <c:if test="${batchPackForm.boxId !=''}">
    <html:form action="batchPack" focus="orderNum">
    <span class="label">Using box: </span>${batchPackForm.boxDescription}
   <br>
    <span class="label">Scan order to pack</span><html:text property="orderNum" value=""/>
      <html:submit property="submit"><bean:message key="button.pack"/></html:submit>
    </html:form>
   <hr>
    <span class="label">Last Order: </span>${batchPackForm.lastOrder}    <br>
    <br><br>        <p><br></p>
    <html:link action="batchPack" styleClass="button"><bean:message key="button.newBox"/></html:link>


 
 </c:if>

    </body></html>
