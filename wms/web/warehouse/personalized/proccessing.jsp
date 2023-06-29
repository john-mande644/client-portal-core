<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
    <title>Please wait</title>
    <meta http-equiv="refresh" content="5;url=<s:url includeParams="all" />"/>
  </head>
  <body>
    Please wait while we process the stickers. We have proccessed <s:property value="numberDone"/> of <s:property value="orderInfo.size()"/> far.
    <br>
    <hr>


    <s:iterator  value="orderInfo.values()">
        <s:property value="info"/><br>
    </s:iterator>
     <br><p></p>
    Click <a href="<s:url includeParams="all" />">HERE</a> if this page does not reload automatically.
  </body>
</html>
