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

<div id="rightContent2">
    <div id="return2">
             <span class="label">Adjust Id: </span>${adjustForm.receiveId} <br>
            <span class="label">Transaction Number: </span>${adjustForm.transactionNum}<br>
        <span class="label">Adjust User: </span>${adjustForm.receiveUser} <br>
         <table><tr><td colspan="3">
              <tr><td colspan="5"><b>Carrier Information:</b></td><td class="returnright"></td><td></td></tr>
                <tr><td colspan="8"><hr></td></tr>
                <tr>
                    <td class="returnrightsmall">Service:</td>
                    <td>${adjustForm.carrier}</td>
                    <td class="returnrightsmall">B/L No.:</td><td>${adjustForm.blNum}</td>
                    <td class="returnrightsmall">Driver:</td><td>${adjustForm.driver}</td>
                  <td class="returnrightsmall">Ref No.:</td><td><span id="refNum"> ${adjustForm.refNum}</span> </td>

                </tr>
            </table>
       <span id="items">

               <table class="returnitems">
                   <th class="return">Item Number</th>
                   <th class="return">Description</th>

                   <th class="return">Status</th>
                   <th class="return">Quantity</th>
                   

                       <c:forEach var="formItems" items="${adjustForm.formItems}" varStatus="itemStatus">
                           <tr>

                               <td>${formItems.inventoryNum}</td>
                               <td>${formItems.description}</td>
                               <td>${formItems.itemStatus}</td>

                               <td>${formItems.quantity}</td>


                           </tr>


                       </c:forEach>

               </table>

       </span>
            <table>
                <tr><td class="returnright">Notes:</td><td colspan="7">${adjustForm.notes}</td></tr>
            </table>


        <span id="errors">  <html:errors/>${errors}</span>
      <html:link action="startAdjust">back</html:link>

    </div>


</div>

<jsp:include page="/geneva/gfooter.jsp"/>
</body>
</html>
