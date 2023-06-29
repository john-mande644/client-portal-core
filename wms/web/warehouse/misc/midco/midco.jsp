<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

 <head>
     <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
 </head>
 <body>

 <a href="/wms/miscMenu/start.action" class="button">MENU</a> <P class="button>">
     <s:if test="hasActionErrors()">
     <div id="errorDiv" style="padding-left: 10px; margin-bottom: 5px">
     <span class="error">
     <s:iterator value="actionErrors">
     <span class="errorMessage"><s:property escape="false" /></span>
     </s:iterator>
     </span>
     </div>
     </s:if>
  <s:actionmessage/>
 <s:if test="serialnum==null">
         <s:form action="getSerial.action">
     Please Scan the Serial number off the DTA <br>
         <s:textfield name="serialnum" id="autoSubmit" value=""/>
      <s:submit></s:submit>
         </s:form>


 </s:if>
 <s:else>
     <s:form action="getUa.action">
     This is the Serial Number: ${serialnum}  <br>
         Please Scan the UA number off the DTA  <br>
     <s:hidden name="serialnum"></s:hidden>
             <s:textfield name="uanum" id="autoSubmit" value=""/>
          <s:submit></s:submit>
             </s:form>
   </s:else>

