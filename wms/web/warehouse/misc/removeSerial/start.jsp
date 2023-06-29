<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

 <head>
     <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
 </head>
 <body onload="document.getElementById('autoSubmit').focus();">

 <a href="/wms/miscMenu/start.action" class="button">MENU</a> <P class="button>">
     <s:if test="hasActionErrors()">
         <script>
                try{
                    javautil.playErrorSound();
                }catch(err){

                }
            </script>
     <div id="errorDiv" style="padding-left: 10px; margin-bottom: 5px">
     <span class="error">
     <s:iterator value="actionErrors">
     <span class="errorMessage"><s:property escape="false" /></span>
     </s:iterator>
     </span>
     </div>
     </s:if>
  <s:actionmessage/>

         <s:form action="remove.action">
     Please Scan the Serial number off the DTA <br>
         <s:textfield name="serial" id="autoSubmit" value="" />
      <s:submit></s:submit>
         </s:form>


</body>
</html>
