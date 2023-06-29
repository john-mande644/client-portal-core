<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
</head>
<body>
   <div class="changer">
    <s:form action="start">
        <s:select list="facilityList" name="facility" label="Facility"></s:select>
        <s:submit label="Change Facility"></s:submit>
    </s:form>
</div>
<table>
    <thead>
    <th>Report ID</th>
    <th>Created Date</th>
    </thead>

<s:iterator value="DispatchList.DispatchList">
        <tr>
            <td width="170px">
            <a href="<s:url action="load.action"/>?dispatchId=<s:property value='DispatchId'/>&date=<s:property value='CreatedDate'/>"> <s:property value="DispatchId"/></a>
            </td>
                <td width="170px">
                    <s:property value="CreatedDate"/>
                </td>
        </tr>


</s:iterator>
   </table>
</body>

