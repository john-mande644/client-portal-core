<%@ taglib prefix="s" uri="/struts-tags" %>

<html>

 <font color="red"> <s:actionerror /></font>
 These are the orders that were processed.
 <hr>
 <s:iterator  value="orderInfo.values()">
        <s:property value="info"/><br>
    </s:iterator>
</html>

