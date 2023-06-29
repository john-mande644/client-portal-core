<%@ taglib prefix="s" uri="/struts-tags" %>
  <head>
      <style type="text/css">
          .barcode{
              width:140px;

          }
          .id{

          }
          .num{

          }
          table{

          }
          td{
              border-bottom: 2px solid #000000;
              padding-bottom:11px;
          }
      </style>
  </head>
<s:form action="selectClient.action">

     <s:select list="clientList" listKey="action" listValue="display" name="client"  headerKey="" headerValue = "Select Client" theme="simple"/>
       <s:submit theme="simple"/>
</s:form>

<div class="items">
     <s:form action="saveChanges.action">
         <table>
            <tr>
                <th></th>
                <th>ID</th>
                <th>SKU</th>
                <th>Description</th>
                <th>No Scan</th>
            </tr>
             <s:iterator value="inventory" status="key">
                    <tr>
                        <td class="barcode">
                            <img src="http://internal.owd.com:8080/internal/bbq?data=<s:property value="inventoryId"/>&width=0&type=Code39&height=15"/>
                        </td>
                        <td class="id">
                            <s:property value="inventoryId"/>
                            <s:hidden name="inventory[%{#key.index}].inventoryId" value="%{inventoryId}"/>
                        </td>
                        <td class="num">
                            <s:property value="inventoryNum"/>
                        </td>
                        <td>
                            <s:property value="description"/>
                        </td>
                        <td>
                            <s:checkbox name="inventory[%{#key.index}].noScan" value="noScan"  theme="simple"/>

                        </td>
                    </tr>

                     </s:iterator>


         </table>

            <br>

            <s:submit/>

     </s:form>
</div>