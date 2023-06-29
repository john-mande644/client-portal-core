<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Screen Pop</title>

<style type="text/css">

    body, html {

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
      .body{
          background-color:#0065A2;
          padding-left:5px;
          padding-right:5px;
      }
      div{
          background-color:white;
          padding-left:3px;
          padding-left:3px;
      }
      #topscreen{
         height:500px;
          margin-top:10px;
          overflow:auto;

      }
    .problemlinks{
        padding-bottom: 3px;
        background-color: #d0d0d0;
        text-align:center;

    }
    .problemlinks a, .problemlinks a:visited{
        color: #000000;
        padding-left:60px;
        font-size:20px;
        padding-right:60px;
    }
    #bottomwrapper{
        margin-top:10px;

    }
    #thetopithink{

    }

</style>


      
</head>
<body class="body">


  <div class="problemlinks" id="top" tabindex="0">
          <a href="http://internal.owd.com:8080/callcenter/problemForm.do?client=<s:property value="ccClient.mlogCampaignName"/> &amp;callId="
           target="_blank"
           title="send problem form">Escalation Form</a>

      <!-- <a href="http://internal.owd.com:8080/callcenter/caseSearch.do?clientId=<s:property value="ccClient.clientFkey"/>" target="_blank">ProblemForm Search</a>
          -->

         <a href="http://internal.owd.com/machform/view.php?id=10" target="_blank">Supervisor Escalation Form</a>

  </div>

<div id="thetopithink">


        <table width="100%">
            <s:if test="%{ccClient.urlEntry.length()>0}">
            <tr>
<td><a href="<s:property value="%{ccClient.urlEntry}"/>" target="_blank"><s:property value="%{ccClient.urlEntryName}"/></a></td>

            </s:if>
             <s:if test="%{ccClient.urlStatus.length()>0}">

<td><a href="<s:property value="%{ccClient.urlStatus}"/>" target="_blank"><s:property value="%{ccClient.urlStatusName}"/></a></td>

            </s:if>
             <s:if test="%{ccClient.urlService.length()>0}">

<td><a href="<s:property value="%{ccClient.urlService}"/>" target="_blank"><s:property value="%{ccClient.urlServiceName}"/></a></td>

            </s:if>
           <s:if test="%{ccClient.urlKbBase.length()>0}" >

<td><a href="<s:property value="%{ccClient.urlKbBase}"/>" target="_blank">Kbase</a></td>

            </s:if>
            <s:if test="%{ccClient.urlQuickRef.length()>0}">
          
<td><a href="<s:property value="%{ccClient.urlQuickRef}"/>" target="_blank"><s:property value="%{ccClient.urlQuickRefName}"/></a></td>

            </s:if>
              <s:if test="%{ccClient.topUrlSix.length()>0}">

<td><a href="<s:property value="%{ccClient.topUrlSix}"/>" target="_blank"><s:property value="%{ccClient.topUrlSixName}"/></a></td>

            </s:if>
             </tr>
        </table>


</div>






    <div  id="topscreen" >
                  ${ccClient.announceScript}
    </div>
<div id="bottomwrapper">
    <div id="leftscreen" style="width:48%;float:left">
            ${ccClient.sideInfo}
            </div>
    <div  id="rightscreen" style="width:48%;float:right;height:800px;">
        <iframe id="theframe" src="<s:property value="ccClient.frameUrl"/>" width="98%" height="98%" onload="focus()">

        </iframe>


    </div>


</div>



<!--end Border Container -->



</body>
</html>
