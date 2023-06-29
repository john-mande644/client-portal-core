<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

 <head>
     <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
 </head>
 <body>
 <form action="/wms/do/inv-menu"><input type="submit" value="INV MENU"> <s:property value="name"/> </form>

 <s:property value="theError"/>
 <s:actionmessage/>


 Please scan the Supplies you are going to use.
     <s:form action="getSku.action">
         <s:hidden name="name"/>
     <s:textfield name="invId" id="autoSubmit" value=""/>
     <s:submit></s:submit>
     </s:form>


 </body>
 <script>
  try{
     document.getElementById("autoSubmit").type = 'number'
     }catch(err){

     }
 </script>
 </html>
