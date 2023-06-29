<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Screen Pop</title>

<style type="text/css">
    @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dijit/themes/soria/soria.css";
    @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/resources/dojo.css";
    body, html {
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0
    }

    #header {
        margin: 0;
    }

    .testClass {
        background-color: #00aaee;
    }

</style>



<!-- load the dojo toolkit base -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/dojo.xd.js"
        djConfig="parseOnLoad:true,isDebug:false"></script>

<script type="text/javascript">
    dojo.require("dijit.layout.AccordionContainer");
    //dojo.require("dijit.layout.AccordionPane");

    dojo.require("dijit.layout.BorderContainer");


    dojo.require("dijit.TitlePane");
    dojo.require("dojo.parser");

    dojo.require("dojox.layout.ContentPane");
    dojo.require("dijit._Calendar");



    dojo.addOnLoad(function() {


        dojo.byId('cal').setAttribute("dojoType", "dijit._Calendar");
        dojo.parser.parse(dojo.byId('cal').parentNode)



    });</script>
      
</head>
<body class="soria">


<div id="main" dojoType="dijit.layout.BorderContainer" liveSplitters="false" design="sidebar"
     style="width:100%; height:200%">


<div dojoType="dijit.layout.ContentPane" region="top" id="thetopithink">


        <table width="100%">
            <s:if test="%{ccClient.urlEntry.length()>0}">
            <tr>
<td><a href="<s:property value="%{ccClient.urlEntry}"/>" target="_blank">Place Order</a></td>

            </s:if>
             <s:if test="%{ccClient.urlStatus.length()>0}">

<td><a href="<s:property value="%{ccClient.urlStatus}"/>" target="_blank">Order Status</a></td>

            </s:if>
             <s:if test="%{ccClient.urlService.length()>0}">

<td><a href="<s:property value="%{ccClient.urlService}"/>" target="_blank">Customer Service</a></td>

            </s:if>
           <s:if test="%{ccClient.urlKbBase.length()>0}" >

<td><a href="<s:property value="%{ccClient.urlKbBase}"/>" target="_blank">Kbase</a></td>

            </s:if>
            <s:if test="%{ccClient.urlQuickRef.length()>0}">
          
<td><a href="<s:property value="%{ccClient.urlQuickRef}"/>" target="_blank">AutoShip</a></td>

            </s:if>
              <s:if test="%{ccClient.topUrlSix.length()>0}">

<td><a href="<s:property value="%{ccClient.topUrlSix}"/>" target="_blank"><s:property value="%{ccClient.topUrlSixName}"/></a></td>

            </s:if>
             </tr>
        </table>


</div>
<div dojoType="dijit.layout.ContentPane" style="width: 200px;" id="leftAccordion" region="leading" splitter="false">

        <br>
        <center>

On Call:  <script language="JavaScript">
TargetDate = new Date();
BackColor = "white";
ForeColor = "navy";
CountActive = true;
CountStepper = 1;
LeadingZero = true;
DisplayFormat = " %%M%%:%%S%% ";
FinishMessage = "It is finally here!";
OverCall = <s:property value="ccClient.callThreshold"/>;            
</script>
<script language="JavaScript" src="/timer.js"></script> <br>
            
            <b><s:property value="ccClient.mlogCampaignName"/> </b>
        </center>

    <hr>
    <div id="cal"></div>
    <br><br>
    <b>SEND:</b><br>
    <center>
        <a href="http://internal.owd.com:8080/callcenter/problemForm.do?client=<s:property value="ccClient.mlogCampaignName"/> &amp;callId="
           target="_blank"
           title="send problem form">Escalation Form</a>

        <p><a href="http://internal.owd.com:8080/callcenter/caseSearch.do?clientId=<s:property value="ccClient.clientFkey"/>" target="_blank">Escalation Form Search</a>

        <p>
         <a href="http://internal.owd.com/machform/view.php?id=10" target="_blank">Supervisor Escalation Form</a>
        </p></center>

         <iframe id="theframeside" src="<s:property value="ccClient.sideLinks"/>" width="98%" height="98%"></iframe>
</div>
<div dojoType="dijit.layout.BorderContainer" liveSplitters="true" region="center" splitter="false">

    <div dojoType="dijit.layout.ContentPane" id="topscreen" region="top" style="overflow:auto;height:300px">
                  ${ccClient.announceScript}
    </div>
    <div dojoType="dijit.layout.ContentPane" id="leftscreen" region="left" style="width:48%">
            ${ccClient.sideInfo}
            </div>
    <div dojoType="dojox.layout.ContentPane" id="rightscreen" region="right" style="width:48%">
        <iframe id="theframe" src="<s:property value="ccClient.frameUrl"/>" width="98%" height="98%"></iframe>


    </div>


</div>


</div>
<!--end Border Container -->


</body>
</html>
