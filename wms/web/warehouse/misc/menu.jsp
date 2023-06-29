<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
</head>
     <body>

   <a href="/wms/do/menu" class="button">MENU</a> <P class="button>">
       <hr size="1"/>
 <!-- <a href="/wms/midcoBarcode/start.action" class="button">MIDCO DTA</a> <P class="button>">-->
  <!--
       <a href="/wms/removeSerialFromOrder/start.action" class="button">REMOVE DTA SERIALS</a> <P class="button>">
      -->

   <a href="/wms/checkDCHold/start.action" class="button">Check Hold Status</a> <P class="button>">
       <a href="/wms/workOrderHold/start.action" class="button">Work Order Hold</a> <P class="button>">
       <a href="/wms/bulkLocationCheck/start.action" class="button">location check</a> <P class="button>">

       <s:if test="quickAssign">

           <a href="/wms/quickToteAssign/start.action" class="button">Quick Tote Assign</a> <P class="button>">
           </s:if>
       <!-- <a href="/wms/sneakPack/start.action" class="button">Candles!!!!!</a> <P class="button>">
        <a href="/wms/onDemandPrinting/start.action" class="button">Boll Branch</a> <P class="button>">-->
     </body>

</html>