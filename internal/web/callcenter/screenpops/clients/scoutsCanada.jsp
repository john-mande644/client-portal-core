<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<body>
  <s:actionmessage/>
  <s:actionerror/>

<s:form action="scoutsCanadaSubmit">
 Please enter in the customers info.
    <s:hidden name="callid"/>
     <s:textfield name="name" label="Customer Name"/>
     <s:textfield name="phone" label="Phone Number"/>
    <s:textfield name="email" label="E-mail Address"/>
    <s:textfield name="zip" label="Postal Code"/>


     <s:textarea name="notes" rows="20" cols="50" label="Extra Info">

    </s:textarea>
    <s:submit value="Submit Info"/>
</s:form>


</body>
</html>