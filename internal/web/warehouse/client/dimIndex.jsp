<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Ship Method Thresholds</title>
    <style>
        .label{
            font-weight:bold;
        }
        .cancel{
            float:right;
        }

        .error{
            color:red;
        }
        .lines{
            width:75%;
            border-collapse: collapse;
        }
        .lines td{
            text-align:left;
            padding:5px;
        }
        .row-levelName{
            width:40%;
        }
        .row-code{
            width:40%;
        }
        .row-dim{
            width:10%;
        }

        .oddrow {
            background-color: lightgrey;
        }
        .evenrow{

        }
        .clientName{
            font-size: 25px;
        }
        .info{

            background-color: lightblue;
            font-size:16px;
            padding:15px;

        }
    </style>
</head>

<body>
<div class="error"><s:actionerror/></div>

<s:form action="saveDims.action" theme="simple">
    <s:hidden name="clientId" value="%{clientId}" theme="simple"/>

    <s:url action="loadClientMethod.action" var="urlTag" >
        <s:param name="clientId" value="%{clientId}"></s:param>
    </s:url>

    <div class="clientName"><s:property value="clientName"/> &nbsp;&nbsp;&nbsp;&nbsp; <a href="<s:property value="#urlTag" />" target="_new">Edit Ship Method Thresholds</a></div>
    <div class="info">Update Dim factor as needed. Enter 0 if they don't have a custom factor for that method.</div>

<table class="lines"><thead><tr>
    <th class="row-1 row-levelName">Flat Rate Name</th><th class="row-2 row-code">Code</th><th class="row-3 row-dim">Dim Factor</th>
</tr>
</thead>

    <s:iterator value="flatRateDimBeanList" status="status" >
        <s:if test="#status.odd == true">
            <tr class="oddrow">
        </s:if>
        <s:else>
            <tr class="evenrow">
        </s:else>


        <td>
        <s:hidden name="flatRateDimBeanList[%{#status.index}].dimId" value="%{dimId}" theme="simple"/>
        <s:hidden name="flatRateDimBeanList[%{#status.index}].clientFkey" value="%{clientFkey}" theme="simple"/>
        <s:hidden name="flatRateDimBeanList[%{#status.index}].shipServiceId" value="%{shipServiceId}" theme="simple"/>
        <s:property value="levelName"/>
    </td>
        <td>
            <s:property value="levelCode"/>
        </td>
        <td>
            <s:textfield name="flatRateDimBeanList[%{#status.index}].dim" theme="simple" size="5" value="%{dim}"/>
        </td>
    </tr>




    </s:iterator>
</table>
    <s:submit onclick="return confirm('Are you sure you want to edit Dim Factors?')"/>




</s:form>


</body>
</html>