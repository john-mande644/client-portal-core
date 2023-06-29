<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus" %>
<%@ page import="com.owd.hibernate.generated.OwdFacility" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if(request.getParameter("location")!=null)
    {
        request.getSession(true).setAttribute("owd_current_adjustment_location", request.getParameter("location"));
        //request.getSession(true).setAttribute("owd_current_location",request.getParameter("location"));
    }
    String currentLocation = WarehouseStatus.getCurrentAdjustmentLocation(request);
    List<OwdFacility> facilities = WarehouseStatus.getFacilityList();
    StringBuffer fsb = new StringBuffer();
    String currFacility = "UNKNOWN ERROR";
    for(OwdFacility f: facilities)
    {
        if(currentLocation.equals(f.getFacilityCode()))
        {
            currFacility = f.getFacilityCode()+" - "+f.getCity()+", "+f.getState();
        } else
        {
            fsb.append("<li><a href=\"/internal/do/startAdjust?location="+f.getFacilityCode()+"\" class=\"confirmation\">"+f.getFacilityCode()+" - "+f.getCity()+", "+f.getState()+"</a></li>");
        }
    }
%>
<html>
<head>
    <link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<html:rewrite page="/js/Ajax.js" module=""/>"></script>

    <link rel="Stylesheet" href="/internal/stylesheets/locationmenu.css" type="text/css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <title> OWD Adjust Inventory!!!!!! </title>
</head>

<body onload="toggleVisibility('throb','hidden','hidden','hidden')">
<div id="header"><p class="header">OWD Adjustments</p></div>

<div id="leftMenu">
    <jsp:include page="menu.jsp"/>
</div>

    <div id="return">

        <div id="rightContent">
            <div class="click-nav">
                <ul class="no-js">
                    <li>
                        <a href="#" class="clicker"><%= currFacility %></a>
                        <ul><%= fsb.toString() %></ul>
                    </li>
                </ul>
            </div>

            <script>
                $(function() {
                    // Clickable Dropdown
                    $('.click-nav > ul').toggleClass('no-js js');
                    $('.click-nav .js ul').hide();
                    $('.click-nav .js').click(function(e) {
                        $('.click-nav .js ul').slideToggle(200);
                        $('.clicker').toggleClass('active');
                        e.stopPropagation();
                    });
                    $(document).click(function() {
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

                    var changeFacility =function(){
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
        <html:form action="saveAdjust" focus="sku">

            <html:hidden property="receiveUser"/>
            <html:hidden property="receiveDate"/>
            <html:hidden property="timeIn"/>
            <html:hidden property="currentLocation" value="<%= currentLocation %>"/>


            <table>
                <tr>
                    <td width="240px">
                         <span id="clientList">
                         <html:hidden property="orderId"/>
               <html:select name="adjustForm" property="clientFkey" styleClass="form">
                   <html:optionsCollection name="adjustForm" property="clientList" value="action" label="display"/>
               </html:select>
            </span>
                    </td>
                    <td align="left" nowrap>Select Client</td>
                    <td align="right"></td>
                    <td></td>
                </tr>
                <tr><td><b>Carrier Information:</b></td>
                    <td class="returnright" nowrap>Item Number:</td>
                    <td><span
                            id="sku"><html:text property="sku" size="30" styleClass="form"/></span></td>
                    <td><html:link
                            href="javascript:retrieveURLt('/internal/addAdjustItem.do?ask=COMMAND_NAME_1','adjustForm');">
                        <html:img
                                src="/intranet/images/add.png" border="0"/></html:link></td></tr>
                <tr><td colspan="4"><hr></td></tr></table>
            <table>

                <tr>
                    <td class="returnrightsmall">Service:</td>
                    <td><html:select name="adjustForm" property="carrier" styleClass="form">
                        <html:optionsCollection name="adjustForm" property="methodsList" value="action"
                                                label="display"/></html:select></td>
                    <td class="returnrightsmall">B/L No.:</td><td><html:text property="blNum" size="10"
                                                                             styleClass="form"/></td>
                    <td class="returnrightsmall">Driver:</td><td><html:text property="driver" size="10"
                                                                            styleClass="form"/></td>
                    <td class="returnrightsmall">Ref No.:</td><td><span id="refNum"> <html:text property="refNum"
                                                                                                size="10"
                                                                                                styleClass="form"/></span>
                </td>

                </tr>
            </table>
       <span id="items">

               <table class="returnitems">
                   <th class="returnxl">Item Number</th>
                   <th class="returnxl">Description</th>
                   <th class="returnsmall">O.H.</th>
                   <th class="return">Status</th>

                   <th class="return">Quantity</th>
                   <th class="lotValue">Lot Value</th>
                   <!-- <th class="return">Return Reason</th>-->
                   <c:if test="${sessionScope.adjustForm.formItems[0].inventoryId!=null}">
                       <c:forEach var="formItems" items="${sessionScope.adjustForm.formItems}" varStatus="itemStatus">
                           <tr>

                               <td class="returnxl">${formItems.inventoryNum}<html:hidden name="formItems" property="inventoryNum" indexed="true"/></td>
                               <td class="returnxl">${formItems.description}<html:hidden name="formItems" property="description" indexed="true"/></td>
                               <td>${formItems.invOnHand}</td>
                               <td><html:select name="formItems" property="itemStatus" indexed="true" styleClass="form">
                                   <html:optionsCollection name="formItems" property="statusList" value="action"
                                                           label="display"/>
                               </html:select></td>

                               <td><html:text name="formItems" property="quantity" indexed="true" size="10"
                                              styleClass="form"/></td>
                               <!-- <td><html:select name="formItems" property="returnReason" indexed="true">
                                   <html:optionsCollection name="formItems" property="reasonList" value="action"
                                                           label="display"/>
                               </html:select></td>-->



                               <td>
                                   <c:if test="${formItems.lotControlled == true}">
                                   <html:select name="formItems" property="lotValue" indexed="true">
                                       <html:options name="formItems" property="lotList"/>
                                   </html:select>
                                   </c:if>
                               </td>
                               <td><html:link
                                       href="javascript:retrieveURLt('/internal/removeAdjustItem.do?sku=${formItems.inventoryId}','adjustForm');">
                                   Remove</html:link></td>

                           </tr>

                           <html:hidden name="formItems" property="createdBy" indexed="true"/>
                           <html:hidden name="formItems" property="createdDate" indexed="true"/>
                           <html:hidden name="formItems" property="inventoryId" indexed="true"/>
                           <html:hidden name="formItems" property="invOnHand" indexed="true"/>
                       </c:forEach>
                   </c:if>
               </table>

       </span>
            <table>
                <tr><td class="returnright">Notes:</td><td colspan="7"><html:textarea property="notes" cols="80"
                                                                                      rows="6" styleClass="form"/></td>
                </tr>
            </table>
            <html:submit property="submit"><bean:message key="button.saveReturn"/></html:submit>
        </html:form>
        <span id="errors">  <html:errors/>${errors}</span>
        <div id="throb"> <img src="<html:rewrite page="/images/indicator_white.gif" module=""/>" alt="" /> </div>

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
</body>
</html>