<%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link href="<html:rewrite page="/css/geneva.css" />" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<html:rewrite page="/js/owd.js" />"></script>

    <title>Geneva Warehouse</title>
</head>

<body onload="toggleVisibility('throb','hidden','hidden','hidden')">
 <html:form action="warehouse">
 <div id="container">
   <div id=header>
     <div id="hcenter"> 
       <center>
           <html:link href="warehouse.do">
           <img src="<html:rewrite page="/images/orders.png"/>" alt="Orders" border=0/></html:link>
           <html:link href="inventory.do"><img src="<html:rewrite page="/images/inventory.png"/>" alt="Inventory" border=0/></html:link>
           <img src="<html:rewrite page="/images/Reports.png"/>" alt="Reports"/></center>

       Search:       
        <html:select name="genevaForm" property="criteria">
                    <html:optionsCollection name="genevaForm" property="criterias" value="action" label="display"/>
                </html:select>
         <html:text property="search"/><html:submit property="submit"><bean:message key="button.geneva.submit"/></html:submit>  
     
     </div>
   </div>
   <div id="bottom">

       <c:if test="${sessionScope.genevaForm.formItems[0].orderId!=null}">

        <html:hidden property="elementId" value="0"></html:hidden> 
           <c:forEach var="formItems" items="${sessionScope.genevaForm.formItems}">
               <div class="item">
               <span id="${formItems.orderRefNum}">
                  <div class="top">
                   <div class="ref">${formItems.orderRefNum}</div>

                   <div class="shipvia">Ship Via: ${formItems.shipService}</div>
                    <div class="weight">${formItems.weight} <bean:message key="label.geneva.kg"/></div>
                   <div class="links"><html:link href="javascript:retrieveURLt('/webapps/geneva/loadOrder.do?elementId=${formItems.elementId}&formItems[${formItems.elementId}].showOrder=${formItems.showOrder}','genevaForm');">Load Order</html:link>
                       <html:link href="javascript:retrieveURLt('/webapps/geneva/viewShipOrder.do?elementId=${formItems.elementId}&formItems[${formItems.elementId}].enterShip=${formItems.enterShip}','genevaForm');">Ship</html:link>
                       <html:link href="printOrder.do?printid=${formItems.orderId}" >Print</html:link>
                   </div>
                         

                      </div>
                   <div style="clear:both;"></div>
                   <c:if test="${formItems.enterShip=='yes'}">
                       <div class="ship">
                           ${outcome} ${error} <html:errors/>
                         <c:if test="${formItems.isShipped!='yes'}">
                         <table class="order">
                             <tr>
                                 <td class="right" colspan="2"><bean:message key="label.geneva.track"/> </td><td colspan="2"><html:text property="track" size="20"/> </td>
                                 <td class="right"><bean:message key="label.geneva.method"/></td><td>
                                 <html:select name="genevaForm" property="method">
                    <html:optionsCollection name="genevaForm" property="methods" value="action" label="display"/>
                </html:select>

                                 </td>
                                 </tr>
                             <tr><td class="info" colspan=7>Enter multiple tracking numbers seperated by comma's (track1,track2,...)</td></tr><tr>
                                 <td class="right"><bean:message key="label.geneva.packs"/></td><td><html:text property="packs" size="5"/> </td>
                             <td class="right"><bean:message key="label.geneva.weight"/> </td><td><html:text property="weight" size="5"/><bean:message key="label.geneva.kg"/></td>
                                 <td class="right"><bean:message key="label.geneva.cost"/></td><td><html:text property="cost" size="5"/>
                         <html:link href="javascript:retrieveURLt('/webapps/geneva/shipOrder.do?elementId=${formItems.elementId}&formItems[${formItems.elementId}].enterShip=${formItems.enterShip}','genevaForm');">Enter Ship Info</html:link></td>
                             </tr>

                         </table>
                         </c:if>
                         <c:if test="${formItems.isShipped=='yes'}">
                          <table class="order">
                              <tr>
                                  <td><bean:message key="label.geneva.shippedon"/></td><td>${formItems.order.shippedDate}</td>
                              </tr>
                          </table>

                         </c:if>
                       </div>


                   </c:if>
                   <c:if test="${formItems.showOrder=='yes'}">
                       <div class="middle">

                        ${formItems.order.shipinfo.shipFirstName}&nbsp;${formItems.order.shipinfo.shipLastName}<br>
                           ${formItems.order.shipinfo.shipAddressOne}<br>
                           ${formItems.order.shipinfo.shipAddressTwo}<br>
                           ${formItems.order.shipinfo.shipCity}<br>
                           ${formItems.order.shipinfo.shipCountry}<br>
                           <br>
                               <center>

                            <c:forEach var="item" items="${formItems.order.lineitems}">
                            <table class=items cellspacing="0">
                            <tr class="items"> <td class="q">
                                   ${item.quantityActual}</td>
                                <td class="itemnum"> ${item.inventoryNum}
                                 </td>
                                <td calss="desc">${item.description}
                             </td></tr>   
                               </table>
                                <br>
                           </c:forEach>

                                   </center>
                       </div>
                   </c:if>

                   
                       
                       <html:hidden name="formItems" property="country" indexed="true"/>


              </span>
               </div>


           </c:forEach>





       </c:if>

   </div>

   </div>
 <div id="throb"> <img src="<html:rewrite page="/images/indicator_white.gif"/>" alt="" /> </div>
   </html:form>
</body>
</html>