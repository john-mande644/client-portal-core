<html>
<head>
  <%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <script type="text/javascript" src="<html:rewrite page="/js/owd.js" />"></script>
 <link href="<html:rewrite page="/css/geneva.css" />" rel="stylesheet" type="text/css">
    <link href="<html:rewrite page="/css/intranet.css" />" rel="stylesheet" type="text/css">
</head>
<body onload="toggleVisibility('throb','hidden','hidden','hidden')">
<jsp:include page="/geneva/gheader.jsp"/>
<div id="rightContent">
    <div id="return">&nbsp
        <html:form action="saveAdjust" focus="sku">

            <html:hidden property="receiveUser"/>
            <html:hidden property="receiveDate"/>
            <html:hidden property="timeIn"/>


            <table>
                <tr>
                    <td width="240px">
                         <span id="clientList">
                         <html:hidden property="orderId"/>

            </span>
                    </td>
                    <td align="left" nowrap></td>
                    <td align="right"></td>
                    <td></td>
                </tr>
                <tr><td><b>Carrier Information:</b></td>
                    <td class="returnright" nowrap>Item Number:</td>
                    <td><span
                            id="sku"><html:text property="sku" size="10" styleClass="form"/></span></td>
                    <td><html:link
                            href="javascript:retrieveURLt('/webapps/geneva/addAdjustItem.do?ask=COMMAND_NAME_1','adjustForm');">
                        <html:img
                                src="/webapps/geneva/images/add.png" border="0"/></html:link></td></tr>
                <tr><td colspan="4"><hr></td></tr></table>
            <table>

                <tr>
                    <td class="returnrightsmall">Service:</td>
                    <td><html:select name="adjustForm" property="carrier" styleClass="form">
                        <html:optionsCollection name="adjustForm" property="methodsList" value="action"
                                                label="display"/></html:select></td>
                    <td class="returnrightsmall">B/L No.:</td><td><html:text property="blNum" size="10"
                                                                             styleClass="form"/></td>
                    <td class="returnrightsmall">Driver:</td><td><html:text property="driver" size="10"
                                                                            styleClass="form"/></td>
                    <td class="returnrightsmall">Ref No.:</td><td><span id="refNum"> <html:text property="refNum"
                                                                                                size="10"
                                                                                                styleClass="form"/></span>
                </td>

                </tr>
            </table>
       <span id="items">

               <table class="returnitems">
                   <th class="return">Item Number</th>
                   <th class="return">Description</th>
                   <th class="returnsmall">O.H.</th>
                   <th class="return">Status</th>

                   <th class="return">Quantity</th>
                   <!-- <th class="return">Return Reason</th>-->
                   <c:if test="${sessionScope.adjustForm.formItems[0].inventoryId!=null}">
                       <c:forEach var="formItems" items="${sessionScope.adjustForm.formItems}" varStatus="itemStatus">
                           <tr>

                               <td><html:text name="formItems" property="inventoryNum" indexed="true" size="10"
                                              readonly="true"/></td>
                               <td><html:text name="formItems" property="description" indexed="true" size="15"
                                              readonly="true"/></td>
                               <td>${formItems.invOnHand}</td>
                               <td><html:select name="formItems" property="itemStatus" indexed="true" styleClass="form">
                                   <html:optionsCollection name="formItems" property="statusList" value="action"
                                                           label="display"/>
                               </html:select></td>

                               <td><html:text name="formItems" property="quantity" indexed="true" size="10"
                                              styleClass="form"/></td>
                               <!-- <td><html:select name="formItems" property="returnReason" indexed="true">
                                   <html:optionsCollection name="formItems" property="reasonList" value="action"
                                                           label="display"/>
                               </html:select></td>-->
                               <td><html:link
                                       href="javascript:retrieveURLt('/webapps/geneva/removeAdjustItem.do?sku=${formItems.inventoryId}','adjustForm');">
                                   Remove</html:link></td>

                           </tr>

                           <html:hidden name="formItems" property="createdBy" indexed="true"/>
                           <html:hidden name="formItems" property="createdDate" indexed="true"/>
                           <html:hidden name="formItems" property="inventoryId" indexed="true"/>
                           <html:hidden name="formItems" property="invOnHand" indexed="true"/>
                       </c:forEach>
                   </c:if>
               </table>

       </span>
            <table>
                <tr><td class="returnright">Notes:</td><td colspan="7"><html:textarea property="notes" cols="80"
                                                                                      rows="6" styleClass="form"/></td>
                </tr>
            </table>
            <html:submit property="submit"><bean:message key="button.geneva.saveReturn"/></html:submit>
        </html:form>
        <span id="errors">  <html:errors/>${errors}</span>


    </div>


</div>
<jsp:include page="/geneva/gfooter.jsp"/>
</body>
</html>
