<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
 <s:head theme="ajax" debug="false"/>
<link rel="Stylesheet" href="<s:url value="/styles/screendirect.css" />" type="text/css"/>
<link rel="Stylesheet" href="<s:url value="/styles/test.css" />" type="text/css"/>
<s:set name="redirectAction" value="%{'edit.action'}" scope="request"/>
<s:set name="redirectNamespace" value="%{'/autoship'}" scope="request"/>
    <script type="text/javascript">
<!--
function resettoggle(id) {
var e = document.getElementById(id);
e.style.display = 'none';
}

function toggle_visibility(id) {
var e = document.getElementById(id);
if(e.style.display == 'none')
e.style.display = 'block';
else
e.style.display = 'none';
}
//-->
</script>
</head>
<body onload="resettoggle('shiptotable');">
<s:include value="/miniheader.jsp"></s:include>
<div style="text-align:center;">Autoship Manager</div>
<s:actionerror/>
<s:actionmessage/>
<table width="100%"><tr><td width="30%"  valign=top>
<div class="data">
<h3>Customer/Billing Info</h3>

    <s:form action="crud!save.action" method="post" >

    <s:textfield name="as.billName" value="%{as.billName}" label="%{getText('as.billName')}" size="40"/>
    <s:textfield name="as.billAddressOne" value="%{as.billAddressOne}" label="%{getText('as.billAddressOne')}" size="40"/>
    <s:textfield name="as.billAddressTwo" value="%{as.billAddressTwo}" label="%{getText('as.billAddressTwo')}" size="40"/>
    <s:textfield name="as.billCity" value="%{as.billCity}" label="%{getText('as.billCity')}" size="40"/>
    <s:textfield name="as.billState" value="%{as.billState}" label="%{getText('as.billState')}" size="40"/>

    <s:textfield name="as.billZip" value="%{as.billZip}" label="%{getText('as.billZip')}" size="40"/>
    <s:textfield name="as.billCountry" value="%{as.billCountry}" label="%{getText('as.billCountry')}" size="40"/>
    <s:textfield name="as.billPhone" value="%{as.billPhone}" label="%{getText('as.billPhone')}" size="40"/>
    <s:textfield name="as.billEmail" value="%{as.billEmail}" label="%{getText('as.billEmail')}" size="40"/>
    <s:textfield name="as.billName" value="%{as.billName}" label="%{getText('as.bname')}" size="40"/>
    <tr><td colspan="2">&nbsp;</td></tr>

    <s:textfield name="as.ccNum" value="%{as.ccNum}" label="%{getText('as.ccNum')}" size="40"/>
    <s:textfield name="as.ccExpMon" value="%{as.ccExpMon}" label="%{getText('as.ccExpMon')}" size="40"/>
    <s:textfield name="as.ccExpYear" value="%{as.ccExpYear}" label="%{getText('as.ccExpYear')}" size="40"/>
      <tr><td colspan="2"><p>&nbsp;</p>
      <table width="100%"><tr><td align="left">
      </td><td align="right">
          <s:submit theme="simple"  cssStyle="width:auto;float:right;" cssClass="actions" name="  Save Billing Information  " value="  Save Billing Information  " />
      </td></tr></table>
         </td></tr>
</s:form>

</div>
</td><td  valign=top>
<div class="data">
<h3>Shipment #1 (${as.autoShipId})</h3>

    <s:form action="crud!save.action" method="post" >

    <s:textfield name="as.billName" value="%{as.billName}" label="%{getText('as.bname')}" size="40"/>
    <s:textfield name="as.billAddressOne" value="%{as.billName}" label="%{getText('as.bname')}" size="40"/>
    <s:textfield name="as.billName" value="%{as.billName}" label="%{getText('as.bname')}" size="40"/>
    <s:textfield name="as.billName" value="%{as.billName}" label="%{getText('as.bname')}" size="40"/>
    <s:textfield name="as.billName" value="%{as.billName}" label="%{getText('as.bname')}" size="40"/>
        <tr></tr><td colspan=2>
    <hr>
    <table width="100%">
        <tr><th>SKU</th><th>Description</th><th>Quantity</th><th>unitPrice</th></tr>
     <s:iterator value="as.owdOrderAutoItems" >
     <tr><td><s:textfield theme="simple" name="sku" value="%{sku}" label="%{getText('sku')}" size="10"/></td>
         <td>Description</td>
         <td><s:textfield theme="simple" name="quantity" value="%{quantity}" size="5"/></td>
         <td><s:textfield theme="simple" name="unitPrice" value="%{unitPrice}" label="%{getText('unitPrice')}" size="10"/></td>
                        </tr>
                        </s:iterator>
         <tr><td colspan="4"><hr>Add New Item<BR></td>
                        </tr>
           <tr><td><s:textfield theme="simple" name="newsku" value="%{newsku}" label="%{getText('sku')}" size="10"/></td>
         <td></td>
         <td><s:textfield theme="simple" name="newquantity" value="%{newquantity}" size="5"/></td>
         <td><s:textfield theme="simple" name="newunitPrice" value="%{newunitPrice}" label="%{getText('unitPrice')}" size="10"/></td>
                        </tr>
    </table>
    <hr>
      <s:tabbedPanel id="test" >
       <s:div id="one" label="Ship To" theme="ajax" labelposition="top" >

           <table class="wwFormTable" border="0" >
         <tbody> <tr><td colspan="2" align="left" valign="top">
             <s:checkbox cssStyle="width:auto;" theme="simple" name="hello" value="1" label="" onclick="toggle_visibility('shiptotable');" /> <span style="font-size:1.2em;">Ship to billing address</span>
             </td></tr></tbody>

<tbody id="shiptotable" >
       <s:textfield name="as.shipName" value="%{as.shipName}" label="%{getText('as.shipName')}" size="40"/>
    <s:textfield name="as.shipAddressOne" value="%{as.shipAddressOne}" label="%{getText('as.shipAddressOne')}" size="40"/>
    <s:textfield name="as.shipAddressTwo" value="%{as.shipAddressTwo}" label="%{getText('as.shipAddressTwo')}" size="40"/>
    <s:textfield name="as.shipCity" value="%{as.shipCity}" label="%{getText('as.shipCity')}" size="40"/>
    <s:textfield name="as.shipState" value="%{as.shipState}" label="%{getText('as.shipState')}" size="40"/>

    <s:textfield name="as.shipZip" value="%{as.shipZip}" label="%{getText('as.shipZip')}" size="40"/>
    <s:textfield name="as.shipCountry" value="%{as.shipCountry}" label="%{getText('as.shipCountry')}" size="40"/>
    <s:textfield name="as.shipPhone" value="%{as.shipPhone}" label="%{getText('as.shipPhone')}" size="40"/>
    <s:textfield name="as.shipEmail" value="%{as.shipEmail}" label="%{getText('as.shipEmail')}" size="40"/>
               </tbody>
           </table>
       </s:div>
       <s:div id="two" label="History" theme="ajax" labelposition="top"  >
           <ul>
            <s:iterator value="as.owdOrderAutoHistories" >
                <li><s:date name="created" format="MM/dd/yyyy" />/${message}
                </s:iterator>
                </ul>
       </s:div>  <s:div id="three" label="Orders" theme="ajax" labelposition="top"  >
           CREATED<ul>
           <s:iterator value="as.createdOrders" >
             <li><A target="_neworder" HREF="http://service.owd.com/webapps/extranet/extranet.jsp?act=cngMgr&extranet.currmgr=Orders&ordermgr=edit&oid=${orderId}">${orderNum}/${orderRefnum}</A> - ${orderStatus} - ${orderTotal}</s:iterator>
                  </ul>
                <ul>
               <HR>FROM<s:iterator value="as.sourceOrders" >
            <li>${orderNum}   </s:iterator>     </ul>
       </s:div>
    </s:tabbedPanel>
    <p>&nbsp;</p>
      <table width="100%"><tr><td align="left">
         <s:submit theme="simple"  cssStyle="width:auto;" cssClass="actions" name=" Cancel  " value="  Cancel  " />
         <s:submit theme="simple"  cssStyle="width:auto;" cssClass="actions" name=" Suspend  " value="  Suspend  " />
         <s:submit theme="simple"  cssStyle="width:auto;" cssClass="actions" name=" Clone  " value="  Clone  " />
      </td><td align="right">
         <s:submit theme="simple"  cssStyle="width:auto;float:right;" cssClass="actions" name=" Save  " value="  Save  " />
      </td></tr></table>
         </td></tr>



      
    <!-- tabs -->
</s:form>

</div>
<div class="data">
<h3 style="color:#0000FF;">Add A Shipment</h3>

    <s:form action="crud!save.action" method="post" >


</s:form>

</div>
</td></tr></table>

<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

</BODY>
</HTML>
    
