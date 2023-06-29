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
                <li><label>What</label></li>
                <li>Menu</li>
                <li> Search </li>
                <li> Etc</li>

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
                <div class="small-12 columns">No UPC assigned for This item. Print any Labels needed.</div>

            </div>


            <div class="row printLabels">

                <div class="small-3 columns">
                    Label Qty: <input type="text" id="printQty" value="<s:property value="count"/>"/>


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

            <div class="row">
                <div class="small-4 columns">
                    <s:form  action="doneWithLabels">
                        <s:hidden name="employeeId"></s:hidden>
                        <s:hidden name="employeeName"/>
                        <s:hidden name="asnId"/>
                        <s:submit value="Done With Labels"/>
                    </s:form>
                </div>

            </div>
        </section>
        <a class="exit-off-canvas"></a>
    </div>

</div>






</body>
<script type="text/javascript">
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
        $.post("/wms/receive/printTagsLabel.action",{employeeName:"<s:property value="employeeName"/>",
            printQty:qty,skuId:"<s:property value="skuId"/>"}).done(function( data ){
            alert(data);
        });

    }
</script>