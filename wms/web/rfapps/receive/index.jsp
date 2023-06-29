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
                <li><a href="/wms/do/recv-menu">Back</a></li>
                <li><a href="/wms/receive/loadASNSearch.action?employeeId=<s:property value="employeeId"/>&employeeName=<s:property value="employeeName"/>">ASN Search</a></li>


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


<div class="row">
    <div class="small-12 columns">
<h3>Please enter the asn you are working on</h3>
            <s:form action="loadAsn" theme="simple">
                <s:hidden name="employeeId"/>
                <s:hidden name="employeeName"/>
                <s:textfield name="asnId"  id="autoSubmit" value=""/>

                <s:submit/>
            </s:form>

    </div>
</div>
        </section>
        <a class="exit-off-canvas"></a>
    </div>

</div>






</body>
</html>