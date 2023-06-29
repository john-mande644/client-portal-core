<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html:html locale="false">

<link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<html:rewrite page="/js/Ajax.js" module=""/>"></script>


<body class="wrapup" onload="toggleVisibility('throb','hidden','hidden','hidden')">
<div id="header"><p class="header">Call Wrap Up</p></div>

<div id="ccOutcontent">
   <span id="errors"> <span class="errors">
   <c:if test="${errors!='notValid'}">
      ${errors} 
   </c:if> <html:errors/><br></span></span>

     <table width="500px"><tr>
     <td class="right">Campaign:</td><td>${recordOutcomeForm.campaign}</td>
     <td class="right">Agent:</td><td>${recordOutcomeForm.agentid}</td></tr>
      <tr><td></td><td></td><td class="right">Call Id:</td><td>${recordOutcomeForm.callId}</td>

     </tr></table>
    <html:form action="wrapupRecord" >
    <span id="hiddens">
        <html:hidden property="agentid"/>
        <html:hidden property="campaign"/>
        <html:hidden property="callId"/>
        <html:hidden property="orderForm"/>
        <html:hidden property="orderFormv"/>
     </span>
    <span id="calltype">
   <table>
       <c:if test="${recordOutcomeForm.campaign=='VITAMIN HEALTH' || recordOutcomeForm.campaign=='CASS_LABORATORY'|| recordOutcomeForm.campaign=='NEOFLEX' }">
           <tr><td class="right">Customer Id: </td><td><html:text property="customerId"/> </td></tr>
       </c:if>
       <tr><td class="right">
     Result: </td><td>  <html:select property="result" onchange="javascript:retrieveURLt('/callcenter/wrapupLoadType.do?test=hi&crosssell=0','recordOutcomeForm');" styleClass="outcomeForm">
           <html:option value="Select..."></html:option>
           <html:option value="Contact"></html:option>
           <html:option value="No Call"></html:option>
       </html:select>
          </td></tr>


    <tr>
               <td class="right">
       <c:if test="${null!=recordOutcomeForm.typeList}">



      CallType: </td><td>     <html:select name="recordOutcomeForm" property="callTypeId"
                                onchange="javascript:retrieveURLt('/callcenter/wrapupLoadOutcome.do?test=hi&crosssell=0','recordOutcomeForm');"
                                title="Select Call Type" styleClass="outcomeForm">
                       <html:optionsCollection name="recordOutcomeForm" property="typeList" value="action"
                                               label="display"/></html:select>

       </c:if>
      </td>
             </tr>
      <tr><td class="right">
       <c:if test="${null!=recordOutcomeForm.outcomeList}">
          Outcome: </td><td >

               <html:select name="recordOutcomeForm" property="outcome"  title="Select Outcome " styleClass="outcomeForm">
                   <html:optionsCollection name="recordOutcomeForm" property="outcomeList" value="action"
                                           label="display"/></html:select>
            </td><td> <html:checkbox  property="crosssell" value="1" title="Successful CrossSell"/> Upsell
       </c:if>
      </td></tr>  </table>
    <br>
    <table><tr><td class="right">Notes:     </td><td><html:textarea property="notes" rows="10" cols="50" styleClass="outcomeForm"></html:textarea>
        </td></tr>

     <tr>
         <td colspan="2"> <center>
             <table width="300">     <tr>
                <td>Order Number: <html:text property="orderId" size="12"/> </td>
                  <c:if test="${recordOutcomeForm.orderFormv==1}">
                <td>Subtotal: <html:text property="subtotal" size="7"/> </td>
                  </c:if>
            </tr>
        </table>
             </center>
         </td>
     </tr>

        <tr><td class="right" colspan="2"> <html:submit property="submit" /> </td></tr>
        </table>
    </span>

    </html:form>

   <br><p>
</div>
<div id="heads"><img src="<html:rewrite page="/images/heads.gif" module=""/>" alt=""/> </div>
 <div id="throb"> <img src="<html:rewrite page="/images/indicator_white.gif" module=""/>" alt="" /> </div>
</body>
</html:html>