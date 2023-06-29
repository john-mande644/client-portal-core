<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="viewport" content="width=device-width, height=device-height, user-scalable=no, initial-scale=1" />
<link rel="stylesheet" href="/css/foundation.css" />
<link rel="stylesheet" href="/wms/css/responsiveAddon.css" />
<script src="/js/vendor/modernizr.js"></script>
<script src="/js/vendor/jquery.js"></script>
<script src="/js/foundation.min.js"></script>
<script src="/js/foundation/foundation.offcanvas.js"></script>
<script>
$(document).ready(function(){
    $(document).foundation();
})
</script>






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
