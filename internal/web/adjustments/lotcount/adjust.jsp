<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus" %>
<%@ page import="com.owd.hibernate.generated.OwdFacility" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (request.getParameter("location") != null) {
    //    request.getSession(true).setAttribute("owd_current_location", request.getParameter("location"));
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
            fsb.append("<li><a href=\"/internal/do/indexLotCount?location=" + f.getFacilityCode() + "\" class=\"confirmation\">" + f.getFacilityCode() + " - " + f.getCity() + ", " + f.getState() + "</a></li>");
        }
    }
%>
<html>
<head>
    <link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<html:rewrite page="/js/Ajax.js" module=""/>"></script>

    <link rel="Stylesheet" href="/internal/stylesheets/locationmenu.css" type="text/css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <title> OWD Lot Count!!!!!! </title>
</head>

<body onload="toggleVisibility('throb','hidden','hidden','hidden')">
<div id="header"><p class="header">OWD Lot Counts</p></div>

<div id="leftMenu">
    <jsp:include page="../adjust/menu.jsp"/>
</div>

<div id="return">

    <div id="rightContent">
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

                var changeFacility = function(){
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
        <html:form action="saveLotCount" focus="sku">


            <table>
                <tr>
                    <td><b></b></td>
                    <td class="returnright" nowrap>Select Client:</td>
                    <td width="240px">
                         <span id="clientList">
               <html:select name="lotCountForm" property="clientFkey" styleClass="form">
                   <html:optionsCollection name="lotCountForm" property="clientList" value="action" label="display"/>
               </html:select>
            </span>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td><b></b></td>
                    <td class="returnright" nowrap>Item Number:</td>
                    <td><span
                            id="sku"><html:text property="sku" size="30" styleClass="form"/></span></td>
                    <td><html:link
                            href="javascript:retrieveURLt('/internal/startLotCount.do?ask=COMMAND_NAME_1','lotCountForm');">
                        <html:img
                                src="/internal/images/add.png" border="0"/></html:link></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <hr>
                    </td>
                </tr>
            </table>
            <span id="errors">  <html:errors/>${errors}</span>
            <span class="outcome" id="outcome">${outcome}</span>
       <span id="items">
                       <html:hidden property="createdBy"/>

                       <html:hidden property="inventoryId"/>
            <html:hidden property="createdDate"/>
            <html:hidden property="description"/>
            <html:hidden property="currentLocation" value="<%= currentLocation %>"/>

          <c:if test="${sessionScope.lotCountForm.inventoryId!=null}">
              <table>
                  <tr>
                      <td colspan="4">${lotCountForm.sku} - ${lotCountForm.description}
                          <hr>
                          Lot Quantities should be all units received but not officially shipped as of when this form is saved
                      </td>
                  </tr>
              </table>
              <table class="returnitems">
                  <th class="returnxl">Lot Value</th>
                  <th class="returnxl">Current Quantity</th>
                  <th class="returnsmall">New Quantity</th>
                  <!-- <th class="return">Return Reason</th>-->
                  <c:forEach var="formItems" items="${sessionScope.lotCountForm.formItems}" varStatus="itemStatus">
                      <tr>

                          <td class="returnxl">${formItems.lotValue}</td>

                          <td class="returnxl">${formItems.lotQty}</td>
                          <td>
                              <html:hidden name="formItems" property="lotValue" indexed="true"/>
                              <html:hidden name="formItems" property="lotQty" indexed="true"/><html:text
                                  name="formItems" property="newQty" indexed="true" size="10" styleClass="form"/></td>


                      </tr>

                  </c:forEach>
                  <tr>
                      <td class="returnxl"><html:text name="lotCountForm" property="newLotValue" size="30" styleClass="form"/><html:submit property="submit" value="addLot"><bean:message
                              key="button.addLotValue"/></html:submit></td>
                      <td class="returnxl"></td>
                      <td></td>
                  </tr>
              </table>
              <html:submit property="submit"><bean:message key="button.saveLotCountAdjust"/></html:submit>
          </c:if>
       </span>

        </html:form>

        <div id="throb"><img src="<html:rewrite page="/images/indicator_white.gif" module=""/>" alt=""/></div>

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