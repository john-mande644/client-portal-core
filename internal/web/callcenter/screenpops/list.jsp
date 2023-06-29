<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<body>

OWD Call Center Screen Pop Configuration
<s:actionmessage/>
<s:form action="admin" theme="simple"><s:submit value="Create New Client Entry" theme="simple"/> </s:form><hr>

<s:form action="list" theme="simple">
    <s:hidden name="deploy" value="all" theme="simple"/>
    <s:submit value="Deploy All Pages" theme="simple"/>
</s:form>
<table border="1">
    <tr>
        <th>
            Client
        </th>
        <th>Campaign Name</th>
        <th>Action</th>
    </tr>
    <s:iterator value="pageList">
        <tr>

            <td>
                 <s:property value="clientName"/>

            </td>
            <td>
                <s:property value="campaingnName" />
            </td>
            <td>
                <s:form action="admin" theme="simple"><s:hidden name="id" theme="simple"/> <s:submit value="EDIT" theme="simple"/>
                <a href="http://internal.owd.com/screen/redirect.php?campaign=<s:property value="campaingnName"/>&agentId=tester&agentfname=tester" target="_blank">View Live Page</a>

                <a href="<s:url action="test"/>?id=<s:property value="id"/>" target="_blank">Test Screen Pop</a>
                </s:form>
               
            </td>
        </tr>


    </s:iterator>


</table>



</body>
</html>