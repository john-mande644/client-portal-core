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
        .lots{
            background-color: ghostwhite;
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
<s:property value="data.client"/>
 Loaded on <s:property value="data.date"/>
<table width="670px;" class="cycleSheet">
    <th>
        Inv Id
    </th>
    <th>
        Inv Num

    </th>
    <th>
        UPC

    </th>
    <th>
        Supp lier/ Harm
    </th>
    <th>
        Desc
    </th>
    <th>
        On hand
    </th>
    <th>
        Location
    </th>
    <th>
        Count
    </th>

    <s:iterator value="data.inventory" status="status">
         <tr class="<s:property value='#status.odd'/>">
             <td class="inventoryId"><s:property value="value.id"/> </td>
             <td class="inventoryNum"><s:property value="value.invNumber"/></td>
             <td class="upc"><s:property value="value.upc"/></td>
             <td class="supplier"><s:property value="value.supplier"/> </td>
             <td class="desc"><s:property value="value.desc"/> </td>
             <td class="qty"><s:property value="value.qty"/> </td>
             <td class="location"><s:iterator value="value.location">
                 <s:property/><br>
             </s:iterator> </td>
             <td class="count">

                 <s:iterator value="value.location">
                     _______<br>
                 </s:iterator>
             </td>
         </tr>
        <s:if test="value.lots.size>0">
            <tr class="lots">
                <td colspan="3">

                </td>
                <td colspan="1">
                    Lots: <s:property value="value.invNumber"/>
                </td>
                <td colspan="3">
                    <s:iterator value="value.lots">
                        <s:property value="value"/>:&nbsp; <s:property value="key"/> &nbsp; _______<br/>
                    </s:iterator>
                </td>
            </tr>

        </s:if>

    </s:iterator>

</table>