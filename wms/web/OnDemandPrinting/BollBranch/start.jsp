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

Please scan the order that has a gift message that needs to be printed.
<s:form action="loadOrder">
             <s:textfield name="orderNum" value="" id="autoSubmit"/>
    <s:submit></s:submit>
    </s:form>