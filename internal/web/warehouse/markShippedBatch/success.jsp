<%@ taglib uri="/struts-tags" prefix="s" %>



Success<br>
We have processed <s:property value="processed"/><br>

Problem orders:<br>
<s:iterator value="errorOrderList">
    <s:property/>
    </s:iterator>

