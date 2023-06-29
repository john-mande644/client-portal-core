<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>



  <html>
  <body>



    <s:actionmessage/>

      <div id="<s:property value="clientId"/>_f" dojoType="dijit.form.Form">
         <table>
            <th width="15"> </th><th width="50">Lines</th><th w>Orders</th><th >Ship Method</th><th>SLA</th>


       <s:iterator value="details">
           <tr>
                <td>
      <input dojoType="dijit.form.RadioButton" type="radio"  name="printList<s:property value="clientId"/>" value="<s:property value="lines"/>v5v<s:property value="method"/>v5v<s:property value="sla"/>"/>
                </td>
            <td><s:property value="lines"/>:&nbsp</td>
            <td><s:property value="orders"/></td><td>  <s:property value="method"/></td>
               <td><s:property value="sla"/></td>
                </tr>

        </s:iterator>
               </table>
         </div>
    <!-- Hiding all print buttons to transition to Better printing ability-->
     <!--  <select id="<s:property value="clientId"/>_detailsPrinter" dojoType="dijit.form.ComboBox"  autoComplete="false">
       <s:iterator value="singlePrinters.entrySet()"><option value="<s:property value="key"/>"><s:property value="value"/></option></s:iterator>
       </select>
        <button dojoType="dijit.form.Button" onclick="printDetails(<s:property value="clientId"/>,'printList<s:property value="clientId"/>');">Print Selected</button>
 <s:if test="hasGroup">
  <button dojoType="dijit.form.Button" onclick="printGroup(<s:property value="clientId"/>);">Print Personalized</button>
     </s:if> -->
 </body>

 </html>       



   