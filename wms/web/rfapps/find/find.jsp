<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    java.sql.ResultSet rs = null;
    String reportAction = (String) request.getAttribute("searchSKU");

%>
<!doctype html public "-//w3c/dtd HTML 4.0//en">

<html>
<head>


    <title>Warehouse Location Manager</title>

    <jsp:include page="/rfapps/includes/androidResponsive.jsp"/>
</head>
<div class="row">
    <div class="large-6 columns">
    <TABLE>
        <TR>
            <TH ALIGN=LEFT>
                <html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit>${loginName}
                </html:form></TH>
            <TH ALIGN=RIGHT>
            </TH>
        </TR>
    </TABLE>
    <HR>
    <c:if test='${error != null}'>
        <script>
            try {
                javautil.playErrorSound();
            } catch (err) {

            }
        </script>
        <B class="error">${error}</B>
        <script>
            var gen = new ActiveXObject("SymbolBrowser.Generic");
            gen.PlayWave("\\err.wav", 0);
        </script>
        <br>
    </c:if>
    <c:if test='${outcome != null}'>
        <font color='blue' size="+2">
            <b>${outcome}</b>
        </font>
        <hr>
    </c:if>
    <span class="label">Item Location History</span>
    <BR>
    <html:form action="findSku" focus="sku">
        <html:text property="sku" value="" styleId="autoSubmit"/>
        <br>
        <html:submit value="FIND"/>
    </html:form>

    <c:if test='${searchSku.inventoryId != null}'>
        Found ${fn:length(searchSku.locations)} Locations!!!

       <div class="row">
           <div class="small-6 columns">
               <c:if test='${searchSku.imageThumb != null}'>

                           ${searchSku.imageThumb}

               </c:if>
           </div>

           <div class="small-6 columns">
                   ${searchSku.clientName}<BR>
               <span class="inventorynum">${searchSku.inventoryNum}</span><BR>
               <span class="inventoryid">${searchSku.inventoryId}</span><BR>
                   ${searchSku.description}<br>
               On Hand: ${searchSku.quanityOnHand}  <br>
                       <c:if test='${searchSku.size.length() >0}'>
                           <span class="size">Size: ${searchSku.size}</span><br>
                       </c:if>
                       <c:if test='${searchSku.color.length() >0}'>
                           <span class="color">Color: ${searchSku.color}</span><br>
                       </c:if>
                       <c:if test = '${searchSku.lotControlled}'>
                           <span class="lotType">Lots: ${searchSku.lotControlledType}</span>
                       </c:if>
               <HR>
           </div>
       </div>




        <table class="thelocationslist">
            <c:forEach var="item" items="${searchSku.locations}">
                <tr>
                    <td><span class="location2 ${item.priority}">${item.barcode}</span></td>
                    <td>Assigned: ${item.assignDate}</td>
                </tr>
                <c:if test='${item.notes != ""}'>
                <tr>
                    <td colspan="2">

                            Notes: ${item.notes}

                        <hr>
                    </td>

                </tr>
                </c:if>
                <c:if test="${item.lot != null}">
                    <tr>
                        <td colspan="2">
                            Lot Assigned: ${item.lot.lotValue}
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="2">
                        <hr/>

                    </td>
                </tr>


            </c:forEach>
        </table>

        <html:form action="printInventoryLabel">
            <html:hidden property="sku" value='${searchSku.inventoryId}'/>
            <html:hidden property="action" value="find"/>
            <html:text property="numlabels" value="1" size="6"/>
            <html:submit property="submit"><bean:message key="button.invLabel"/></html:submit>
            <br>
            <html:submit property="submit"><bean:message key="button.printLargeLabel"/></html:submit> <br>

            <html:submit property="submit"><bean:message key="button.palletTag"/></html:submit>
        </html:form>
    </c:if>

    </div>
    <div class="large-6 columns locHistory small-uncollapse" >
        <c:if test='${deletedInfo != null}'>
            <div>

              <h3>These are deleted locations</h3><br>
                <div class="row" >
                    <div class="small-2 columns show-for-medium-up">
                            Location Id
                    </div>
                    <div class="small-2 columns">
                           Location
                    </div>
                    <div class="small-2 columns">
                           Assigned
                    </div>
                    <div class="small-2 columns">
                           By Who
                    </div>
                    <div class="small-3 columns">
                           Deleted
                    </div>
                </div>
            <c:forEach var="item" items="${deletedInfo}" varStatus="loop">
                <div class="row ${loop.index % 2 == 0 ? 'even' : 'odd'} ">
                    <div class="small-2 columns show-for-medium-up">
                            ${item.location}

                    </div>
                    <div class="small-2 columns">
                            ${item.readableLocation}
                    </div>
                    <div class="small-2 columns">
                            ${item.assignDate}
                    </div>
                    <div class="small-2 columns">
                            ${item.user}
                    </div>
                    <div class="small-3 columns">
                            ${item.removedate}
                    </div>
                </div>






                    </c:forEach>


            </div>
        </c:if>


    </div>
</div>

</body>

</html>