<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
   <style type="text/css">
       .boxName{
           font-size:40px;
           color:blue;
       }
   </style>
</head>
<body onload="document.getElementById('autoSubmit').focus();" >
 <div style="float:left;">
<a href="/wms/miscMenu/start.action" class="button">MENU</a>
     </div>
   <div style="float:right;position:relative;">
       <s:set name="theme" value="'simple'" scope="page" />
    <s:form action="cancelPack.action">
        <s:hidden name="pickerId"/>
        <s:submit value="Cancel" onclick="return confirm('Are you sure you want to cancel?')"></s:submit>
    </s:form>
        </div>
    <s:if test="hasActionErrors()">
<div id="errorDiv" style="padding-left: 10px; margin-bottom: 5px">
    <script>
        try{
            javautil.playErrorSound();
        }catch(err){

        }
    </script>
     <span class="error">
     <s:iterator value="actionErrors">
         <span class="errorMessage"><s:property escape="false" /></span>
     </s:iterator>
     </span>
</div>
</s:if>
<s:actionmessage/>
<div style="clear:both"></div>
 <div class="boxName">
     Use This box:
     <s:property value="boxName"/>
 </div>





<s:form action="scanItem.action">
    Scan Item you are packing!! <br>
    <s:textfield name="itemScan" id="autoSubmit" value=""/>
    <s:hidden name="pickerId"/>
    <s:hidden name="boxName"/>
    <s:submit></s:submit>
</s:form>

<table class="fullWidthTable" style="font-size:25px;">

    <thead><tr>
        <th>
            InvId
        </th>
        <th>
            InvNum
        </th>
        <th>
            Qty
        </th>
        <th>
             Packed
        </th>
    </tr>

    </thead>



<s:iterator value="items">
   <tr> <td>
       <s:property value="invId"/>
   </td>
   <td>
       <s:property value="invNum"/>
   </td>
   <td>
       <s:property value="qty"/>
   </td>
   <td>
       <s:property value="qtyPacked"/>
   </td></tr>

</s:iterator>
</table>


</body>
</html>
