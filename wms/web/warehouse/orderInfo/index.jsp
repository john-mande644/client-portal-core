<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
     <link href="/dojo-release-1.3.1/dijit/themes/tundra/tundra.css" rel="stylesheet" type="text/css">
       <script type="text/javascript" src="/dojo-release-1.3.1/dojo/dojo.js"
	    djConfig="parseOnLoad:true, isDebug:false"></script>
     <script type="text/javascript">
			dojo.require("dijit.TitlePane");
            </script>
    <style type="text/css">
        div {
            padding: 5px;

        }
        .pack{
            padding-left:15px;
            background:#eeeeee;
            margin-bottom:15px;
        }
        .onepackage{
            padding-left:35px;
            border:solid black 3px;
            margin-bottom:7px;
        }
        .packages{
            padding-left:25px;
        }
        .lines{
            padding-left:45px;
        }
        .title {
            font-weight: bold;
            font-size: 24px;

        }
         .voided{
           background: #ff3333 
        }
        .billing {
            width: 40%;
            float: left;
        }

        .shipping {
            width: 40%;
            float: right;

        }

        .PackingInfo {
            clear: left;
        }
        .labellarge{
            font-weight:bold;
            width:140px;
            text-align:right;
            display:inline-block;
            padding-right: 13px;

        }
         .label{
            font-weight:bold;
            width:75px;
            text-align:right;
            display:inline-block;
            padding-right: 13px;

        }
           .labelmore{
            font-weight:bold;
            width:200px;
            text-align:right;
            display:inline-block;
            padding-right: 13px;

        }
        .items th{
           padding: 3px;
        }
        .items td{
           padding:  5px;
           border:  1px solid
        }
        .hold th{
            padding: 3px;
        }
        .hold td{
            padding: 3px;
            border:1px solid;

        }
        .events th{
            padding: 3px;
        }
        .events td{
            padding: 3px;
            border:1px solid;

        }
        .put th{
            padding: 3px;
        }
        .put td{
            padding: 3px;
            border:1px solid;

        }
        .onHold1{
            background-color:red;
        }
        .ohHold0{
            background-color:green;
        }
        .groupName{
         color:darkslateblue;
        }

    </style>
</head>
<body class="tundra">
<s:form action="load">
 OWD Order Num:    <s:textfield name="orderNum" theme="simple"/>   <br>
    or<br>

    Order ID <s:textfield name="orderId" theme="simple"/>
    <s:submit/>
</s:form>
 <div class="errors">
     <s:actionerror/>
 </div>
<s:if test="order!= null">
    <div class="topWrapper">
        <div class="orderInfo">
            <span class="title">  <s:property value="order.client.CompanyName"/>&nbsp; <span class="groupName"> <s:property value="order.groupName"/></span> </span><br>
           <span class="labellarge"> Owd Order Num: </span><a href="https://service.owd.com/webapps/extranet/extranet.jsp?act=cngCid&cid=<s:property value="order.client.clientId"/>&ordermgr=edit&oid=<s:property value="order.orderId" />" target="_blank"><s:property value="order.orderNum"/></a><br>
          <span class="labellarge">  Order Ref Num: </span><s:property value="order.orderRefnum"/><br>
          <span class="labellarge"> Order Ship Method: </span><s:property value="order.shipinfo.carrService"/><br>
          <span class="labellarge">  Order Status: </span><s:property value="order.orderStatus"/><br>
            <span class="labellarge">  DC Hold Status: </span>
            <s:if test="order.holdinfo.isOnWhHold ==1">
                True

            </s:if>
            <s:else>
                False
            </s:else>
            <br>
           <span class="labellarge"> Order Picked By:</span> <s:property value="order.pickBy"/><br>
           <s:if test="order.pickBy != null">
                   <div id="picktimes" dojoType="dijit.TitlePane" title="Pick Time Details" refreshOnShow="true" open="false" width="500" href="/wms/orderInfo/pick.action?orderId=<s:property value="order.orderId"/>">
           </s:if>

        </div>
        <div class="billing">
            <span class="title">   Billing Info:<br>    </span>
            <span class="label"> Name:</span> <s:property value="order.billFirstName"/> <s:property value="order.billLastName"/> <br>
            <span class="label">Company:</span> <s:property value="order.billCompanyName"/> <br>
            <span class="label">Address 1:</span> <s:property value="order.BillAddressOne"/><br>
            <span class="label">Address 2: </span><s:property value="order.BillAddressTwo"/> <br>
           <span class="label"> City:</span> <s:property value="order.BillCity"/> <br>
           <span class="label"> State:</span> <s:property value="order.BillState"/> <br>
           <span class="label"> Zip:</span> <s:property value="order.BillZip"/> <br>
           <span class="label"> Country:</span><s:property value="order.BillCountry"/> <br>
           <span class="label"> Phone:</span> <s:property value="order.billPhoneNum"/> <br>


        </div>
        <div class="shipping">
            <span class="title">  Shipping Info:<br></span>
           <span class="label"> Name:</span> <s:property value="order.shipinfo.shipFirstName"/> <s:property value="order.shipinfo.shipLastName"/>
            <br>
          <span class="label">  Company:</span> <s:property value="order.shipinfo.shipCompanyName"/> <br>
           <span class="label"> Address 1:</span> <s:property value="order.shipinfo.shipAddressOne"/><br>
           <span class="label"> Address 2:</span> <s:property value="order.shipinfo.shipAddressTwo"/> <br>
           <span class="label"> City:</span> <s:property value="order.shipinfo.shipCity"/> <br>
           <span class="label"> State:</span> <s:property value="order.shipinfo.shipState"/> <br>
           <span class="label"> Zip:</span> <s:property value="order.shipinfo.shipZip"/> <br>
           <span class="label"> Country:</span><s:property value="order.shipinfo.shipCountry"/><br>
           <span class="label"> Phone: </span><s:property value="order.shipinfo.shipPhoneNum"/> <br>



        </div>
    </div>
        <div style="clear:both"></div>
        <s:if test="putAways.size>0">
        <div id="putAways" dojoType="dijit.TitlePane" title="Put Aways" refreshOnShow="false" open="false" >
              <table class="put">
                  <thead>
                  <th>Packer</th>
                  <th>Picker</th>
                  <th>Inventory Id</th>
                  <th>Inventory Num</th>
                  <th>qty</th>
                  <th>Reported Time</th>
                  </thead>
                  <s:iterator value="putAways">
                      <tr>
                          <td><s:property value="packer"/></td>
                          <td><s:property value="picker"/></td>
                          <td><s:property value="inventory_id"/></td>
                          <td><s:property value="inventory_num"/></td>
                          <td><s:property value="qty"/></td>
                          <td><s:property value="reported_time"/></td>
                      </tr>
                  </s:iterator>
              </table>
        </div>
        </s:if>
        <div id="events" dojoType="dijit.TitlePane" title="Order Events" refreshOnShow="false" open="false" >
          <table class="events">
             <thead>
             <th>Creator</th>
             <th>Time Stamp</th>
             <th> Message</th>
             </thead>
            <s:iterator value="events">
                <tr>
                    <td><s:property value="creator"/> </td>
                    <td><s:property value="eventStamp"/> </td>
                    <td><s:property value="message"/> </td>
                </tr>
            </s:iterator>



          </table>

        </div>
        <s:if test="order.holdinfo != null">


        <div id="holdinfo" dojoType="dijit.TitlePane" title="Hold Info" refreshOnShow="false" open="false" >
              <table class="hold">
                  <thead>
                  <th>
                      On Hold
                  </th>
                  <th>
                    Created By
                  </th>
                  <th>
                      Cleared By

                  </th>
                  <th>
                      Hold Reason
                  </th>
                  <th>
                      Hold Notes
                  </th>
                  <th>
                      Created DAte
                  </th>
                  <th>
                      resolution Date
                  </th>
                  <th>
                      resolution type
                  </th>
                  <th>resolution note</th>
                  </thead>
            <s:iterator value="order.holdinfo">
                      <tr class="onHold<s:property value="isOnWhHold"/>">
                          <td><s:property value="isOnWhHold"/> </td>
                          <td><s:property value="createdBy"/> </td>
                          <td><s:property value="clearedBy"/> </td>
                          <td><s:property value="whHoldReason"/> </td>
                          <td><s:property value="whHoldNotes"/> </td>
                          <td><s:property value="createdDate"/> </td>
                          <td><s:property value="resolutionDate"/> </td>
                          <td><s:property value="resolutionType"/> </td>
                          <td><s:property value="resNote"/> </td>
                      </tr>
            </s:iterator>
              </table>

        </div>
        </s:if>
        <div id="alltheotherinfo" dojoType="dijit.TitlePane" title="More Info Than you Require" refreshOnShow="false" open="false" >
         <div class="middlewarpper">

             <div class="billing">
                 <span class="title">OWD_ORDER Info</span>   <br>
            <span class='labelmore'>orderId:</span> <s:property value="order.orderId"/> <br>
            <span class='labelmore'>clientFkey:</span> <s:property value="order.clientFkey"/> <br>
            <span class='labelmore'>customerFkey:</span> <s:property value="order.customerFkey"/> <br>
            <span class='labelmore'>orderNum:</span> <s:property value="order.orderNum"/> <br>
            <span class='labelmore'>orderRefnum:</span> <s:property value="order.orderRefnum"/> <br>
            <span class='labelmore'>orderNumBarcode:</span> <s:property value="order.orderNumBarcode"/> <br>
            <span class='labelmore'>poNum:</span> <s:property value="order.poNum"/> <br>
            <span class='labelmore'>ordersalesperson:</span> <s:property value="order.ordersalesperson"/> <br>
            <span class='labelmore'>actualOrderDate:</span> <s:property value="order.actualOrderDate"/> <br>
            <span class='labelmore'>orderSubTotal:</span> <s:property value="order.orderSubTotal"/> <br>
            <span class='labelmore'>discount:</span> <s:property value="order.discount"/> <br>
            <span class='labelmore'>taxPct:</span> <s:property value="order.taxPct"/> <br>
            <span class='labelmore'>taxAmount:</span> <s:property value="order.taxAmount"/> <br>
            <span class='labelmore'>shipHandlingFee:</span> <s:property value="order.shipHandlingFee"/> <br>
            <span class='labelmore'>orderTotal:</span> <s:property value="order.orderTotal"/> <br>
            <span class='labelmore'>paidAmount:</span> <s:property value="order.paidAmount"/> <br>
            <span class='labelmore'>paidDate:</span> <s:property value="order.paidDate"/> <br>
            <span class='labelmore'>orderBalance:</span> <s:property value="order.orderBalance"/> <br>
            <span class='labelmore'>customerNum:</span> <s:property value="order.customerNum"/> <br>
            <span class='labelmore'>customerVendorNo:</span> <s:property value="order.customerVendorNo"/> <br>
            <span class='labelmore'>billLastName:</span> <s:property value="order.billLastName"/> <br>
            <span class='labelmore'>billFirstName:</span> <s:property value="order.billFirstName"/> <br>
            <span class='labelmore'>billAddressOne:</span> <s:property value="order.billAddressOne"/> <br>
            <span class='labelmore'>billAddressTwo:</span> <s:property value="order.billAddressTwo"/> <br>
            <span class='labelmore'>billCity:</span> <s:property value="order.billCity"/> <br>
            <span class='labelmore'>billState:</span> <s:property value="order.billState"/> <br>
            <span class='labelmore'>billZip:</span> <s:property value="order.billZip"/> <br>
            <span class='labelmore'>billCountry:</span> <s:property value="order.billCountry"/> <br>
            <span class='labelmore'>billCompanyName:</span> <s:property value="order.billCompanyName"/> <br>
            <span class='labelmore'>billTitle:</span> <s:property value="order.billTitle"/> <br>
            <span class='labelmore'>billPhoneNum:</span> <s:property value="order.billPhoneNum"/> <br>
            <span class='labelmore'>billFaxNum:</span> <s:property value="order.billFaxNum"/> <br>
            <span class='labelmore'>billEmailAddress:</span> <s:property value="order.billEmailAddress"/> <br>
            <span class='labelmore'>prtPickReqd:</span> <s:property value="order.prtPickReqd"/> <br>
            <span class='labelmore'>prtInvoiceReqd:</span> <s:property value="order.prtInvoiceReqd"/> <br>
            <span class='labelmore'>prtPackReqd:</span> <s:property value="order.prtPackReqd"/> <br>
            <span class='labelmore'>prtPriceonslipReqd:</span> <s:property value="order.prtPriceonslipReqd"/> <br>
            <span class='labelmore'>isBackorder:</span> <s:property value="order.isBackorder"/> <br>
            <span class='labelmore'>backorderOrderNum:</span> <s:property value="order.backorderOrderNum"/> <br>
            <span class='labelmore'>originalOrderNum:</span> <s:property value="order.originalOrderNum"/> <br>
            <span class='labelmore'>isFutureShip:</span> <s:property value="order.isFutureShip"/> <br>
            <span class='labelmore'>isVoid:</span> <s:property value="order.isVoid"/> <br>
            <span class='labelmore'>postDate:</span> <s:property value="order.postDate"/> <br>
            <span class='labelmore'>createdDate:</span> <s:property value="order.createdDate"/> <br>
            <span class='labelmore'>createdBy:</span> <s:property value="order.createdBy"/> <br>
            <span class='labelmore'>modifiedDate:</span> <s:property value="order.modifiedDate"/> <br>
            <span class='labelmore'>modifiedBy:</span> <s:property value="order.modifiedBy"/> <br>
            <span class='labelmore'>rowIsLocked:</span> <s:property value="order.rowIsLocked"/> <br>
            <span class='labelmore'>ccisGift:</span> <s:property value="order.ccisGift"/> <br>
            <span class='labelmore'>giftMessage:</span> <s:property value="order.giftMessage"/> <br>
            <span class='labelmore'>giftWrapFee:</span> <s:property value="order.giftWrapFee"/> <br>
            <span class='labelmore'>reported:</span> <s:property value="order.reported"/> <br>
            <span class='labelmore'>ccNum:</span> <s:property value="order.ccNum"/> <br>
            <span class='labelmore'>ccExpMon:</span> <s:property value="order.ccExpMon"/> <br>
            <span class='labelmore'>ccExpYear:</span> <s:property value="order.ccExpYear"/> <br>
            <span class='labelmore'>pickStatus:</span> <s:property value="order.pickStatus"/> <br>
            <span class='labelmore'>paymentStatus:</span> <s:property value="order.paymentStatus"/> <br>
            <span class='labelmore'>pickBy:</span> <s:property value="order.pickBy"/> <br>
            <span class='labelmore'>coupon:</span> <s:property value="order.coupon"/> <br>
            <span class='labelmore'>backorderLevel:</span> <s:property value="order.backorderLevel"/> <br>
            <span class='labelmore'>discountPct:</span> <s:property value="order.discountPct"/> <br>
            <span class='labelmore'>shippedCost:</span> <s:property value="order.shippedCost"/> <br>
            <span class='labelmore'>shippedWeight:</span> <s:property value="order.shippedWeight"/> <br>
            <span class='labelmore'>trackingNums:</span> <s:property value="order.trackingNums"/> <br>
            <span class='labelmore'>packagesShipped:</span> <s:property value="order.packagesShipped"/> <br>
            <span class='labelmore'>shippedDate:</span> <s:property value="order.shippedDate"/> <br>
            <span class='labelmore'>orderStatus:</span> <s:property value="order.orderStatus"/> <br>
            <span class='labelmore'>facilityCode:</span> <s:property value="order.facilityCode"/> <br>
                 <span class='labelmore'>groupName:</span> <s:property value="order.groupName"/> <br>
            </div>
            <div class="shipping">
                <span class="title">OWD_ORDER_SHIP_INFO</span> <br>
                <span class='labelmore'>orderShipInfoId:</span> <s:property value="order.shipinfo.orderShipInfoId"/> <br>
                <span class='labelmore'>scheduledShipDate:</span> <s:property value="order.shipinfo.scheduledShipDate"/> <br>
                <span class='labelmore'>shipLastName:</span> <s:property value="order.shipinfo.shipLastName"/> <br>
                <span class='labelmore'>shipFirstName:</span> <s:property value="order.shipinfo.shipFirstName"/> <br>
                <span class='labelmore'>shipAddressOne:</span> <s:property value="order.shipinfo.shipAddressOne"/> <br>
                <span class='labelmore'>shipAddressTwo:</span> <s:property value="order.shipinfo.shipAddressTwo"/> <br>
                <span class='labelmore'>shipCity:</span> <s:property value="order.shipinfo.shipCity"/> <br>
                <span class='labelmore'>shipState:</span> <s:property value="order.shipinfo.shipState"/> <br>
                <span class='labelmore'>shipZip:</span> <s:property value="order.shipinfo.shipZip"/> <br>
                <span class='labelmore'>shipCountry:</span> <s:property value="order.shipinfo.shipCountry"/> <br>
                <span class='labelmore'>shipCountryRefNum:</span> <s:property value="order.shipinfo.shipCountryRefNum"/> <br>
                <span class='labelmore'>shipCompanyName:</span> <s:property value="order.shipinfo.shipCompanyName"/> <br>
                <span class='labelmore'>shipTitle:</span> <s:property value="order.shipinfo.shipTitle"/> <br>
                <span class='labelmore'>shipPhoneNum:</span> <s:property value="order.shipinfo.shipPhoneNum"/> <br>
                <span class='labelmore'>shipFaxNum:</span> <s:property value="order.shipinfo.shipFaxNum"/> <br>
                <span class='labelmore'>shipEmailAddress:</span> <s:property value="order.shipinfo.shipEmailAddress"/> <br>
                <span class='labelmore'>carrService:</span> <s:property value="order.shipinfo.carrService"/> <br>
                <span class='labelmore'>carrServiceRefNum:</span> <s:property value="order.shipinfo.carrServiceRefNum"/> <br>
                <span class='labelmore'>carrFreightTerms:</span> <s:property value="order.shipinfo.carrFreightTerms"/> <br>
                <span class='labelmore'>carrFreightTermsRefNum:</span> <s:property value="order.shipinfo.carrFreightTermsRefNum"/> <br>
                <span class='labelmore'>thirdPartyRefnum:</span> <s:property value="order.shipinfo.thirdPartyRefnum"/> <br>
                <span class='labelmore'>ssAddlHand:</span> <s:property value="order.shipinfo.ssAddlHand"/> <br>
                <span class='labelmore'>ssDeclaredValue:</span> <s:property value="order.shipinfo.ssDeclaredValue"/> <br>
                <span class='labelmore'>declaredValue:</span> <s:property value="order.shipinfo.declaredValue"/> <br>
                <span class='labelmore'>ssProofDelivery:</span> <s:property value="order.shipinfo.ssProofDelivery"/> <br>
                <span class='labelmore'>ssCallTag:</span> <s:property value="order.shipinfo.ssCallTag"/> <br>
                <span class='labelmore'>callTag:</span> <s:property value="order.shipinfo.callTag"/> <br>
                <span class='labelmore'>ssCod:</span> <s:property value="order.shipinfo.ssCod"/> <br>
                <span class='labelmore'>codCharge:</span> <s:property value="order.shipinfo.codCharge"/> <br>
                <span class='labelmore'>ssSaturday:</span> <s:property value="order.shipinfo.ssSaturday"/> <br>
                <span class='labelmore'>ssTracking:</span> <s:property value="order.shipinfo.ssTracking"/> <br>
                <span class='labelmore'>ssOversize:</span> <s:property value="order.shipinfo.ssOversize"/> <br>
                <span class='labelmore'>ssHazardous:</span> <s:property value="order.shipinfo.ssHazardous"/> <br>
                <span class='labelmore'>ssResidential:</span> <s:property value="order.shipinfo.ssResidential"/> <br>
                <span class='labelmore'>comments:</span> <s:property value="order.shipinfo.comments"/> <br>
                <span class='labelmore'>whseNotes:</span> <s:property value="order.shipinfo.whseNotes"/> <br>
                <span class='labelmore'>rowIsLocked:</span> <s:property value="order.shipinfo.rowIsLocked"/> <br>
                <span class='labelmore'>customsValue:</span> <s:property value="order.shipinfo.customsValue"/> <br>
                <span class='labelmore'>customsDesc:</span> <s:property value="order.shipinfo.customsDesc"/> <br>
                <span class='labelmore'>status:</span> <s:property value="order.shipinfo.status"/> <br>
                <span class='labelmore'>printDate:</span> <s:property value="order.shipinfo.printDate"/> <br>

            </div>
            <div style="clear:both"> </div>
             </div>
         </div>
   <div id="alltheotherinf2o" dojoType="dijit.TitlePane" title="More Line Item Info Than you Require" refreshOnShow="false" open="false" style="overflow:auto">
          <table class="items">
           <thead>
           <th>lineItemId</th>
           <th>inventoryNum</th>
           <th>description</th>
           <th>custRefnum</th>
           <th>quantityRequest</th>
           <th>quantityActual</th>
           <th>quantityBack</th>
           <th>price</th>
           <th>totalPrice</th>
           <th>createdDate</th>
           <th>createdBy</th>
           <th>modifiedDate</th>
           <th>modifiedBy</th>
           <th>rowIsLocked</th>
           <th>itemColor</th>
           <th>itemSize</th>
           <th>lineItemDisc</th>
           <th>longDesc</th>
           <th>customsDesc</th>
           <th>decItemValue</th>
           <th>isParent</th>
           <th>parentKey</th>
           <th>isInsert</th>

           </thead>
             <s:iterator value="order.lineitems">
             <tr>
                 <td><s:property value="lineItemId"/></td>
                 <td><s:property value="inventoryNum"/></td>
                 <td><s:property value="description"/></td>
                 <td><s:property value="custRefnum"/></td>
                 <td><s:property value="quantityRequest"/></td>
                 <td><s:property value="quantityActual"/></td>
                 <td><s:property value="quantityBack"/></td>
                 <td><s:property value="price"/></td>
                 <td><s:property value="totalPrice"/></td>
                 <td><s:property value="createdDate"/></td>
                 <td><s:property value="createdBy"/></td>
                 <td><s:property value="modifiedDate"/></td>
                 <td><s:property value="modifiedBy"/></td>
                 <td><s:property value="rowIsLocked"/></td>
                 <td><s:property value="itemColor"/></td>
                 <td><s:property value="itemSize"/></td>
                 <td><s:property value="lineItemDisc"/></td>
                 <td><s:property value="longDesc"/></td>
                 <td><s:property value="customsDesc"/></td>
                 <td><s:property value="decItemValue"/></td>
                 <td><s:property value="isParent"/></td>
                 <td><s:property value="parentKey"/></td>
                 <td><s:property value="isInsert"/></td>



             </tr>
             </s:iterator>
              </table>
       </div>
        <s:if test="thePackage.size > 0">
        <div id="packageinfo" dojoType="dijit.TitlePane" title="Package Info For Quick Copy" refreshOnShow="false" open="false" style="overflow:auto">

        <s:iterator value="thePackage">

            <s:if test="Void==false">
                <s:iterator value="packages">
                      Box: <s:property value="packageNumber"/>&nbsp;<s:property value="boxWidth"/>x<s:property value="boxDepth"/>x<s:property value="boxHeight"/> &nbsp;  <s:property value="weight"/>&nbsp;lbs  <br>
                 </s:iterator>
                    </s:if>
                </s:iterator>



          </div>
        </s:if>
    <div class="PackingInfo">
        <s:if test="thePackage.size > 0">
            <span class="title">Pack Info:</span>
            <hr>
            <s:iterator value="thePackage">


                <div class="pack <s:if test="Void">voided</s:if>">
                    <span class="labellarge">PackType: </span>

                    <s:if test="packType==0">
                        Ship Station
                    </s:if>
                    <s:if test="packType==1">
                        Batch Pack
                    </s:if>
                    <s:if test="packType==2">
                        Pack Station
                    </s:if>
                    <br/>

                   <span class="labellarge">  Is Void:</span> <s:property value="Void"/><br>
                    <s:if test="void">
                   <span class="labellarge">      Voided By:</span> <s:property value="voidBy"/><br>
                   <span class="labellarge">      Voided Time:</span> <s:property value="voidTime"/><br>
                    </s:if>
                   <span class="labellarge">  Packed By:</span> <s:property value="packerName"/>  <br>
                  <span class="labellarge">   Number of Packages:</span> <s:property value="numberOfPackages"/><br>
                  <span class="labellarge">   Shipped Packages:</span> <s:property value="packagesShipped"/> <br>

                  <span class="labellarge">   Pack Start Time:</span> <s:property value="packStart"/><br>
                  <span class="labellarge">   Pack End Time:</span> <s:property value="packEnd"/><br>

                   <div class="packages">
                    <span class="title">Packages for this Pack Record</span><br>
                    <s:iterator value="packages">
                        <div class="onepackage">
                            <span class="title">Box Number <s:property value="packageNumber"/> </span> <br>
                       <span class="labellarge">  Package Barcode:</span> <s:property value="packBarcode"/><br>
                       <span class="labellarge">  Package Weight:</span> <s:property value="weight"/> <br>
                      <span class="labellarge">   Estimated Lines Weight:</span> <s:property value="estimatedWeight"/><br>
                       <span class="labellarge">  Box Weight:</span> <s:property value="BoxWeight"/><br>
                       <span class="labellarge">  Total Estimated Weight:</span> <s:property value="EstimatedTotalWeight"/><br>
                       <span class="labellarge">  Package Box:</span> <s:property value="box.name"/>
                            <s:if test="boxId == 1">
                              Custom Box, <s:property value="boxWidth"/>x<s:property value="boxDepth"/>x<s:property value="boxHeight"/>
                            </s:if>
                            <br>
                      <span class="labellarge">   Package Tracking #:</span> <s:property value="tracking.trackingNo"/><br>
                      <span class="labellarge">   Package Ship Method:</span> <s:property value="tracking.serviceCode" /><br>
                        <s:if test="GSS">
                            Shipped via USPS GSS <br>
                        </s:if>

                        <div class="lines">
                        <span class="title">Packed Lines in this package</span><br>
                            <table>

                            <thead>
                            <th>Inventory Num</th>
                            <th>Qty:</th>
                            <th>Single Item Weight</th>
                            <th>Total weight</th>
                            </thead>

                            <s:iterator value="packedLines">

                             <tr>
                                    <td width="300px">
                                      Inventory Num: <s:property value="lineItem.inventoryNum"/>
                                    </td>
                                    <td width="75px" align="center">
                                         <s:property value="packQuantity"/><br>
                                    </td>
                                 <td width="150px" align="center">
                                     <s:property value="weight"/>
                                 </td>
                                 <td width="150px" align="center">
                                    <s:property value="TotalWeight"/>
                                 </td>
                                </tr>

                        </s:iterator>
                                  </table>
                        </div>
                        </div>
                    </s:iterator>
                     </div>
                </div>

            </s:iterator>


        </s:if>
    </div>
</s:if>

</body>

