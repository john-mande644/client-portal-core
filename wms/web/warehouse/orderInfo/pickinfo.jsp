<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
 <head>
    <style type="text/css">
       .odd{background: #ccccff;
       }
      table{border:none;
      border-collapse:collapse}
        </style>
 </head>
<body >
<div class="errors">
     <s:actionerror/>
 </div>
<div class="pick">
   Picked By: <s:property value="pickbean.pickBy"/> <br>
   Number of Times Picked: <s:property value="pickbean.nubmerPicks"/><br>
   Start Pick Time: <s:property value="pickbean.startTime"/><br>
   End Pick Time <s:property value="pickbean.endTime"/> <br>
   Total Pick Time Minutes: <s:property value="pickbean.pickTime"/><br>
   Total Replenish Time Seconds <s:property value="pickbean.replenish"/><br>

    <div class="pickitems">
        <table>
           
            <thead>
                <th>
            Inventory Id
            </th>
            <th>
            Inventory Num
            </th>
            <th>
              First Scan Seconds:
            </th>
            <th>
                Last Item Scan Seconds:
            </th>
            <th>
                Units:
            </th>
            <th>
                Replenish Seconds:
            </th>
                </thead>

          <s:iterator value="pickbean.pickItems" status="stat">
              <tr <s:if test="#stat.odd == true"> class="odd"</s:if>>
                  <td width="100px"><s:property value="inventoryId"/></td>
                  <td width="175px"><s:property value="inventoryNum"/></td>
                  <td width="100px" align="center"><s:property value="firstScan"/></td>
   <td width="100px"align="center"><s:property value="lastScan"/></td>
    <td width="100px" align="center"><s:property value="unitsPicked"/></td>
   <td width="100px" align="center"><s:property value="replenishTime"/></td>
                  </tr>
    </s:iterator>
            </table>
    </div>
</div>
</body>
</html>