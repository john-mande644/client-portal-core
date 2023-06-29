<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus" %>
<%@ page import="com.owd.hibernate.generated.OwdFacility" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.Action" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (request.getParameter("location") != null) {
      //  request.getSession(true).setAttribute("owd_current_location", request.getParameter("location"));
        request.getSession(true).setAttribute("owd_current_adjustment_location", request.getParameter("location"));
    }
    String currentLocation = WarehouseStatus.getCurrentAdjustmentLocation(request);
    List<OwdFacility> facilities = WarehouseStatus.getFacilityList();
    StringBuffer fsb = new StringBuffer();
    String currFacility = "UNKNOWN ERROR";
    for (OwdFacility f : facilities) {
        if (currentLocation.equals(f.getFacilityCode())) {
            currFacility = f.getFacilityCode() + " - " + f.getCity() + ", " + f.getState();
        } else {
            fsb.append("<li><a href=\"/internal/do/startReturn?location=" + f.getFacilityCode() + "\" class=\"confirmation\">" + f.getFacilityCode() + " - " + f.getCity() + ", " + f.getState() + "</a></li>");
        }
    }
%>
<html>
<head>
    <link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<html:rewrite page="/js/Ajax.js" module=""/>"></script>

    <link rel="Stylesheet" href="/internal/stylesheets/locationmenu.css" type="text/css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <title> OWD Return Processing </title>
</head>

<body style="font-family:Helvetica,Arial,sans-serif;" onload="toggleVisibility('throb','hidden','hidden','hidden')">
<div id="leftMenu">
    <jsp:include page="../adjust/menu.jsp"/>
</div>

<div id="rightContent">
    <div id="return">&nbsp
        <html:form action="saveReturn" focus="sku" >


            <table width=895>
                <tr>
                    <td colspan=3 nowrap align=center><font size=+2>OWD Return Entry</font>
                        <hr>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <div class="click-nav">
                            <ul class="no-js">
                                <li>
                                    <a href="#" class="clicker"><%= currFacility %>
                                    </a>
                                    <ul><%= fsb.toString() %>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                        <script>
                            $(function () {
                                // Clickable Dropdown
                                $('.click-nav > ul').toggleClass('no-js js');
                                $('.click-nav .js ul').hide();
                                $('.click-nav .js').click(function (e) {
                                    $('.click-nav .js ul').slideToggle(200);
                                    $('.clicker').toggleClass('active');
                                    e.stopPropagation();
                                });
                                $(document).click(function () {
                                    if ($('.click-nav .js ul').is(':visible')) {
                                        $('.click-nav .js ul', this).slideUp();
                                        $('.clicker').removeClass('active');
                                    }
                                });
                                // $('.confirmation').on('click', function () {
                                //     return confirm('Are you sure you want to change Facility?');
                                // });

                                var newFacility='';

                                $('#modal_screen').hide();
                                $("#error_message").hide();


                                $("#facility_change_cancel").click(function (){
                                    newFacility='';
                                    $('#location_change_password').val("");
                                    $('#error_message').html("");
                                    $('#modal_screen').slideUp('fast');
                                });

                                $("#facility_change_submit").click(function (){
                                    $('#error_message').html('').slideUp('fast');
                                    if($('#location_change_password').val() == "donotsharethis"){
                                        document.location.href = newFacility;
                                        $('#modal_screen').hide();
                                    }else{
                                        error_message("password is incorrect");
                                    }

                                });

                                var changeFacility =function() {
                                    $('#error_message').hide();
                                    $('#error_message').html("");
                                    $('#location_change_password').val("");
                                    $('#modal_screen').slideDown("slow");
                                    $('#location_change_password').focus().select();
                                }

                                function error_message(message){
                                    console.log(message);
                                    $("#error_message").html(message);
                                    $("#error_message").slideDown("slow");
                                    $("#location_change_password").focus().select();
                                }

                                $('.confirmation').click(function(e){
                                    console.log(e);
                                    console.log($(this));
                                    console.log($(this).context.href);
                                    newFacility = $(this).context.href;
                                    changeFacility();
                                    return false;
                                });

                                $('#location_change_password').keyup(function(e){
                                    if(e.keyCode == 13)
                                    {
                                        $("#facility_change_submit").trigger("click");
                                    }
                                });
                            });
                        </script>
                    </td>
                </tr>
                <tr>
                    <td nowrap><span id="ordernum">
                   <c:choose>
                       <c:when test="${!empty requestScope.orderList}">
                           <html:select name="returnForm" property="oid">
                               <html:optionsCollection name="orderList" value="oid" label="description"/>
                           </html:select>

                       </c:when>
                       <c:otherwise>
                           <html:text property="orderNum" size="15"/>
                           <html:checkbox property="startsWith" >Starts&nbsp;With</html:checkbox>
                       </c:otherwise>
                   </c:choose>&nbsp;&nbsp;
               <html:link
                       href="javascript:retrieveURLt('/internal/loadReturnOrder.do?ask=COMMAND_NAME_1','returnForm');">
                   <c:choose>
                       <c:when test="${!empty requestScope.orderList}">Load&nbsp;Selected&nbsp;Order</c:when>
                       <c:otherwise>Find&nbsp;Order&nbsp;Reference</c:otherwise>
                   </c:choose>
               </html:link>
                </span></td>
                    <td width=100%>&nbsp;</td>
                    <td align=right nowrap>Client:&nbsp;<span id="clientList">
               <html:hidden property="receiveUser"/>
            <html:hidden property="receiveDate"/>
            <html:hidden property="timeIn"/>
            <html:hidden property="currentLocation" value="<%= currentLocation %>"/>
                <html:hidden property="orderId"/>
               <html:select name="returnForm" property="clientFkey">
                   <html:optionsCollection name="returnForm" property="clientList" value="action" label="display"/>
               </html:select>
            </span></td>
                </tr>
            </table>
            <HR>
            <table width=895>
                <tr>
                    <td colspan="3">

              <span id="orderName"> ${sessionScope.returnForm.orderName}
              </span>
                    </td>
                </tr>
                <tr>
                    <td class="returnrightsmall">Service:</td>
                    <td><html:select name="returnForm" property="carrier">
                        <html:optionsCollection name="returnForm" property="methodsList" value="action"
                                                label="display"/></html:select></td>
                    <td slign=left rowspan=4>Notes:<BR><html:textarea property="notes" cols="40" rows="6"/></td>
                </tr>
                <tr>
                    <td class="returnrightsmall">Client&nbsp;RMA#:&nbsp;</td>
                    <td><span id="blNum"><html:text property="blNum" size="15"/></span></td>
                </tr>
                <tr>
                    <td class="returnrightsmall">Postage&nbsp;Due:&nbsp;</td>
                    <td><span id="postage"><html:text property="driver" size="10" styleId="postage"/></span></td>
                </tr>
                <tr>
                <td class="returnrightsmall">OWD&nbsp;Order&nbsp;Ref:&nbsp;</td>
                <td><span id="refNum"> <html:text readonly="true" property="refNum" size="10"/></span></td>

                </tr>
            </table>

            <HR>
       <span id="items">


               <table class="returnitems">
                   <th class="returnxl">Item Number</th>
                   <th class="returnxl">Description</th>
                   <th class="returnsmall">O.H.</th>
                   <th class="return">Status</th>

                   <th class="return">Serial</th>
                   <th class="return">Quantity</th>
                   <th class="return">Lot</th>
                   <th class="returnlarge">Return Reason</th>
                   <c:if test="${sessionScope.returnForm.formItems[0].inventoryId!=null}">
                       <c:forEach var="formItems" items="${sessionScope.returnForm.formItems}" varStatus="itemStatus">
                           <tr>

                               <td class="returnxl"><html:hidden name="formItems" property="inventoryNum"
                                                                 indexed="true"/>${formItems.inventoryNum}</td>
                               <td class="returnxl"><html:hidden name="formItems" property="description"
                                                                 indexed="true"/>${formItems.description}</td>
                               <td>${formItems.invOnHand}</td>
                               <td><html:select name="formItems" property="itemStatus" indexed="true">
                                   <html:optionsCollection name="formItems" property="statusList" value="action"
                                                           label="display"/>
                               </html:select></td>

                               <td><c:choose>
                                   <c:when test="${formItems.serialRequired}"><html:text name="formItems"
                                                                                         property="serial"
                                                                                         indexed="true"
                                                                                         size="10"/></c:when>
                                   <c:otherwise>N/A</c:otherwise></c:choose></td>
                               <td><html:text name="formItems" property="quantity" indexed="true" size="10"/></td>
                               <td>
                                   <c:if test="${formItems.lotControlled == true}">
                                       <html:select name="formItems" property="lotValue" indexed="true">
                                           <html:options name="formItems" property="lotList"/>
                                       </html:select>
                                   </c:if>
                               </td>
                               <td><html:select name="formItems" property="returnReason" indexed="true">
                                   <html:optionsCollection name="formItems" property="reasonList" value="action"
                                                           label="display"/>
                               </html:select></td>
                               <td><html:link
                                       href="javascript:retrieveURLt('/internal/removeItem.do?sku=${formItems.inventoryId}','returnForm');">Remove</html:link></td>

                           </tr>

                           <html:hidden name="formItems" property="createdBy" indexed="true"/>
                           <html:hidden name="formItems" property="createdDate" indexed="true"/>
                           <html:hidden name="formItems" property="serialRequired" indexed="true"/>
                           <html:hidden name="formItems" property="inventoryId" indexed="true"/>
                           <html:hidden name="formItems" property="invOnHand" indexed="true"/>
                       </c:forEach>
                   </c:if>
               </table>

       </span>
            <table width=895>
                <tr>
                    <td width='100%'>&nbsp;</td>
                    <td class="returnrightsmall">Add&nbsp;Item&nbsp;(SKU):</td>
                    <td><span
                            id="sku"><html:text property="sku" size="25"/></span></td>
                    <td><html:link
                            href="javascript:retrieveURLt('/internal/addItem.do?ask=COMMAND_NAME_1','returnForm');"><html:img
                            src="/internal/images/add.jpg" border="0" height="24" width="24"/></html:link></td>
                </tr>
                </tr>

            </table>
            <hr>
            <table>
                <tr></tr>
            </table>

            <html:submit property="submit"><bean:message key="button.saveReturn"/></html:submit>
        </html:form>

        <span id="errors">  <html:errors/>${errors}</span>
   <span id="orderrnotes">
         <table width=895 cellpadding="5">
             <tr>
                 <td width=50% align=left><h3>Order Notes</h3></td>
                 <td width=50% align=left><h3>Order Events</h3></td>
             </tr>
             <tr>
                 <td width=50%>
                     <c:if test="${sessionScope.returnForm.formItems[0].inventoryId!=null}">
                         <div id="ordernotes">
                             <c:forEach var="event" items="${returnForm.comments}">
                                 <b>Date:</b>  ${event.eventstamp}&nbsp;<b>Who:</b>  ${event.creator}
                                 <br>${event.message}
                                 <hr>
                             </c:forEach>
                         </div>
                         <hr class="seperate" size="4">
                         <br>
                     </c:if>
                 </td>
                 <td width=50%>
                     <c:if test="${sessionScope.returnForm.formItems[0].inventoryId!=null}">
                         <div id="orderevents">
                             <c:forEach var="event" items="${returnForm.events}">
                                 <b>Date:</b>  ${event.eventstamp}&nbsp;<b>Who:</b>  ${event.creator}
                                 <br>${event.message}
                                 <hr>
                             </c:forEach>
                         </div>

                     </c:if>
                 </td>
             </tr>
         </table>
         </span>

        <div id="throb"><img src="<html:rewrite page="internal/images/indicator_white.gif" module=""/>" alt=""/></div>

    </div>


</div>
<div id="modal_screen">
    <div id='facility_change_header'>
        <span id="facility_change_cancel">X</span>
    </div>
    <div id="location_change">
        To change to a different facility please input the system password: <input id='location_change_password' type='password'/>
        <button id="facility_change_submit">Submit</button>
    </div>
    <div id="error_message">&nbsp;</div>
</div>
<script>
    $("#postage").bind("paste keyup", function() {
        if($(this).val() > 50){
            alert("Postage cannot be more the $50. Please enter a new value");
            $(this).val("");
        }

    });
</script>
</body>
</html>