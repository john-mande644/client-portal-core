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

    
      <span id="formstart"><html:form action="saveReturn" focus="${returnForm.focus}"></span>

            <html:hidden property="receiveUser"/>
            <html:hidden property="receiveDate"/>
            <html:hidden property="timeIn"/>

           <table><tr><td><span id="ordernum"><html:text property="orderNum" size="15"/></span> </td>
               <td><html:link href="javascript:retrieveURLt('/webapps/geneva/loadReturnOrder.do?ask=COMMAND_NAME_1','returnForm');">Load Order</html:link></td>

               </tr></table>
            <table><tr><td colspan="3">
           <span id="clientList">
                <html:hidden property="orderId"/>

            </span>
            </td><td align="left"></td><td></td><td colspan="2" align="right">Add item</tr>
                <tr><td colspan="5"><b>Carrier Information:</b></td><td class="returnright">Item Number:</td><td><span
                        id="sku"><html:text property="sku" size="10"/></span></td><td><html:link
                        href="javascript:retrieveURLt('/webapps/geneva/addItem.do?ask=COMMAND_NAME_1','returnForm');"><html:img
                        src="/webapps/geneva/images/add.png" border="0"/></html:link></td></tr>
                <tr><td colspan="8"><hr></td></tr>
                <tr>
                    <td class="returnrightsmall">Service:</td>
                    <td><html:select name="returnForm" property="carrier">
                        <html:optionsCollection name="returnForm" property="methodsList" value="action"
                                                label="display"/></html:select></td>
                    <td class="returnrightsmall">B/L No.:</td><td><html:text property="blNum" size="10"/></td>
                    <td class="returnrightsmall">Driver:</td><td><html:text property="driver" size="10"/></td>
                  <td class="returnrightsmall">Ref No.:</td><td><span id="refNum"> <html:text property="refNum" size="10"/></span> </td>

                </tr>
            </table>
       <span id="items">

               <table class="returnitems">
                   <th class="return">Item Number</th>
                   <th class="return">Description</th>
                   <th class="returnsmall">O.H.</th>
                   <th class="return">Status</th>

                   <th class="return">Quantity</th>
                   <th class="returnlarge">Return Reason</th>
                   <c:if test="${sessionScope.returnForm.formItems[0].inventoryId!=null}">
                       <c:forEach var="formItems" items="${sessionScope.returnForm.formItems}" varStatus="itemStatus">
                           <tr>

                               <td><html:text name="formItems" property="inventoryNum" indexed="true" size="10"
                                              readonly="true"/></td>
                               <td><html:text name="formItems" property="description" indexed="true" size="15"
                                              readonly="true"/></td>
                               <td>${formItems.invOnHand}</td>
                               <td><html:select name="formItems" property="itemStatus" indexed="true">
                                   <html:optionsCollection name="formItems" property="statusList" value="action"
                                                           label="display"/>
                               </html:select></td>

                               <td><html:text name="formItems" property="quantity" indexed="true" size="10"/></td>
                               <td><html:select name="formItems" property="returnReason" indexed="true">
                                   <html:optionsCollection name="formItems" property="reasonList" value="action"
                                                           label="display"/>
                               </html:select></td>
                               <td><html:link href="javascript:retrieveURLt('/webapps/geneva/removeItem.do?sku=${formItems.inventoryId}','returnForm');">Remove</html:link></td>

                           </tr>

                           <html:hidden name="formItems" property="createdBy" indexed="true"/>
                           <html:hidden name="formItems" property="createdDate" indexed="true"/>
                           <html:hidden name="formItems" property="inventoryId" indexed="true"/>
                           <html:hidden name="formItems" property="invOnHand" indexed="true"/>
                       </c:forEach>
                   </c:if>
               </table>

       </span>
        <hr>
           <table>
            <table>
                 <tr><td  class="right">Return Resolution: </td>
                <td ><html:select name="returnForm" property="returnResolution">
                    <html:optionsCollection name="returnForm" property="resolution" value="action" label="display"/>
                </html:select></td>
            </tr>
              <tr><td  class="right">Credit Type:</td>
                <td ><html:select name="returnForm" property="creditType">
                    <html:optionsCollection name="returnForm" property="credit" value="action" label="display"/>
                </html:select></td><td class="right">Amount: </td><td><html:text property="amount" size="10"/> </td>
            </tr>
            <tr><td  class="right"><html:checkbox property="newShipCreated" value="yes"/></td>
                <td >New Shipment created?</td>
            </tr>
            <tr><td  class="right">Client Order Ref: </td>
                <td ><html:text property="clientOrderRef"size="15"/> </td>
                <td  class="right">OWD Order Ref: </td>
                <td><html:text property="owdOrderRef" size="15"/> </td>
            </tr>
            </table>
           </table>
            <table>
                            <tr><td class="returnright">Notes:</td><td colspan="7"><html:textarea property="notes" cols="80"
                                                                                      rows="6"/></td></tr>
            </table>
            <html:submit property="submit"><bean:message key="button.geneva.saveReturn" /></html:submit>

        <span id="errors">  <html:errors/>${errors}</span>
        
        <span id="formend"></html:form></span> 





<jsp:include page="/geneva/gfooter.jsp"/>
</body>
</html>

