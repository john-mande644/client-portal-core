<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
     <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
 </head>
<body>
 <span class="error"><s:actionerror/> </span>
<s:actionmessage/>
<a href="/wms/checkFront/start.action" class="button">Back</a>
 
<hr>
 <span class="label">
SKU: <s:property value="sku"/><br>
Description: <s:property value="description"/><br>
First Location: <s:property value="frontLocation"/><br>

<s:if test="goodfront">
 <s:if test="otherLocations.size == 0">
   <font color="blue">  Everything Should be fine, no locations to change first to.</font>

     </s:if>
    <s:else>
    First Location is found in current list. You can change if needed.
            </s:else>
        
        

    
</s:if>
      <s:else>
<span class="error">    First Location is not Valid. Please fix.</span>
          </s:else>
    </span>

 <hr>

 <table>
     <s:iterator value="otherLocations" id="loc">
         <tr><td>
     <s:form action="changeFront" theme="simple">
        <span class="label"> <s:property/></span>
         <s:hidden name="sku" value="%{sku}"/>
         <s:hidden name="newLocation" value="%{loc}"/>
         <s:hidden name="description" value="%{description}"/>
          <s:hidden name="barcode" value="%{barcode}"/>
         <s:hidden name="otherLocations" value="%{otherLocations}"/>
         </td><td>
         <s:submit label="Make First" value="Make First" theme="simple"/>
         </s:form>
             </td>
             <td>
                    <s:form action="removeLocation" theme="simple">
                         <s:hidden name="sku" value="%{sku}"/>
         <s:hidden name="newLocation" value="%{loc}"/>
         <s:hidden name="description" value="%{description}"/>
          <s:hidden name="barcode" value="%{barcode}"/>
         <s:hidden name="otherLocations" value="%{otherLocations}"/>
                          <s:submit label="Remove Location" value="Remove Location" theme="simple" onclick="return confirm('Are you sure you want to remove?')"/>
                        </s:form>
             </td>
         </tr>
     </s:iterator>
   </table>


</body>
</html>
