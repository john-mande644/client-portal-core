<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<html>
<head>
    <link href="/wms/css/packslip.css" rel="stylesheet" type="text/css">
    <link href="/dojo-release-1.3.1/dijit/themes/tundra/tundra.css" rel="stylesheet" type="text/css">
	
    <script type="text/javascript" src="/dojo-release-1.3.1/dojo/dojo.js"
	    djConfig="parseOnLoad:true, isDebug:false"></script>

	<script type="text/javascript">
			dojo.require("dijit.TitlePane");
         dojo.require("dijit.form.Form");
        dojo.require("dijit.form.CheckBox");
         dojo.require("dijit.form.ComboBox");
        dojo.require("dijit.form.Button")
            </script>
 <script type="text/javascript">

         function printGroup(id){
             console.log(id);
             var printer =  dijit.byId(id+"_detailsPrinter").value;
            var req =	dojo.xhrGet( {
        // The following URL must match that used to test the server.
        url: "/wms/packSlipRelease/groupPrint.action",
        handleAs: "json",
        timeout: 20000,
        content: {detailsPrinter: printer,clientId:id},
        load: function(responseObject, ioArgs) {
          // Now you can just use the object
          console.dir(responseObject);  // Dump it to the console
          console.log(responseObject.message);
          console.log(responseObject.error.length);


          return responseObject;
        }

});

req.addCallback(function(response) {
	console.log("This is the first callback");
	console.log(response.message);
	if(response.error.length>0){
		console.log("error");
		return new Error(response.error);


	}
      dijit.byId(id+"_drop").refresh();

    return response;
});

req.addErrback(function(response){
	console.log("Errororororor");
	console.log(response.message);

	return response;
});






     return false;

         }


          function printDetails(id,namev){
            console.log(id);
              console.log(namev);
            var printListv = dijit.byId(id+"_f").attr('value');

            console.dir(printListv[namev])  ;

           var printer =  dijit.byId(id+"_detailsPrinter").value;
             var req =	dojo.xhrGet( {
        // The following URL must match that used to test the server.
        url: "/wms/packSlipRelease/detailsPrint.action",
        handleAs: "json",
        timeout: 20000,
        content: {detailsPrinter: printer,printList: printListv[namev],clientId:id},
        load: function(responseObject, ioArgs) {
          // Now you can just use the object
          console.dir(responseObject);  // Dump it to the console
          console.log(responseObject.message);
          console.log(responseObject.error.length);


          return responseObject;
        }

});

req.addCallback(function(response) {
	console.log("This is the first callback");
	console.log(response.message);
	if(response.error.length>0){
		console.log("error");
		return new Error(response.error);


	}
      dijit.byId(id+"_drop").refresh();
    
    return response;
});

req.addErrback(function(response){
	console.log("Errororororor");
	console.log(response.message);

	return response;
});





       return false;
    }
  </script>
 <script type="text/javascript">
     function enterCode() {
       var code = prompt("Enter the access code");
         if(code==7172){
             return true;
         }else{
             return false;
         }
     }
 </script>
</head>
<title>OWD Packing Slip Release Page</title>

<body class="tundra">
<div class="container">
    <div id="advancedLinks">
        <span class="avlink">
           <a href="/wms/packSlipRelease/advancedPrint.action?facility=<s:property value="location"/>&clientId=business"  target="_blank">Business Orders</a>
        </span>
        <span class="avlink">
            <a href="/wms/packSlipRelease/startBatchPrint.action?facility=<s:property value="location"/>" target="_blank">Batch Print</a>
        </span>
        <span class="avlink">
           <a href="/wms/packSlipRelease/groupReprint.action?facility=<s:property value="location"/>" onclick="return enterCode()" target="_blank">Reprint Non-picked</a>
        </span>
        <span class="avlink">
           <a href="/wms/packSlipRelease/advancedPrint.action?facility=<s:property value="location"/>&clientId=red"  target="_blank">REDS!!!!</a>
        </span>

         <span class="avlink">
           <a href=" /wms/packSlipRelease/printInfoCheck.action"  target="_blank">PackSlip Status</a>
        </span>
        <span class="avlink">
           <a href="/wms/packSlipRelease/advancedPrint.action?facility=<s:property value="location"/>&clientId=prsnl"  target="_blank">Personalization</a>
        </span>
        <span class="avlink">
           <a href="/wms/packSlipRelease/advancedPrint.action?facility=<s:property value="location"/>&clientId=gcard"  target="_blank">Greeting Cards</a>
        </span>
        <br>
        <span class="packTopText">Printing removed from this page. Please use advanced print links or specific links above.</span>
    </div>
    <div id="packtitle"class="center title"><H2>OWD Packing Slip Release</H2></div>
    <div class="messages"><font color="red"> <s:actionerror/></font><font color="blue"><s:actionmessage/></font></div>
  <div id="data">
    <div id="large">
    <div class="printerscontainer">
         <s:if test="clients.size == 0">
       <div class="label">     Nothing to print</div>
        </s:if>
        <s:form action="release">
            <!-- <s:submit label="Print Selected" value="Print Selected" theme="simple"/> -->
            <table class="printlist">
               
                <thead>
                    <th>Client</th>
                    <th>Today's Orders</th>
                    <th>Other SLA Orders</th>
                    <th><!--Printer to Print To--></th>
                    <th>Print</th>
                </thead>
                <s:iterator value="clients">

                    <tr>
                        <td class="client"><s:property value="value.clientName"/><br>
                            <a href="/wms/packSlipRelease/advancedPrint.action?facility=<s:property value="location"/>&clientId=<s:property value="key"/>" target="_blank">Advanced Print</a></td>
                        <td class="orders"><s:property value="value.todayCount"/></td>
                        <td class="orders"><s:property value="value.otherCount"/></td>
                        <td class="printers">
                          <!--  <s:select list="printers" name="clients['%{key}'].printer" theme="simple"
                                      /> --><br>
                        </td>
                        <td class="print"><s:hidden name="clients['%{key}'].clientName"
                                                    value="%{clients.get(key).clientName}"/>
                            <s:hidden name="clients['%{key}'].clientId" value="%{clients.get(key).clientId}"/>
                            <!-- <s:checkbox name="clients['%{key}'].print" value= "%{value.print}" theme="simple"
                                       /> -->
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">

                        <div id="<s:property value="value.clientId"/>_drop"dojoType="dijit.TitlePane" title="order details" refreshOnShow="true" open="false" width="275" href="/wms/packSlipRelease/clientDetails.action?clientId=<s:property value="value.clientId"/>&clientName=<s:property value="value.clientName"/>">


				</div><!-- end title pane -->
                            <hr>
                        </td>
                    </tr>


                </s:iterator>
                <tr>
                    <td>
                       <!-- <s:submit label="Print Selected" value="Print Selected" theme="simple"/> -->
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
        </div>

    <div id="small">
    <div class="currentprinting">
       <div class="printerlist">
        <div class="label">Current Orders Printing:</div>

        <s:iterator value="currentPrinting.entrySet()">
            <s:property value="key"/>:&nbsp
            <s:property value="value"/>
                <br>

        </s:iterator>

        </div>

        <div class="center bottom"><span class="label">Date Loaded: </span><s:property value="dateLoaded"/></div>
    </div>

    <div class="settings">
      <div class="top">

       <span class="label">Change Current Location</span>


       <s:form action="location">

        <s:select list="locations" name="location" theme="simple" value="location"
                                    />

        </div>
         <div class="center bottom">
        <s:submit   label="Set New Location/Refresh" value="Set New Location/Refresh" theme="simple"/>
           </s:form>
         </div>

    </div>
    <div class="singleOrder">
    <div class="center top">
        <span class="label">Enter Order Number To Print</span>
        <br>(This is for orders in print queue)

       <s:form action="singleOrder">
         <s:textfield name="orderNum" theme="simple" value=""/>
           <br>
        <s:select list="singlePrinters" name="singlePrinter" theme="simple"
                                    />

        </div>
         <div class="center bottom">
        <s:submit   label="Print Order" value="Print Order" theme="simple"/>
           </s:form>
         </div>

    </div>

      <div class="reprint">
          <span class="label">Reprint order</span>
           <s:form action="reprintOrder">
         <s:textfield name="orderNum" theme="simple" value=""/>
           <br>
        <s:select list="singlePrinters" name="singlePrinter" theme="simple"
                                    />
                  <s:submit   label="Print Order" value="Print Order" theme="simple"/>
                  </s:form>
      </div>
        </div>
      </div>
</div>
</body>
</html>

