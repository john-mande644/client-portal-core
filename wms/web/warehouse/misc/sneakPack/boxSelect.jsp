<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
  <style type="text/css">
      *[id^='selectBox_']{
          font-size: 40px;
      }
  </style>
</head>
<body>
<s:set name="theme" value="'simple'" scope="page" />
<a href="/wms/miscMenu/start.action" class="button">MENU</a> <P class="button>">
    <s:if test="hasActionErrors()">
<div id="errorDiv" style="padding-left: 10px; margin-bottom: 5px">
     <span class="error">
     <s:iterator value="actionErrors">
         <span class="errorMessage"><s:property escape="false" /></span>
     </s:iterator>
     </span>
</div>
</s:if>
<s:actionmessage/>



Select Box for ship method <s:property value="shipMethod"/>

<table>
    <tr>
      <s:iterator value="boxes" status="rowstatus">

                <td>
          <s:form action="selectBox.action">
              <s:hidden name="boxId" value="%{id}"/>
              <s:hidden name="pickerId"/>
              <s:submit value="%{name}"></s:submit>

          </s:form>
          </td>
          <s:if test="%{#rowstatus.modulus(3)==0}">
    <tr></tr>
    </s:if>
      </s:iterator>


    </tr>



</table>

</body>
</html>
