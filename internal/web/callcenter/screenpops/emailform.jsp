<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<body>
  <s:actionmessage/>
  <s:actionerror/>
<s:form action="email">
 Please enter in your problem and your email address.
    <s:hidden name="send" value="yes"/>
     <s:textfield name="eaddress" label="Your E-mail:"/>
     <s:textarea name="body" rows="30" cols="100" label="Info:">

    </s:textarea>
    <s:submit value="Send Email"/>
</s:form>


</body>
</html>