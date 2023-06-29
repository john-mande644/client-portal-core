<%@ taglib prefix="s" uri="/struts-tags" %>

<jsp:include page="/rfapps/includes/androidStuff.jsp"/>
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

Please load the Card in the printer you are using and scan the barcode on the printer.
<s:form action="printMessage">
    <s:hidden name="orderId"/>
    <s:textfield name="printer" value="" id="autoSubmit"/>
    <s:submit></s:submit>
</s:form>