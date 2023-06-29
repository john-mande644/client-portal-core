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
                <li><label>Menu</label></li>
                <li><a href="/wms/receive/complete.action?asnId=<s:property value="asnId"/>&receiveId=<s:property value="receiveId"/>&employeeId=<s:property value="employeeId"/>&employeeName=<s:property value="employeeName"/>">Complete</a> </li>
                <li><a href="/wms/receive/loadSearch.action?asnId=<s:property value="asnId"/>&receiveId=<s:property value="receiveId"/>&employeeId=<s:property value="employeeId"/>&employeeName=<s:property value="employeeName"/>">Search</a>  </li>


            </ul>
        </aside>
        <section class="main-section">
           <div class="row receiveHeader">

               <a href="android://take-picture/receive/<s:property value="asnId"/>/ /false/initial receive">Take Picture of Receive</a>
               <div class="small-8 columns"><h6>Working on ASN ID: <s:property value="asnId"/> / Receive Id: <s:property value="receiveId"/> </h6></div>
                <div class="small-4 columns"><s:form action="start" theme="simple"><s:submit value="Exit:"/></s:form></div>
           </div>
            <div class="row">
                <div class="small-10 small-offset-1 columns">
                    <hr class="hrtitle"/>
                    <div class="flexbox">
                        <div class="left small-6">
                            <s:property value="clientName"/>
                            <br/>
                            <s:property value="asnDescription"/>
                        </div>
                        <div class="right small-6">
                            <div>
                                Created: <s:property value="asnCreatedDate" />
                            </div>
                            <div>ASN Notes:</div>
                            <s:property value="asnNotes"/>
                            <div>Receive Notes:</div>
                            <s:property value="rcvNotes"/>
                        </div>
                    </div>
                    <hr class="hrtitle"/>
                </div>
            </div>

            <div class="row form">
                <div class="small-12 small-centered columns">
                    Scan Sku you are receiving.
                    <s:form action="loadSku" theme="simple">
                        <s:hidden name="employeeId"/>
                        <s:hidden name="employeeName"/>
                        <s:hidden name="asnId"/>
                        <s:hidden name="receiveId"/>
                        <s:textfield name="scannedSkuId"  id="autoSubmit" value=""/>

                        <s:submit/>
                    </s:form>

                </div>


            </div>

            <div class="row countData">
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
                <div class="small-12 columns">
                    <div class="row">
                        <div class="small-3 columns">
                            Sku Id
                        </div>
                        <div class="small-3 columns">
                            Qty

                        </div>
                        <div class="small-3 columns">
                            Dmg
                        </div>
                        <div class="small-3 columns">
                            Notes
                        </div>
                    </div>
                    <s:iterator value="countList">
                        <div class="row">
                            <div class="small-3 columns">
                                <s:property value="inventoryId"/>
                            </div>
                            <div class="small-3 columns">
                                <s:property value="qtyReceive"/>

                            </div>
                            <div class="small-3 columns red">
                                <s:property value="qtyDamaged"/>
                            </div>
                            <div class="small-3 columns">
                                <s:property value="notes"/>
                            </div>
                        </div>
                    </s:iterator>
                </div>
            </div>


        </section>
        <a class="exit-off-canvas"></a>
    </div>

</div>






</body>
</html>