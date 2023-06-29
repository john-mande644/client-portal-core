<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/themes/smoothness/jquery-ui.css" />
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/js/chosen.jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="/css/chosen.min.css">


    <script type="text/javascript">
      $( document).ready(function(){
           $(".clients").chosen();

      });
        function showSkus(){
         if($(".facility").prop("selectedIndex")=="0"){
             alert("Please Select Facility");
             return;
         }
         if($(".clients").prop("selectedIndex")=="0"){
             alert("Please Select Client");
             return;
         }
          $("#skus").load("/wms/cycleCountSheets/getSkus.action",{'clientId':$(".clients").prop("value"), 'facility':$(".facility").prop("value")});



        }
    </script>
    <style type="text/css">
        .hide{display: none}
        .advanced{padding-top:15px;}
        .numArea{
            height:300px;
            width:200px;

        }
        .typelist{
            float:left;
        }
        .dotheselect{
            float:right;
            position:relative;
        }
        #lstBox1, #lstBox2{
          height:300px;
          width:200px;

        }
    </style>
</head>


<s:actionerror/>

<s:form action="load.action" theme="simple">
  <s:select list="clients" name="clientId" label="Client Id" headerKey="-1" headerValue="Select Client" cssClass="clients"/>

    <s:select list="facilities" name="facility" label="Facility" headerKey="-1" headerValue="Select Facility" cssClass="facility"/>


     <s:submit label="Load All Sku's" value="Load All Sku's"/>
</s:form>
    <button class="selectSku" onClick="showSkus(); return false;" >Advanced Selection</button>
    <div id="skus" >

    </div>
