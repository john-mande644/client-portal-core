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
                <div class="small-12 columns">Please enter the info to finish receive</div>

            </div>


            <div class="row form">
                <div class="small-12 small-centered columns">

                    <s:form action="saveFinished">
                        <s:hidden name="receiveId"/>
                        <s:hidden name="employeeName"/>
                        <s:radio label="Packing Slip" name="packingSlip" list="#{'no':'No Slip','nomatch':'No Match','match':'Match'}"/>
                        <s:textfield name="packages"  label="Packages"/>
                        <s:textfield name="pallets" label="Pallet Count"/>
                        <s:textfield name="minutes" label="Minutes"/>

                        <s:submit value="SAVE RECEIVE"/>
                    </s:form>

                </div>


            </div>




        </section>
        <a class="exit-off-canvas"></a>
    </div>

</div>






</body>
