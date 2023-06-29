<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Flat Rate Dim Factor</title>
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
            width:30%;
        }
        .row-code{
            width:30%;
        }
        .row-dim{
            width:10%;
        }
        .row-button{
            width:6%;
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
        .addMethod{
            padding:5px;
            border: #0F4962 4px solid;
            margin-bottom:30px;
        }
    </style>
</head>

<body>
<div class="error"><s:actionerror/></div>

    <div class="clientName"><s:property value="clientName"/> </div>


<div class="addMethod">
    <div class="info">
        Select Flat Rate Method and Ship Method Combo. Enter in size to start calculating dims after.
        <br>
        Example 12x12x12  entry should be 1728  - dim weight would be used once size hits 1729
    </div>
<s:form action="saveNewMethodDims.action">
    <s:hidden name="clientId"/>
    <s:select list="serviceListMap"  listKey="key" listValue="value" name="serviceLevelId" label="Flat Rate"/>
    <s:select list="shipMethodMap"  listKey="key" listValue="value" name="methodCode" label="Ship Method"/>
    <s:textfield name="thresholdUpdate" value="" label="Threshold"/>
    <s:submit value="Add"/>
</s:form>
</div>
<table class="lines"><thead><tr>
    <th class="row-1 row-levelName">Flat Rate Name</th><th class="row-2 row-code">Code</th><th class="row-3 row-dim">Threshold</th><th class="row-button">&nbsp;</th><th class="row-button">&nbsp;</th>
</tr>
</thead>
    <s:iterator value="flatRateMethodDimMethodList" status="status" >



        <s:if test="#status.odd == true">
            <tr class="oddrow">
        </s:if>
        <s:else>
            <tr class="evenrow">
        </s:else>
        <s:form action="updateMethodDims.action" theme="simple">

        <td>
        <s:hidden name="serviceMethodId" value="%{id}" theme="simple"/>
            <s:hidden name="clientId"/>

        <s:property value="flatRateMethod"/>
    </td>
        <td>
            <s:property value="shipMethod"/>
        </td>
        <td>
            <s:textfield name="thresholdUpdate" value="%{threshold}" theme="simple"/>
        </td>

            <td> <s:submit onclick="return confirm('Are you sure you want to edit Method Factors?')" value="Update"/>

        </s:form>



        </td>
        <td>
            <s:form action="removeMethodDims.action" theme="simple">
                <s:hidden name="serviceMethodId" value="%{id}" theme="simple"/>
                <s:hidden name="clientId" theme="simple"/>
                <s:submit onclick="return confirm('Are you sure you want to remove %{flatRateMethod} / %{shipMethod} entry?')" value="Delete" theme="simple"/>
            </s:form>
        </td>
    </tr>





    </s:iterator>
</table>










</body>
</html>