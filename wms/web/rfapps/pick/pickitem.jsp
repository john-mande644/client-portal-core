<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>


    <meta name="HandheldFriendly" content="true"/>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>


    <SCRIPT>

        function pickMulti() {
            s = prompt('Enter Quantity you picked', '0');
            if (s > 0) {
                document.forms['skuForm1'].elements['pickMultiple'].value = s;
                document.forms['skuForm1'].submit();
            }
        }

    </SCRIPT>
</head>
<body>
<c:if test='${pickItem.redOrder}'>

<div class="body redOrder">
    </c:if>
    <c:if test='${pickItem.redOrder ==false}'>
    <div class="body">
        </c:if>

        <div class="header">
            <TABLE width="100%" class="fullWidthTable">
                <TR>
                    <td>
                        <html:form action="menu"><html:submit><bean:message
                                key="button.mainmenu"/></html:submit></html:form></td>
                    <td align="right">${loginName}
                    </td>
                </TR>
            </TABLE>
        </div>
        <HR color=black>
        <c:if test='${error != null}'>
            <script>
                try {
                    javautil.playErrorSound();
                } catch (err) {

                }
            </script>
            <B class="error">${error}</B>

        </c:if>


        <table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box" class="fullWidthTable">
            <tr>
                <td valign=top align=left>
                    <b>
                        <c:if test='${pickItem.verify == "0"}'>Picking&nbsp;</c:if><c:if
                            test='${pickItem.verify == "1"}'>VERIFYING&nbsp;</c:if>${pickItem.orderNum}</b>

                    <c:if test='${pickItem.license !=null and pickItem.license != ""}'>
                        LP: ${pickItem.license}
                    </c:if>

                </td>
                <TD align=right><B><font color="FD1200">Line&nbsp;${pickItem.currentItem+1}
                    of ${pickItem.itemSize} </font> </B>
                </TD>
            </tr>

            <TR>
                <TD align=left colspan=2>
                    <HR ALIGN="center" color=black>
                </TD>
            </TR>

            <TR>
                <TD align=left colspan="2">

                    <font size=+3 color="01D709"><B>${pickItem.barcode}</B></font>


                </TD>
            </tr>
            <c:if test="${pickItem.caseQty>0}">
                <tr>
                    <td>
                        <div class="caseInfo">
                                ${pickItem.caseQty} units
                            <br>
                            Per Case
                        </div>
                    </td>
                    <td>
                        <div class="casePickInfo">
                                ${pickItem.casePick} Cases
                            <hr>
                            and <br>
                                ${pickItem.eachPick} Unit
                        </div>
                    </td>

                </tr>


            </c:if>


            <tr>
                <TD align=right colspan="2"><font size=+2><B>Unit&nbsp;${pickItem.qtyPicked}
                    of ${pickItem.qtyToPick}</B></font>&nbsp (${pickItem.onHand})
                </TD>
            </TR>

            <TR>
                <TD align=center colspan=2>
                    <br>
                    <B><font color="85147C" size="+3">${pickItem.sku}</font></B>
                    <BR><br>${pickItem.description}<br><br>
                </TD>
            </TR>
            <TR>
                <TH align=left VALIGN=BOTTOM>
                    <c:if test="${pickItem.imageThumb != null}">

                        <br>
                    </c:if>

                    <div class="locationDisplay">${pickItem.locFirst}</div>


                    </font><BR>
                </TH>
                <TH ALIGN=RIGHT VALIGN=TOP>
                    <html:form action="pickitem" focus="sku" styleId="thePick">
                        <bean:message key="label.pickitem.scan"/><br>
                        <html:text property="sku" value="" size="6" styleId="autoSubmit"/>
                        <html:hidden property="pickMultiple" value="0"/><br>
                        <html:submit><bean:message key="button.pickitem"/> </html:submit>
                    </html:form>

                </TH>
            </TR>
            <c:if test='${pickItem.qtyPicked>=2 && pickItem.qtyToPick>24}'>
                <tr>
                    <td></td>
                    <td><html:submit onclick="javascript:pickMulti()">Pick Multiple</html:submit></td>
                </tr>
            </c:if>
            <TR>
                <TD COLSPAN=2><br>

                    <HR ALIGN="center" SIZE="1">
                </TD>
        </table>
        <table>
            <TR>
                <TD align=left>
                    <html:form action="pickcancel">
                        <html:hidden property="sku" value="${pickItem.orderNum}"/>
                        <html:submit onclick="return tryCancel()"><bean:message key="button.cancelpick"/></html:submit>
                    </html:form>
                </TD>

                <!-- this is the new skip button -->
                <TD align=left>

                    <html:form action="skipLine">
                        <html:hidden property="inventoryId" value="${pickItem.barcode}"/>
                        <html:hidden property="pickItemId" value="${pickItem.pickItemId}"/>
                        <html:hidden property="pickStatusId" value="${pickItem.pickStatusId}"/>
                        <html:submit value="SKIP LINE" onclick="return trySkip()"/>
                    </html:form>


                    <script type="text/javascript">
                        function trySkip() {
                            s = confirm('This will Skip the the current line');
                            if (s == true) {
                                return true;
                            }
                            return false;
                        }
                    </script>
                </td>

                <!-- end skip line button -->


                <TD align=center>
                    <html:form action="replenish">
                        <html:hidden property="inventoryId" value="${pickItem.barcode}"/>
                        <html:hidden property="pickItemId" value="${pickItem.pickItemId}"/>
                        <html:hidden property="pickStatusId" value="${pickItem.pickStatusId}"/>
                        <html:submit value="REPLENISH" styleId="replenish"/>
                    </html:form>
                </TD>

                <TD align=left>
                    <button id='problem'>PROBLEM</button>

                    <script type="text/javascript">
                        function tryCancel() {
                            s = prompt('This will Cancel the Pick. To do so Enter 9137', 'Cancel Code');
                            if (s == 9137) {
                                return true;
                            }
                            return false;
                        }
                    </script>
                </td>
            </tr>
            <tr></tr>
            <tr>
                <td></td>
            </tr>
        </table>
    </div>

    <c:if test="${pickItem.caseQty>0}">
    <div class="overlay casepick" style="display:none; background-color:#eeffa4">
        <div class="caseHeader">
            Case Info Available<br><br>


            Are you picking a Case of ${pickItem.caseQty}?

        </div>
        <div class="caseFooter">


            <table class="selectCase">
                <tr>
                    <td colspan="2">
                        # of Cases picked <input type="text" id="multiCase"><br><br>

                        <button onclick="pickCaseMulti()">Pick Multiple Cases of ${pickItem.caseQty}</button>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;<hr>
                    </td>
                </tr>
            </table>

            <table class="selectCase">
                <tr>
                    <th>
                        Pick Case
                    </th>
                    <th>
                        Pick Single
                    </th>
                </tr>
                <tr></tr>

                <tr>
                    <td>
                        <button onclick="pickCase()">Case of ${pickItem.caseQty}</button>

                    </td>
                    <td>
                        <button onclick="pickSingle()">Pick 1</button>
                    </td>
                </tr>
            </table>
        </div>
        <script type="text/javascript">
            if (${pickItem.qtyToPick}-${pickItem.qtyPicked} >= ${pickItem.caseQty}) {
                //add on load event to grab submit
                console.log("try the event");
                $(function () {
                    $("#thePick").on('submit', function (event) {
                        console.log("fire the event");
                        event.preventDefault();
                        submitRedirect();
                        return false;

                    });

                });
            }

            function submitRedirect() {

                console.log("hello redirectr");
                //unbind submit so we can submit normaly what is entered
                $("#thePick").unbind();
                var good = false;
                data = document.getElementById("autoSubmit").value;
                if ("${pickItem.UPC}" == data) {
                    good = true;
                }
                if ("${pickItem.ISBN}" == data) {
                    good = true;
                }
                if ("${pickItem.barcode}" == data) {
                    good = true;
                }

                //if valid, display the overlay for case pick
                if (good) {
                    $(".casepick").show();

                } else {
                    //no match submit what was scanned to process and receive error
                    console.log("Hello Else");
                    $("#thePick").submit();
                }


            }

            function pickCaseMulti() {
                console.log("pickMultiCase");
                //grab qty from input
                qty = $('#multiCase').val();
                if (qty.length > 0) {
                    //mulitply and submit
                    pickQty = qty * ${pickItem.caseQty};
                    console.log(pickQty);
                    document.forms['skuForm1'].elements['pickMultiple'].value = pickQty;
                    $("#thePick").submit();


                }


            }

            function pickCase() {
                console.log("pickCase");
                document.forms['skuForm1'].elements['pickMultiple'].value = ${pickItem.caseQty};
                $("#thePick").submit();

            }

            function pickSingle() {
                $("#thePick").submit();

            }

        </script>
    </div>

    </c:if>
    <div id="problemSelection">
        <button id='problemClose'>close</button>

        <div class="problemForm">
            <div class="problemItem">
                <html:form action="setHold">
                    <html:submit value="Damage" onclick="return tryCancelHold();"/>
                    <html:hidden property="inventoryId" value="${holdForm.inventoryId}"/>
                    <html:hidden property="inventoryNum" value="${holdForm.inventoryNum}"/>
                    <html:hidden property="locations" value="${holdForm.locations}"/>
                    <html:hidden property="onHand" value="${holdForm.onHand}"/>
                    <html:hidden property="user" value="${holdForm.user}"/>
                    <html:hidden property="holdReason" value="Damage"/>
                    <html:hidden property="orderNum" value="${holdForm.orderNum}"/>
                    <html:hidden property="pickItemId" value="${holdForm.pickItemId}"/>
                </html:form>
            </div>
            <div class="problemItem">
                <html:form action="setHold">
                    <html:submit value="Barcode not Assigned" onclick="return tryCancelHold();"/>
                    <html:hidden property="inventoryId" value="${holdForm.inventoryId}"/>
                    <html:hidden property="inventoryNum" value="${holdForm.inventoryNum}"/>
                    <html:hidden property="locations" value="${holdForm.locations}"/>
                    <html:hidden property="onHand" value="${holdForm.onHand}"/>
                    <html:hidden property="user" value="${holdForm.user}"/>
                    <html:hidden property="holdReason" value="Barcode not Assigned"/>
                    <html:hidden property="orderNum" value="${holdForm.orderNum}"/>
                    <html:hidden property="pickItemId" value="${holdForm.pickItemId}"/>
                </html:form>
            </div>
            <div class="problemItem">
                <html:form action="setHold">
                    <html:submit value="Missing Tag" onclick="return tryCancelHold();"/>
                    <html:hidden property="inventoryId" value="${holdForm.inventoryId}"/>
                    <html:hidden property="inventoryNum" value="${holdForm.inventoryNum}"/>
                    <html:hidden property="locations" value="${holdForm.locations}"/>
                    <html:hidden property="onHand" value="${holdForm.onHand}"/>
                    <html:hidden property="user" value="${holdForm.user}"/>
                    <html:hidden property="holdReason" value="Missing Tag"/>
                    <html:hidden property="orderNum" value="${holdForm.orderNum}"/>
                    <html:hidden property="pickItemId" value="${holdForm.pickItemId}"/>
                </html:form>
            </div>
            <div class="problemItem">
                <html:form action="setHold">
                    <html:submit value="Unknown Location" onclick="return tryCancelHold();"/>
                    <html:hidden property="inventoryId" value="${holdForm.inventoryId}"/>
                    <html:hidden property="inventoryNum" value="${holdForm.inventoryNum}"/>
                    <html:hidden property="locations" value="${holdForm.locations}"/>
                    <html:hidden property="onHand" value="${holdForm.onHand}"/>
                    <html:hidden property="user" value="${holdForm.user}"/>
                    <html:hidden property="holdReason" value="Unknown Location"/>
                    <html:hidden property="orderNum" value="${holdForm.orderNum}"/>
                    <html:hidden property="pickItemId" value="${holdForm.pickItemId}"/>
                </html:form>
            </div>

            <div class="problemItem">
                <html:form action="setHold">
                    <html:submit value="Empty bin" onclick="return tryCancelHold();"/>
                    <html:hidden property="inventoryId" value="${holdForm.inventoryId}"/>
                    <html:hidden property="inventoryNum" value="${holdForm.inventoryNum}"/>
                    <html:hidden property="locations" value="${holdForm.locations}"/>
                    <html:hidden property="onHand" value="${holdForm.onHand}"/>
                    <html:hidden property="user" value="${holdForm.user}"/>
                    <html:hidden property="holdReason" value="Empty bin"/>
                    <html:hidden property="orderNum" value="${holdForm.orderNum}"/>
                    <html:hidden property="pickItemId" value="${holdForm.pickItemId}"/>
                </html:form>
            </div>
        </div>
    </div>

</body>

<script type="text/javascript">
    function tryCancelHold() {
        s = confirm('This will put the order on hold.');
        if (s) {
            return true;
        }
        return false;
    }
</script>

<script type="text/javascript">
    $(document).ready(function () {
        let cookies = {};
        let user;
        console.log("cookieString", document.cookie);
        let c = document.cookie.split(";");
        c.forEach(function (cook) {
            let keyVal = cook.trim().split("=");
            cookies[keyVal[0]] = keyVal[1].replace('"', '');
        });
        console.log("Cookies", cookies);
        console.log("Ready");
        $('#problem').click(function () {
            console.log("button clicked");
            $("#problemSelection").css('visibility', 'visible');
        });

        $('#problemClose').click(function () {
            $("#problemSelection").css('visibility', 'hidden');
        });
        if (cookiesy['wmsFacility'] == 'DC6') {
            $('#replenish').css('visibility', 'hidden');
        }
    });
</script>
</HTML>

