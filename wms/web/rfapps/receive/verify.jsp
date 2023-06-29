<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <jsp:include page="/rfapps/includes/androidResponsive.jsp"/>
    <script type="text/javascript">
         function editCount(){
            s = prompt("What should the count be?",'<s:property value="count"/>')
             if (!s == false){
                 if(!isNaN(s)){
                     $('#count').text(s);
                     $('#verify_count').val(s)
                 }
             }
        }
         function editDamaged(){
             s = prompt("What should the Damaged count be?",'<s:property value="damaged"/>')
             if (!s == false){
                 if(!isNaN(s)){
                     $('#damaged').text(s);
                     $('#verify_damaged').val(s)
                 }
             }
         }
         function editWeight(){
             s = prompt("What should the weight be?",'<s:property value="weight"/>')
             if (!s == false){
                 if(!isNaN(s)){
                     $('#weight').text(s);
                     $('#verify_weight').val(s)
                 }
             }
         }
         function editLength(){
             s = prompt("What should the length be?",'<s:property value="length"/>')
             if (!s == false){
                 if(!isNaN(s)){
                     $('#length').text(s);
                     $('#verify_length').val(s)
                 }
             }
         }
         function editWidth(){
             s = prompt("What should the width be?",'<s:property value="width"/>')
             if (!s == false){
                 if(!isNaN(s)){
                     $('#width').text(s);
                     $('#verify_width').val(s)
                 }
             }
         }
         function editHeight(){
             s = prompt("What should the height be?",'<s:property value="height"/>')
             if (!s == false){
                 if(!isNaN(s)){
                     $('#height').text(s);
                     $('#verify_height').val(s)
                 }
             }
         }
         <s:if test="lotCode.length() != 0">
         function editLotCode(){
             s = prompt("What should the lot code be?",'<s:property value="lotCode"/>')
             if (!s == false){
                 if(!isNaN(s)){
                     $('#lotCode').text(s);
                     $('#verify_lotCode').val(s)
                 }
             }
         }
        </s:if>
    </script>
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
                <h1 class="title"><s:property value="employeeName"/> / Verify Info</h1>
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
                <div class="small-12 columns">Verify Information</div>

            </div>
            <div class="row">
                <div class="small-12 columns">
                    Quantity: <span id ="count"><s:property value="count"/></span>
                    <button id="btnCount" onclick="editCount()">Edit Count</button> <br>
                    Damaged: <span id="damaged"><s:property value="damaged"/>  </span>
                    <button id="btnDamaged" onclick="editDamaged()">Edit Damaged</button> <br>
                    <br/>

                    Weight: <span id="weight"><s:property value="weight"/></span>
                    <button id="btnWeight" onclick="editWeight()">Edit Weight</button> <br>
                    Length: <span id="length"><s:property value="length"/></span>
                    <button id="btnLength" onclick="editLength()">Edit Length</button> <br>
                    Width: <span id="width"><s:property value="width"/></span>
                    <button id="btnWidth" onclick="editWidth()">Edit Widht</button> <br>
                    Height: <span id="height"><s:property value="height"/></span>
                    <button id="btnHeight" onclick="editHeight()">Edit Height</button> <br>
                    <br/>
                    <s:if test="lotCode.length() != 0">
                    Lot Code: <span id="lotCode"><s:property value="lotCode"/></span>
                    <button id="btnLotCode" onclick="editLotCode()">Edit Lot Code</button> <br>
                    </s:if>
                </div>
            </div>


            <div class="row form">
                <div class="small-12 small-centered columns">

                    <s:form action="verify" theme="simple">
                        <s:hidden name="employeeId"/>
                        <s:hidden name="employeeName"/>
                        <s:hidden name="asnId"/>
                        <s:hidden name="receiveId"/>
                        <s:hidden name="damaged"/>
                        <s:hidden name="skuId"/>
                        <s:hidden name="receiveItemId"/>
                        <s:hidden name="count"/>
                        <s:hidden name="doLabelPrint"/>
                        <s:hidden name="weight"/>
                        <s:hidden name="length"/>
                        <s:hidden name="width"/>
                        <s:hidden name="height"/>
                        <s:hidden name="lotCode"/>
                      Notes:  <s:textarea name="notes"/>
                        <s:submit value="VERIFY"/>
                        <s:token/>

                    </s:form>
                    <br>
                    <span class="red">VERIFY WHAT YOU HAVE ENTERED, EDIT IF NEED BE</span>
                </div>


            </div>




        </section>
        <a class="exit-off-canvas"></a>
    </div>

</div>






</body>
</html>