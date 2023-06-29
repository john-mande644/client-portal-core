<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <s:head theme="xhtml" debug="false"/>
    <title>Order Tracking</title>
    <link rel="stylesheet" href="<s:url value="/styles/trace.css" />" type="text/css" media="screen" charset="utf-8">
</head>
<body>
<a href="http://biotanicalhealth.3dcartstores.com/"><img src="<s:url value="/images/biotanical/LOGO_Biotanical.gif" />" alt="" border="0"/></a>
<p><strong>Please enter your email address:</strong></p>
<s:actionerror/>
<s:form action="traceemail" method="POST" validate="true" namespace="/biotanical">
    <s:textfield name="emailInput"
                 value="%{emailInput}"
                 label="Email Address"
                 size="30"/>
    <s:submit value="Track Order(s)"/>
</s:form>
</body>
</html>