<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>

<meta name="viewport" content="width=device-width, height=device-height, user-scalable=no target-densitydpi=high-dpi" />
 <meta name="viewport" content="width=device-width, height=device-height, user-scalable=no, initial-scale=1" />
      <link href="<html:rewrite page="/rfapps/computer.css" />" rel="stylesheet" type="text/css" media="screen">
      <link href="<html:rewrite page="/rfapps/handhelds.css" />" rel="stylesheet" type="text/css" media="handheld">
      <link href="<html:rewrite page="/css/androidmdpi.css" />"  rel="stylesheet" media="all and (-webkit-device-pixel-ratio:1) and (max-device-width: 500px)">
     <link href="<html:rewrite page="/css/androidmdpi.css" />"  rel="stylesheet" media="all and (-webkit-device-pixel-ratio:1.5) and (max-device-width: 500px)">
<link href="<html:rewrite page="/css/androidmedium.css" />"  rel="stylesheet" media="all and (min-device-width:501px) and (max-device-width: 900px)">
     <link href="<html:rewrite page="/css/androidhdpi.css" />"  rel="stylesheet" media="all and (min-device-width: 1000px)">
<link href="<html:rewrite page="/css/styles.css" />"  rel="stylesheet" >
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<SCRIPT>
             function insertAndSubmit(scanData){
                 var justinsert;
                 try{
                   justinsert = entertext;
                 }catch (Exception ){
                     justinsert = false
                 }

                 if(justinsert){
                  document.activeElement.value = scanData;

                 } else{


               document.getElementById("autoSubmit").value = scanData;
                     $('#autoSubmit').parents('form:first').submit();
                 }
             }
</SCRIPT>

 <%
     try{
     if(request.getSession(true).getAttribute("owd_current_location").toString().equals(request.getSession(true).getAttribute("owd_default_location"))==false){  %>
<script>
$(document).ready(function(){
    $("body").prepend('<div class="locationMismatch">Working in <%=request.getSession(true).getAttribute("owd_current_location").toString()%> you are in <%=request.getSession(true).getAttribute("owd_default_location")%></div>');


})
</script>
 <%}
     }catch (Exception e){

     }
 %>

