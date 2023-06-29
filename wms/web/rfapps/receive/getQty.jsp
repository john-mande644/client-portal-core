<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <jsp:include page="/rfapps/includes/androidResponsive.jsp"/>
</head>

<body>
<!-- top bar navigation -->
<div class="off-canvas-wrap" data-offcanvas>

    <div class="inner-wrap">
        <nav class="tab-bar">
            <section class="left-small">
                <a class="left-off-canvas-toggle menu-icon"><span></span></a>
            </section>
            <section class="middle tab-bar-section">
                <h1 class="title"><s:property value="employeeName"/> / Receiving</h1>
            </section>
        </nav>
        <aside class="left-off-canvas-menu">
            <ul class="off-canvas-list">
                <li><a href="/wms/receive/loadAsn.action?asnId=<s:property value="asnId"/>&receiveId=<s:property value="receiveId"/>&employeeId=<s:property value="employeeId"/>&employeeName=<s:property value="employeeName"/>">Back</a>  </li>

            </ul>
        </aside>
        <section class="main-section">
            <s:if test="hasActionErrors()">
                <div class="errors">
                    <script>
                        try{
                            javautil.playErrorSound();
                        }catch(err){

                        }
                    </script>
                    <s:iterator value="actionErrors">
                        <span class="errorMessage"><s:property escape="false" /></span>
                    </s:iterator>
                </div>
            </s:if>
            <div class="row receiveHeader">
                <div class="small-12 columns">Verify Info and Enter Received Quantity</div>

            </div>
            <div class="row">
                <div class="small-12 columns">
                   <div class="small-6 left">
                       <span class="inventoryid"><s:property value="inventoryBean.inventoryId"/><br></span>
                       <span class="inventorynum"><s:property value="inventoryBean.inventoryNum"/></span>
                       <br>Qty on Hand <s:property value="inventoryBean.qtyOnHand"/>
                       <br><s:property value="inventoryBean.description"/>
                   </div>
                   <div class="small-6 right">
                       <div>
                           <strong>Receive item Notes:</strong><br />
                            <s:property value="rcvNotes" />
                        </div>
                        <div>
                            <strong>Asn Item Notes:</strong><br />
                            <s:property value="asnNotes" />
                        </div>
                   </div>
                </div>
            </div>

            <div class="row form">
                <div class="small-12 small-centered columns">

                    <s:form action="getCount" theme="simple">
                        <s:hidden name="employeeId"/>
                        <s:hidden name="employeeName"/>
                        <s:hidden name="asnId"/>
                        <s:hidden name="receiveId"/>
                        <s:hidden name="weight"/>
                        <s:hidden name="skuId"/>
                        <s:hidden name="receiveItemId"/>
                        <s:hidden name="damaged"/>
                        <s:hidden name="doLabelPrint"/>
                        <s:if test="inventoryBean.hasBarcode == false">
                            <s:label for="upcCode">UPC:</s:label><s:textfield name="upcCode" id="autoSubmit" />
                        </s:if>
                        <s:if test="inventoryBean.hasBarcode == true">
                            <s:label for="upcCode">UPC:</s:label><s:textfield name="upcCode" disabled="true" value="%{inventoryBean.upcCode}" />
                        </s:if>

                        <s:label for="count">QTY:</s:label><s:textfield name="count" value=""/>

                        <s:submit value="SUBMIT QUANTITY"/>
                    </s:form>
<br>
                    <s:form action="loadAsn" theme="simple">
                        <s:hidden name="employeeId"/>
                        <s:hidden name="employeeName"/>
                        <s:hidden name="asnId"/>
                        <s:submit value="BACK"/>
                    </s:form>
                    There have been <s:property value="countedInfo.qtyReceiveTotal"/> counted and You have counted <s:property value="countedInfo.qtyReceiveEmployee"/>
               <br>
                    <span class="red">DO NOT INCLUDE DAMAGED QUANTITY</span>
                </div>


            </div>

            <div class="row printLabels">

                <div class="small-3 columns">
                    Label Qty: <input type="text" id="printQty" value="1"/>


                </div>
            </div>
            <div class="row">
                <div class="small-4 columns">
                   <button onclick="printSmall()">Small Label</button>
                </div>
                <div class="small-4 columns">
                    <button onclick="printLarge()">Large Label</button>
                </div>
                <div class="small-4 columns">
                    <button onclick="printTags()">Pallet Tag</button>
                </div>
            </div>


        </section>
        <a class="exit-off-canvas"></a>
    </div>

</div>






</body>
<script type="text/javascript">
    var entertext = true;
    function printSmall(){
qty = $("#printQty").val();
        $.post("/wms/receive/printSmallLabel.action",{employeeName:"<s:property value="employeeName"/>",
            printQty:qty,skuId:"<s:property value="skuId"/>"}).done(function( data ){
            alert(data);
        });

    }

    function printLarge(){
        qty = $("#printQty").val();
        $.post("/wms/receive/printLargeLabel.action",{employeeName:"<s:property value="employeeName"/>",
            printQty:qty,skuId:"<s:property value="skuId"/>"}).done(function( data ){
            alert(data);
        });

    }
    function printTags(){
        qty = $("#printQty").val();
        $.post("/wms/receive/printTags.action",{employeeName:"<s:property value="employeeName"/>",
            printQty:qty,skuId:"<s:property value="skuId"/>"}).done(function( data ){
            alert(data);
        });

    }
</script>