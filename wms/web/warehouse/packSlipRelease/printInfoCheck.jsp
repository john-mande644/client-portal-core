<%@ taglib prefix="s" uri="/struts-tags" %>
<meta http-equiv="refresh" content="30">

<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/themes/smoothness/jquery-ui.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script>
    <script>
        $(function() {
            $( document ).tooltip({
                        track: true
                    }
            );
        });
    </script>
<style type="text/css">
    body{
        background-color: #F3F3F3;
    }
    .header{
        font-size: 2em;
        text-align: center;
    }
    .global{
        width: 75%;
    }
    .facilitiesWrapper{
padding-top: 30px;

    }
    .addressVerify{
        float:left;
        background-color: white;
        padding:10px;
        margin-right:15px;

    }
    .created{
        float: left;
        background-color: white;
        padding:10px;
    }
    .label{
        padding: 2px;
        font-size: 1.3em;
    }
    .clearer{
        clear:both;

    }
    .infolabel{
        font-weight: bold;
    }
    .facility{
        background-color: white;
        float: left;
        margin-bottom: 15px;
        margin-right: 15px;
        padding: 10px;
        width: 29%;
        -moz-box-shadow: 0 4px 8px rgba(0,0,0,0.5);
        -webkit-box-shadow: 0 4px 8px rgba(0,0,0,0.5);
        box-shadow: 0 4px 8px rgba(0,0,0,0.5);
    }
    .facilitylabel{
        border-bottom: solid #000000 2px;
    }
    .general{
        padding-top:12px;

    }
    .section{
        font-size: 1.2em;
        padding-bottom: 4px;
    }
    .download, .printCheck{
        background-color: lightgreen;
        padding: 5px;
    }
    .download{
        margin-bottom: 15px;
        margin-top:15px;
    }
    .red{
        background-color: red;
    }
    .headlabel{
        font-size: 1.3em;

    }
    .shaddow{
        -moz-box-shadow: 0 4px 8px rgba(0,0,0,0.5);
        -webkit-box-shadow: 0 4px 8px rgba(0,0,0,0.5);
        box-shadow: 0 4px 8px rgba(0,0,0,0.5);
    }
</style>


    </head>

<body>
<div class="header">
Packing Slip Error Info Page
</div>

<div class="globalwrapper">
    <div class="global">
        <span class="headlabel" title="This info is global, if they are problems indicated here all facilities will be affected">Global Info</span>
        <br>
    <div class="addressVerify shaddow" title="Number of orders left to verify. There will usually be a few in here. If total is greater by 100's, then Address Verify might be broken.">
        <span class="label">Address Verify</span><br>
   <span class="infolabel"> Total to Verify:</span> <s:property value="info.addressTotal"/><br>
     <span class="infolabel">  New within last 3 hours:</span> <s:property value="info.addressNew"/>
    </div>
    <div class="created shaddow" title="Number of packing slips that need to be created. Should match the verify numbers. If the numbers are high, as long as they are going down each time you refresh the page everything is working.">
        <span class="label">Create Packing Slip</span><br>
       <span class="infolabel"> Total to Create:</span> <s:property value="info.createdTotal"/><br>
       <span class="infolabel"> New within last 3 hours:</span> <s:property value="info.createNew"/>
    </div>
        </div>


</div>
<div class="clearer"></div>
<div class="facilitiesWrapper">
<div class="facilities">
<span class="headlabel" title="Facility breakdown. Any troubles here are specific to that facility">Facility Breakdown</span>
    <br>
    <s:iterator value="info.facilities">
        <div class="facility" id="<s:property value="value.name"/>">
         <span class="label facilitylabel">Facility <s:property value="value.name"/></span><br>
            <div class="general">
                  <span class="infolabel" title="Number of Orders Left to Verify">Left to Verify: </span><s:property value="value.toVerify"/><br>

                  <span class="infolabel" title="Number of Packing Slips needing to be created">Left to Create: </span><s:property value="value.toCreate"/><br>

            </div>
            <div class="download  <s:if test="%{value.downloadDifference > 5}">red </s:if> shaddow">
               <span class="section">Download Check Info</span> <br><br>
                <span class="infolabel" title="Time last reported">Last Check:</span> <s:property value="value.downloadTime"/><br>
                <span class="infolabel">Left to Download:</span> <s:property value="value.toDownload"/><br>
                <span class="infolabel" title="Time Since last check, if this is greater than 5 check the local printserver">Time Since Download Check:</span> <s:property value="value.downloadDifference"/> min<br>

            </div>
            <div class="printCheck <s:if test="%{value.printCheckDifference > 5}">red </s:if> shaddow">
                 <span class="section">Print Check Info</span><br><br>
                <span class="infolabel" title="Time last reported">Last Check:</span> <s:property value="value.printCheckTime"/><br>
                <span class="infolabel">Left to Print:</span> <s:property value="value.printing"/><br>
                <span class="infolabel" title="Time Since last check, if this is greater than 5 check the local printserver">Time Since Print Check:</span> <s:property value="value.printCheckDifference"/> min<br>

            </div>
        </div>

    </s:iterator>
</div>
<div class="clearer"></div>




Hover over section for info
</div>

</body>
</html>