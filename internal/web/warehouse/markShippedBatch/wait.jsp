<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>wait</title>

    <meta http-equiv="refresh" content="1.5;url='<s:url  includeParams="none"/>'">
</head>
<body>

<p>your request is processing...</p>
We have processed <s:property value="processed"/> out of <s:property value="total"/>.
<br>
We have <s:property value="numLeft"/> left to process.
<br>
Last processed is <s:property value="lastProcessed"/>

</body>
</html>