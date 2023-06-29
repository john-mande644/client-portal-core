<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>

</head>
  <body>
  <div>
          You are currently set to print to <s:property value="printerName"/>
      <br>
      Select your printer to set it.
      <s:form action="savePrinter.action">
          <s:select list="printerMap.keySet()" name="printerName"/>
          <s:hidden name="pickerId"/>
          <s:submit/>

      </s:form>

      <a href="<s:url value="start.action"/>">Cancel</a>
  </div>

  </body>
