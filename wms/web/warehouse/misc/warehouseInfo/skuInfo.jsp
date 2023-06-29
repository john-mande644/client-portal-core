<%@ taglib prefix="s" uri="/struts-tags" %>
<html>


<head>

</head>
<body>
<h1>Please select the Facility and Client to get info for</h1>
<s:form action="skuLoadInfo">
    <s:select list="locations" label="Facility" name="facility"/>
    <s:select list="clients" name="clientId" listKey="value" listValue="key" label="Client"/>
    <s:select list="groupNames" name="groupName" label="Group Name"/>
    <s:textfield name="caseQty" label="Case qty Calculation"/>
    <s:textfield name="palletQty" label="Pallet qty Calculation"/>
    <s:submit/>
</s:form>