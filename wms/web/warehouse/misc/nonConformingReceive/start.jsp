<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: danny
  Date: 11/4/2016
  Time: 9:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<s:form action="enterForm" method="post" enctype="multipart/form-data">
  <s:file name="upload" label="File"/>
  <s:submit/>
</s:form>
</body>
</html>
