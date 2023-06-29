<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
    <style type="text/css">
        table,th,td{
            border: 1px solid black;

        }
        table{
            border-collapse:collapse;

        }
        .cycleSheet, .cycleSheet tr{
            border:2px solid;
        }
        .cycleSheet tr{
            padding:1px;
        }
        td{
            padding:3px;
            vertical-align: top;
        }

        .true{
            background-color:#d3d3d3;
        }
        .false{

        }
        .location{
            width:180px;
        }
        .inventoryId{

        }
        .inventoryNum{

        }
        .qty{

        }
        .supplier{

        }
        .count{

        }
        .desc{

        }
    </style>
</head>
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
<table width="670px;" class="cycleSheet">
    <th>
        Inv Id
    </th>
    <th>
        Inv Num

    </th>
    <th>
        Desc
    </th>
    <th>
        Units
    </th>
    <th>
        Orders
    </th>
    <th>
        Pallets
    </th>
    <th>
        Cases
    </th>

    <s:iterator value="skuInfo" status="status">
        <tr class="<s:property value='#status.odd'/>">
            <td class="inventoryId"><s:property value="inventoryId"/> </td>
            <td class="inventoryNum"><s:property value="inventoryNum"/> </td>
            <td class="supplier"><s:property value="description"/> </td>
            <td class="desc"><s:property value="units"/> </td>
            <td class="qty"><s:property value="orders"/> </td>
            <td class="location"><s:property value="pallets"/> </td>
            <td class="cases"><s:property value="cases"/> </td>



        </tr>

    </s:iterator>

</table>