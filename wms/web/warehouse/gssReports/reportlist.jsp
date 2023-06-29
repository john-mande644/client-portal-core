<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
</head>
<body>
Reports for Dispatch Id <s:property value="dispatchId"/> created on <s:property value="date"/>

<table>


<s:iterator value="ReportList">
        <tr>
            <td width="170px">
        <a href="Http://<%= request.getServerName() %>/GssReports/<s:property value="dispatchId"/>/<s:property/>">

            <s:property/>
        </a>


            </td>

        </tr>


</s:iterator>
   </table>
</body>

