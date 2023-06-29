<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

 <head>
     <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
 </head>
 <body>
 <form action="/wms/do/inv-menu"><input type="submit" value="INV MENU"><s:property value="name"/> </form>
 <s:actionerror/>
  <s:actionmessage/>


 Please enter in the Qty you used of <s:property value="stb.inventoryNum"/>.
     <s:form action="getQtyHH.action">
         <s:hidden name="currentFacility"/>
         <s:hidden name="invId"/>
         <s:hidden name="name"/>
     <s:textfield name="qty"  id="autoSubmit" value="" />
     <s:submit onclick="return confirm('You are using ' + document.getElementById('autoSubmit').value + 'of  %{stb.inventoryNum}')"></s:submit>
     </s:form>


 </body>
 <script>
  try{
     document.getElementById("autoSubmit").type = 'number'
      document.getElementById("num").type = 'number'
        javautil.delayedFocus();
     }catch(err){
        document.forms[1].elements[2].focus();
     }
     function focusButton(){
          document.getElementById("autoSubmit").focus();
         }
 </script>
 </html>
