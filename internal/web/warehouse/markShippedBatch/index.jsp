<%@ taglib uri="/struts-tags" prefix="s" %>

<s:actionerror/>

<s:form action="processBatch" theme="simple">
    <s:label name="clientId">Client</s:label><s:select list="clients" name="clientId"/><br>
    <s:label name="theListContains">Identifier:</s:label><s:select name="theListContains" list="containsList"/>


    <br>
    <s:label name="theAction">What Action:</s:label><s:select name="theAction" list="actionList"/><br/>
    <s:label name="groupName">Group Name(if using)</s:label><s:textfield name="groupName"/><br/>
    <s:label name="addToTracking">Tracking Text:</s:label> <s:textfield name="addToTracking"/><br/>
    Enter each order on one line<br>
    <s:textarea name="theList" cols="50" rows="20"/><br>
    <s:label name="email">Email Address For Report: </s:label><s:textfield name="email"/>
    <s:submit/>
    </s:form>